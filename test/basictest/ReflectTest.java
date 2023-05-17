package basictest;

import org.junit.Test;

import java.net.URL;

/**
 * @author: Leilei Du
 * @Date: 2018/7/8 10:38
 */
public class ReflectTest {
    @Test
    public void fun1() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Object str = new String("hahaha");
//        System.out.println(str);
        String className = str.getClass().getName();
        System.out.println(className);
    }

    @Test
    public void fun2(){
//        String result = ReflectTest.class.getResource("\\improved\\clients.properties").toString();
        String result = this.getClass().getResource("/improved/clients.properties").toString();
        System.out.println(result);
    }
}