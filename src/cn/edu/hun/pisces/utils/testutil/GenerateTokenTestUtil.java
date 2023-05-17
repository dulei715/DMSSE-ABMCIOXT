package cn.edu.hun.pisces.utils.testutil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Leilei Du
 * @Date: 2018/11/22 15:22
 */
public class GenerateTokenTestUtil {
    public static List<Integer> getDefaultTestTokenNumber(){
        List<Integer> numberList = new ArrayList<>();
//        numberList.add(1);
        numberList.add(2);
        numberList.add(5);
        numberList.add(10);
        numberList.add(100);
        return numberList;
    }
}