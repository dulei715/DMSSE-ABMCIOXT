package basictest;

import cn.edu.hun.pisces.utils.ioutil.MyPrint;
import org.junit.Test;

import java.math.BigInteger;
import java.util.Random;

public class BigIntegerTest {
    BigInteger bigInteger = null;

    @Test
    public void fun1(){
        bigInteger = new BigInteger("10");
        System.out.println(bigInteger);
    }
    @Test
    public void fun2(){
        byte[] bytes = new byte[2];
        bytes[0] = bytes[1] = 1;
        bigInteger = new BigInteger(bytes);
        System.out.println(bigInteger);
    }
    @Test
    public void fun3(){
        String str = "-0";
        int radix = 16;
        bigInteger = new BigInteger(str,radix);
        System.out.println(bigInteger);
    }

    @Test
    public void fun4(){
        String str = "123";
        bigInteger = new BigInteger(str);
        System.out.println(bigInteger);
        BigInteger bigint = bigInteger.clearBit(0).clearBit(1);
        System.out.println(bigInteger);
        System.out.println(bigint);
        int result = bigint.compareTo(bigInteger);
        System.out.println(result);
        BigInteger r1 = bigInteger.divide(new BigInteger("2"));
        System.out.println(r1);
    }

    @Test
    public void fun5(){
        bigInteger = new BigInteger("67");
        BigInteger bigint = new BigInteger("-23");
        BigInteger[] result = bigInteger.divideAndRemainder(bigint);
        System.out.println(result[0]);
        System.out.println(result[1]);
    }

    @Test
    public void fun6(){
        bigInteger = new BigInteger("85");
        BigInteger bigint = new BigInteger("34");
        BigInteger result = bigInteger.gcd(bigint);
        System.out.println(result);
    }

    @Test
    public void fun7(){
        bigInteger = new BigInteger("7");
        BigInteger bigint = new BigInteger("1");
        BigInteger mbigint = new BigInteger("17");
        BigInteger result = null;
        String restr = "";
        for (int i = 0; i < 17; i++) {
            result = bigInteger.modPow(bigint,mbigint);
            restr += bigInteger + " ^ " + bigint + " % " + mbigint +
                    " = " + result;
            System.out.println(restr);
            restr = "";
            bigint = bigint.add(new BigInteger("1"));
        }
//        System.out.println(result);
    }

    @Test
    public void fun8(){
        bigInteger = new BigInteger("7");
        BigInteger bigint = new BigInteger("-2");
        BigInteger mbigint = new BigInteger("17");
        BigInteger result = bigInteger.modPow(bigint, mbigint);
        System.out.println(result);
        System.out.println(bigInteger.modInverse(mbigint));
    }

    @Test
    public void fun9(){
        bigInteger = new BigInteger("128");
        int bigint = bigInteger.getLowestSetBit();
        System.out.println(bigint);
    }

    @Test
    public void fun10(){
        bigInteger = new BigInteger("4");
        int certainty = 1;
        System.out.println(bigInteger.isProbablePrime(certainty));
    }

    @Test
    public void fun11(){
        Random random = new Random(1);
        bigInteger = BigInteger.probablePrime(10,random);
        System.out.println(bigInteger);
        bigInteger = BigInteger.probablePrime(10,random);
        System.out.println(bigInteger);
    }

    @Test
    public void fun12(){
        Random random = new Random(12345678L);
        Random random1 = new Random(12345678L);
        BigInteger bigInteger00 = new BigInteger(32,random);
        BigInteger bigInteger01 = new BigInteger(32,random);
        BigInteger bigInteger10 = new BigInteger(32,random1);
        BigInteger bigInteger11 = new BigInteger(32,random1);
        System.out.println(bigInteger00);
        System.out.println(bigInteger01);
        System.out.println(bigInteger10);
        System.out.println(bigInteger11);
    }

    @Test
    public void fun13(){
        BigInteger bigInteger = new BigInteger("13");
        BigInteger bigInteger1 = new BigInteger("17");
        boolean result = bigInteger.compareTo(bigInteger1) < 0;
        System.out.println(result);
    }

    @Test
    public void fun14(){
        BigInteger bigInteger = new BigInteger("12");
        int bitCount = bigInteger.bitCount();
        int bigLength = bigInteger.bitLength();
        byte[] bytes = bigInteger.toByteArray();
        System.out.println(bitCount);
        System.out.println(bigLength);
        MyPrint.showBytes(bytes);
    }


    private void changeBigInteger(BigInteger bigInteger){
        bigInteger = new BigInteger("12");
    }


    @Test
    public void fun15(){
        BigInteger bigInteger = new BigInteger("16");
        changeBigInteger(bigInteger);
        System.out.println(bigInteger);
    }

    @Test
    public void fun16(){
        BigInteger bigInteger = BigInteger.probablePrime(22, new Random());
        System.out.println(bigInteger);
    }


}