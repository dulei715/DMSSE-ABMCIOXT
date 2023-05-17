package usefultest;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Leilei Du
 * @Date: 2018/7/15 13:59
 */
public class GeneratePrimeInFieldTest {

    public boolean isPrime(int num){
        if (num <= 1){
            return false;
        }
        if (num == 2){
            return true;
        }
        for (int i = 2; i * i <= num; ++i) {
            if (num % i == 0){
                return false;
            }
        }
        return true;
    }

    @Test
    public void fun1(){
        int field = 312;
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i < field; ++i) {
            if (isPrime(i)){
                list.add(i);
            }
        }
        Object[] intList = list.toArray();
        System.out.println(intList.length);
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        int i = 0;
        for (; i < intList.length - 1; ++i) {
            sb.append(intList[i]).append(", ");
        }
        sb.append(intList[i]).append("}");

        System.out.println(sb.toString());

    }

    @Test
    public void fun2(){
        int value = 1073741567;
        System.out.println(isPrime(value));
    }

}