package cn.edu.hun.leileidu.utils.cryptography;

import cn.edu.hun.leileidu.utils.Constant;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Pairing;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

/**
 * @author: Leilei Du
 * @Date: 2018/7/14 13:16
 */
public class BilinearUtil {
    private Pairing defaultPairing = Constant.DEFAULT_BILINEAR_PAIRING;
    private String defaultBilinearStringP = Constant.DEFAULT_BILINEAR_STRING_P;
    private String defaultBilinearStringG = Constant.DEFAULT_BILINEAR_STRING_G;
    private Element bilinearP = null;
    private Element bilinearG = null;
    private Field pField = null;
    private Field gField = null;
    private Field zField = null;
    private Field tField = null;

    private void init(){
        try {
            byte[] pBytes = defaultBilinearStringP.getBytes("UTF-8");
            byte[] gBytes = defaultBilinearStringG.getBytes("UTF-8");
            this.pField = this.defaultPairing.getG1();
            this.gField = this.defaultPairing.getG2();

            bilinearP = this.pField.newElement().setFromHash(pBytes, 0, pBytes.length).getImmutable();
            bilinearG = this.gField.newElement().setFromHash(gBytes, 0, gBytes.length).getImmutable();

            this.zField = this.defaultPairing.getZr();
            this.tField = this.defaultPairing.getGT();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    public BilinearUtil(Pairing defaultPairing) {
        this.defaultPairing = defaultPairing;
        init();
    }

    /**
     * 获取指数的域对象
     * @param bigInteger
     * @return
     */
    public Element getZrElement(BigInteger bigInteger){
        return this.zField.newElement().set(bigInteger).getImmutable();
    }

    public BilinearUtil() {
        init();
    }


    public Pairing getDefaultPairing(){
        return this.defaultPairing;
    }

    public Element getBilinearP(){
        return this.bilinearP;
    }

    public Element getBilinearG(){
        return this.bilinearG;
    }

    public Field getzField(){
        return this.zField;
    }


    /**
     * 计算生成元的给定指数的运算结果
     * @param bigInteger
     * @return
     */
    public Element getPPowValue(BigInteger bigInteger){
        return this.bilinearP.pow(bigInteger).getImmutable();
    }

    public Element getPPowValue(Element indElem){
        return this.bilinearP.powZn(indElem).getImmutable();
    }

    public Element getGPowValue(BigInteger bigInteger){
        return this.bilinearG.pow(bigInteger).getImmutable();
    }

    public Element getGPowValue(Element indElem){
        return this.bilinearG.powZn(indElem).getImmutable();
    }

    public Element getTPowValue(BigInteger bigInteger){
        return this.pair(this.bilinearP, this.bilinearG).pow(bigInteger).getImmutable();
    }

    public Field getpField() {
        return pField;
    }

    public Field getgField() {
        return gField;
    }

    public Field gettField() {
        return tField;
    }

    public Element getTPowValue(Element indElem){
        return this.pair(this.bilinearP, this.bilinearG).powZn(indElem).getImmutable();
    }

    /**
     * 获取指数域的阶数
     * @return
     */
    public BigInteger getZFieldOrder(){
        return this.zField.getOrder();
    }


    /**
     *  计算 e(P^{indexA}, g^{indexB}), 其中indexA和indexB是Element(Z_r)类型.
     *  该方法不同于pairing.getPair(ElementA, ElementB)
     * @param indexA
     * @param indexB
     * @return
     */
    public Element getPair(Element indexA, Element indexB){
        Element elementA = this.bilinearP.powZn(indexA).getImmutable();
        Element elementB = this.bilinearG.powZn(indexB).getImmutable();
        return this.defaultPairing.pairing(elementA, elementB).getImmutable();
    }

    /**
     * 计算 e(P^{a}, g^{b}), 其中a和b是大整数类型
     * @param a
     * @param b
     * @return
     */
    public String getPair(BigInteger a, BigInteger b){
        Element indexA = this.zField.newElement().set(a).getImmutable();
        Element indexB = this.zField.newElement().set(b).getImmutable();
        Element pairing = this.getPair(indexA, indexB).getImmutable();
        return pairing.toString();
    }

    /**
     * 计算 e(elementA, elementB)
     * @param elementA
     * @param elementB
     * @return
     */
    public Element pair(Element elementA, Element elementB){
        return this.defaultPairing.pairing(elementA, elementB).getImmutable();
    }

    /**
     * 计算 e(P, element)
     * @param element
     * @return
     */
    public Element getPairWithP(Element element){
        return this.defaultPairing.pairing(this.bilinearP, element).getImmutable();
    }

    /**
     * 计算 e(element, g)
     * @param element
     * @return
     */

    public Element getPairWithG(Element element){
        return this.defaultPairing.pairing(element, this.bilinearG).getImmutable();
    }

//    public String getPairWithP(BigInteger a){
//        Element index = this.zField.newElement().set(a).getImmutable();
//        Element element = this.bilinearG.powZn(index).getImmutable();
//        Element pairing = this.defaultPairing.pairing(this.bilinearP, element).getImmutable();
//        return pairing.toString();
//    }
//
//    public String getPairWithG(BigInteger a){
//        Element index = this.zField.newElement().set(a).getImmutable();
//        Element element = this.bilinearP.powZn(index).getImmutable();
//        Element pairing = this.defaultPairing.pairing(element, this.bilinearG).getImmutable();
//        return pairing.toString();
//    }
    @Deprecated
    public String getTPairString(BigInteger bint){
        return this.getTPowValue(bint).toString();
    }

}