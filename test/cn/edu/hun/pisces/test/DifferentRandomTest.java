package cn.edu.hun.pisces.test;

import cn.edu.hun.pisces.utils.cryptography.DifferentRandom;
import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Leilei Du
 * @Date: 2018/7/29 13:00
 */
public class DifferentRandomTest {
    @Test
    public void fun1(){
        DifferentRandom difRandom = new DifferentRandom();
        difRandom.setUpBound(new BigInteger("13"));
        List<BigInteger> bIntList = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            BigInteger elem = difRandom.getDifferentBigInteger();
            bIntList.add(elem);
        }
        System.out.println(bIntList);
    }
}