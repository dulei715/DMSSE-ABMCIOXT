package usefultest;

import org.junit.Test;

import java.math.BigInteger;
import java.util.Random;

/**
 * @author: Leilei Du
 * @Date: 2018/7/14 16:05
 */
public class GenerateBigPrimeTest {
    @Test
    public void fun1(){
        int bit = 10;
        BigInteger bigInteger = BigInteger.probablePrime(bit, new Random());
        System.out.println(bigInteger);
    }
}