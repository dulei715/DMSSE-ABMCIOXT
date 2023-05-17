package cn.edu.hun.pisces.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

public class ForwardIndexTableTest {
    @Before
    public void beforeFun(){

    }


    @Test
    public void fun1(){
        HashSet<String> set = new HashSet<>();
        set.add("haha");
        set.add("xixi");
        set.add("huohuo");
        set.add("caca");
        System.out.println(set);
        System.out.println(set);
        System.out.println(set);
    }


    @After
    public void afterFun(){

    }
}