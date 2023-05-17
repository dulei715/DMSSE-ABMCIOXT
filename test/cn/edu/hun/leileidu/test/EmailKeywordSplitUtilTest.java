package cn.edu.hun.leileidu.test;

import cn.edu.hun.leileidu.utils.Constant;
import cn.edu.hun.leileidu.utils.fileutil.EmailKeywordSplitUtil;
import org.junit.Test;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author: Leilei Du
 * @Date: 2018/11/15 13:12
 */
public class EmailKeywordSplitUtilTest {
    @Test
    public void fun1(){
        String dirIn = "F:\\Users\\Administrator\\IdeaProjects\\OXTImprovedataset\\dataset";
        String fileOut = "F:\\Users\\Administrator\\IdeaProjects\\OXTImprovedataset\\z_out_can_delete\\keywordResult.txt";
        long startTime = System.currentTimeMillis();
        EmailKeywordSplitUtil.splitAndWriteToFile(dirIn, fileOut);
        long endTime = System.currentTimeMillis();
        long useTime = endTime - startTime;
        System.out.println("USing time is: " + useTime);
    }

    @Test
    public void fun2(){
        String filePath = "F:\\Users\\Administrator\\IdeaProjects\\OXTImprovedataset\\z_out_can_delete\\keywordResult.txt";
        long startTime = System.currentTimeMillis();
        Map<String, List<File>> result = EmailKeywordSplitUtil.readReverseIndexFromFile(filePath);
        long endTime = System.currentTimeMillis();
        long useTime = endTime - startTime;
        System.out.println("USing time is: " + useTime);
        System.out.println(result.size());
//        System.out.println(result.get("Content: Slater").size());
        List<File> fileList = result.get("Content: Slater");
        for (File file : fileList) {
            System.out.println(file.getName());
        }
    }

    @Test
    public void fun3(){
        String filePath = "F:\\Users\\Administrator\\IdeaProjects\\OXTImprovedataset\\z_out_can_delete\\keywordResult.txt";
        Map<String, List<File>> result = EmailKeywordSplitUtil.readReverseIndexFromFile(filePath);
        Set<String> keySet = result.keySet();
        Set<String> attrSet = new HashSet<>();
        for (String s : keySet) {
            String[] parts = s.split(Constant.DEFAULT_COMBINE_SYMBOL);
            attrSet.add(parts[0]);
        }
        System.out.println(attrSet);
        System.out.println("The size is: " + attrSet.size());
    }

    @Test
    public void fun4(){
        String filePath = "F:\\Users\\Administrator\\IdeaProjects\\OXTImprovedataset\\z_out_can_delete\\keywordResult.txt";
        Map<String, List<File>> result = EmailKeywordSplitUtil.readReverseIndexFromFile(filePath);
        int count = 0;
        for (Map.Entry<String, List<File>> stringListEntry : result.entrySet()) {
            count += stringListEntry.getValue().size();
        }
        System.out.println(count);
    }



}