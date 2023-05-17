package cn.edu.hun.leileidu.test;

import cn.edu.hun.leileidu.basestruct.Keyword;
import cn.edu.hun.leileidu.basestruct.KeywordComparator;
import cn.edu.hun.leileidu.utils.TreeSetUtil;
import org.junit.Test;

import java.util.Collection;
import java.util.HashSet;
import java.util.TreeSet;

/**
 * @author: Leilei Du
 * @Date: 2018/7/11 22:41
 */
public class TreeSetUtilTest {
    @Test
    public void fun1(){
        Keyword[] keywords = new Keyword[5];
        keywords[0] = new Keyword("haha",2);
        keywords[1] = new Keyword("xixi",1);
        keywords[2] = new Keyword("hehe",5);
        keywords[3] = new Keyword("huahua",3);
        keywords[4] = new Keyword("haha",4);

        TreeSet treeSet = new TreeSet(new KeywordComparator());

        for (int i = 0; i < keywords.length; i++) {
            treeSet.add(keywords[i]);
        }

        System.out.println(treeSet);

        Collection<Keyword> collection = new HashSet<>();
        collection.add(new Keyword("hehe"));
        collection.add(new Keyword("haha"));
        collection.add(new Keyword("xixi"));

        Keyword firstAppearElement = TreeSetUtil.getFirstAppearElement(treeSet, collection, new KeywordComparator());
        System.out.println(firstAppearElement);


    }



}