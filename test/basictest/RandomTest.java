package basictest;

import org.junit.Test;

import java.util.Random;

/**
 * @author: Leilei Du
 * @Date: 2018/7/7 12:00
 */
public class RandomTest {
    @Test
    public void fun1(){
        Random random = new Random();
        int result = random.nextInt(5);
        System.out.println(result);
        System.out.println(Integer.MAX_VALUE);
        System.out.println(Long.MAX_VALUE);
    }

}