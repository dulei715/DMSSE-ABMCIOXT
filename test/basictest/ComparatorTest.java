package basictest;

import cn.edu.hun.leileidu.basestruct.Keyword;
import cn.edu.hun.leileidu.basestruct.KeywordComparator;
import org.junit.Test;

import java.util.TreeSet;

/**
 * @author: Leilei Du
 * @Date: 2018/7/9 17:26
 */
public class ComparatorTest {
    @Test
    public void fun1(){
        TreeSet<Keyword> treeSet = new TreeSet<>(new KeywordComparator());
        Keyword k1 = new Keyword("haha", 23);
        Keyword k2 = new Keyword("xixi", 16);
        Keyword k3 = new Keyword("ycccc", 8);
        treeSet.add(k1);
        treeSet.add(k2);
        treeSet.add(k3);
        System.out.println(treeSet);
    }
}