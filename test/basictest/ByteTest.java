package basictest;

import org.junit.Test;

import java.io.*;
import java.util.Properties;

/**
 * @author: Leilei Du
 * @Date: 2018/7/8 18:01
 */
public class ByteTest {
    @Test
    public void fun1(){
        String str = "ff";
        int b = Integer.parseInt(str,16);
        byte bb = (byte)b;
        System.out.println(b);
        System.out.println(bb);
    }

    @Test
    public void fun2() throws IOException {
        String path = "datafile\\bytetest.properties";
        OutputStream out = new FileOutputStream(new File(path));
        byte[] bytes = new byte[10];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte)(i+7);
        }
        Properties properties = new Properties();
        properties.setProperty("test", bytes.toString());
        properties.store(out, "test for bytes");
    }

}