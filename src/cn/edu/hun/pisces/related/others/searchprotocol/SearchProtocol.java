package cn.edu.hun.pisces.related.others.searchprotocol;

import cn.edu.hun.pisces.basestruct.Keyword;
import cn.edu.hun.pisces.basestruct.TSetElement;
import cn.edu.hun.pisces.related.basestruct.ReverseIndexTable;
import cn.edu.hun.pisces.related.basestruct.TSet;
import cn.edu.hun.pisces.related.basestruct.XSet;
import cn.edu.hun.pisces.related.basestruct.XToken;
import cn.edu.hun.pisces.related.basestruct.stag.Stag;
import cn.edu.hun.pisces.related.basestruct.stag.StringStag;
import cn.edu.hun.pisces.utils.Constant;
import cn.edu.hun.pisces.utils.cryptography.CryptoFunciton;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author: Leilei Du
 * @Date: 2018/7/9 13:11
 */
@Deprecated
public class SearchProtocol {
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
    private byte[] kt = { 29, 3, -123, 118, 25, -25, -87, 74, 36, 37, 51, 67, 91, -77, -70, 59, -46, -127, 53, -88, -120, -66, -32, -108, 73, 126, 82, -118, 90, 102, -115, -115, -28, 83, -69, 66, 49, -21, 62, -49, -43, 2, 122, 89, 103, -63, 70, -9, 117, 7, 0, -52, -13, -51, -103, 42, 47, -44, 31, -117, 54, -50, -61, 25 };
    //64字节，512位
    private byte[] ks = { 50, -28, -24, -91, -115, -38, -58, 117, 25, -49, -62, 84, 1, 33, -13, 111, 94, 64, 71, -94, -125, 0, 123, -73, 61, -102, -81, 5, 68, 6, -57, -90, -55, -86, 75, -21, 3, 102, -95, 20, -106, 104, 71, -63, -46, -64, -112, -95, 87, -17, 15, -104, 39, 69, 83, 95, -6, 73, 105, -57, 45, -101, 123, 37 };
    private byte[] kx = { 41, -20, 113, -92, 44, -6, -103, -55, 4, 19, -122, 81, 14, 123, 121, -56, 47, -121, -96, 90, -52, 112, 17, 32, 34, 85, 38, -18, 92, 6, 15, -27, -102, -67, -8, -97, 12, 16, 0, -13, -82, -102, -46, -128, -24, -23, -124, -73, 87, -72, -123, 42, 59, -125, -64, -33, 82, 112, -11, -71, -19, 12, -20, -115 };
    private byte[] ki = { -93, 26, -82, 99, -29, -31, -104, 103, 90, -107, 25, -2, -32, 0, -1, -96, -117, -113, -39, 89, 78, -18, -19, -16, 72, 105, 16, -4, 67, -98, 76, 13, 5, -27, -81, -5, -48, 9, -108, 125, 114, -122, 16, -18, -20, 112, -100, 19, -117, -43, -121, -7, 85, -104, 60, -31, -80, -96, 84, -19, -103, 26, 65, 37 };
    private byte[] kz = { -88, 62, 69, 42, 4, 2, 18, 46, 90, -123, -62, -101, 14, -102, 75, -124, -86, -54, -102, 76, -74, 62, 14, -57, 1, -34, 0, 82, 121, 93, -50, 73, 76, 67, 25, -93, -115, -120, 26, 117, 31, 89, 126, 19, -57, -24, 64, 64, -81, 24, 45, -88, 97, 90, 42, 29, 25, -67, -69, -1, -57, -103, -60, 5 };

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
     * 生成stag
     * @param kt
     * @param keyword
     * @return
     */
    public Stag tSetGetTag(byte[] kt, Keyword keyword){
        String value = CryptoFunciton.funcs(kt, keyword.getValue());
        Stag stag = new StringStag(value);
        return stag;
    }

    public XToken getXToken(int count, Keyword sterm, Set<Keyword> keywordSet){
        List<BigInteger> list = new ArrayList<>();
        BigInteger z = CryptoFunciton.funcp(kz, sterm.getValue().concat(String.valueOf(count)), this.eulerPrimeP, eulerPrimeP);
        BigInteger xtokenElem = null;
        for (Keyword keyword : keywordSet) {
            BigInteger xtag = CryptoFunciton.funcp(kx, keyword.getValue(), this.eulerPrimeP, null);
            xtokenElem = this.generateElementG.modPow(z.multiply(xtag), this.primeP);
            list.add(xtokenElem);
        }
        return new XToken(list);
    }





}