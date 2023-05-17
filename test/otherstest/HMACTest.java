package otherstest;

import cn.edu.hun.pisces.utils.ioutil.MyPrint;
import cn.edu.hun.pisces.utils.cryptography.HMAC;
import org.junit.Test;

/**
 * @author: Leilei Du
 * @Date: 2018/7/7 15:08
 */
public class HMACTest {
    @Test
    public void fun1(){

        byte[] bytes = new byte[10];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) (7+i);
        }

        String data = "hsudofhafh";

        byte[] result = HMAC.encryptHMAC(bytes, data.getBytes());

        String resultStr = HMAC.encryptHMAC(bytes, data);

        int len = result.length;

        System.out.println(len);

        MyPrint.showBytes(result);

        System.out.println(resultStr);

        System.out.println(resultStr.length());

    }
}