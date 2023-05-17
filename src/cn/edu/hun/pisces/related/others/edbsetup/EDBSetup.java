package cn.edu.hun.pisces.related.others.edbsetup;

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
import cn.edu.hun.pisces.utils.fileutil.FileSplitUtil;
import cn.edu.hun.pisces.utils.cryptography.CryptoFunciton;

import java.io.File;
import java.math.BigInteger;
import java.util.*;

/**
 * @author: Leilei Du
 * @Date: 2018/7/7 18:11
 */
@Deprecated
public class EDBSetup {
    /**
     * TSet数据结构
     */
    private TSet tset = null;
    /**
     * XSet数据结构
     */
    private XSet xset = null;

//    private ForwardIndexTable<DocFile> forwardIndexTable = null;
    private ReverseIndexTable reverseIndexTable = null;
    private Map<Keyword,List<TSetElement>> arrayT = null;

    /**
     *  密钥 Kt, Ks, Ki, Kz
     *
     *  Kt: 加密 stag
     *  Ks: 加密 keyword
     *  Ki: 加密 ind 使其变为Gp中的元素
     *  Kz: 加密 w||c 使其变为Gp中的元素z
     *
     */
    //TODO 统一测试的时候，kt没有初始值
    private byte[] kt = Constant.DEFAULT_K_T;
    //64字节，512位
    private byte[] ks = Constant.DEFAULT_K_S;
    private byte[] kx = Constant.DEFAULT_K_X;
    private byte[] ki = Constant.DEFAULT_K_I;
    private byte[] kz = Constant.DEFAULT_K_Z;

    /**
     * 大素数 p， 用来做模数
     */
    private BigInteger primeP = Constant.DEFAULT_BIG_PRIME;

    /**
     * 大素数 p 的欧拉函数
     */
    private BigInteger eulerPrimeP = Constant.DEFAULT_BIG_EULER_PRIME;

    /**
     * Gp中生成元g
     */
    private BigInteger generateElementG = Constant.DEFAULT_GENERATE_ELEMENT;



    /**
     * 选择各个密钥
     * @param change
     */
    public void selectKey(boolean change){
        if(change){
            CryptoFunciton.getKeyBytes(ks);
            CryptoFunciton.getKeyBytes(kx);
            CryptoFunciton.getKeyBytes(ki);
            CryptoFunciton.getKeyBytes(kz);
        }
    }

    /**
     * 构建正向索引和倒排索引
     * @param docDirPath
     */
    public void parseDataBase(String docDirPath){
        //倒排索引
        File dir = new File(docDirPath);
        HashMap<String, List<File>> reverseIndexData = FileSplitUtil.extractReverseIndexData(dir, Constant.DEFAULT_REGEX_SPLIT);

        reverseIndexTable = new ReverseIndexTable();
        reverseIndexTable.construct(reverseIndexData);

    }

    public void initialize(){
        xset = new XSet();
        arrayT = new HashMap<>();
    }
    public void buildArrayTAndXSet(){

        HashMap<Keyword, FileList> indexTable = this.reverseIndexTable.getIndexTable();
        Set<Map.Entry<Keyword, FileList>> entries = indexTable.entrySet();
        for (Map.Entry<Keyword, FileList> entry : entries) {
            Keyword keyword = entry.getKey();
            //计算得到Ke
            byte[] ke = CryptoFunciton.func(ks, keyword.getValue());
            FileList value = entry.getValue();
            List<DocFile> list = value.getList();
            //在构建FileList的时候已经保证了File的顺序是随机的
            int c = 0;
            List<TSetElement> elementList = new ArrayList<>();
            for (DocFile docFile: list) {
                String ind = docFile.getInd();
                BigInteger xind = CryptoFunciton.funcp(ki, ind, this.eulerPrimeP, null);
                ++c;
                BigInteger z = CryptoFunciton.funcp(kz, keyword.getValue().concat(String.valueOf(c)), this.eulerPrimeP, this.eulerPrimeP);
                BigInteger y = z.modPow(new BigInteger("-1"), this.eulerPrimeP).multiply(xind).mod(this.eulerPrimeP);

                String e = CryptoFunciton.encrypt(ke,ind);
                TSetElement element = new TSetElement(e,y);

                elementList.add(element);


                BigInteger xtrap = CryptoFunciton.funcp(kx, keyword.getValue(), this.eulerPrimeP, null);
                BigInteger xtag = generateElementG.modPow(xtrap.multiply(xind), this.primeP);

                xset.add(xtag);
            }
            arrayT.put(keyword, elementList);
        }

    }

    /**
     * 输入为 Map<Keyword,List<TSetElement>> arrayT
     * 输出为 TSet和Kt
     */
    public EDB tSetSetup(){
        tset = new TSet();
//        统一测试的时候kt是变化的 //TODO
//        kt = CryptoFunciton.getKeyBytes(Constant.DEFAULT_KT_BYTE_LENGTH);
        Set<Map.Entry<Keyword, List<TSetElement>>> entries = arrayT.entrySet();
        for (Map.Entry<Keyword, List<TSetElement>> entry : entries) {
            Keyword keyword = entry.getKey();
            String encKeywordValue = CryptoFunciton.funcs(kt, keyword.getValue());
            Stag stag = new StringStag(encKeywordValue);
            List<TSetElement> value = entry.getValue();
            tset.putElement(stag, value);
        }
        return new EDB(tset, xset);
    }

    public static void main(String[] args) {
        int len = new EDBSetup().ki.length;
        System.out.println(len);
    }

}