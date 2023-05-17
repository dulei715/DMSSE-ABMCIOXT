package cn.edu.hun.pisces.utils;

import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

public class Constant {


    public static final String DEFAULT_PROGRAM_PATH = "F:\\Users\\Administrator\\IdeaProjects\\ABMCIOXT\\";

    /**
     * Constant for sigle variable
     */
    public static final String DEFAULT_REGEX_SPLIT = "\\b";

    //have been deprecated
    public static final String DEPRECATED_DEFAULT_COMBINE_SYMBOL = ":";

    public static final String DEFAULT_COMBINE_SYMBOL = ": ";

    //for Class FileList
    public static final String DEFAULT_ORDER_KEY = "1";

    //for the bit number of getHashPrime result
    public static final int PRIME_BIT_NUM = 10;

    //for AES keyLength
    public static final int DEFAULT_AES_KEY_LENGTH = 128;

    //for kt byteLength
    public static final int DEFAULT_KT_BYTE_LENGTH = 64;

    //for security parameter
    public static final int DEFAULT_SECURITY_PARAMETER = DEFAULT_KT_BYTE_LENGTH * 8;

    //for default key byteLength
    public static final int DEFAULT_KEY_BYTE_LENGTH = 32;

    //for common generate element g
    public static final BigInteger DEFAULT_GENERATE_ELEMENT = new BigInteger("71");

    // for common big prime p
    public static final BigInteger DEFAULT_BIG_PRIME = new BigInteger("3969569");
//    public static final BigInteger DEFAULT_BIG_PRIME = new BigInteger("867988061");

    // for common big prime p's Euler function value
    public static final BigInteger DEFAULT_BIG_EULER_PRIME = new BigInteger("3969568");
//    public static final BigInteger DEFAULT_BIG_EULER_PRIME = new BigInteger("867988060");


    // for bilinear mapping
    public static final Pairing DEFAULT_BILINEAR_PAIRING = PairingFactory.getPairing("curve.properties");

    // 该字符串用来生成双线性函数的生成元p
    public static final String DEFAULT_BILINEAR_STRING_P = "wjfoiefavn eiouohgsfnfakjbv.m,Znbk;gaj";
    // 该字符串用来生成双线性函数的生成元g //8603720,2189005,0
    public static final String DEFAULT_BILINEAR_STRING_G = "wjfoiefavn eiouohgsfnfakjbv.m,Znbk;gaj";

    // for improved basic client's default secret key
    public static final BigInteger DEFAULT_SECRET_KEY = new BigInteger("673");


    // for OSPIT-OXT, 关键字的属性个数
    public static final int DEFAULT_ATTRIBUITE_NUMBER = 20;

    public static final String DEFAULT_BILINEAR_STRING = "wjfoiefavn eiouohgsfnfakjbv.m,Znbk;gaj";

    // for keywords' letter limit
    public static final int KEYWORD_LETTER_LOW_BOUND = 4;

    // for keywords' ind number limit
    public static final int KEYWORD_IND_LOW_BOUND = 10;





    /**
     * Constant for array
     */
    //for Class ReverseIndexTable
    public static final String[] DEFAULT_ORDER_KEY_ARRAY = {
            "1", "2", "3", "4", "5", "6", "7", "8", "9"
    };


    /**
     *
     */
    public static final byte[] DEFAULT_K_T = { 29, 3, -123, 118, 25, -25, -87, 74, 36, 37, 51, 67, 91, -77, -70, 59, -46, -127, 53, -88, -120, -66, -32, -108, 73, 126, 82, -118, 90, 102, -115, -115, -28, 83, -69, 66, 49, -21, 62, -49, -43, 2, 122, 89, 103, -63, 70, -9, 117, 7, 0, -52, -13, -51, -103, 42, 47, -44, 31, -117, 54, -50, -61, 25 };
//    //64字节，512位
//    public static final byte[] DEFAULT_K_S = { 50, -28, -24, -91, -115, -38, -58, 117, 25, -49, -62, 84, 1, 33, -13, 111, 94, 64, 71, -94, -125, 0, 123, -73, 61, -102, -81, 5, 68, 6, -57, -90, -55, -86, 75, -21, 3, 102, -95, 20, -106, 104, 71, -63, -46, -64, -112, -95, 87, -17, 15, -104, 39, 69, 83, 95, -6, 73, 105, -57, 45, -101, 123, 37 };
//    public static final byte[] DEFAULT_K_X = { 41, -20, 113, -92, 44, -6, -103, -55, 4, 19, -122, 81, 14, 123, 121, -56, 47, -121, -96, 90, -52, 112, 17, 32, 34, 85, 38, -18, 92, 6, 15, -27, -102, -67, -8, -97, 12, 16, 0, -13, -82, -102, -46, -128, -24, -23, -124, -73, 87, -72, -123, 42, 59, -125, -64, -33, 82, 112, -11, -71, -19, 12, -20, -115 };
//    public static final byte[] DEFAULT_K_I = { -93, 26, -82, 99, -29, -31, -104, 103, 90, -107, 25, -2, -32, 0, -1, -96, -117, -113, -39, 89, 78, -18, -19, -16, 72, 105, 16, -4, 67, -98, 76, 13, 5, -27, -81, -5, -48, 9, -108, 125, 114, -122, 16, -18, -20, 112, -100, 19, -117, -43, -121, -7, 85, -104, 60, -31, -80, -96, 84, -19, -103, 26, 65, 37 };
//    public static final byte[] DEFAULT_K_Z = { -88, 62, 69, 42, 4, 2, 18, 46, 90, -123, -62, -101, 14, -102, 75, -124, -86, -54, -102, 76, -74, 62, 14, -57, 1, -34, 0, 82, 121, 93, -50, 73, 76, 67, 25, -93, -115, -120, 26, 117, 31, 89, 126, 19, -57, -24, 64, 64, -81, 24, 45, -88, 97, 90, 42, 29, 25, -67, -69, -1, -57, -103, -60, 5 };
//
//    public static final byte[] DEFAULT_K_M = { 19, 62, 11, 105, 5, -18, -122, -20, -84, -29, -106, -10, -44, -91, 98, 78, -46, 22, 104, -56, 54, 89, 33, -23, -63, -89, 109, 80, -3, 9, 45, 51, 51, 84, 120, -105, 100, -13, 74, 106, -25, -103, -101, 29, 100, 84, -100, -87, 83, 113, 57, 98, -125, -23, -60, -125, 43, -37, -104, -63, -98, -17, 64, 89 };

//32字节，256位
public static final byte[] DEFAULT_K_S = {
//        50, -28, -24, -91, -115, -38, -58, 117,
//        25, -49, -62, 84, 1, 33, -13, 111,
//        94, 64, 71, -94, -125, 0, 123, -73,
//        61, -102, -81, 5, 68, 6, -57, -90,
        -55,-86, 75, -21, 3, 102, -95, 20,
        -106, 104, 71, -63, -46, -64, -112, -95,
        87, -17, 15, -104, 39, 69, 83, 95,
        -6, 73, 105, -57, 45, -101, 123, 37
};
    public static final byte[] DEFAULT_K_X = {
//            41, -20, 113, -92, 44, -6, -103, -55,
//            4, 19, -122, 81, 14, 123, 121, -56,
//            47, -121, -96, 90, -52, 112, 17, 32,
//            34, 85, 38, -18, 92, 6, 15, -27,
            -102, -67, -8, -97, 12, 16, 0, -13,
            -82, -102, -46, -128, -24, -23, -124, -73,
            87, -72, -123, 42, 59, -125, -64, -33,
            82, 112, -11, -71, -19, 12, -20, -115
    };
    public static final byte[] DEFAULT_K_I = {
//            -93, 26, -82, 99, -29, -31, -104, 103,
//            90, -107, 25, -2, -32, 0, -1, -96,
//            -117, -113, -39, 89, 78, -18, -19, -16,
//            72, 105, 16, -4, 67, -98, 76, 13,
            5, -27, -81, -5, -48, 9, -108, 125,
            114, -122, 16, -18, -20, 112, -100, 19,
            -117, -43, -121, -7, 85, -104, 60, -31,
            -80, -96, 84, -19, -103, 26, 65, 37
    };
    public static final byte[] DEFAULT_K_Z = {
//            -88, 62, 69, 42, 4, 2, 18, 46,
//            90, -123, -62, -101, 14, -102, 75, -124,
//            -86, -54, -102, 76, -74, 62, 14, -57,
//            1, -34, 0, 82, 121, 93, -50, 73,
            76, 67, 25, -93, -115, -120, 26, 117,
            31, 89, 126, 19, -57, -24, 64, 64,
            -81, 24, 45, -88, 97, 90, 42, 29,
            25, -67, -69, -1, -57, -103, -60, 5 };

    public static final byte[] DEFAULT_K_M = {
//            19, 62, 11, 105, 5, -18, -122, -20,
//            -84, -29, -106, -10, -44, -91, 98, 78,
//            -46, 22, 104, -56, 54, 89, 33, -23,
//            -63, -89, 109, 80, -3, 9, 45, 51,
            51, 84, 120, -105, 100, -13, 74, 106,
            -25, -103, -101, 29, 100, 84, -100, -87,
            83, 113, 57, 98, -125, -23, -60, -125,
            43, -37, -104, -63, -98, -17, 64, 89
    };


    public static final int DEFAULT_CLIENT_NUMBER = 10;

    public static final BigInteger DEFAULT_OSPIR_CLIENT_NUMBER_UP_BOUND = BigInteger.valueOf(10000);

    // for test
    // 默认的赋给client的随机数r (20个)
//    public static final BigInteger[] DEFAULT_CLIENTS_RANDOM_R = {
//            new BigInteger("791077064"),
//            new BigInteger("812658702") ,
//            new BigInteger("389194313") ,
//            new BigInteger("473611619") ,
//            new BigInteger("452491947") ,
//            new BigInteger("748751829"),
//            new BigInteger("872818738"),
//            new BigInteger("686215350"),
//            new BigInteger("905985513"),
//            new BigInteger("380811932"),
//            new BigInteger("621413502"),
//            new BigInteger("513576194"),
//            new BigInteger("258007261"),
//            new BigInteger("682817595"),
//            new BigInteger("986008423"),
//            new BigInteger("957695125"),
//            new BigInteger("488138985"),
//            new BigInteger("386702578"),
//            new BigInteger("886836611"),
//            new BigInteger("508309772")
//    };
//    // 默认client的密钥 (20个)
//    public static final BigInteger[] DEFAULT_CLIENT_SECRETE_KEY = {
//            new BigInteger("480150055"),
//            new BigInteger("1032828731"),
//            new BigInteger("394402126"),
//            new BigInteger("132596036"),
//            new BigInteger("690357369"),
//            new BigInteger("428926517"),
//            new BigInteger("1065490692"),
//            new BigInteger("27380114"),
//            new BigInteger("94260301"),
//            new BigInteger("891941980"),
//            new BigInteger("289279963"),
//            new BigInteger("49197501"),
//            new BigInteger("814829124"),
//            new BigInteger("330332804"),
//            new BigInteger("52508974"),
//            new BigInteger("892651207"),
//            new BigInteger("221351501"),
//            new BigInteger("657419757"),
//            new BigInteger("215026034"),
//            new BigInteger("986850239")
//    };


    /**
     * Constant for set
     */
//    public static final Set<String> DEFAULT_EXCLUSIVE_SET = new HashSet<String>(){};


}