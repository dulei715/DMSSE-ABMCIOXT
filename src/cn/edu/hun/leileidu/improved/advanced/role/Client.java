package cn.edu.hun.leileidu.improved.advanced.role;

import cn.edu.hun.leileidu.basestruct.Keyword;
import cn.edu.hun.leileidu.improved.advanced.basestruct.AdvXToken;
import cn.edu.hun.leileidu.related.basestruct.XToken;
import cn.edu.hun.leileidu.utils.Constant;
import cn.edu.hun.leileidu.utils.cryptography.BilinearUtil;
import cn.edu.hun.leileidu.utils.cryptography.CryptoFunciton;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;

import java.math.BigInteger;
import java.util.*;

/**
 * @author: Leilei Du
 * @Date: 2018/7/13 14:59
 */
public class Client {
    protected BilinearUtil bilinearUtil = new BilinearUtil();
//    protected BigInteger zFieldSize = null;
//    protected Element generateP = null;
//    protected Element generateG = null;

    private Field zField = bilinearUtil.getzField();



    protected Map<Keyword, byte[]> ks = null;
    protected Map<Keyword, byte[]> kx = null;
    protected byte[] ki = null;
    protected Map<Keyword, byte[]> kz = null;
    protected Map<Keyword, byte[]> kt = null;

    public Map<Keyword, byte[]> getKs() {
        return ks;
    }

    public Map<Keyword, byte[]> getKx() {
        return kx;
    }

    public Map<Keyword, byte[]> getKz() {
        return kz;
    }

    public Map<Keyword, byte[]> getKt() {
        return kt;
    }

    public byte[] getKi() {
        return ki;
    }

    public void setKs(Map<Keyword, byte[]> ks) {
        this.ks = ks;
    }

    public void setKx(Map<Keyword, byte[]> kx) {
        this.kx = kx;
    }

    public void setKi(byte[] ki) {
        this.ki = ki;
    }

    public void setKz(Map<Keyword, byte[]> kz) {
        this.kz = kz;
    }

    public void setKt(Map<Keyword, byte[]> kt) {
        this.kt = kt;
    }

    protected Element secretKey = null;
    protected Element publicKey = null;
    protected Element gr = null;

    public Element getGr() {
        return gr;
    }

    //由dataowner在生成数据库的时候给予的有序关键字列表(根据相关的文件数升序排序)，结合查询的关键字生成sterm和xtermList
    protected TreeSet<Keyword> orderedKeywordSet = null;
    protected Keyword sterm = null;
    //xterm列表，顺序和查询关键字数据相同，并不一定是按相关文件数降序排序
    protected List<Keyword> xtermList = null;



    public Element getPublicKey() {
        return publicKey;
    }

    public void setOrderedKeywordSet(TreeSet<Keyword> orderedKeywordSet) {
        this.orderedKeywordSet = orderedKeywordSet;
    }

    public TreeSet<Keyword> getOrderedKeywordSet() {
        return orderedKeywordSet;
    }

    public Keyword getSterm() {
        return sterm;
    }

    public List<Keyword> getXtermList() {
        return xtermList;
    }

//    /**
//     * 最后设置的关键字sterm会包含真的fileNumber, 而xtermList中的各个元素的fileNumber
//     * 和keywordSet中的保持一致
//     * @param keywordSet 该用户可以查询的关键字
//     */
//    public void setStemAndXtem(Set<Keyword> keywordSet){
//        Set<Keyword> tempSet = new HashSet<>(keywordSet);
//        this.sterm = TreeSetUtil.getFirstAppearElement(this.orderedKeywordSet, keywordSet,new KeywordComparator());
//        tempSet.remove(this.sterm);
//        this.xtermList = new ArrayList<>(tempSet);
//    }
    public void setStermAndXterm(List<Keyword> keywordList){
        Iterator<Keyword> iterator = keywordList.iterator();
        this.sterm = iterator.next();
        this.xtermList = new ArrayList<>();
        while(iterator.hasNext()){
            this.xtermList.add(iterator.next());
        }
    }


    public Client(Element secretKey, Element gr){
        this.secretKey = secretKey.getImmutable();
        this.publicKey = bilinearUtil.getBilinearG().powZn(this.secretKey).getImmutable();
        this.gr = gr;
    }

    public Client(BigInteger secretKeyBigInt, Element gr){
//        Element sKey = this.bilinearUtil.getGPowValue(secretKeyBigInt);
        Element sKey = this.bilinearUtil.getZrElement(secretKeyBigInt);
        this.secretKey = sKey;
        this.publicKey = bilinearUtil.getBilinearG().powZn(this.secretKey).getImmutable();
        this.gr = gr;
    }
    public Client(BigInteger secretKey, BigInteger r) {
        this.secretKey = this.bilinearUtil.getZrElement(secretKey);
        this.publicKey = bilinearUtil.getBilinearG().powZn(this.secretKey).getImmutable();
//        this.gr = this.bilinearUtil.getBilinearG().pow(r).getImmutable();
        this.gr = this.bilinearUtil.getGPowValue(r);
    }

    public Client() {
        this(Constant.DEFAULT_SECRET_KEY, BigInteger.ZERO);
    }

    public void setSterm(Keyword sterm) {
        this.sterm = sterm;
    }

    public void setXtermList(List<Keyword> xtermList) {
        this.xtermList = xtermList;
    }

    public void setGr(Element gr) {
        this.gr = gr;
    }

    public Element getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(Element secretKey) {
        this.secretKey = secretKey.getImmutable();
        this.publicKey = this.bilinearUtil.getGPowValue(secretKey);
    }

    //由data owner 给予
    public void setKeys(Map<Keyword, byte[]> ks, Map<Keyword, byte[]> kx, byte[] ki, Map<Keyword, byte[]> kz, Map<Keyword, byte[]> kt){
        this.ks = ks;
        this.kx = kx;
        this.ki = ki;
        this.kz = kz;
        this.kt = kt;
    }



    /**
     * 生成stag
     * @return
     */
    public Element getSToken(){
        byte[] subKt = this.kt.get(this.sterm);
//        BigInteger xtrapInteger = CryptoFunciton.funcp(subKt, this.sterm.getValue(), this.zFieldSize, null);
//        Element invertSecretKey = this.secretKey.pow(BigInteger.ONE.negate()).getImmutable();
//        Element stoken = this.bilinearUtil.getPPowValue(invertSecretKey.mul(xtrapInteger));
        Element xtrap = CryptoFunciton.getHashValueFromZField(subKt, this.sterm.getValue(), this.zField);
        Element invertSecretKey = this.secretKey.invert().getImmutable();
        Element stoken = this.bilinearUtil.getPPowValue(invertSecretKey.mulZn(xtrap));

//        return new StringStag(stagElem.toString());
        return stoken;
    }

    public AdvXToken generateXToken(int stermNum){

        //计算出所有用的xtrap(为了优化)
        List<Element> xtrapList = new ArrayList<>();
        for (Keyword keyword : this.xtermList){
            byte[] subKx = this.kx.get(keyword);
//            BigInteger xtrapBigInteger = CryptoFunciton.funcp(subKx, keyword.getValue(), this.zFieldSize, null);
//            Element xtrap = this.bilinearUtil.getZrElement(xtrapBigInteger);
            Element xtrap = CryptoFunciton.getHashValueFromZField(subKx, keyword.getValue(), this.zField);
            xtrapList.add(xtrap);
        }

        //投建left xtoken
        XToken<Element> leftXToken = this.getLeftXToken(xtrapList);

        //构建righ xtoken
        List<XToken> rightXTokenList = this.generateRightSearchXToken(xtrapList, stermNum);

        return new AdvXToken(leftXToken, rightXTokenList);
    }

    private XToken<Element> getRightXToken(List<Element> xtrapList, int count) {
        List<Element> list = new ArrayList<>();
        byte[] subKz = this.kz.get(this.sterm);
//        BigInteger zBigInteger = CryptoFunciton.funcp(subKz, this.sterm.getValue().concat(String.valueOf(count)), this.zFieldSize, null);
//        Element z = this.bilinearUtil.getZrElement(zBigInteger);
        Element z = CryptoFunciton.getHashValueFromZField(subKz, this.sterm.getValue().concat(String.valueOf(count)), this.zField);

        Element rightXtokenElem = null;
        for (Element xtrap : xtrapList) {
            rightXtokenElem = this.bilinearUtil.getPPowValue(xtrap.mulZn(z));
            list.add(rightXtokenElem);
        }
        return new XToken(list);
    }


    private XToken<Element> getLeftXToken(List<Element> xtrapList){
        List<Element> leftXTokenList = new ArrayList<>();
        for (Element xtrap : xtrapList) {
            Element leftXTokenElem = this.gr.powZn(xtrap.mulZn(this.secretKey)).getImmutable();
            leftXTokenList.add(leftXTokenElem);
        }
        XToken<Element> leftXToken = new XToken<>(leftXTokenList);
        return leftXToken;
    }

    @Deprecated
    protected XToken<Element> getRightXToken(int count) {
        List<Element> list = new ArrayList<>();
        byte[] subKz = this.kz.get(this.sterm);
//        BigInteger zBigInteger = CryptoFunciton.funcp(subKz, this.sterm.getValue().concat(String.valueOf(count)), this.zFieldSize, null);
//        Element z = this.bilinearUtil.getZrElement(zBigInteger);
        Element z = CryptoFunciton.getHashValueFromZField(subKz, this.sterm.getValue().concat(String.valueOf(count)), this.zField);

        Element rightXtokenElem = null;
        for (Keyword keyword : this.xtermList) {
            byte[] subKx = this.kx.get(keyword);
            //TODO 此处可以优化，直接传xtrap进来，不必每个子xtoken都计算一次xtrap
//            BigInteger xtrapBigInteger = CryptoFunciton.funcp(subKx, keyword.getValue(), this.zFieldSize, null);
//            Element xtrap = this.bilinearUtil.getZrElement(xtrapBigInteger);
            Element xtrap = CryptoFunciton.getHashValueFromZField(subKx, keyword.getValue(), this.zField);

//            xtokenElem = this.generateElementG.modPow(z.multiply(xtrap), this.primeP);
//            rightXtokenElem = this.gr.powZn(z.mul(xtrap).mul(secretKey));
            rightXtokenElem = this.bilinearUtil.getPPowValue(xtrap.mulZn(z));
            list.add(rightXtokenElem);
        }
        return new XToken(list);
    }

    @Deprecated
    protected XToken<Element> getLeftXToken(){
        List<Element> leftXTokenList = new ArrayList<>();
        for (Keyword keyword : this.xtermList) {
            byte[] subKx = this.kx.get(keyword);
            if(subKx == null){
                throw new RuntimeException("There is no secrete key for keyword: " + keyword);
            }
            //TODO 此处可以优化，直接传xtrap进来，不必每个子xtoken都计算一次xtrap
//            BigInteger xtrapBigInteger = CryptoFunciton.funcp(subKx, keyword.getValue(), this.zFieldSize, null);
//            Element xtrap = this.bilinearUtil.getZrElement(xtrapBigInteger);
            Element xtrap = CryptoFunciton.getHashValueFromZField(subKx, keyword.getValue(), this.zField);

            Element leftXTokenElem = this.gr.powZn(xtrap.mulZn(this.secretKey)).getImmutable();
            leftXTokenList.add(leftXTokenElem);
        }
        XToken<Element> leftXToken = new XToken<>(leftXTokenList);
        return leftXToken;
    }

    //TODO:静态的 //这里假设用户一次性知道sterm个数，在stermNum中
    private List<XToken> generateRightSearchXToken(List<Element> xtrapList, int stermNum){
        List<XToken> xTokenList = new ArrayList<>();
        for (int i = 1; i <= stermNum; i++) {
            XToken<Element> xToken = getRightXToken(xtrapList, i);
            xTokenList.add(xToken);
        }
        return xTokenList;
    }

    @Deprecated
    protected List<XToken> generateRightSearchXToken(int stermNum){
        List<XToken> xTokenList = new ArrayList<>();
        for (int i = 1; i <= stermNum; i++) {
            XToken<Element> xToken = getRightXToken(i);
            xTokenList.add(xToken);
        }
        return xTokenList;
    }

    //静态的
    public List<String> decryptE(List<String> elemList){
        byte[] subKs = this.ks.get(this.sterm);
        byte[] ke = CryptoFunciton.func(subKs, this.sterm.getValue());
        List<String> list = new ArrayList<>();
        for (String elem : elemList) {
            String ind = CryptoFunciton.decrypt(ke, elem);
            list.add(ind);
        }
        return list;
    }

    /*
        下面是针对Client类的基本方法
     */

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Client that = (Client) obj;

        return this.publicKey != null ? this.publicKey.equals(that.publicKey) : that.publicKey == null;
    }

    @Override
    public int hashCode() {
        return this.publicKey != null ? this.publicKey.toString().hashCode() : 0;
    }

}