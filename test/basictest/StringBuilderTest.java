package basictest;

import org.junit.Test;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

/**
 * @author: Leilei Du
 * @Date: 2018/7/12 10:27
 */
public class StringBuilderTest {
    @Test
    public void fun1(){
        StringBuilder sb = new StringBuilder();
        sb.append("haha: ").append(1).append(System.lineSeparator());
        sb.append("xixi: ").append(2).append(System.lineSeparator());
        System.out.println(sb.toString());
        sb.setLength(0);
        sb.append("hehe: ").append(3).append(System.lineSeparator());
        sb.append("huohuo: ").append(4).append(System.lineSeparator());
        System.out.println(sb.toString());
    }
}