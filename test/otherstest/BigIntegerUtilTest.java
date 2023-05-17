package otherstest;

import cn.edu.hun.leileidu.utils.BigIntegerUtil;
import cn.edu.hun.leileidu.utils.cryptography.BilinearUtil;
import org.junit.Test;

import java.math.BigInteger;
import java.util.Random;

/**
 * @author: Leilei Du
 * @Date: 2018/7/13 11:23
 */
public class BigIntegerUtilTest {
    @Test
    public void fun1(){
        BigInteger bigInteger = new BigInteger("867988060");
        BigInteger eulerValue = BigIntegerUtil.getEulerValue(bigInteger);
        System.out.println(eulerValue);
    }
    @Test
    public void fun2(){
        BigInteger bigInteger = new BigInteger("100");
        BigInteger bint = new BigInteger("2");
        Random random = new Random();
        BigInteger result = BigIntegerUtil.nextCoprimeBigInteger(bigInteger, random, bint);
        System.out.println(result);
    }

    @Test
    public void fun3(){
        BigInteger bigInteger = BigInteger.probablePrime(20, new Random());
        System.out.println(bigInteger);
    }

    @Test
    public void fun4(){
        BilinearUtil bilinearUtil = new BilinearUtil();
        BigInteger order = bilinearUtil.getgField().getOrder();
        System.out.println(order);
    }

}