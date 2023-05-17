package cn.edu.hun.pisces.utils;

/**
 * @author: Leilei Du
 * @Date: 2018/7/18 8:50
 */
@SuppressWarnings("ALL")
public class ByteUtil {
    public static String getString(byte[] bytes, String splitSymbol, String endSymbol){
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        int len = bytes.length - 1;
        for (; i < len; i++) {
            stringBuilder.append(bytes[i]).append(splitSymbol);
        }
        stringBuilder.append(bytes[i]).append(endSymbol);
        return stringBuilder.toString();
    }

    public static String getString(byte[] bytes, String splitSymbol){
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        int len = bytes.length - 1;
        for (; i < len; i++) {
            stringBuilder.append(bytes[i]).append(splitSymbol);
        }
        stringBuilder.append(bytes[i]);
        return stringBuilder.toString();
    }

    public static byte[] toBytes(String bytesStr, String splitSymbol){
        String[] byteStrs = bytesStr.split(splitSymbol);
        int byteStringLength = byteStrs.length;
        byte[] result = new byte[byteStringLength];
        for (int i = 0; i < byteStringLength; i++) {
            result[i] = Byte.valueOf(byteStrs[i]);
        }
        return result;
    }

}