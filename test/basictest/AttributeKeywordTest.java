package basictest;

import cn.edu.hun.leileidu.ospir_oxt.basestruct.AttributeKeyword;
import org.junit.Test;

/**
 * @author: Leilei Du
 * @Date: 2018/7/22 10:28
 */
public class AttributeKeywordTest {
    @Test
    public void fun1(){
        AttributeKeyword attributeKeywordA = new AttributeKeyword("name", "duleilei", 20);
        AttributeKeyword attributeKeywordB = new AttributeKeyword("name", "dulei", 20);
        AttributeKeyword attributeKeywordC = new AttributeKeyword("age","24",10);
        AttributeKeyword attributeKeywordD = new AttributeKeyword("name", "duleilei", 20);
        System.out.println(attributeKeywordA.equals(attributeKeywordB));
        System.out.println(attributeKeywordA.equals(attributeKeywordC));
        System.out.println(attributeKeywordA.equals(attributeKeywordD));
        System.out.println();
        System.out.println(attributeKeywordA.hashCode());
        System.out.println(attributeKeywordD.hashCode());
    }

    @Test
    public void fun2(){
        AttributeKeyword attributeKeywordA = new AttributeKeyword("computer", "non");
        AttributeKeyword attributeKeywordB = new AttributeKeyword("computer", "Non");
//        System.out.println(attributeKeywordA.equals(attributeKeywordB));
        System.out.println(attributeKeywordA);
    }

}