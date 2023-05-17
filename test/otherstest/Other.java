package otherstest;

import cn.edu.hun.pisces.utils.Constant;
import cn.edu.hun.pisces.utils.StringUtil;
import cn.edu.hun.pisces.utils.cryptography.BilinearUtil;
import cn.edu.hun.pisces.utils.cryptography.CryptoFunciton;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.*;

/**
 * @author: Leilei Du
 * @Date: 2018/7/14 16:09
 */
public class Other {
    @Test
    public void fun1(){
        //1073741567
        BigInteger bint1 = new BigInteger("1073741567");
        BigInteger bint2 = new BigInteger("673");
        System.out.println(bint1.mod(bint2));
    }

    @Test
    public void fun2(){
        final int[] arr = null;
//        FinalTestClass finalTestClass = new FinalTestClass("123");
    }

    public void add(Collection<Integer> collection){
        collection.add(new Integer(1));
        collection.add(new Integer(2));
    }

    @Test
    public void fun3(){
        List<Integer> list = new ArrayList<>();
        list.add(new Integer(3));
        add(list);
        System.out.println(list);

        Set<Integer> set = new HashSet<>();
        set.add(new Integer(4));
        add(set);
        System.out.println(set);

    }
    public void modify(Integer integer){
        integer = new Integer("3");
    }
    @Test
    public void fun4(){
        Integer integer = new Integer("2");
        modify(integer);
        System.out.println(integer);
    }

    @Test
    public void fun5(){
        int maxValue = Integer.MAX_VALUE;
        System.out.println(maxValue);
    }

    @Test
    public void fun6() throws UnsupportedEncodingException {
        BilinearUtil bilinearUtil = new BilinearUtil();
        Field gField = bilinearUtil.getgField();
        String strA = "Content: Message";
        String strB = "Content: better";
        Element hashA = CryptoFunciton.getHashValueFromGroupField(strA, gField);
        Element hashB = CryptoFunciton.getHashValueFromGroupField(strB, gField);
        System.out.println(hashA);
        System.out.println(hashB);
        System.out.println(hashA.isEqual(hashB));
        System.out.println();

        Field zField = bilinearUtil.getzField();
        byte[] byteA = strA.getBytes("utf-8");
        byte[] byteB = strB.getBytes("utf-8");

        Element indA = zField.newElement().setFromHash(byteA, 0, byteA.length).getImmutable();
        Element indB = zField.newElement().setFromHash(byteB, 0, byteB.length).getImmutable();
        System.out.println(indA);
        System.out.println(indB);
        System.out.println();
//        System.out.println(strA.hashCode());
//        System.out.println(strB.hashCode());

        System.out.println(StringUtil.reverse(strA));
    }

    @Test
    public void fun7(){
        BilinearUtil bilinearUtil = new BilinearUtil();
        Field zField = bilinearUtil.getzField();
        Field gField = bilinearUtil.getgField();

        String sterm = "Content: better";
        String xterm = "Content: attorney";
        Element hashSterm = CryptoFunciton.getHashValueFromGroupField(sterm, gField);
        Element hashXterm = CryptoFunciton.getHashValueFromGroupField(xterm, gField);
        System.out.println("hashSterm: " + hashSterm);
        System.out.println("hashXterm: " + hashXterm);

        String ind = "F:\\Users\\Administrator\\IdeaProjects\\OXTImprovedataset\\dataset_00\\45.txt";
        Element xind = CryptoFunciton.getHashValueFromZField(Constant.DEFAULT_K_I, ind, zField);
        System.out.println("xind: " + xind);

        Element kt = zField.newElement().set(471914).getImmutable();
        Element kx = zField.newElement().set(68914).getImmutable();
        int c = 5;
        Element ks = zField.newElement().set(527219).getImmutable();
        Element strap = hashSterm.powZn(ks).getImmutable();
        System.out.println("strap: " + strap);

        byte[] kz = CryptoFunciton.functao(strap, 1, Constant.DEFAULT_SECURITY_PARAMETER);
        byte[] ke = CryptoFunciton.functao(strap, 2, Constant.DEFAULT_SECURITY_PARAMETER);

        Element z = CryptoFunciton.getHashValueFromZField(kz, String.valueOf(c), zField);
        System.out.println("z: " + z);
        Element y = xind.mulZn(z.invert()).getImmutable();
        System.out.println("y: " + y);

        Element xtoken = hashXterm.powZn(kx.mulZn(z)).getImmutable();
        System.out.println("xtoken: " + xtoken);

        Element stagResult = xtoken.powZn(y).getImmutable();
        System.out.println("stagResult: " + stagResult);

        Element stagReal = hashXterm.powZn(kx.mulZn(xind)).getImmutable();
        System.out.println("stagReal: " + stagReal);

    }

}