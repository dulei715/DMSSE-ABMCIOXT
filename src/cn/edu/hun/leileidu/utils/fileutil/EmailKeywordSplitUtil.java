package cn.edu.hun.leileidu.utils.fileutil;

import cn.edu.hun.leileidu.utils.Constant;
import cn.edu.hun.leileidu.utils.condition.Condition;
import cn.edu.hun.leileidu.utils.condition.StringNewCondition;
import cn.edu.hun.leileidu.utils.ioutil.MyRead;
import cn.edu.hun.leileidu.utils.ioutil.MyWrite;

import java.io.*;
import java.util.*;

/**
 * @author: Leilei Du
 * @Date: 2018/11/14 16:59
 */
public class EmailKeywordSplitUtil {
    public static final String contentAttribute = "Content";
    public static final String splitRegex = Constant.DEFAULT_REGEX_SPLIT;
    public static final Condition condition = new StringNewCondition();

    /**
     * 将关键字提取出来并且写入相应文件中
     * @param inPath
     * @param outPath
     * @return count 关键字总数对应的文件总数。
     */
    public static int splitAndWriteToFile(String inPath, String outPath){
        Map<String,List<File>> result = null;
        int count = 0;
        File inFile = new File(inPath);
        File outFile = null;
        if(outPath != null){
            outFile = new File(outPath);
        }
        if(!inFile.exists()){
            throw new RuntimeException("The path is not exists...");
        }
        result = new HashMap<>();
        File[] fileList = inFile.listFiles();
        for (File file : fileList) {
            if(!file.isFile()){
                continue;
            }
            if(file.getName().endsWith(".txt")){
                Set<String> set = handle(file);
                for (String keyword : set) {
                    if(!result.containsKey(keyword)){
                        result.put(keyword, new ArrayList<>());
                    }
                    result.get(keyword).add(file);
                }
            }
        }
        if(outFile != null){
            count = MyWrite.writeReverseIndex(outFile, result);
        }
        return count;
    }

    private static Set<String> handle(File file) {
        // 根据空行分割成两部分
        BufferedReader buf = null;
        Set<String> set = new HashSet<>();
        String line = null;
        StringBuilder sb = new StringBuilder();
        try {
            buf = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            //处理键值对
            for(line = buf.readLine().trim(); !"".equals(line); line = buf.readLine().trim()){
                //删去那些以冒号结尾的关键字。因为这些关键字表示只有属性名，没有属性值。
                if(line.endsWith(":")){
                    continue;
                }
                //删去那些多行值
                if(!line.contains(":")){
                    continue;
                }
                set.add(line);
            }
            //处理其他正文
            while((line = buf.readLine()) != null){
                Set<String> subSet =  extractKeyword(contentAttribute, line, splitRegex);
                set.addAll(subSet);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                buf.close();
                buf = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return set;
    }

    private static Set<String> extractKeyword(String contentAttribute, String lineStr, String splitRegex) {
        Set<String> set = new HashSet<>();
        String[] subLineStr = lineStr.split(splitRegex);
        for (int i = 0; i < subLineStr.length; i++) {
            String subStr = subLineStr[i].trim();
            if(!condition.satisfy(subStr, Constant.KEYWORD_LETTER_LOW_BOUND)){
                continue;
            }

            set.add(contentAttribute + ": " + subStr);
        }
        return set;
    }

    public static Map<String, List<File>> readReverseIndexFromFile(String inPath){
        File file = new File(inPath);
        if(!file.exists()){
            throw new RuntimeException("Cannot find file: " + file.getName() + "!");
        }
        return MyRead.readReverseIndex(file);
    }

}