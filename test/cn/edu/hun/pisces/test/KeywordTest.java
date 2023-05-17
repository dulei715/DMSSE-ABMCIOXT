package cn.edu.hun.pisces.test;

import cn.edu.hun.pisces.basestruct.Keyword;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * @author: Leilei Du
 * @Date: 2018/7/11 21:08
 */
public class KeywordTest {
    @Test
    public void fun1(){
        Keyword k1 = new Keyword("haha");
        Keyword k2 = new Keyword("xixi");
        Keyword k3 = new Keyword("haha");

        Set<Keyword> set = new HashSet<>();
        set.add(k1);
        set.add(k2);
        set.add(k3);

        System.out.println(set);

    }
}