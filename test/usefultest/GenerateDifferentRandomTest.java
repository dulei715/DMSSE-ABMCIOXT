package usefultest;

import cn.edu.hun.pisces.utils.cryptography.DifferentRandom;
import org.junit.Test;

import java.math.BigInteger;
import java.util.HashSet;

/**
 * @author: Leilei Du
 * @Date: 2018/7/15 23:05
 */
public class GenerateDifferentRandomTest {
    @Test
    public void fun1(){
        int clientNum = 20;
        DifferentRandom differentRandom = new DifferentRandom(new HashSet<>(), new BigInteger("1073741567"));
        for (int i = 0; i < clientNum; i++) {
            BigInteger random = differentRandom.getDifferentPositiveBigInteger();
            System.out.println(random);
        }
    }
}