package basictest;

import org.junit.Test;

/**
 * @author: Leilei Du
 * @Date: 2018/11/18 17:21
 */
public class GroupTest {
    @Test
    public void fun1(){
        for (int i = 0; i < 13; i++) {
            System.out.println("7^" + i +" mod 13: " + Math.pow(7,i) % 13);
        }
    }
}