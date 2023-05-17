package cn.edu.hun.leileidu.test;

import cn.edu.hun.leileidu.basestruct.Keyword;
import cn.edu.hun.leileidu.basestruct.KeywordComparator;
import org.junit.Test;

import java.util.*;

/**
 * @author: Leilei Du
 * @Date: 2018/7/10 9:37
 */
public class ClientTest {
    @Test
    public void fun1(){
        Set<Keyword> keywordSet = new HashSet<>();
        keywordSet.add(new Keyword("haha",76));
        keywordSet.add(new Keyword("xixi",36));
        keywordSet.add(new Keyword("hehe",46));
        keywordSet.add(new Keyword("huohuo",16));
        Set<Keyword> tempSet = new HashSet<>(keywordSet);
        TreeSet<Keyword> keywords = new TreeSet<>(new KeywordComparator());

        keywords.addAll(tempSet);
        Keyword sterm = keywords.first();
        tempSet.remove(sterm);
        List<Keyword> list = new ArrayList<>(tempSet);
        //TODO 测试这个keywordSet是否会改变
        System.out.println(keywordSet);
        System.out.println(tempSet);
        System.out.println(keywords);
    }


    @Test
    public void fun2(){
        Set<Keyword> keywordSet = new HashSet<>();
        keywordSet.add(new Keyword("haha",76));
        keywordSet.add(new Keyword("xixi",36));
        keywordSet.add(new Keyword("hehe",46));
        keywordSet.add(new Keyword("huohuo",16));
//        Set<Keyword> kSet = new HashSet<>(keywordSet);
        Set<Keyword> kSet = new HashSet<>();
        kSet.addAll(keywordSet);
        keywordSet.remove(new Keyword("haha",76));
//        keywordSet.add(new Keyword("haha",76));
        System.out.println(keywordSet);
        System.out.println(kSet);
    }

    @Test
    public void fun3(){
        Keyword k1 = new Keyword("haha",76);
        Keyword k2 = new Keyword("haha",76);
        System.out.println(k1 == null ? k2 == null : k1.equals(k2));
        System.out.println(k1 == k2);
        System.out.println(k1.equals(k2));
        Set<Keyword> set = new HashSet<>();
        set.add(k1);
        set.add(k2);
        System.out.println(set);
    }

    @Test
    public void fun4(){
        String s1 = new String("haha");
        String s2 = new String("haha");
        Set<String> set = new HashSet<>();
        set.add(s1);
        set.add(s2);
        System.out.println(set);
    }

}