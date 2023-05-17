package cn.edu.hun.pisces.test;

import cn.edu.hun.pisces.utils.fileutil.FileSplitUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.HashMap;

public class FileSplitUtilTest2 {
    private String filePath = null;
    private File file = null;
    private BufferedReader bufr = null;
    //private BufferedWriter bufw = null;

    @Before
    public void beforeFun() throws FileNotFoundException {
        filePath = "datafile\\data2.txt";
        file = new File(filePath);
        bufr = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
    }


    @Test
    public void fun1(){
        HashMap<String, Integer> result = FileSplitUtil.extractKeyword(file);
        System.out.println(result);
    }


    @After
    public void afterFun() throws IOException {
        if(bufr != null){
            bufr.close();
        }
    }
}