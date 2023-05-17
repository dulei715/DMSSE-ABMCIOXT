package basictest;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Leilei Du
 * @Date: 2018/7/16 14:36
 */
public class MapTest {
    @Test
    public void fun1(){
        Map<String, List<String>> map = new HashMap();
        List<String> strList = new ArrayList<>();
        strList.add(new String("heheda"));
        map.put(new String("xixi"), strList);
        System.out.println(map);
        strList.add(new String("woca"));
        System.out.println(map);
    }
}