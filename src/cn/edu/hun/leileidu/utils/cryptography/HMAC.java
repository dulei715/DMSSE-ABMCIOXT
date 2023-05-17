package cn.edu.hun.leileidu.utils.cryptography;

import cn.edu.hun.leileidu.utils.StringUtil;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;

/** 
 * Created by xiang.li on 2015/2/27. 
 */  
public class HMAC {
	//四个伪随机函数密钥
	public static byte[] k1 = {-61,-94,8,50,81,-12,-103,-103,42,48,66,25,-66,84,114,12,90,62,61,113,-115,99,-40,-101,66,-38,99,98,-55,-16,35,86,57,-64,119,32,-49,81,45,-66,49,125,-86,-43,10,115,5,-113,-39,-38,51,-112,78,59,10,-24,-44,55,-19,-110,73,100,36,71};
	public static byte[] k2 = {-2,29,-93,2,-16,96,29,-11,-121,-77,27,-26,70,-101,-102,110,-6,-53,104,108,39,82,-100,40,-13,7,-124,39,-92,31,-4,60,21,64,-120,119,75,62,-41,-24,127,-123,38,-54,51,-115,119,-92,-12,-116,110,68,-125,51,-40,127,90,-6,76,-117,-102,77,113,4};
	public static byte[] k3 = {103,26,-66,81,31,71,19,41,64,-112,86,71,-31,54,-113,-42,8,-61,28,-91,88,-1,113,-78,49,51,14,84,100,55,-83,-45,44,-96,21,54,-85,-91,92,-6,70,84,-43,-9,-77,59,-124,-12,-18,27,-46,-61,11,43,89,51,103,-28,-60,-90,78,15,70,123};	
	public static byte[] k4 = {51,11,124,43,113,-19,101,15,35,59,107,71,-120,-93,98,-113,-5,66,-81,33,90,1,113,31,-10,90,56,-20,-128,56,-68,90,-89,53,-104,6,-42,-70,-43,-69,108,-95,18,-13,54,-117,87,-15,91,51,-22,-45,118,14,67,-63,-48,32,-38,-115,81,66,98,11};
    /** 
     * 定义加密方式 
     * MAC算法可选以下多种算法 
     * <pre> 
     * HmacMD5 
     * HmacSHA1 
     * HmacSHA256 
     * HmacSHA384 
     * HmacSHA512 
     * </pre> 
     */  
    private final static String KEY_MAC = "HmacMD5";//hash函数算法
  
    /** 
     * 构造函数 
     */  
    public HMAC() {}    
  
    /** 
     * 初始化HMAC密钥 
     * @return 
     */  
    public static byte[] init() {  
        SecretKey key;  
        byte[] keybyte= {};  
        try {  
            KeyGenerator generator = KeyGenerator.getInstance(KEY_MAC);//通过提供的加密算法生成密钥生成对象
            key = generator.generateKey();//生成密钥对象            
            keybyte = key.getEncoded(); //得到密钥的字节数组 
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return keybyte;  
    }  
  
    /** 
     * HMAC加密 
     * @param data 需要加密的字节数组 
     * @param keybyte 密钥
     * @return 字节数组 
     */  
    public static byte[] encryptHMAC(byte[] keybyte, byte[] data) {
        SecretKey secretKey;  
        byte[] bytes = null;  
        try {  
            secretKey = new SecretKeySpec(keybyte, KEY_MAC);//根据密钥字节数字和算法得到密钥对象  
            Mac mac = Mac.getInstance(secretKey.getAlgorithm());  
            mac.init(secretKey);  
            bytes = mac.doFinal(data);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return bytes;  
    }  
  
    /** 
     * HMAC加密 
     * @param data 需要加密的字符串 
     * @param key 密钥 
     * @return 字符串 
     */  
    public static String encryptHMAC(byte[] key, String data) {
        if (data.isEmpty()||data==" ") {  
            return null;  
        }  
        byte[] bytes = encryptHMAC(data.getBytes(), key);  
        return StringUtil.byteArrayToHexString(bytes);//得到的摘要为20字节
    }  
  
  


    /** 
     * 转换字符串为16进制编码
     * @param s 字符串 
     * @return 十六进制字符串 
     */
    @Deprecated //没有考虑一个字符转换后的位数
    public static String toHexString(String s) {  
       String str = "";  
       for (int i = 0; i < s.length(); i++) {  
        int ch = (int) s.charAt(i);  
        String s4 = Integer.toHexString(ch);  
        str = str + s4;  
       }  
       return str;  
    }  
    // 转化十六进制编码为字符串  
    public static String toStringHex1(String s) {  
       byte[] baKeyword = new byte[s.length() / 2];  
       for (int i = 0; i < baKeyword.length; i++) {  
        try {  
         baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(  
           i * 2, i * 2 + 2), 16));  
        } catch (Exception e) {  
         e.printStackTrace();  
        }  
       }  
       try {  
        s = new String(baKeyword, "utf-8");// UTF-16le:Not  
       } catch (Exception e1) {  
        e1.printStackTrace();  
       }  
       return s;  
    }  
    /** 
     * 测试方法 
     * @param args 
     */  
    public static void main(String[] args) throws Exception {    

    }  
}