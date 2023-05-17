package basictest;

import org.junit.Test;

import java.io.File;

/**
 * @author: Leilei Du
 * @Date: 2018/11/17 19:10
 */
public class ClassTest {
    @Test
    public void fun1(){
        String path = this.getClass().getResource("/improved/privilege.properties").getPath();
        System.out.println(path);
        File file = new File(path);
        System.out.println(file.exists());
    }
}