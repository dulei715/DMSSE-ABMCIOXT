package improve.base.keytest;

import cn.edu.hun.leileidu.improved.others.basestruct.key.StringKey;
import org.junit.Test;

/**
 * @author: Leilei Du
 * @Date: 2018/7/15 14:17
 */
public class StringKeyTest {
    @Test
    public void fun1(){
        String kStr1 = new String("hahaxixi");
        String kStr2= new String("hahaxixi");
        StringKey stringKey1 = new StringKey(kStr1);
        StringKey stringKey2 = new StringKey(kStr2);
        System.out.println(stringKey1);
        System.out.println(stringKey2);
        System.out.println(stringKey1.equals(stringKey2));
        System.out.println(stringKey1.hashCode());
        System.out.println(stringKey2.hashCode());
    }
}