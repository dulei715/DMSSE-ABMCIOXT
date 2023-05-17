package basictest;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author: Leilei Du
 * @Date: 2018/11/15 17:03
 */
public class ArraysTest {
    @Test
    public void fun1(){
        Set<String> set = new HashSet<>();
        set.add("123");
        set.add("456");
        set.add("hahaha");
        set.add("123");
        set.add("xixixi");
        set.add("123");
        set.add("xixixi");
        System.out.println(set);

        List<String> strings = Arrays.asList((String[]) set.toArray());
        System.out.println(strings);

    }
}