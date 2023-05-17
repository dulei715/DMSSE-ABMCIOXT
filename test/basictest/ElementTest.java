package basictest;

import cn.edu.hun.pisces.utils.Constant;
import cn.edu.hun.pisces.utils.cryptography.BilinearUtil;
import cn.edu.hun.pisces.utils.cryptography.CryptoFunciton;
import com.sun.jna.Pointer;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import org.junit.Test;

import java.math.BigInteger;

/**
 * @author: Leilei Du
 * @Date: 2018/7/14 21:38
 */
public class ElementTest {
    @Test
    public void fun1(){
        BilinearUtil bilinearUtil = new BilinearUtil();
        Field field = bilinearUtil.getzField();
        BigInteger order = field.getOrder();
        System.out.println(order);
        int lengthInBytes = field.getLengthInBytes();
        System.out.println(lengthInBytes);
    }

    @Test
    public void fun2(){
        BilinearUtil bilinearUtil = new BilinearUtil();
//        Field field = bilinearUtil.getzField();
//        String data = "hahahahsfhweoioidfvcn ,";
//        Element fun = CryptoFunciton.fun(Constant.DEFAULT_K_I, data, field);
//        System.out.println(fun);
        System.out.println("ahaha");
    }

}