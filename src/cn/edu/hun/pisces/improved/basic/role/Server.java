package cn.edu.hun.pisces.improved.basic.role;

import cn.edu.hun.pisces.basestruct.TSetElement;
import cn.edu.hun.pisces.related.basestruct.TSet;
import cn.edu.hun.pisces.related.basestruct.XSet;
import cn.edu.hun.pisces.related.basestruct.XToken;
import cn.edu.hun.pisces.related.basestruct.stag.Stag;
import cn.edu.hun.pisces.related.basestruct.stag.StringStag;
import cn.edu.hun.pisces.utils.cryptography.BilinearUtil;
import it.unisa.dia.gas.jpbc.Element;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author: Leilei Du
 * @Date: 2018/7/16 9:58
 */
public class Server {
    private TSet tset = null;
    private XSet<String> xset = null;

    private BilinearUtil bilinearUtil = new BilinearUtil();

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
     * for test, public; for reality private
     * @param stoken
     * @param publicKey
     * @return 查询的stag对应的元素列表
     */
    public List<TSetElement> tSetRetrieve(Element stoken, Element publicKey){
        Element stagElem = this.bilinearUtil.pair(stoken, publicKey);
        Stag<String> stag = new StringStag(stagElem.toString());
        return tset.getElement(stag);
    }

    public List<TSetElement> tSetRetrieve(Element stoken, Element publicKey, int num){
        Element stagElem = this.bilinearUtil.pair(stoken, publicKey);
        Stag<String> stag = new StringStag(stagElem.toString());
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
     * @param xToken
     * @param y 第c个元素 (e,y) 的 y 值
     * @return
     */
    private boolean judgeElement(XToken<Element> xToken, Element y){
        List<Element> list = xToken.getXtokenElementList();
        String elem = null;
        for (Element xtokenElem : list) {
//            elem = xtokenElem.modPow(y, this.primeP);
            elem = this.bilinearUtil.pair(y, xtokenElem).toString();
            if (!xset.contains(elem)){
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @param elemList
     * @param xTokenList
     * @return
     */
    @SuppressWarnings("Duplicates")
    public List<String> searchXTokenListPart(List<TSetElement> elemList, List<XToken> xTokenList){
        List<String> eList = new ArrayList<>();
        if(xTokenList.size() == 0){
            for (TSetElement<String, Element> tSetElement : elemList) {
                eList.add(tSetElement.getE());
            }
            return eList;
        }
        Iterator<XToken> xTokenIterator = xTokenList.iterator();
        for (TSetElement<String, Element> tSetElement : elemList) {
            Element y =  tSetElement.getY();
            XToken<Element> xtoken = xTokenIterator.next();
            if(this.judgeElement(xtoken, y)){
                eList.add(tSetElement.getE());
            }
        }
        return eList;
    }

    //静态的 //暂不调用
    @SuppressWarnings("Duplicates")
    public List<String> search(Element stoken, List<XToken> xTokenList, Element publicKey){
        List<TSetElement> elementList = tSetRetrieve(stoken, publicKey);
        Iterator<XToken> xTokenIterator = xTokenList.iterator();
        List<String> eList = new ArrayList<>();
        for (TSetElement<String, Element> tSetElement : elementList) {
            Element y = tSetElement.getY();
            XToken<Element> xtoken = xTokenIterator.next();
            if(this.judgeElement(xtoken, y)){
                eList.add(tSetElement.getE());
            }
        }
        return eList;
    }

}