package cn.edu.hun.pisces.related.role;

import cn.edu.hun.pisces.basestruct.Keyword;
import cn.edu.hun.pisces.basestruct.KeywordComparator;
import cn.edu.hun.pisces.related.basestruct.XToken;
import cn.edu.hun.pisces.related.basestruct.stag.Stag;
import cn.edu.hun.pisces.related.basestruct.stag.StringStag;
import cn.edu.hun.pisces.utils.Constant;
import cn.edu.hun.pisces.utils.TreeSetUtil;
import cn.edu.hun.pisces.utils.cryptography.BilinearUtil;
import cn.edu.hun.pisces.utils.cryptography.CryptoFunciton;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;

import java.security.Key;
import java.util.*;

/**
 * @author: Leilei Du
 * @Date: 2018/7/9 13:26
 */
public class Client {
//    protected BigInteger generateElementG = Constant.DEFAULT_GENERATE_ELEMENT;
//    protected BigInteger primeP = Constant.DEFAULT_BIG_PRIME;
//    protected BigInteger eulerPrimeP = Constant.DEFAULT_BIG_EULER_PRIME;

    /**
     * 提供群的生成元，指数域等。
     */
    protected BilinearUtil bilinearUtil = new BilinearUtil();
    private Field zFile = bilinearUtil.getzField();

    //由dataowner在生成数据库的时候给予的有序关键字列表(根据相关的文件数降序排序)，结合查询的关键字生成sterm和xtermList
    protected TreeSet<Keyword> orderedKeywordSet = null;
    protected Keyword sterm = null;
    //xterm列表，顺序和查询关键字数据相同，并不一定是按相关文件数降序排序
    protected List<Keyword> xtermList = null;

    // xtoken的数量，实际中client不知道，这里为了测试方便 //
//    protected int xtokenNumber = -1;

    protected byte[] ks = Constant.DEFAULT_K_S;
    protected byte[] kx = Constant.DEFAULT_K_X;
    protected byte[] ki = Constant.DEFAULT_K_I;
    protected byte[] kz = Constant.DEFAULT_K_Z;
    protected byte[] kt = Constant.DEFAULT_K_T;


    public void setSterm(Keyword sterm) {
        this.sterm = sterm;
    }

    public void setXtermList(List<Keyword> xtermList) {
        this.xtermList = xtermList;
    }

    /*
            for test
            由dataowner给client.
            dataowner调用该方法，传给client.保证orderedKeywordSet是一个以KeywordComparator
            为比较器的TreeSet
         */
    public void setOrderedKeywordSet(TreeSet<Keyword> orderedKeywordSet){
        this.orderedKeywordSet = orderedKeywordSet;
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
        /*
         *  默认第一个是sterm, 其他的构成xtermList
         */
        Iterator<Keyword> iterator = keywordList.iterator();
        this.sterm = iterator.next();
        this.xtermList = new ArrayList<>();
        while (iterator.hasNext()){
            this.xtermList.add(iterator.next());
        }
    }

//    private Keyword getFirstAppearElement()

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
    public Stag tSetGetTag(){
        String value = CryptoFunciton.funcs(this.kt, this.sterm.getValue());
        Stag stag = new StringStag(value);
        return stag;
    }


    protected XToken<Element> getXToken(int count){
        List<Element> list = new ArrayList<>();
//        BigInteger z = CryptoFunciton.funcp(this.kz, this.sterm.getValue().concat(String.valueOf(count)), this.eulerPrimeP, this.eulerPrimeP);
        Element z = CryptoFunciton.getHashValueFromZField(this.kz, this.sterm.getValue().concat(String.valueOf(count)), this.zFile);
        Element xtokenElem = null;
        for (Keyword keyword : this.xtermList) {
//            BigInteger xtrap = CryptoFunciton.funcp(this.kx, keyword.getValue(), this.eulerPrimeP, null);
            Element xtrap = CryptoFunciton.getHashValueFromZField(this.kx, keyword.getValue(), zFile);
//            xtokenElem = this.generateElementG.modPow(z.multiply(xtrap), this.primeP);
            xtokenElem = this.bilinearUtil.getGPowValue(z.mulZn(xtrap));
            list.add(xtokenElem);
        }
        return new XToken(list);
    }



    //静态的 //这里假设用户一次性知道sterm个数，在stermNum中 TODO
    public List<XToken> generateSearchXToken(int stermNum){
        List<XToken> xTokenList = new ArrayList<>();
        for (int i = 1; i <= stermNum; i++) {
            XToken<Element> xToken = getXToken(i);
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

//    /**
//     *  测试用
//     *  返回实际生成token的毫秒数
//     */
//    public List<Object> generateToken(List<Keyword> keywordList, int stermFileNumber){
//        /*
//         *    需要初始化 sterm 和 xtermList。
//         *    默认keywordList的第一个是 sterm, 其他部分构成xtermList
//         */
//        List<Object> list = new ArrayList<>();
//        this.setStermAndXterm(keywordList);
//        long startTime = System.currentTimeMillis();
//        Stag stag = this.tSetGetTag();
//        this.generateSearchXToken();
//        long endTime = System.currentTimeMillis();
//    }

}