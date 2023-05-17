package basictest;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * @author: Leilei Du
 * @Date: 2018/11/20 14:13
 */
public class SetTest {
    private void putSet(Set<String> set){
        set.add("fdgvsdafd");
        set.add("fdgvsdafsdfjsadd");
        set.add("fdgvsdafd4t5ert");
    }
    @Test
    public void fun1(){
        Set<String> set = new HashSet<>();
        putSet(set);
        System.out.println(set);
    }
}