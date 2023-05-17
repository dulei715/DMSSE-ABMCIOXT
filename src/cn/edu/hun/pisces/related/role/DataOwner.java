package cn.edu.hun.pisces.related.role;

import cn.edu.hun.pisces.basestruct.FileList;
import cn.edu.hun.pisces.basestruct.Keyword;
import cn.edu.hun.pisces.basestruct.TSetElement;
import cn.edu.hun.pisces.basestruct.docfile.DocFile;
import cn.edu.hun.pisces.related.basestruct.EDB;
import cn.edu.hun.pisces.related.basestruct.ReverseIndexTable;
import cn.edu.hun.pisces.related.basestruct.TSet;
import cn.edu.hun.pisces.related.basestruct.XSet;
import cn.edu.hun.pisces.related.basestruct.stag.Stag;
import cn.edu.hun.pisces.related.basestruct.stag.StringStag;
import cn.edu.hun.pisces.utils.Constant;
import cn.edu.hun.pisces.utils.fileutil.EmailKeywordSplitUtil;
import cn.edu.hun.pisces.utils.fileutil.FileSplitUtil;
import cn.edu.hun.pisces.utils.cryptography.CryptoFunciton;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;

import java.io.File;
import java.util.*;

/**
 * @author: Leilei Du
 * @Date: 2018/7/9 13:27
 */

@SuppressWarnings("ALL")
public class DataOwner extends Client {

    /**
     * 该数据结构只是为了做实验使用。分离文件读取和文件处理。
     */
    private Map<String, List<File>>  reverseIndexData = new HashMap<>();

    //用来做群的指数运算
    private Field zFile = super.bilinearUtil.getzField();

    /**
     * TSet数据结构
     */
    private TSet tset = null;
    /**
     * XSet数据结构
     */
    private XSet<String> xset = null;

    //    private ForwardIndexTable<DocFile> forwardIndexTable = null;
    private ReverseIndexTable reverseIndexTable = null;
    private Map<Keyword,List<TSetElement>> arrayT = null;


    //for test
    public Map<Keyword, List<TSetElement>> getArrayT(){
        return this.arrayT;
    }

    //for test
    public Map<Stag, List<TSetElement>> getTSetMap(){
        return this.tset.getTSetMap();
    }


    public TSet getTset() {
        return tset;
    }

    public XSet getXset() {
        return xset;
    }

    public ReverseIndexTable getReverseIndexTable() {
        return reverseIndexTable;
    }

    /**
     * 选择各个密钥
     * @param change
     */
    private void selectKey(boolean change){
        if(change){
            CryptoFunciton.getKeyBytes(this.ks);
            CryptoFunciton.getKeyBytes(this.kx);
            CryptoFunciton.getKeyBytes(this.ki);
            CryptoFunciton.getKeyBytes(this.kz);
        }
    }

    /**
     * 构建正向索引和倒排索引
     * @param docDirPath
     */
    @Deprecated
    private void parseDataBase0(String docDirPath){
        //倒排索引
        File dir = new File(docDirPath);
        HashMap<String, List<File>>  reverseIndexData = FileSplitUtil.extractReverseIndexData(dir, Constant.DEFAULT_REGEX_SPLIT);

        this.reverseIndexTable = new ReverseIndexTable();
        this.reverseIndexTable.construct(reverseIndexData);

    }

    public void readDataFromFile(String docDirPath){
        this.reverseIndexData = EmailKeywordSplitUtil.readReverseIndexFromFile(docDirPath);
    }

    /**
     * 读取文件中的倒排索引，建立相应的倒排索引结构
     */
    public void parseDataBase(){

        this.reverseIndexTable = new ReverseIndexTable();
        this.reverseIndexTable.construct(this.reverseIndexData);

    }

    public void initialize(){
        xset = new XSet();
        arrayT = new HashMap<>();
        tset = new TSet();
    }

    private void buildArrayTAndXSet(){

        HashMap<Keyword, FileList> indexTable = this.reverseIndexTable.getIndexTable();
        Set<Map.Entry<Keyword, FileList>> entries = indexTable.entrySet();
        for (Map.Entry<Keyword, FileList> entry : entries) {
            Keyword keyword = entry.getKey();
            //计算得到Ke
            byte[] ke = CryptoFunciton.func(this.ks, keyword.getValue());
            FileList value = entry.getValue();
            List<DocFile> list = value.getList();
            //在构建FileList的时候已经保证了File的顺序是随机的
            int c = 0;
            List<TSetElement> elementList = new ArrayList<>();
            for (DocFile docFile: list) {

                ++c;

                String ind = docFile.getInd();
//                BigInteger xind = CryptoFunciton.funcp(this.ki, ind, this.eulerPrimeP, null);
                Element xind = CryptoFunciton.getHashValueFromZField(this.ki, ind, this.zFile);


//                BigInteger z = CryptoFunciton.funcp(this.kz, keyword.getValue().concat(String.valueOf(c)), this.eulerPrimeP, this.eulerPrimeP);
                Element z = CryptoFunciton.getHashValueFromZField(this.kz, keyword.getValue().concat(String.valueOf(c)), this.zFile);

//                BigInteger y = z.modPow(new BigInteger("-1"), this.eulerPrimeP).multiply(xind).mod(this.eulerPrimeP);
                Element y = z.invert().mul(xind).getImmutable();

                String e = CryptoFunciton.encrypt(ke,ind);
                TSetElement element = new TSetElement(e,y);

                elementList.add(element);


//                BigInteger xtrap = CryptoFunciton.funcp(this.kx, keyword.getValue(), this.eulerPrimeP, null);
                Element xtrap = CryptoFunciton.getHashValueFromZField(this.kx, keyword.getValue(), zFile);
//                BigInteger xtag = generateElementG.modPow(xtrap.multiply(xind), this.primeP);
                Element xtag = bilinearUtil.getGPowValue(xtrap.mulZn(xind));

                xset.add(xtag.toString());
            }
            arrayT.put(keyword, elementList);
        }

    }

    /**
     * 输入为 Map<Keyword,List<TSetElement>> arrayT
     * 输出为 TSet和Kt
     */
    private EDB tSetSetup(){
//        统一测试的时候kt是变化的 //TODO
//        kt = CryptoFunciton.getKeyBytes(Constant.DEFAULT_KT_BYTE_LENGTH);
        Set<Map.Entry<Keyword, List<TSetElement>>> entries = this.arrayT.entrySet();
        for (Map.Entry<Keyword, List<TSetElement>> entry : entries) {
            Keyword keyword = entry.getKey();
            String encKeywordValue = CryptoFunciton.funcs(this.kt, keyword.getValue());
            Stag stag = new StringStag(encKeywordValue);
            List<TSetElement> value = entry.getValue();
            this.tset.putElement(stag, value);
        }
        return new EDB(tset, xset);
    }


    public EDB edbSetup(String dbDir){
        System.out.println("The DataBase is being constructed ......");
        this.readDataFromFile(dbDir);

        long startTime = System.currentTimeMillis();

        this.selectKey(false);
        this.parseDataBase();
        this.initialize();
        this.buildArrayTAndXSet();

        long middleTime = System.currentTimeMillis();

        this.tSetSetup();

        long endTime = System.currentTimeMillis();

        System.out.println("The DataBase has been constructed!");
        long totalTime = endTime - startTime;
        long tsetTime = endTime - middleTime;
        long usedTime = middleTime - startTime;
        System.out.println("(Total time, TSet time, Used time): " + "(" + totalTime + ", " + tsetTime + " , " + usedTime + ")");

        return new EDB(this.tset, this.xset);
    }

//    public EDB edbSetup(String dbDir){
//        System.out.println("The DataBase is being constructed ......");
//
//        long beforeStartTime = System.currentTimeMillis();
//
//        this.readDataFromFile(dbDir);
//
//        long startTime = System.currentTimeMillis();
//
//        this.selectKey(false);
//
//        long midTime1 = System.currentTimeMillis();
//
//        this.parseDataBase();
//
//        long midTime2 = System.currentTimeMillis();
//
//        this.initialize();
//
//        long midTime3 = System.currentTimeMillis();
//
//        this.buildArrayTAndXSet();
//
//        long midTime4 = System.currentTimeMillis();
//
//        this.tSetSetup();
//
//        long endTime = System.currentTimeMillis();
//
//        EDB edb = new EDB(this.tset, this.xset);
//
//        long afterEndTime = System.currentTimeMillis();
//
//        System.out.println("The DataBase has been constructed!");
//
//        System.out.println("readDataFromFile: " + (startTime - beforeStartTime));
//        System.out.println("selectKey: " + (startTime - beforeStartTime));
//        System.out.println("readDataFromFile: " + (startTime - beforeStartTime));
//        System.out.println("selectKey: " + (midTime1 - startTime));
//        System.out.println("parseDataBase: " + (midTime2 - midTime1));
//        System.out.println("initialize: " + (midTime3 - midTime2));
//        System.out.println("buildArrayTAndXSet: " + (midTime4 - midTime3));
//        System.out.println("tSetSetup: " + (endTime - midTime4));
//        System.out.println("edb: " + (afterEndTime - endTime));
//
//        return edb;
//    }

}