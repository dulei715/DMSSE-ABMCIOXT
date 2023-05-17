package basictest;

import cn.edu.hun.pisces.utils.ioutil.MyPrint;
import org.junit.Test;

import javax.crypto.spec.DHPrivateKeySpec;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

/**
 * @author: Leilei Du
 * @Date: 2018/7/7 16:22
 */
public class KeyFactoryTest {
    BigInteger g = new BigInteger("71");
    BigInteger p = new BigInteger("867988061");
    BigInteger x = new BigInteger("7");
    KeySpec keySpec = new DHPrivateKeySpec(x, p, g);
    KeyFactory keyFactory = null;

    @Test
    public void fun1() throws NoSuchAlgorithmException, InvalidKeySpecException {
        keyFactory = KeyFactory.getInstance("DiffieHellman");
        PrivateKey sk = keyFactory.generatePrivate(keySpec);
//        PublicKey pk = keyFactory.generatePublic(keySpec);
        byte[] skEncode = sk.getEncoded();
//        byte[] pkEncode = pk.getEncoded();
        MyPrint.showBytes(skEncode);
//        MyPrint.showBytes(pkEncode);
    }


}