package basictest;

import cn.edu.hun.pisces.utils.Constant;
import cn.edu.hun.pisces.utils.cryptography.BilinearUtil;
import cn.edu.hun.pisces.utils.cryptography.Hash;
import it.unisa.dia.gas.jpbc.*;
import it.unisa.dia.gas.plaf.jpbc.field.gt.GTFiniteElement;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import it.unisa.dia.gas.plaf.jpbc.pairing.a.TypeACurveGenerator;
import it.unisa.dia.gas.plaf.jpbc.pairing.a1.TypeA1CurveGenerator;
import it.unisa.dia.gas.plaf.jpbc.pbc.curve.PBCTypeACurveGenerator;
import it.unisa.dia.gas.plaf.jpbc.pbc.curve.PBCTypeDCurveGenerator;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

/**
 * @author: Leilei Du
 * @Date: 2018/7/13 17:23
 */
public class JPBCTest {

    @Test
    public void baseFun(){
        int x, y, result1, result2;
        x = 903;
        y = 826;
        result1 = y*y % 911;
        result2 = (x*x*x + x) % 911;
        System.out.println(result1);
        System.out.println(result2);



    }

    @Test
    public void fun0(){
        int rBit = 160;
        int qBit = 512;
        TypeACurveGenerator pg = new TypeACurveGenerator(rBit, qBit);
        PairingParameters generate = pg.generate();
        System.out.println(generate);
    }

    @Test
    public void fun1(){
        int rBit = 20;
        int qBit = 20;
        TypeACurveGenerator pg = new TypeACurveGenerator(rBit, qBit);
        PairingParameters generate = pg.generate();
        System.out.println(generate);
    }
    @Test
    public void fun1_1(){
        int rBit = 5;
        int qBit = 5;
        TypeACurveGenerator pg = new TypeACurveGenerator(rBit, qBit);
        PairingParameters generate = pg.generate();
        System.out.println(generate);
    }

    @Test
    public void fun1_2(){
        int rBit = 30;
        int qBit = 30;
        TypeA1CurveGenerator pg = new TypeA1CurveGenerator(rBit, qBit);
        PairingParameters generate = pg.generate();
        System.out.println(generate);
    }

    @Test
    public void fun2(){

        Pairing pairing = PairingFactory.getPairing("curve.properties");
        Field g1 = pairing.getG1();
        Field g2 = pairing.getG2();
        Field gt = pairing.getGT();
        Field rz = pairing.getZr();
        System.out.println(g1);
        System.out.println(g2);
        System.out.println(gt);
        System.out.println(rz);
    }

    @Test
    public void fun3(){
//        PairingFactory.getInstance().setUsePBCWhenPossible(true);
        Pairing pairing = PairingFactory.getPairing("curve.properties");
        Element in1 = pairing.getG1().newRandomElement().getImmutable();
        Element in2 = pairing.getG2().newRandomElement().getImmutable();
        Element out = pairing.pairing(in1, in2);
        System.out.println(in1);
        System.out.println(in2);
        System.out.println(out);
    }

    @Test
    public void fun4(){
        int rBit = 5;
        int qBit = 5;
        TypeACurveGenerator pg = new TypeACurveGenerator(rBit, qBit);
        PairingParameters typeParams = pg.generate();
        Pairing pairing = PairingFactory.getPairing(typeParams);
        System.out.println(typeParams);
        System.out.println("***************************************************");
        Element element1 = pairing.getG1().newRandomElement().getImmutable();
        Element element2 = pairing.getG2().newRandomElement().getImmutable();
        Element result = pairing.pairing(element1, element2);
        System.out.println(element1);
        System.out.println(element2);
        System.out.println(result);
    }

    @Test
    public void fun5(){
        Pairing pairing = PairingFactory.getPairing("curve.properties");
        Element in1 = pairing.getG1().newRandomElement();
        System.out.println(in1);
//        in1.set(new BigInteger("2"));
        in1.setToOne();
        System.out.println(in1);
    }

    @Test
    public void fun6(){
        Pairing pairing = PairingFactory.getPairing("curve.properties");
        Field field = pairing.getG1();
        Element element = field.newElement().getImmutable();
        Element element1 = field.newZeroElement().getImmutable();
        Element element2 = field.newOneElement().getImmutable();
        Element element3 = field.newRandomElement().getImmutable();
        Element element4 = field.newElement().getImmutable();
        System.out.println(element);
        System.out.println(element1);
        System.out.println(element2);
        System.out.println(element3);
        System.out.println(element4);

    }

    @Test
    public void fun7(){
        Pairing pairing = PairingFactory.getPairing("curve.properties");
        Pairing pairing1 = PairingFactory.getPairing("curve.properties");
        Field field = pairing.getG1();
        Field field1 = pairing1.getG1();
        Element i = field.newElement().setFromHash(Constant.DEFAULT_K_I, 0, Constant.DEFAULT_K_I.length);
        Element j = field1.newElement().setFromHash(Constant.DEFAULT_K_I, 0, Constant.DEFAULT_K_I.length);
        System.out.println(i);
        System.out.println(j);

        Element pairing2 = pairing.pairing(i, j);
        System.out.println(pairing2);

    }

    @Test
    public void fun8() throws UnsupportedEncodingException {
        Pairing pairing = Constant.DEFAULT_BILINEAR_PAIRING;
        Field g1 = pairing.getG1();
        Field z = pairing.getZr();
        String str1 = new String("wjfoiefavn eiouohgsfnfakjbv.m,Znbk;gaj");
        String str2 = new String("iopwsdfbakjggbdfsfsdf,,mnbvgfkhqhp;jkdfsbnbvlag");
        String str3 = new String("sdfaasddfn eiouohgfalkjhahfsakjZnbk;gaj");

//        Element element = g1.newElement().setFromHash(str1.getBytes("UTF-8"), 0, str1.length());
//        Element element2 = g1.newElement().setFromHash(str2.getBytes("UTF-8"), 0, str1.length());

        Element element = z.newElement().setFromHash(str1.getBytes("UTF-8"), 0, str1.length()).getImmutable();
        Element element2 = z.newElement().setFromHash(str2.getBytes("UTF-8"), 0, str1.length()).getImmutable();
        Element element3 = z.newElement().setFromHash(str3.getBytes("UTF-8"), 0, str1.length()).getImmutable();

        System.out.println(element);
        System.out.println(element2);
        System.out.println(element3);

        System.out.println("***********************************************");

        Element element4 = z.newElement().set(0).getImmutable();
        System.out.println(element4);

        Element e1 = g1.newRandomElement().getImmutable();
        System.out.println(e1);

        Element e2 = g1.newRandomElement().getImmutable();
        System.out.println(e2);

        Element e3 = e1.mul(e2).getImmutable();
        System.out.println(e3);

    }

    @Test
    public void fun9() throws UnsupportedEncodingException {
        Pairing pairing = Constant.DEFAULT_BILINEAR_PAIRING;
        Field g1 = pairing.getG1();
        Field z = pairing.getZr();
        String str1 = new String("wjfoiefavn eiouohgsfnfakjbv.m,Znbk;gaj");
        String str2 = new String("iopwsdfbakjggbdfsfsdf,,mnbvgfkhqhp;jkdfsbnbvlag");
        String str3 = new String("sdfaasddfn eiouohgfalkjhahfsakjZnbk;gaj");

        Element baseElem = g1.newElement().setFromHash(str1.getBytes("UTF-8"), 0, str1.length()).getImmutable();
        Element element2 = z.newElement().setFromHash(str2.getBytes("UTF-8"), 0, str1.length()).getImmutable();

        Element element3 = z.newElement().setFromHash(str3.getBytes("UTF-8"), 0, str1.length()).getImmutable();

        System.out.println(baseElem);


        BilinearUtil bilinearUtil = new BilinearUtil();
        Element bilinearG = bilinearUtil.getBilinearG();
        System.out.println(bilinearG);


        System.out.println();
        System.out.println(baseElem.pow(new BigInteger("-1")));
        System.out.println(baseElem.pow(new BigInteger("0")));
        System.out.println(baseElem.pow(new BigInteger("1")));
        System.out.println(baseElem.pow(new BigInteger("2")));
        System.out.println(baseElem.pow(new BigInteger("3")));
        System.out.println("-----------------------------------");
        System.out.println(element2);
        System.out.println(element3);

        System.out.println("*********************************************");

        BigInteger bigA = element2.toBigInteger();
        BigInteger bigB = element3.toBigInteger();
        Element multi = z.newElement().set(bigA.multiply(bigB));

        System.out.println(multi);

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++");

        Element pairing1 = pairing.pairing(baseElem.powZn(element2).getImmutable(), baseElem.powZn(element3).getImmutable());

        Element pairing2 = pairing.pairing(baseElem, baseElem.powZn(multi).getImmutable());

        System.out.println(pairing1);

        System.out.println(pairing2);

        System.out.println(pairing1.equals(pairing2));

        System.out.println(pairing1.hashCode());
        System.out.println(pairing2.hashCode());

    }

    @Test
    public void fun10(){
        BilinearUtil bilinearUtil = new BilinearUtil();
        Pairing defaultPairing = bilinearUtil.getDefaultPairing();
        Element e1 = defaultPairing.getG1().newRandomElement().getImmutable();
        Element e2 = defaultPairing.getG2().newRandomElement().getImmutable();

        Element pairing = defaultPairing.pairing(e1, e2);
        System.out.println(pairing);

        System.out.println(pairing.getClass());

        GTFiniteElement pairing1 = (GTFiniteElement) pairing;

        BigInteger bigInteger = pairing1.toBigInteger();

        System.out.println(bigInteger);

    }

    @Test
    public void fun11(){
        BilinearUtil bilinearUtil = new BilinearUtil();
        Field field = bilinearUtil.getzField();
        Element element = field.newElement(10).getImmutable();
        Element element1 = field.newOneElement().add(field.newOneElement()).getImmutable();
        System.out.println("element:" + element);
        System.out.println("element1:" + element1);

        Element element2 = element.add(element1).getImmutable();
        System.out.println("element2:" + element2);

        Element element33 = element.div(element1).getImmutable();
        System.out.println("element33:" + element33);

        Element element3 = element.div(element1.add(element1)).getImmutable();
        System.out.println("element3:" + element3);

        Element element4 = element3.invert();
        System.out.println("element4:" + element4);

        System.out.println(element3.mul(element4));

        System.out.println();
        System.out.println("element2:" + element2);
        System.out.println("element2.square():" + element2.square());
        System.out.println("element2.sqrt():" + element2.sqrt());


    }

    @Test
    public void fun12(){
        PairingFactory.getInstance().setUsePBCWhenPossible(true);
        int discriminant = 9563;
        PBCTypeDCurveGenerator curveGenerator = new PBCTypeDCurveGenerator(discriminant);
        PairingParameters generate = curveGenerator.generate();
        System.out.println(generate);
    }

    @Test
    public void fun13(){
        BilinearUtil bilinearUtil = new BilinearUtil();
        Element bilinearG = bilinearUtil.getBilinearG();
        System.out.println(bilinearG);
    }

    @Test
    public void fun14(){
        Pairing pairing = PairingFactory.getPairing("curve.properties");
        Element[] in1 = new Element[5];
        Element[] in2 = new Element[5];
        for (int i = 0; i < in1.length; i++) {
            in1[i] = pairing.getG1().newElement();
            System.out.println("in1["+ i +"]:" + in1[i]);
        }
        for (int i = 0; i < in2.length; i++) {
            in2[i] = pairing.getG2().newElement();
            System.out.println("in2["+ i +"]:" + in2[i]);
        }
        System.out.println();
        Element out = pairing.pairing(in1, in2);
        System.out.println(out);
    }


    @Test
    public void fun15() throws UnsupportedEncodingException {
        Pairing pairing = Constant.DEFAULT_BILINEAR_PAIRING;
        Field g1 = pairing.getG1();
        Field g2 = pairing.getG2();
        Field z = pairing.getZr();
        String str1 = new String("wjfoiefavn eiouohgsfnfakjbv.m,Znbk;gaj");
        String str2 = new String("iopwsdfbakjggbdfsfsdf,,mnbvgfkhqhp;jkdfsbnbvlag");
        String str3 = new String("sdfaasddfn eiouohgfalkjhahfsakjZnbk;gaj");


        Element element = g1.newElement().setFromHash(str1.getBytes("UTF-8"), 0, str1.length()).getImmutable();
        Element element2 = g2.newElement().setFromHash(str1.getBytes("UTF-8"), 0, str1.length()).getImmutable();

        System.out.println(element);
        System.out.println(element2);
    }

    @Test
    public void fun16(){
        BilinearUtil bilinearUtil = new BilinearUtil();
        Field zField = bilinearUtil.getzField();
        BigInteger a = new BigInteger("3");
        BigInteger b = new BigInteger("4");
        Element indexA = zField.newElement().set(a).getImmutable();
        Element indexB = zField.newElement().set(b).getImmutable();

        System.out.println("indexA: " + indexA);
        System.out.println("indexB: " + indexB);

    }

    @Test
    public void fun17(){
        Pairing pairing = PairingFactory.getPairing("curve_test.properties");
        String s = "sdfjhsdakfhlsk";
        Field zr = pairing.getZr();
        Element element = pairing.getG1().newElement().setFromHash(s.getBytes(), 0, s.length());
        BigInteger bint = BigInteger.valueOf(21L);
        Element result1 = element.pow(bint);
        Element result2 = element.powZn(zr.newElement().set(bint));

        System.out.println(result1);
        System.out.println(result2);

        System.out.println("*******************************************************");

        System.out.println(zr.getOrder());

        System.out.println("*******************************************************");

        Element element1 = zr.newElement().set(BigInteger.valueOf(19L));
        System.out.println(element1);

        System.out.println("*******************************************************");


        Element element2 = zr.newElement().set(BigInteger.ONE.negate());
        System.out.println(element2);

        System.out.println("**********************************************************");

        Element element3 = zr.newElement().set(BigInteger.valueOf(5));

        Element element4 = element3.pow(BigInteger.valueOf(2));

        System.out.println(element4);

    }

    @Test
    public void fun18(){
        Pairing pairing = PairingFactory.getPairing("curve_test.properties");
        String s = "sdfjhsdakfhlsk";
        Field zr = pairing.getZr();
        BigInteger zrOrder = zr.getOrder();
        System.out.println("zrOrder: " + zrOrder);
        Element element = zr.newZeroElement();
        System.out.println(element);
        System.out.println("*************************************************");
        Field g1 = pairing.getG1();
        BigInteger g1Order = g1.getOrder();
        System.out.println("g1Order: " + g1Order);
        Element element1 = g1.newZeroElement();
        System.out.println(element1);
        Element element2 = element1.pow(BigInteger.TEN.negate());
        System.out.println(element2);
        System.out.println("*************************************************");
//        Element element3 = zr.newRandomElement();
        Element element3 = zr.newElement(BigInteger.valueOf(18));
        element3.add(zr.newOneElement());
        System.out.println(element3);
        System.out.println("*************************************************");
        Element element4 = g1.newOneElement();
        System.out.println(element4);
        System.out.println(element1.equals(element4));
    }

    @Test
    public void fun19(){
        Pairing pairing = PairingFactory.getPairing("curve_test.properties");
        String s = "sdfjhsdakfhlsk";
        Field zr = pairing.getZr();
        Field g1 = pairing.getG1();
//        Element generator = g1.newRandomElement().getImmutable();
        Element generator = g1.newElement().setFromHash("hahaha".getBytes(), 0, 6).getImmutable();
//        Element generator = g1.newElement().setFromBytes("hahaha".getBytes()).getImmutable();
//        Element generator = g1.newElementFromBytes("hahaha".getBytes()).getImmutable();

//        Element generator1 = g1.newOneElement().getImmutable();
//        Element generator2 = g1.newOneElement().getImmutable();
//        Element generator = generator1.add(generator2).getImmutable();
//        System.out.println(generator1);
//        System.out.println(generator2);
//        System.out.println(generator);

        System.out.println("*********************************************");


        BigInteger order = g1.getOrder();
        System.out.println("The generator is: " + generator);
        System.out.println("The order is: " + order);
        int orderInt = order.intValue();
        for (int i = 0; i <= orderInt + 1; i++) {
            Element ind = zr.newElement(i);
            Element element = generator.powZn(ind).getImmutable();
            Element element1 = generator.pow(BigInteger.valueOf(i)).getImmutable();
            boolean result = element.isEqual(g1.newOneElement());
            boolean result1 = element1.isEqual(g1.newOneElement());
            System.out.println(result + " --- " + result1 + ": " + element + " --- " + element1);
        }
        System.out.println("************************************************");
    }

    @Test
    public void fun20(){
        BilinearUtil bilinearUtil = new BilinearUtil();
        Field gField = bilinearUtil.getgField();
        Element element = gField.newRandomElement();
        byte[] bytes = element.toBytes();
        String s = Hash.byte2Hex(bytes);
        System.out.println(s);
        byte[] bytes1 = Hash.Hex2byte(s);
        Element element1 = gField.newElementFromBytes(bytes1);
        System.out.println(element);
        System.out.println(element1);
        System.out.println(element.equals(element1));
    }

}