package basictest;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author: Leilei Du
 * @Date: 2018/11/16 21:42
 */
public class FileTest {
    @Test
    public void fun1() throws IOException {
        File file = new File("F:\\Users\\Administrator\\IdeaProjects\\MutiClientOXTImprove\\datafile\\data1.txt");
        System.out.println(file.getName());
        System.out.println(file.getAbsoluteFile());
        System.out.println(file.getCanonicalPath());
    }
}