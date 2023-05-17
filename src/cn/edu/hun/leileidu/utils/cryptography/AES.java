package cn.edu.hun.leileidu.utils.cryptography;

import cn.edu.hun.leileidu.utils.Constant;
import cn.edu.hun.leileidu.utils.StringUtil;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Arrays;

/**
 * @author: Leilei Du
 * @Date: 2018/7/8 16:20
 */
public class AES {
    private static int keyLength = Constant.DEFAULT_AES_KEY_LENGTH;


    private static Cipher initAESCipher(byte[] key, int cipherMode){
        Cipher cipher = null;
        SecretKey secretKey = null;
        try {
            cipher = Cipher.getInstance("AES/ECB/PKCS7Padding");
            secretKey = getKey(key);
            cipher.init(cipherMode, secretKey);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return cipher;
    }

    public static byte[] encryptAES(byte[] key, byte[] plainDataBytes){
        Cipher cipher = initAESCipher(key, Cipher.ENCRYPT_MODE);
        byte[] result = null;
        try {
            result = cipher.doFinal(plainDataBytes);
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static byte[] decryptAES(byte[] key, byte[] cipherDataBytes){
        Cipher cipher = initAESCipher(key, Cipher.DECRYPT_MODE);
        byte[] result = null;
        try {
            result = cipher.doFinal(cipherDataBytes);
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static SecretKey getKey(byte[] keyBytes) {
        byte[] realKeyBytes = new byte[keyLength / 8];
        SecretKeySpec key = null;
        Arrays.fill(realKeyBytes, (byte) 0x0);
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        int length = keyBytes.length < realKeyBytes.length ? keyBytes.length : realKeyBytes.length;
        System.arraycopy(keyBytes, 0, realKeyBytes, 0, length);
        key = new SecretKeySpec(realKeyBytes, "AES");
        return key;
    }

    public static String encryptAES(byte[] key, String plainData){
        byte[] encStrByte = null;
        String encStr = null;
        try {
            encStrByte = encryptAES(key,plainData.getBytes("UTF-8"));
            encStr = StringUtil.byteArrayToHexString(encStrByte);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encStr;
    }

    public static String decryptAES(byte[] key, String cipherData){
        byte[] decStrByte = null;
        String decStr = null;
        decStrByte = decryptAES(key, StringUtil.hexStringToByteArray(cipherData));
        try {
            decStr = new String(decStrByte, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return decStr;
    }

}