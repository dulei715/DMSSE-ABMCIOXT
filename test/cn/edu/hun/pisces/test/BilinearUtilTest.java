package cn.edu.hun.pisces.test;

import cn.edu.hun.pisces.utils.cryptography.BilinearUtil;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.plaf.jpbc.field.gt.GTFiniteElement;
import org.junit.Test;

import java.math.BigInteger;

/**
 * @author: Leilei Du
 * @Date: 2018/7/14 15:13
 */
public class BilinearUtilTest {
    @Test
    public void fun1(){
        BilinearUtil bilinearUtil = new BilinearUtil();
        BigInteger bintA = new BigInteger("123");
        BigInteger bintB = new BigInteger("456");
        String pair = bilinearUtil.getPair(bintA, bintB);
        System.out.println(pair);
        BigInteger bintC = new BigInteger("123");
        BigInteger bintD = new BigInteger("456");
        String pair2 = bilinearUtil.getPair(bintC, bintD);
        System.out.println(pair2);
    }

    @Test
    public void fun2(){
        BilinearUtil bilinearUtil = new BilinearUtil();
        BigInteger bintA = new BigInteger("789");
        BigInteger bintB = new BigInteger("456");
        String pair = bilinearUtil.getPair(bintA, bintB);
        System.out.println("pair:" + pair);

        BigInteger bintC = bintA.multiply(bintB);
        String pair1 = bilinearUtil.getTPairString(bintC);
        System.out.println("pair1:" + pair1);

        System.out.println(pair.equals(pair1));
        System.out.println(pair.hashCode());
        System.out.println(pair1.hashCode());

    }

    @Test
    public void fun3(){
        BilinearUtil bilinearUtil = new BilinearUtil();
        Field field = bilinearUtil.getzField();
        System.out.println(field.getOrder());

        Element e1 = field.newElement().set(2).getImmutable();
        System.out.println(e1);

        Element e2 = e1.pow(BigInteger.ONE.negate()).getImmutable();

        System.out.println(e2);

        System.out.println(e2.mul(e1));

        BigInteger bigInteger1 = new BigInteger("2");
        BigInteger bigInteger2 = new BigInteger("536870784");
        BigInteger bigIntegerBase = new BigInteger("1073741567");
        System.out.println(bigInteger1.multiply(bigInteger2).mod(bigIntegerBase));

        int value = bigIntegerBase.intValue();
        System.out.println(value);



    }

    @Test
    public void fun4() throws NoSuchFieldException {
        BilinearUtil bilinearUtil = new BilinearUtil();
        Element zrElement = bilinearUtil.getZrElement(new BigInteger("13"));
        Element powValue = bilinearUtil.getPairWithG(bilinearUtil.getGPowValue(zrElement));
        System.out.println(powValue);
        System.out.println(powValue.getClass());

        GTFiniteElement gtFiniteElement = (GTFiniteElement)powValue;
//        java.lang.reflect.Field pairingMap = gtFiniteElement.getClass().getField("PairingMap");
//        java.lang.reflect.Field value = gtFiniteElement.getClass().getField("value");
//        System.out.println(pairingMap);
//        System.out.println(value);

        java.lang.reflect.Field[] fields = gtFiniteElement.getClass().getFields();
        Class<?> superclass = gtFiniteElement.getClass().getSuperclass();
        System.out.println(superclass.getName());

        java.lang.reflect.Field[] fields1 = superclass.getDeclaredFields();


        System.out.println(fields1.length);
        for (java.lang.reflect.Field field : fields1) {
            System.out.println(field);
        }

        System.out.println("******************************");

        java.lang.reflect.Field pairing = superclass.getDeclaredField("pairing");
        System.out.println(pairing);

    }

    @Test
    public void fun5(){
        BilinearUtil bilinearUtil = new BilinearUtil();
        Element bilinearG = bilinearUtil.getBilinearG();
        Field field = bilinearUtil.getzField();
        System.out.println(field.getOrder());
        BigInteger bigInteger = new BigInteger("1073741569");
        System.out.println(bigInteger);
        System.out.println();
        Element indA = field.newElement().set(bigInteger).getImmutable();
        System.out.println(indA);

        Element res1 = bilinearG.powZn(indA);
        Element res2 = bilinearG.pow(bigInteger);
        System.out.println(res1);
        System.out.println(res2);
    }

    @Test
    public void fun6(){
        BilinearUtil bilinearUtil = new BilinearUtil();
        Field field = bilinearUtil.getzField();
        Element elem1 = field.newElement().set(123);
        Element elem2 = field.newElement().set(123);
        System.out.println(elem1.equals(elem2));

        System.out.println(elem1.hashCode());
        System.out.println(elem2.hashCode());

        System.out.println(elem1.toString().hashCode());
        System.out.println(elem2.toString().hashCode());

    }

}