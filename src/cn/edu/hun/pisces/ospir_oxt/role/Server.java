package cn.edu.hun.pisces.ospir_oxt.role;

import cn.edu.hun.pisces.basestruct.TSetElement;
import cn.edu.hun.pisces.ospir_oxt.basestruct.BXToken;
import cn.edu.hun.pisces.related.basestruct.TSet;
import cn.edu.hun.pisces.related.basestruct.XSet;
import cn.edu.hun.pisces.related.basestruct.stag.Stag;
import cn.edu.hun.pisces.related.basestruct.stag.StringStag;
import cn.edu.hun.pisces.utils.Constant;
import cn.edu.hun.pisces.utils.cryptography.BilinearUtil;
import cn.edu.hun.pisces.utils.cryptography.CryptoFunciton;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author: Leilei Du
 * @Date: 2018/7/22 9:56
 */
@SuppressWarnings("ALL")
public class Server {
//    private BigInteger primeP = Constant.DEFAULT_BIG_PRIME;
//    private BigInteger generateG = Constant.DEFAULT_GENERATE_ELEMENT;
//    private BigInteger eulerPrimeP = Constant.DEFAULT_BIG_EULER_PRIME;
//    private Set<BigInteger> excludedElementSet = new HashSet<>();

    private BilinearUtil bilinearUtil = new BilinearUtil();
    private Field gField = bilinearUtil.getgField();
    private Field zField = bilinearUtil.getzField();

    private byte[] km = null;

    private TSet tset = null;
    private XSet<String> xset = null;

    public Server(TSet tset, XSet<String> xset) {
        this.tset = tset;
        this.xset = xset;
    }
    /**
     *  测试用
     */
    public Server() {
    }

    public void setTset(TSet tset) {
        this.tset = tset;
    }

    public void setXset(XSet xset) {
        this.xset = xset;
    }

    /**
     *  记录 dataowner 给 client 的 pBlind
     */
    private List<Element> pBlind = null;

    {
        km = Constant.DEFAULT_K_M;
    }

    /**
     *  验证 client 是否有权限
     * @param env
     * @return
     */
    public boolean checkRight(String[] env){
        return CryptoFunciton.authCheck(this.km, env);
    }

    /**
     * 解密出 pBlind 用于下面的查询
     * @param encrypt
     */
    public void decryptAndGetPBlind(String encrypt){
        String pBlindStr = CryptoFunciton.authDecrypt(this.km, encrypt);
        String[] pBlindStrs = pBlindStr.split(",");
        this.pBlind = new ArrayList<>();
        for (String blindStr : pBlindStrs) {
//            this.pBlind.add(new BigInteger(blindStr));
            this.pBlind.add(zField.newElement().set(Integer.valueOf(blindStr)).getImmutable());
        }
    }

    /**
     *  获取 stag
     * @param bstag
     * @return
     */
    public Stag getStag(Element bstag){
//        BigInteger p1Inverse = this.pBlind.get(0).modInverse(this.eulerPrimeP);
        Element p1Inverse = this.pBlind.get(0).invert().getImmutable();
//        BigInteger stagValue = bstag.modPow(p1Inverse, this.primeP);
        Element stagValue = bstag.powZn(p1Inverse).getImmutable();
//        Stag<BigInteger> stag = new BigIntegerStag(stagValue);
        Stag<String> stag = new StringStag(stagValue.toString());
        return stag;
    }

    /**
     *  根据 stag 查找 TSetElement
     * @param stag
     * @return
     */
    public List<TSetElement> tSetRetrieve(Stag<String> stag){
        return tset.getElement(stag);
    }

    public List<TSetElement> tSetRetrieve(Stag stag, int num){
        List<TSetElement> tsetElementList = tset.getElement(stag);
        if(num <= tsetElementList.size()){
            return tsetElementList.subList(0, num);
        }
        TSetElement tempElement = tsetElementList.get(0);
        for (int i = tsetElementList.size(); i < num; i++) {
            tsetElementList.add(tempElement);
        }
        return tsetElementList;
    }

    /**
     * 判断 xtoken[c,2], xtoken[c,3], ..., xtoken[c,n] 是否都在XSet中
     * 传入的第三个参数是pBlind, 使用是从第二个开始的
     * @param xToken
     * @param y 第c个元素 (e,y) 的 y 值
     */
    private boolean judgeElement(BXToken<Element> xToken, Element y){
        List<Element> list = xToken.getXtokenElementList();
        String elem = null;
        int i = 0;
        for (Element bxtokenElem : list) {
//            elem = xtokenElem.modPow(y, this.primeP);
//            elem = this.bilinearUtil.pair(xtokenElem, y).toString();
            ++i;
            Element p = this.pBlind.get(i);
//            BigInteger pInverse = p.modInverse(this.eulerPrimeP);
            Element pInverse = p.invert().getImmutable();
//            elem = bxtokenElem.modPow(y.multiply(pInverse), this.primeP);
            elem = bxtokenElem.powZn(y.mulZn(pInverse)).toString();

            if (!xset.contains(elem)){
                return false;
            }
        }
        return true;
    }


    public List<String> searchXTokenListPart(List<TSetElement> elemList, List<BXToken> bxTokenList){
        List<String> eList = new ArrayList<>();
        if(bxTokenList.size() == 0){
            for (TSetElement<String, Element> tSetElement : elemList) {
                eList.add(tSetElement.getE());
            }
            return eList;
        }
        Iterator<BXToken> bxTokenIterator = bxTokenList.iterator();

        for (TSetElement<String, Element> tSetElement : elemList) {

            Element y =  tSetElement.getY();
            BXToken<Element> bxtoken = bxTokenIterator.next();
            if(this.judgeElement(bxtoken, y)){
                eList.add(tSetElement.getE());
            }
        }
        return eList;
    }

    public List<String> search(Stag<String> stag, List<BXToken> bxTokenList){
        List<TSetElement> elementList = tSetRetrieve(stag);
        Iterator<BXToken> bxTokenIterator = bxTokenList.iterator();
        List<String> eList = new ArrayList<>();
        for (TSetElement<String, Element> tSetElement : elementList) {
            Element y = tSetElement.getY();
            BXToken<Element> bxtoken = bxTokenIterator.next();
            if(this.judgeElement(bxtoken, y)){
                eList.add(tSetElement.getE());
            }
        }
        return eList;
    }

}