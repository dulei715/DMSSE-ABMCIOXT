package basictest;

import cn.edu.hun.leileidu.utils.ioutil.MyPrint;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Random;

/**
 * @author: Leilei Du
 * @Date: 2018/7/7 15:32
 */
public class StringTest {
    @Test
    public void fun1() throws UnsupportedEncodingException {
        byte[] bytes = new byte[64];
        Random random = new Random();
        random.nextBytes(bytes);
        MyPrint.showBytes(bytes,", ");
    }

    @Test
    public void fun2(){
        String str1 = new String("LET");
        String str2 = new String("LDs");
        System.out.println(str1.hashCode());
        System.out.println(str2.hashCode());
    }

    @Test
    public void fun3(){
        String fileName = "F:\\Users\\Administrator\\IdeaProjects\\MutiClientOXTImprove\\datafile\\data1.txt";
        byte[] bytes = fileName.getBytes();
        System.out.println(bytes.length);
    }

}