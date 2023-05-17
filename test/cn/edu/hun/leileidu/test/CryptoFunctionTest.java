package cn.edu.hun.leileidu.test;

import cn.edu.hun.leileidu.utils.BigIntegerUtil;
import cn.edu.hun.leileidu.utils.Constant;
import cn.edu.hun.leileidu.utils.cryptography.BilinearUtil;
import cn.edu.hun.leileidu.utils.cryptography.Hash;
import cn.edu.hun.leileidu.utils.ioutil.MyPrint;
import cn.edu.hun.leileidu.utils.cryptography.CryptoFunciton;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;
import java.util.Random;

public class CryptoFunctionTest {

    CryptoFunciton cryptoFunciton = null;

    @Before
    public void beforeFun(){
        cryptoFunciton = new CryptoFunciton();
    }

    @Test
    public void fun1(){
        BigInteger bigInteger = BigInteger.probablePrime(30, new Random());
        System.out.println(bigInteger);
    }

    @Test
    public void fun2(){
        BigInteger b1 = new BigInteger("7");
        BigInteger b2 = new BigInteger("3");
        BigInteger result = BigIntegerUtil.powBigInteger(b1, b2);
        System.out.println(result);
    }

    @Test
    public void fun3(){
        String key = "wohuohuohahaxixihehe";
        String data = "mynameisduleilei!";
        int p = 867988061;
//        int result = CryptoFunciton.funcp(key.getBytes(), data, p);
//        System.out.println(result);
    }

    @Test
    public void fun4(){
        byte[] result = CryptoFunciton.getKeyBytes(64);
        MyPrint.showBytes(result, ", ");
    }


    @Test
    public void fun5(){
        BigInteger bigInteger = new BigInteger("57");
//        byte[] bytes = CryptoFunciton.func(bigInteger, BigInteger.ONE, Constant.DEFAULT_SECURITY_PARAMETER);
//        MyPrint.showBytes(bytes);
    }

    @Test
    public void fun6(){
        byte[] km = Constant.DEFAULT_K_M;
        String data = "hahahehehuohuoda";
        String[] strings = CryptoFunciton.authEnc(km, data);
        System.out.println(strings[0]);
        System.out.println(strings[1]);

        boolean result = CryptoFunciton.authCheck(km, strings);
        System.out.println(result);

        String plain = CryptoFunciton.authDecrypt(km, strings[0]);
        System.out.println(plain);


    }

    @Test
    public void fun7_0(){
        byte b = (byte) 16;
        String s = Integer.toHexString(b);
        System.out.println(s);
//        byte b1 = Byte.parseByte(s);
        byte b1 = Byte.parseByte(s, 16);
        System.out.println(b1);

//        String ss = "86";
//        byte b2 = Byte.parseByte(ss, 16);
//        System.out.println(b2);
        byte bb = (byte)255;
        byte bbb = (byte) (bb & 0xff);
        System.out.println(bb);
        System.out.println(bbb);
    }

    @Test
    public void fun7(){
        byte[] key = Constant.DEFAULT_K_S;
        String data = "abcdefghigklmnopqrstuvwxyz";
        System.out.println(data);
        String encrypt = CryptoFunciton.encrypt(key, data);
        System.out.println(encrypt);
        String decrypt = CryptoFunciton.decrypt(key, encrypt);
        System.out.println(decrypt);

    }

    @Test
    public void fun8(){
        String ss = Hash.getMD5StrJava("hahaha");
        System.out.println(ss);
    }

    @Test
    public void fun9(){
        BilinearUtil bilinearUtil = new BilinearUtil();
        Field zField = bilinearUtil.getzField();

        Element elem = zField.newElement().set(7).getImmutable();
        Element invert = elem.invert().getImmutable();
        System.out.println(elem);
        System.out.println(invert);
        System.out.println(elem.mul(invert));

        System.out.println("**********************************************************");

//        Element generator = bilinearUtil.getBilinearG();
        Element gPowValue = bilinearUtil.getGPowValue(elem);

        Element gPowValue1 = bilinearUtil.getGPowValue(elem);

        System.out.println(gPowValue.equals(gPowValue1));

        System.out.println(gPowValue.hashCode() == gPowValue1.hashCode());


        System.out.println(gPowValue.toString().hashCode() == gPowValue1.toString().hashCode());

    }

    @Test
    public void fun10(){
        BilinearUtil bilinearUtil = new BilinearUtil();
        Field zField = bilinearUtil.getzField();
        Field gField = bilinearUtil.getgField();
//        Element result = CryptoFunciton.getHashValueFromZField(Constant.DEFAULT_K_S, "hahahaxixi", gField);
        Element oneElement = gField.newOneElement();
        System.out.println(oneElement.isEqual(gField.newZeroElement()));
//        System.out.println(result);

    }

    @Test
    public void fun11(){
        BilinearUtil bilinearUtil = new BilinearUtil();
        Field gField = bilinearUtil.getgField();
        Element element = gField.newRandomElement().getImmutable();
        System.out.println(element);
        byte[] bytes = element.toBytes();
        MyPrint.showBytes(bytes);
        BigInteger bigInteger = new BigInteger(bytes);
        System.out.println(bigInteger);

        System.out.println("*************************************************");

        System.out.println(element.toBigInteger());

    }


    @After
    public void afterFun(){

    }

}