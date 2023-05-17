package cn.edu.hun.pisces.utils.cryptography;

import cn.edu.hun.pisces.utils.BigIntegerUtil;
import cn.edu.hun.pisces.utils.Constant;
import cn.edu.hun.pisces.utils.StringUtil;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.Random;
import java.util.Set;

/**
 * @author: Leilei Du
 * @Date: 2018/7/7 10:27
 */
public class CryptoFunciton {

    private static Random random = new Random();


    public static GNPair  generateGNPair(){
        //TODO 生成 g 保证是 n 的本原根，并且 n-1 包含大素数
        return new GNPair();
    }

    /**
     * 返回指定字节数的随机字节数组
     * @param byteNum
     * @return
     */
    public static byte[] getKeyBytes(int byteNum){
        byte[] bytes = new byte[byteNum];
        random.nextBytes(bytes);
        return bytes;
    }

    public static void getKeyBytes(byte[] bytes){
        random.nextBytes(bytes);
    }

    /**
     * 返回小于range的哈希函数，除去集合元素
     * @param data
     * @param range
     * @return
     */
    @Deprecated
    public static BigInteger funHash(String data, BigInteger range, Set<BigInteger> excludedSet){
        BigInteger result;
        String hash = Hash.getMD5StrJava(data);
        long left = new BigInteger(hash.substring(0,16), 16).longValue();
        long right = new BigInteger(hash.substring(16,32), 16).longValue();
        long seed = (left + right) / 2;
        Random random = new Random(seed);
        result = BigIntegerUtil.nextBigInteger(range, random);
        return result;
    }


    /**
     * 该函数为统一使用椭圆曲线群提供实现
     * 该函数将指定的数据映射到zr域中
     * 该函数不会返回 0
     * @param data
     * @param zr
     * @return
     */
    public static Element getHashValueFromZField(byte[] key, String data, Field zr){
        String keyStr = Hash.byte2Hex(key);
        // 将秘钥和数据直接连接，取摘要。
        String hash = Hash.getMD5StrJava(keyStr + data); //16个字节，32个字符
        long left = new BigInteger(hash.substring(0,16), 16).longValue();
        long right = new BigInteger(hash.substring(16,32), 16).longValue();
        long seed = (left + right) / 2;
        Random random = new Random(seed);
        BigInteger order = zr.getOrder();
        int resultInt = 0;
        //保证产生在zr的随机数不是0
        while(resultInt == 0){
            resultInt = random.nextInt(order.intValue());
        }
        return zr.newElement().set(resultInt).getImmutable();
    }

    /**
     * 把data映射到群中的元素
     * 该元素不是单位元，否则抛出异常
     * @param data
     * @param groupField
     * @return
     */
    public static Element getHashValueFromGroupField(String data, Field groupField){
        byte[] dataByte = null;
        Element result = null;
        try {
            //为了避免相同的头会被映射到同一个hash值上
            data = StringUtil.reverse(data);
            dataByte = data.getBytes("UTF-8");
            result = groupField.newElement().setFromHash(dataByte, 0, dataByte.length).getImmutable();
            if(result.isEqual(groupField.newOneElement())){
                throw new RuntimeException("The element is one element... ...");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Element getNonZeroRandomElement(Field zField){
        Element result = zField.newRandomElement().getImmutable();
        while(result.isEqual(zField.newZeroElement())){
            System.out.println("zero power index...");
            result = zField.newRandomElement().getImmutable();
        }
        return result;
    }

    /**
     * use key to encrypt data
     * @param key  key
     * @param data plaintext
     * @return cipher
     */
    public static byte[] func(byte[] key, String data) {
        byte[] databyte;
        try {
            databyte = data.getBytes("UTF-8");
            return HMAC.encryptHMAC(key, databyte);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String funcs(byte[] key, String data){
        return StringUtil.byteArrayToHexString(func(key, data));
    }

//    public int funcp(byte[] key, String data){
//        return funcp(key,data,this.p.intValue());
//    }

    /**
     * 返回由key对data加密产生的小于p的正整数, 如果refer不为空，返回的正整数保证与refer互素
     * @param key
     * @param data
     * @param p
     * @return
     */
    @Deprecated
    public static BigInteger funcp(byte[] key, String data, BigInteger p, BigInteger refer){
        BigInteger result;
        String keyStr = Hash.byte2Hex(key);
        String hash = Hash.getMD5StrJava(keyStr + data); //16个字节，32个字符
        long left = new BigInteger(hash.substring(0,16), 16).longValue();
        long right = new BigInteger(hash.substring(16,32), 16).longValue();
        long seed = (left + right) / 2;
        Random random = new Random(seed);
        //保证产生的小于p的随机数不是0
        if(refer != null){
            result = BigIntegerUtil.nextCoprimeBigInteger(p, random, refer);
        }else{
            result = BigIntegerUtil.nextPositiveBigInteger(p, random);
        }
        return result;
    }

//    public static Ele


//    public static String encrypt(byte[] key, String data) {
//        return AES.encryptAES(key, data);
//    }
//
//    public static String decrypt(byte[] key, String encryptData) {
//        return AES.decryptAES(key, encryptData);
//    }

    public static String encrypt(byte[] key, String data) {
//        Random random = new Random()
//        return AES.encryptAES(key, data);
        byte[] dataBytes = null;
        byte[] result = null;
        try {
            dataBytes = data.getBytes("utf-8");
            result = new byte[dataBytes.length];
            for (int i = 0; i < dataBytes.length; i++) {
                result[i] = (byte) (dataBytes[i] ^ key[i%key.length]);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return Hash.byte2Hex(result);
    }

    public static String decrypt(byte[] key, String encryptData) {
        byte[] dataBytes = Hash.Hex2byte(encryptData);
        byte[] result = new byte[dataBytes.length];
        for (int i = 0; i < dataBytes.length; i++) {
            result[i] = (byte) (dataBytes[i] ^ key[i%key.length]);
        }
        String resultStr = null;
        try {
            resultStr = new String(result, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return resultStr;
    }


    /**
     * 将字符串转成素数
     * @param str
     * @return
     */
    public static int getHashPrime(String str) {
        int randomPrime ;
        String hash = Hash.getMD5StrJava(str);
        long before =new BigInteger(hash.substring(0, 16), 16).longValue();
        long after = new BigInteger(hash.substring(16, 32), 16).longValue();
        long seed  = before/2+after/2;
        randomPrime = BigInteger.probablePrime(Constant.PRIME_BIT_NUM, new Random(seed)).intValue();
        return randomPrime;
    }

    public static Element fun(byte[] key, String data, Field field){
        String keyStr = Hash.byte2Hex(key);
        String hash = Hash.getMD5StrJava(keyStr + data);
        long left = new BigInteger(hash.substring(0,16), 16).longValue();
        long right = new BigInteger(hash.substring(16,32), 16).longValue();
        long seed = (left + right) / 2;
        Random random = new Random(seed);
        // 获取域的最大值
        BigInteger order = field.getOrder();
        int bitLength = order.bitLength();
        BigInteger randomBigInteger = new BigInteger(bitLength, random);
        return field.newElement().set(randomBigInteger).getImmutable();
    }

    public static String[] authEnc(byte[] km, String data){
//        return StringUtil.byteArrayToHexString(func(km, data));
//        String s = AES.encryptAES(km, data);
        // s 表示家加密的数据， tag 用来对加密的 s 验证
        String s = encrypt(km, data);
        String tag = StringUtil.byteArrayToHexString(func(km, s));
        String[] result = new String[2];
        result[0] = s;
        result[1] = tag;
        return result;
    }

    public static boolean authCheck(byte[] km, String[] data){
        String tmpTag = StringUtil.byteArrayToHexString(func(km, data[0]));
        if(tmpTag.equals(data[1])) return true;
        return false;
    }

    public static String authDecrypt(byte[] km, String data){
//        return AES.decryptAES(km, data);
        return decrypt(km, data);
    }

    public static String getEnvData(String[] env){
        // 获取密文数据值，即ρ
        return env[0];
    }

    public static String getEnvVerify(String[] env){
        // 获取密文验证值
        return env[1];
    }

    @Deprecated
    public static byte[] functao(BigInteger bigInteger, BigInteger i, int secureParam) {
        long seed = bigInteger.multiply(BigInteger.TEN).add(i).longValue();
        Random random = new Random(seed);
//        BigInteger randomBigInteger = new BigInteger(secureParam, random);
//        return randomBigInteger.toByteArray();
        int byteNum = secureParam / 8;
        byte[] result = new byte[byteNum];
        random.nextBytes(result);
        return result;
    }

    public static byte[] functao(Element element, int i, int secureParam){
        long seed = new BigInteger(element.toBytes()).pow(10).add(BigInteger.valueOf(i)).longValue();
        Random random = new Random(seed);
        int byteNum = secureParam / 8;
        byte[] result = new byte[byteNum];
        random.nextBytes(result);
        return result;
    }

    // 只是为了测试
    public static String getRDK(String ind) {
        return "DefaultDecryptKey";
    }
}