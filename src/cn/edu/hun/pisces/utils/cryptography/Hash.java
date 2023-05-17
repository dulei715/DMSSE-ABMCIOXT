package cn.edu.hun.pisces.utils.cryptography;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {
	/**
     *  ����javaԭ����ժҪʵ��MD5����
     *  ժҪ���Ϊ16���ֽ�. 32�������ַ�
     * @param str ���ܺ�ı���
     * @return ժҪ���Ϊ16���ֽ�. 32�������ַ�
     */
    public static String getMD5StrJava(String str){
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("MD5");//���ݼ��ܷ�ʽ�õ���ϢժҪ����
            messageDigest.update(str.getBytes("UTF-8"));//���ַ�����UTF-8����׼�����ֽ����飬���ø������ֽ��������ժҪ
            encodeStr = byte2Hex(messageDigest.digest());//��ժҪ�ֽ�����ת����16�����ַ���
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }
    
    /**
     *  ����javaԭ����ժҪʵ��SHA-256����
     * @param str ���ܺ�ı���
     * @return
     */
    public static String getSHA256StrJava(String str){
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");//���ݼ��ܷ�ʽ�õ���ϢժҪ����
            messageDigest.update(str.getBytes("UTF-8"));//���ַ�����UTF-8����׼�����ֽ����飬���ø������ֽ��������ժҪ
            encodeStr = byte2Hex(messageDigest.digest());//��ժҪ�ֽ�����ת����16�����ַ���
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodeStr;//ժҪ���Ϊ32���ֽ�
    }
    
    /**
     * ��byteתΪ16����
     * @param bytes
     * @return
     */
    public static String byte2Hex(byte[] bytes){
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i=0;i<bytes.length;i++){
            temp = Integer.toHexString(bytes[i] & 0xFF);//����������16�����ַ�����ʾ
            if (temp.length() == 1){
                //1�õ�һλ�Ľ��в�0����
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
