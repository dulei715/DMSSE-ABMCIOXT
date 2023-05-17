package cn.edu.hun.pisces.ospir_oxt.role;

import cn.edu.hun.pisces.basestruct.Keyword;
import cn.edu.hun.pisces.ospir_oxt.basestruct.AttributeKeyword;
import cn.edu.hun.pisces.ospir_oxt.basestruct.AttributeKeywordComparator;
import cn.edu.hun.pisces.ospir_oxt.basestruct.BXToken;
import cn.edu.hun.pisces.utils.BigIntegerUtil;
import cn.edu.hun.pisces.utils.Constant;
import cn.edu.hun.pisces.utils.TreeSetUtil;
import cn.edu.hun.pisces.utils.cryptography.BilinearUtil;
import cn.edu.hun.pisces.utils.cryptography.CryptoFunciton;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;

import java.math.BigInteger;
import java.util.*;

/**
 * @author: Leilei Du
 * @Date: 2018/7/22 9:56
 */
public class Client {

    protected BigInteger clientID = null;

    /**
     *  随机数发生器
     */
    protected Random random = null;
    protected int securityParameter = Constant.DEFAULT_SECURITY_PARAMETER;

    protected BilinearUtil bilinearUtil = new BilinearUtil();
    private Field gField = bilinearUtil.getgField();
    private Field zField = bilinearUtil.getzField();

    /**
     * 密钥
     */
    protected byte[] ke = null;
    protected byte[] kz = null;

//    /**
//     *  群G的阶，生成元，阶的欧拉函数值，剔除集合
//     */
//    protected BigInteger primeP = Constant.DEFAULT_BIG_PRIME;
//    protected BigInteger generateG = Constant.DEFAULT_GENERATE_ELEMENT;
//    protected BigInteger eulerPrimeP = Constant.DEFAULT_BIG_EULER_PRIME;
//    protected Set<BigInteger> excludedElementSet = new HashSet<>();


    /**
     * 构造代码块。初始化随机数发生器，群G中的剔除元素
     */
    {
        random = new Random();
//        excludedElementSet.add(BigInteger.ZERO);
//        excludedElementSet.add(BigInteger.ONE);
    }

    public Client(BigInteger clientID) {
        this.clientID = clientID;
    }

    public BigInteger getClientID() {
        return clientID;
    }

    public void setClientID(BigInteger clientID) {
        this.clientID = clientID;
    }

    /**
     *  Client 有随机数 rBlind = (r1, r2,..., rm) 用来生成 aBlind = (a1, a2,..., am)
     */
    protected List<Element> rBlind = null;


    /**
     *  关键字的有序列表，根据keyword关联的文件数目升序排序，用来构建sterm和xtermList
     */
    protected TreeSet<AttributeKeyword> orderedAttributeKeywordSet = null;

    /**
     *  待查询的sterm关键字
     */
    protected AttributeKeyword sterm = null;

    /**
     *  待查询的xterm关键字列表
     */
    protected List<AttributeKeyword> xtermList = null;

    public AttributeKeyword getSterm() {
        return sterm;
    }

    public List<AttributeKeyword> getXtermList() {
        return xtermList;
    }

    public TreeSet<AttributeKeyword> getOrderedAttributeKeywordSet() {
        return orderedAttributeKeywordSet;
    }

    public void setSterm(AttributeKeyword sterm) {
        this.sterm = sterm;
    }

    public void setXtermList(List<AttributeKeyword> xtermList) {
        this.xtermList = xtermList;
    }

    public void setOrderedAttributeKeywordSet(TreeSet<? extends Keyword> orderedAttributeKeywordSet) {
        this.orderedAttributeKeywordSet = (TreeSet<AttributeKeyword>) orderedAttributeKeywordSet;
    }

//    /**
//     * 最后设置的关键字sterm会包含真的fileNumber, 而xtermList中的各个元素的fileNumber
//     * 和keywordSet中的保持一致
//     * @param keywordSet 该用户可以查询的关键字
//     */
//    public void setStemAndXtem(Set<AttributeKeyword> keywordSet){
//        Set<AttributeKeyword> tempSet = new HashSet<>(keywordSet);
//        this.sterm = TreeSetUtil.getFirstAppearElement(this.orderedAttributeKeywordSet, keywordSet, new AttributeKeywordComparator());
//        tempSet.remove(this.sterm);
//        this.xtermList = new ArrayList<>(tempSet);
//    }
    public void setStermAndXterm(List<AttributeKeyword> keywordList){
        //默认第一个是sterm
        Iterator<AttributeKeyword> iterator = keywordList.iterator();
        this.sterm = iterator.next();
        this.xtermList = new ArrayList<>();
        while (iterator.hasNext()){
            this.xtermList.add(iterator.next());
        }
    }


    /*************************************************************************
     *
     * GenToken protocol
     * Client part 1
     *
     */

    /**
     *  获取rBlind
     */
    protected List<Element> generateRBlind(){
        this.rBlind = new ArrayList<>();
        int size = this.xtermList.size() + 1;
        Element r = null;
        for (int i = 0; i < size; i++) {
            r = CryptoFunciton.getNonZeroRandomElement(zField);
            rBlind.add(r);
        }
        return this.rBlind;
    }

    /**
     *  生成aBlind
     */
    public List<Element> getABlind(){
        // 在发送给 data owner 之前， 生成 rBlind 来加密keyword
        this.generateRBlind();
        List<Element> aBlind = new ArrayList<>();
//        BigInteger hashValue = CryptoFunciton.funHash(this.sterm.getValue(), this.primeP, this.excludedElementSet);
        Element hashValue = CryptoFunciton.getHashValueFromGroupField(this.sterm.getValue(), this.gField);

        Element a = hashValue.powZn(this.rBlind.get(0)).getImmutable();
        aBlind.add(a);
        for (int i = 0; i < this.xtermList.size(); ++i) {
//            hashValue = CryptoFunciton.funHash(xtermList.get(i).getValue(), primeP, excludedElementSet);
            hashValue = CryptoFunciton.getHashValueFromGroupField(xtermList.get(i).getValue(), gField);
//            a = hashValue.modPow(this.rBlind.get(i+1), this.primeP);
            a = hashValue.powZn(this.rBlind.get(i + 1)).getImmutable();
            aBlind.add(a);
        }
        return aBlind;
    }

    /**
     *  生成认证av
     */
    public List<String> getAttributeVector(){
        List<String> attributeVector = new ArrayList<>();
        String attributeValue = sterm.getAttribute();
        attributeVector.add(attributeValue);
        for (AttributeKeyword attributeKeyword : xtermList) {
            attributeValue = attributeKeyword.getAttribute();
            attributeVector.add(attributeValue);
        }
        return attributeVector;
    }

    /*************************************************************************
     *
     *  GenToken protocol
     *  Client part 2
     *
     */

    /**
     *  查询关键字生成的临时变量
     */
    protected Element strap = null;
    protected Element bstag = null;
    protected List<Element> bxtrapList = null;

    /**
     *  计算 strap
     * @param tempStrap
     * @return
     */
    public Element getStrap(Element tempStrap){
//        BigInteger tmpR1 = this.rBlind.get(0).modInverse(this.eulerPrimeP);
        Element tmpR1 = this.rBlind.get(0).invert().getImmutable();
//        this.strap =  tempStrap.modPow(tmpR1, this.primeP);
        this.strap = tempStrap.powZn(tmpR1).getImmutable();
        return this.strap;
    }

    /**
     *  计算 bstag
     * @param tempBstag
     * @return
     */
    public Element getBstag(Element tempBstag){
//        BigInteger tmpR1 = this.rBlind.get(0).modInverse(this.eulerPrimeP);
        Element tmpR1 = this.rBlind.get(0).invert().getImmutable();
//        this.bstag =  tempBstag.modPow(tmpR1, this.primeP);
        this.bstag = tempBstag.powZn(tmpR1).getImmutable();
        return this.bstag;
    }

    /**
     *  计算 bxtrap
     * @param tempBxtrapList
     * @return
     */
    public List<Element> getBxtrap(List<Element> tempBxtrapList){
        Element tmpR = null;
        Element bxtrap = null;
        this.bxtrapList = new ArrayList<>();
        for (int i = 0; i < tempBxtrapList.size(); i++) {
//            tmpR = this.rBlind.get(i+1).modInverse(this.eulerPrimeP);
            tmpR = this.rBlind.get(i+1).invert().getImmutable();
//            bxtrap = tempBxtrapList.get(i).modPow(tmpR, this.primeP);
            bxtrap = tempBxtrapList.get(i).powZn(tmpR).getImmutable();
            bxtrapList.add(bxtrap);
        }
        return this.bxtrapList;
    }

    /*
     *  测试提醒：
     *      1. 需要初始化 sterm 和 xtermList。 rBlind 不用初始化(在aBlind里会初始化)。
     *      2. 需要和 data owner 交互，因此 data owner 也需要初始化。
     *          (1) data owner 需要初始化 attributeList 和 clientList
     *          (2) data owner 需要初始化 policy
     *          (3) data owner 需要初始化 pBlind, 再调用生成生成TempBstag和TempBxtrap方法(Tempstrap可在其之前调用)
     *
     */


    /****************************************************************************
     *  Search protocol
     */

    /**
     *  生成查询的bxtoken
     * @return
     */
    public BXToken<Element> generateBXToken(int c){
        List<Element> list = new ArrayList<>();
//        this.kz = CryptoFunciton.functao(this.strap, BigInteger.ONE, securityParameter);
        this.kz = CryptoFunciton.functao(this.strap, 1, securityParameter);
//        this.ke = CryptoFunciton.functao(this.strap, new BigInteger("2"), securityParameter);
        this.ke = CryptoFunciton.functao(this.strap, 2, securityParameter);
//        BigInteger zc = CryptoFunciton.funcp(this.kz, String.valueOf(c), this.eulerPrimeP, this.eulerPrimeP);
        Element zc = CryptoFunciton.getHashValueFromZField(this.kz, String.valueOf(c), this.zField);
        for (int i = 0; i < this.bxtrapList.size(); i++) {
            Element bxtrap = this.bxtrapList.get(i);
//            BigInteger bxtokenElem = bxtrap.modPow(zc, this.primeP);
            Element bxtokenElem = bxtrap.powZn(zc).getImmutable();
            list.add(bxtokenElem);
        }
        BXToken bxtoken = new BXToken(list);
        return bxtoken;
    }

    /**
     *  for test
     *  用户实际上不知道指定的bxtoken list有多少个， 这里为了方便测试，给定 num 数量
     * @param num
     * @return
     */
    public List<BXToken> generateBXTokenList(int num){
        List<BXToken> list = new ArrayList<>();
        BXToken bxtoken = null;
        for (int i = 1; i <= num; i++) {
            bxtoken = this.generateBXToken(i);
            list.add(bxtoken);
        }
        return list;
    }


    public List<String> decryptE(List<String> elemList){
        List<String> list = new ArrayList<>();
        for (String elem : elemList) {
            String indAndRdk = CryptoFunciton.decrypt(this.ke, elem);
            list.add(indAndRdk);
        }
        return list;
    }










}