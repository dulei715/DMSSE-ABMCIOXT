package cn.edu.hun.leileidu.test;

import cn.edu.hun.leileidu.basestruct.FileList;
import cn.edu.hun.leileidu.basestruct.Keyword;
import cn.edu.hun.leileidu.related.basestruct.ReverseIndexTable;
import cn.edu.hun.leileidu.utils.Constant;
import cn.edu.hun.leileidu.utils.fileutil.FileSplitUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ReverseIndexTableTest {

    ReverseIndexTable reverseIndexTable = null;

    String fileDir = null;

    @Before
    public void beforeFun(){
        fileDir = "F:\\Users\\Administrator\\IdeaProjects\\OXTImprovedataset\\dataset";
    }


    @Test
    public void fun1(){
        File file = new File(fileDir);
        HashMap<String, List<File>> result = FileSplitUtil.extractReverseIndexData(file, Constant.DEFAULT_REGEX_SPLIT);
//        Set<Map.Entry<String, List<File>>> entrys = result.entrySet();
        reverseIndexTable = new ReverseIndexTable();
        reverseIndexTable.construct(result);
        HashMap<Keyword, FileList> indexTable = reverseIndexTable.getIndexTable();
        Set<Map.Entry<Keyword, FileList>> entrys = indexTable.entrySet();
        for (Map.Entry<Keyword, FileList> entry : entrys) {
            Keyword key = entry.getKey();
            FileList value = entry.getValue();
            String output = key.toString() + ": " + value.getListSize();
            System.out.println(output);
        }

    }


    @After
    public void afterFun(){

    }





}