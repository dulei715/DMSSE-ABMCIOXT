package cn.edu.hun.pisces.utils;

/**
 * @author: Leilei Du
 * @Date: 2018/7/8 17:43
 */
public class StringUtil {
    private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
    /**
     * 将一个字节转化成十六进制形式的字符串
     * @param b 字节数组
     * @return 字符串
     */
    public static String byteToHexString(byte b) {
        int ret = b;
        if (ret < 0) {
            ret += 256;
        }
        int m = ret / 16;
        int n = ret % 16;
        return hexDigits[m] + hexDigits[n];
    }

    public static byte hexStringToByte(String str){
        int temp = Integer.parseInt(str,16);
        return (byte)temp;
    }

    public static byte[] hexStringToByteArray(String str){
        int len = str.length();
        if (len % 2 != 0) {
            throw new RuntimeException("被转换字符串不是偶数！");
        }
        int count = len / 2;
        byte[] result = new byte[count];
        String temp;
        for (int i = 0; i < count; i++) {
            temp = str.substring(i*2, (i+1)*2);
            result[i] = hexStringToByte(temp);
        }
        return result;
    }

    /**
     * 转换字节数组为十六进制字符串
     * @param bytes 字节数组
     * @return 十六进制字符串
     */
    public static String byteArrayToHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(byteToHexString(bytes[i]));
        }
        return sb.toString();
    }

    public static String reverse(String str){
        StringBuilder sb = new StringBuilder(str);
        sb.reverse();
        return sb.toString();
    }

}