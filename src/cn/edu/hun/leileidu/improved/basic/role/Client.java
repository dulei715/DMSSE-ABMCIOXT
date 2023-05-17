package cn.edu.hun.leileidu.improved.basic.role;

import cn.edu.hun.leileidu.basestruct.Keyword;
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
 * @Date: 2018/7/13 15:44
 */
public class Client {

    //用来提供相应的群和双线性对
    protected BilinearUtil bilinearUtil = new BilinearUtil();
    private Field zField = bilinearUtil.getzField();
//    protected BigInteger zFieldSize = null;
    protected Element generateP = null;
    protected Element generateG = null;

    {
        // 此处由于Z的阶是素数，因而其大小也就是它的阶数
//        this.zFieldSize = bilinearUtil.getZFieldOrder();
        this.generateP = bilinearUtil.getBilinearP();
        this.generateG = bilinearUtil.getBilinearG();
    }


    //client 的私钥 // 默认值是为了测试
    protected Element secretKey = null;
    //client 的公钥
    protected Element publicKey = null;
    //data owner 分配给 client 的 g^r
    protected Element gr = null;

    //由dataowner在生成数据库的时候给予的有序关键字列表(根据相关的文件数升序排序)，结合查询的关键字生成sterm和xtermList
    protected TreeSet<Keyword> orderedKeywordSet = null;
    protected Keyword sterm = null;
    //xterm列表，顺序和查询关键字数据相同，并不一定是按相关文件数降序排序
    protected List<Keyword> xtermList = null;


    protected byte[] ks = Constant.DEFAULT_K_S;
    protected byte[] kx = Constant.DEFAULT_K_X;
    protected byte[] ki = Constant.DEFAULT_K_I;
    protected byte[] kz = Constant.DEFAULT_K_Z;
    protected byte[] kt = Constant.DEFAULT_K_T;




    public Client(BigInteger secretKey, Element gr) {
        this.secretKey = this.bilinearUtil.getZrElement(secretKey);
        this.publicKey = bilinearUtil.getBilinearG().powZn(this.secretKey).getImmutable();
        this.gr = gr;
    }

    // for test 该构造函数是为了测试, 第二个参数是r(实际上这个r不能被client知道)
    public Client(BigInteger secretKey, BigInteger r) {
        this.secretKey = this.bilinearUtil.getZrElement(secretKey);
        this.publicKey = bilinearUtil.getBilinearG().powZn(this.secretKey).getImmutable();
//        this.gr = this.bilinearUtil.getZrElement(r);
        this.gr = this.bilinearUtil.getGPowValue(r);
    }

    public Client(Element secretKey, Element gr){
        this.secretKey = secretKey.getImmutable();
        this.publicKey = bilinearUtil.getBilinearG().powZn(this.secretKey).getImmutable();
        this.gr = gr;
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

    public Element getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(Element secretKey) {
        this.secretKey = secretKey.getImmutable();
//        this.publicKey = bilinearUtil.getBilinearG().powZn(this.secretKey).getImmutable();
        this.publicKey = bilinearUtil.getGPowValue(this.secretKey);
    }

    public Element getPublicKey() {
        return publicKey;
    }


    public Element getGr() {
        return gr;
    }

    public void setGr(Element gr) {
        this.gr = gr;
    }



    //for test
    public void setPublicKey(Element publicKey){
        this.publicKey = publicKey;
    }



    public void setOrderedKeywordSet(TreeSet<Keyword> orderedKeywordSet){
        this.orderedKeywordSet = orderedKeywordSet;
    }

    // for test
    public TreeSet<Keyword> getOrderedKeywordSet(){
        return this.orderedKeywordSet;
    }

//    /**
//     * 最后设置的关键字sterm会包含真的fileNumber, 而xtermList中的各个元素的fileNumber
//     * 和keywordSet中的保持一致
//     * @param keywordSet
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

    //测试用
    public List<Keyword> getXtermList() {
        return xtermList;
    }



    public Keyword getSterm() {
        return sterm;
    }

    /**
     * 生成stag
     * @return
     */
    public Element getSToken(){

//        BigInteger xtrapInteger = CryptoFunciton.funcp(this.kt, this.sterm.getValue(), this.zFieldSize, null);
        Element xtrap = CryptoFunciton.getHashValueFromZField(this.kt, this.sterm.getValue(), this.zField);

//        Element inverseSecretKey = this.secretKey.pow(BigInteger.ONE.negate()).getImmutable();
        Element inverseSecretKey = this.secretKey.invert().getImmutable();

//        Element stoken = this.bilinearUtil.getPPowValue(reciprocalSecretKey.mul(xtrapInteger));
        Element stoken = this.bilinearUtil.getPPowValue(inverseSecretKey.mulZn(xtrap));
        return stoken;
    }

//    public



    protected XToken<Element> generateXToken(List<Element> xtrapList, int count) {
        List<Element> list = new ArrayList<>();
//        BigInteger zBigInteger = CryptoFunciton.funcp(this.kz, this.sterm.getValue().concat(String.valueOf(count)), this.zFieldSize, null);
//        Element z = this.bilinearUtil.getZrElement(zBigInteger);
        Element z = CryptoFunciton.getHashValueFromZField(this.kz, this.sterm.getValue().concat(String.valueOf(count)),this.zField);

        Element xtokenElem = null;
        for (Element xtrap : xtrapList) {
//            xtokenElem = this.gr.powZn(z.mul(xtrap).mul(secretKey)).getImmutable();
            xtokenElem = this.gr.powZn(z.mulZn(xtrap).mulZn(secretKey)).getImmutable();
            list.add(xtokenElem);
        }
        return new XToken(list);
    }

    //静态的 //这里假设用户一次性知道sterm个数，在stermNum中 TODO
    public List<XToken> generateSearchXTokens(int stermNum){
        //计算xtrap
        List<Element> xtrapList = new ArrayList<>();
        for (Keyword keyword : this.xtermList) {
//            BigInteger xtrapBigInteger = CryptoFunciton.funcp(this.kx, keyword.getValue(), this.zFieldSize, null);
//            Element xtrap = this.bilinearUtil.getZrElement(xtrapBigInteger);
            Element xtrap = CryptoFunciton.getHashValueFromZField(this.kx, keyword.getValue(), this.zField);
            xtrapList.add(xtrap);
        }

        List<XToken> xTokenList = new ArrayList<>();
        for (int i = 1; i <= stermNum; i++) {
            XToken<Element> xToken = generateXToken(xtrapList, i);
            xTokenList.add(xToken);
        }
        return xTokenList;
    }

    //静态的
    public List<String> decryptE(List<String> elemList){
        byte[] ke = CryptoFunciton.func(this.ks, this.sterm.getValue());
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