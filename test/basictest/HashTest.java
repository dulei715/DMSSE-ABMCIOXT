package basictest;

import cn.edu.hun.leileidu.utils.cryptography.Hash;
import cn.edu.hun.leileidu.utils.ioutil.MyPrint;
import org.junit.Test;

import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author: Leilei Du
 * @Date: 2018/11/17 11:23
 */
public class HashTest {
    @Test
    public void fun1() throws NoSuchAlgorithmException, DigestException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        String data = "hahaxixihehe";
        md5.update(data.getBytes());
        byte[] digest = md5.digest();
        MyPrint.showBytes(digest, ",");
        String s = Hash.byte2Hex(digest);
        System.out.println(s);
    }
}