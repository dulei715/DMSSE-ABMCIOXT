package cn.edu.hun.pisces.test;

import cn.edu.hun.pisces.related.basestruct.stag.Stag;
import cn.edu.hun.pisces.related.basestruct.stag.StringStag;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * @author: Leilei Du
 * @Date: 2018/7/8 10:44
 */
public class StagTest {
    @Test
    public void fun1(){
        StringStag stag1 = new StringStag("haha");
        StringStag stag2 = new StringStag("xixi");
        StringStag stag3 = new StringStag("haha");
        System.out.println(stag1.equals(stag2));
        System.out.println(stag1.equals(stag3));
    }

    @Test
    public void fun2(){
        Stag stag1 = new StringStag("haha");
        Stag stag2 = new StringStag("xixi");
        Stag stag3 = new StringStag("haha");
        Set<Stag> set = new HashSet<>();
        set.add(stag1);
        set.add(stag2);
        set.add(stag3);
        System.out.println("set: " + set);

        System.out.println("stag1.hashCode(): " + stag1.hashCode());
        System.out.println("stag3.hashCode(): " + stag3.hashCode());
        System.out.println("stag1.hashCode()==stag3.hashCode(): "+ (stag1.hashCode()==stag3.hashCode()));
        System.out.println("stag1.equals(stag3):" + stag1.equals(stag3));

        set.add(new StringStag("haha"));
        System.out.println(set);
        for (Stag stag : set) {
            System.out.println(stag.hashCode());
        }

        set.remove(new StringStag("haha"));
        System.out.println(set);

    }

    @Test
    public void fun3(){
        String s1 = new String("hahaha");
        String s2 = new String("hahaha");
        System.out.println(s1.hashCode());
        System.out.println(s2.hashCode());
        Object o1 = new String("hahaha");
        Object o2 = new String("hahaha");
        System.out.println(o1.hashCode());
        System.out.println(o2.hashCode());
    }

    @Test
    public void fun4(){
        Object s1 = new String("haha");
        Object s2 = new String("haha");
        Object s3 = new String("haha");
        Set<Object> set = new HashSet<>();
        set.add(s1);
        set.add(s2);
        set.add(s3);
        System.out.println(set);
    }


}