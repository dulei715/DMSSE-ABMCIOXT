package cn.edu.hun.pisces.test;

import cn.edu.hun.pisces.utils.ByteUtil;
import org.junit.Test;

/**
 * @author: Leilei Du
 * @Date: 2018/7/18 9:02
 */
@SuppressWarnings("ALL")
public class ByteUtilTest {
    @Test
    public void fun1(){
        byte bytes[] = new byte[10];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte)(i+7);
        }
        String bytesStr = ByteUtil.getString(bytes, ", ", ";");
        System.out.println(bytesStr);

        String newBytesStr = bytesStr.replace(";","");
        System.out.println(newBytesStr);
        byte[] bytes1 = ByteUtil.toBytes(newBytesStr,", ");
        for (int i = 0; i < bytes1.length; i++) {
            System.out.println(bytes1[i]);
        }
    }

    @Test
    public void fun2(){
        byte bytes[] = new byte[10];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte)(i+7);
        }
        String bytesStr = ByteUtil.getString(bytes, ", ");
        System.out.println(bytesStr);

        System.out.println(bytesStr);
        byte[] bytes1 = ByteUtil.toBytes(bytesStr,", ");
        for (int i = 0; i < bytes1.length; i++) {
            System.out.println(bytes1[i]);
        }
    }
}