package usefultest;

import cn.edu.hun.pisces.utils.KeyUtil;
import cn.edu.hun.pisces.utils.ioutil.MyPrint;
import org.junit.Test;

import java.util.*;

/**
 * @author: Leilei Du
 * @Date: 2018/7/18 8:29
 */
public class GenerateKeysTest {

    @Test
    public void fun1() {
        String outPath = "datafile\\keys.properties";
        String[] keyNameArr = new String[]{"kt", "ks", "kx", "kz", "ki"};
        Set<String> singleSet = new HashSet<>();
        singleSet.add("ki");
        KeyUtil.generateKeysToFile(outPath, keyNameArr, 64, 10, singleSet);
    }


    @Test
    public void fun2(){
        String inPath = "datafile\\keys.properties";
        Map<String, List<byte[]>> keysFromFile = KeyUtil.getKeysFromFile(inPath);
        MyPrint.showMapCollecitonValueArraybyteValueValue(keysFromFile);
    }

    @Test
    public void fun3(){
        Random random = new Random();
        int len = 64;
        byte[] bytes = new byte[len];
        random.nextBytes(bytes);
        MyPrint.showBytes(bytes, ", ");
    }

}