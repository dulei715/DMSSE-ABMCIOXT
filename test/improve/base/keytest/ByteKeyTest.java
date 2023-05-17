package improve.base.keytest;

import cn.edu.hun.leileidu.improved.others.basestruct.key.ByteKey;
import cn.edu.hun.leileidu.improved.others.basestruct.key.KeyAbstract;
import cn.edu.hun.leileidu.utils.Constant;
import org.junit.Test;

/**
 * @author: Leilei Du
 * @Date: 2018/7/15 13:31
 */
public class ByteKeyTest {

    @Test
    public void fun0(){
        Byte[] bytes1 = new Byte[]{1,2,3};
        Byte[] bytes2 = new Byte[]{1,2,3};
        System.out.println(bytes1.equals(bytes2));
    }



    @Test
    public void fun1(){
        ByteKey byteKey = new ByteKey(Constant.DEFAULT_K_I);
        System.out.println(byteKey);
        //64个
        ByteKey byteKey1 = new ByteKey(new byte[]{
                -93, 26, -82, 99, -29, -31, -104, 103, 90, -107, 25,
                -2, -32, 0, -1, -96, -117, -113, -39, 89, 78, -18, -19,
                -16, 72, 105, 16, -4, 67, -98, 76, 13, 5, -27, -81, -5,
                -48, 9, -108, 125, 114, -122, 16, -18, -20, 112, -100, 19,
                -117, -43, -121, -7, 85, -104, 60, -31, -80, -96, 84, -19,
                -103, 26, 65, 37
        });
        System.out.println(byteKey1);
        System.out.println(byteKey.equals(byteKey1));
        System.out.println(byteKey.hashCode());
        System.out.println(byteKey1.hashCode());
    }

    @Test
    public void fun2(){
        KeyAbstract<byte[]> key = new ByteKey(Constant.DEFAULT_K_I);
        System.out.println(key);

        String s = key.toHexByteString(70);
        System.out.println(s);

    }

    @Test
    public void fun3(){
        StringBuilder sb = new StringBuilder();
        sb.append("hwfhvanvklvn");
        sb.setLength(5);
        System.out.println(sb);
    }

}