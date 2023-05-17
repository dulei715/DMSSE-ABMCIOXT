package cn.edu.hun.leileidu.test;

import cn.edu.hun.leileidu.utils.Constant;
import cn.edu.hun.leileidu.utils.fileutil.FileSplitUtil;
import cn.edu.hun.leileidu.utils.ioutil.MyPrint;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FileSplitUtilTest3 {

    String singleFileStr = null;
    String fileDir = null;

    @Before
    public void beforeFun() throws FileNotFoundException {
        singleFileStr = "F:\\Users\\Administrator\\IdeaProjects\\OXTImprovedataset\\dataset\\1.txt";
        fileDir = "F:\\Users\\Administrator\\IdeaProjects\\OXTImprovedataset\\dataset";
    }


    @Test
    public void fun1(){
        File file = new File(singleFileStr);
        HashMap<String, Integer> result = FileSplitUtil.extractKeyword(file);
        MyPrint.showMap(result);
    }

    @Test
    public void fun2(){
        File file = new File(singleFileStr);
        HashMap<String, List<File>> result = FileSplitUtil.extractReverseIndexData(file, Constant.DEFAULT_REGEX_SPLIT, new HashMap<>());
        Set<Map.Entry<String, List<File>>> entrys = result.entrySet();
        for (Map.Entry<String, List<File>> entry : entrys) {
            String key = entry.getKey();
            List<File> value = entry.getValue();
            System.out.println(key+": "+value.size());
        }
    }

    @Test
    public void fun3(){
        File file = new File(fileDir);
        HashMap<String, List<File>> result = FileSplitUtil.extractReverseIndexData(file, Constant.DEFAULT_REGEX_SPLIT);
        Set<Map.Entry<String, List<File>>> entrys = result.entrySet();
        for (Map.Entry<String, List<File>> entry : entrys) {
            String key = entry.getKey();
            List<File> value = entry.getValue();
            System.out.println(key+": "+value.size());
        }
    }


    @After
    public void afterFun() throws IOException {

    }
}