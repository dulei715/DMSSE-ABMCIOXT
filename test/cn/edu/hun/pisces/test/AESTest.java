package cn.edu.hun.pisces.test;

import cn.edu.hun.pisces.utils.ioutil.MyPrint;
import cn.edu.hun.pisces.utils.cryptography.AES;
import org.junit.Test;

import java.util.Random;

/**
 * @author: Leilei Du
 * @Date: 2018/7/8 17:23
 */
public class AESTest {
    private byte[] bytes = {
            124, 100, 21, 35, -79, 10, 66, -40,
            -88, 51, -57, -95, -93, -46, 13, 102,
            -13, 40, -42, -20, -109, 17, 41, 119,
            32, 104, 10, -90, 87, -42, 123, 100
    };

    @Test
    public void fun1(){
        byte[] bytes = new byte[32];
        Random random = new Random();
        random.nextBytes(bytes);
        MyPrint.showBytes(bytes,", ");
    }

    @Test
    public void fun2(){
        String data = "MyNameIsDuLeileiHahaXixiHuohuoHehe";
        byte[] encryption = AES.encryptAES(bytes, data.getBytes());
        MyPrint.showBytes(encryption);
        byte[] decryption =  AES.decryptAES(bytes, encryption);
        MyPrint.showBytes(decryption);
        String result = new String(decryption);
        System.out.println(result);
    }
    @Test
    public void fun3(){
        String data = "MyNameIsDuLeileiHahaXixiHuohuoHehe";
        String encryptionStr = AES.encryptAES(bytes, data);
        System.out.println(encryptionStr);
        String decryption =  AES.decryptAES(bytes, encryptionStr);
        System.out.println(decryption);
    }

}