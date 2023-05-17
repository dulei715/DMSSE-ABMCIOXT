package usefultest;

import cn.edu.hun.pisces.utils.Constant;
import cn.edu.hun.pisces.utils.MapUtil;
import cn.edu.hun.pisces.utils.fileutil.EmailKeywordSplitUtil;
import cn.edu.hun.pisces.utils.fileutil.FileSplitUtil;
import cn.edu.hun.pisces.utils.ioutil.MyPrint;
import org.junit.Test;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author: Leilei Du
 * @Date: 2018/7/18 9:27
 */
public class TestKeywordNumber {
    @Test
    // 输出关键词
    public void fun1() {
        String path = "F:\\Users\\Administrator\\IdeaProjects\\OXTImprovedataset\\partpartdataset";
//        String outPath = "datafile\\fortest\\keyword_part_part_10.properties";
        HashMap<String, List<File>> stringListHashMap = FileSplitUtil.extractReverseIndexData(new File(path), Constant.DEFAULT_REGEX_SPLIT);
        MyPrint.showMap(stringListHashMap);
        System.out.println("keyword_number:" + stringListHashMap.size());
    }

    @Test
    public void fun2() throws IOException {
        String path = "F:\\Users\\Administrator\\IdeaProjects\\OXTImprovedataset\\partpartdataset";
        String outPath = "datafile\\fortest\\keyword_part_part_10.properties";
        HashMap<String, List<File>> stringListHashMap = FileSplitUtil.extractReverseIndexData(new File(path), Constant.DEFAULT_REGEX_SPLIT);
//        System.out.println("keyword_number:" + stringListHashMap.size());
        Properties properties = new Properties();
        String comment = "keyword: keyword number is " + stringListHashMap.size();

        for (Map.Entry<String, List<File>> stringListEntry : stringListHashMap.entrySet()) {
            String key = stringListEntry.getKey();
            List<File> fileList = stringListEntry.getValue();
            String keyReal = key + "(" + fileList.size() + ")";
            StringBuilder stringBuilder = new StringBuilder();
            int i = 0;
            for (; i < fileList.size() - 1; i++) {
                stringBuilder.append(fileList.get(i).getName()).append(";");
            }
            stringBuilder.append(fileList.get(i).getName());
            properties.setProperty(keyReal, stringBuilder.toString());
            stringBuilder.setLength(0);
        }
        OutputStream out = new FileOutputStream(outPath);
        properties.store(out, comment);
        out.close();
    }


    @Test
    public void fun3() {
        String[] paths = new String[5];
        paths[0] = "F:\\Users\\Administrator\\IdeaProjects\\OXTImprovedataset\\out_reverse_index\\dataset_out_20.txt";
        paths[1] = "F:\\Users\\Administrator\\IdeaProjects\\OXTImprovedataset\\out_reverse_index\\dataset_out_40.txt";
        paths[2] = "F:\\Users\\Administrator\\IdeaProjects\\OXTImprovedataset\\out_reverse_index\\dataset_out_60.txt";
        paths[3] = "F:\\Users\\Administrator\\IdeaProjects\\OXTImprovedataset\\out_reverse_index\\dataset_out_80.txt";
        paths[4] = "F:\\Users\\Administrator\\IdeaProjects\\OXTImprovedataset\\out_reverse_index\\dataset_out_100.txt";
        Map<String, List<File>> reverseIndex = null;
        int size = 0;
        for (int i = 0; i < paths.length; i++) {
            reverseIndex = EmailKeywordSplitUtil.readReverseIndexFromFile(paths[i]);
            size = MapUtil.getMapValueTotalSize(reverseIndex);
            System.out.println("dataset_out_" + 20 * (i + 1) + " keyword number: " + reverseIndex.size());
            System.out.println("dataset_out_" + 20 * (i + 1) + " total identifer number: " + size);
            System.out.println();
        }

    }

}