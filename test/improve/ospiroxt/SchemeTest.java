package improve.ospiroxt;

import cn.edu.hun.pisces.utils.Constant;
import org.junit.Test;

import java.math.BigInteger;

/**
 * @author: Leilei Du
 * @Date: 2018/8/1 9:27
 */
public class SchemeTest {
    @Test
    public void fun1(){
        BigInteger z = new BigInteger("631839721");
        BigInteger bxtoken = new BigInteger("94907473");
        BigInteger y = new BigInteger("423438739");

        BigInteger p = new BigInteger("630360059");
        BigInteger inverseP = p.modInverse(Constant.DEFAULT_BIG_EULER_PRIME);

        BigInteger result = bxtoken.modPow(y.multiply(inverseP), Constant.DEFAULT_BIG_PRIME);

        System.out.println(result);

        System.out.println("***************************************************");

        BigInteger bxtrap = new BigInteger("514917283");
        BigInteger compareBxtoken = bxtrap.modPow(z, Constant.DEFAULT_BIG_PRIME);
        System.out.println(compareBxtoken);

    }
}