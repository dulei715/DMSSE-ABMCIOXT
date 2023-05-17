package basictest;

import cn.edu.hun.pisces.basestruct.TSetElement;
import cn.edu.hun.pisces.related.basestruct.stag.IntegerStag;
import cn.edu.hun.pisces.related.basestruct.stag.Stag;
import cn.edu.hun.pisces.related.basestruct.stag.StringStag;
import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Leilei Du
 * @Date: 2018/7/9 12:50
 */
public class ListTest {
    @Test
    public void fun1(){
        List<TSetElement> list = new ArrayList<>();
        TSetElement tSetElement = new TSetElement("haha", new BigInteger("123"));
        list.add(tSetElement);
        System.out.println(list);
    }

    @Test
    public void fun2(){
        List<Number> list = new ArrayList<>();
        list.add(Integer.valueOf(1));
        list.add(Integer.valueOf(2));
        list.add(Integer.valueOf(3));
        list.add(Integer.valueOf(4));
        list.add(Double.valueOf(3.14));
        System.out.println(list);
        for (Number number : list) {
            System.out.println(number.getClass().getName());
        }
    }

    public void testList(List<? extends Stag> stagList){
        System.out.println(stagList);
    }

    @Test
    public void fun3(){
        List<StringStag> stringStagList = new ArrayList<>();
        testList(stringStagList);
    }

    public List testList2(List<? super Stag> stagList){

        System.out.println(stagList);

        stagList.add(new StringStag());
        stagList.add(new IntegerStag(123));
        return stagList;
    }

    @Test
    public void fun4(){
        List<Object> objectList = new ArrayList<>();
        List list = testList2(objectList);
        System.out.println(objectList);
        System.out.println(list);
    }

}