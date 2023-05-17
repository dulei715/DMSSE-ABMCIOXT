package cn.edu.hun.pisces.utils.cryptography;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {
	/**
     *  利用java原生的摘要实现MD5加密
     *  摘要结果为16个字节. 32个数字字符
     * @param str 加密后的报文
     * @return 摘要结果为16个字节. 32个数字字符
     */
    public static String getMD5StrJava(String str){
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("MD5");//根据加密方式得到消息摘要对象
            messageDigest.update(str.getBytes("UTF-8"));//将字符串用UTF-8编码准换成字节数组，再用给定的字节数组更新摘要
            encodeStr = byte2Hex(messageDigest.digest());//将摘要字节数组转化成16进制字符串
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }
    
    /**
     *  利用java原生的摘要实现SHA-256加密
     * @param str 加密后的报文
     * @return
     */
    public static String getSHA256StrJava(String str){
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");//根据加密方式得到消息摘要对象
            messageDigest.update(str.getBytes("UTF-8"));//将字符串用UTF-8编码准换成字节数组，再用给定的字节数组更新摘要
            encodeStr = byte2Hex(messageDigest.digest());//将摘要字节数组转化成16进制字符串
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodeStr;//摘要结果为32个字节
    }
    
    /**
     * 将byte转为16进制
     * @param bytes
     * @return
     */
    public static String byte2Hex(byte[] bytes){
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i=0;i<bytes.length;i++){
            temp = Integer.toHexString(bytes[i] & 0xFF);//将整型数用16进制字符串表示
            if (temp.length() == 1){
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

    public static byte[] Hex2byte(String str){
        if((str.length()%2) != 0){
            throw new RuntimeException("cannot cast!");
        }
        byte[] result = new byte[str.length()/2];
        for (int i = 0; i < result.length; i++) {
            String temp = str.substring(2*i, 2*(i+1));

            int intval = Integer.parseInt(temp, 16);
            result[i] = (byte)intval;
        }
        return result;
    }

	public static void main(String[] args) {
	    String minwen ="you are a  pig.";
	    String minwen2 = "you are a  pog.";
	    String miwen = getSHA256StrJava(minwen);
	    System.out.println(miwen);
	    miwen= getMD5StrJava(minwen2);
	    System.out.println(miwen);
	}

}
