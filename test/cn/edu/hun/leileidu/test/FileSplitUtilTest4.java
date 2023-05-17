package cn.edu.hun.leileidu.test;

import cn.edu.hun.leileidu.utils.Constant;
import cn.edu.hun.leileidu.utils.fileutil.FileSplitUtil;
import cn.edu.hun.leileidu.utils.ioutil.MyPrint;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class FileSplitUtilTest4 {

    String fileDir = null;

    @Before
    public void beforeFun() throws FileNotFoundException {
        fileDir = "F:\\Users\\Administrator\\IdeaProjects\\OXTImprovedataset\\partpartdataset";
    }


    @Test
    public void fun1(){
        List<String> attributeList = new ArrayList<>();
        attributeList.add("haha");
        attributeList.add("xixi");
        attributeList.add("huohuo");
        File fDir = new File(fileDir);
        Map<String, List<File>> stringListMap = FileSplitUtil.extractMultiFilesReverseIndexDataWithAttribute(fDir, Constant.DEFAULT_REGEX_SPLIT, ":", attributeList);
        MyPrint.showMap(stringListMap);

    }



    @After
    public void afterFun() throws IOException {

    }
}