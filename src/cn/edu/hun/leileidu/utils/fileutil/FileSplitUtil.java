package cn.edu.hun.leileidu.utils.fileutil;

import cn.edu.hun.leileidu.utils.Constant;
import cn.edu.hun.leileidu.utils.condition.StringCondition;

import java.io.*;
import java.util.*;

/**
 * The util to splitAndWriteToFile files and get keywords and their frequency.
 */
@Deprecated
public class FileSplitUtil {

    private static BufferedReader bufferedReader = null;
//    private static BufferedWriter bufferedWriter = null;
    private static StringCondition condition = null;

    private static HashSet defaultExclusiveSet =  new HashSet();

    static {
        defaultExclusiveSet.add("");
//        condition = new StringCondition(Constant.DEFAULT_EXCLUSIVE_SET);
        condition = new StringCondition(defaultExclusiveSet);
    }

    /**
     *
     * @param readFile      A data source
     * @param splitRegex    The splitAndWriteToFile tag, which is regex, for data
     * @param excludedSet   A set to exclude the string which is in it
     * @return
     */
    public static HashMap<String, Integer> extractKeyword(File readFile, String splitRegex, Set<String> excludedSet) {
        if (!readFile.exists()){
            throw new RuntimeException(readFile+" is not exists!");
        }
        HashMap<String, Integer> map = new HashMap<>();
        //StringBuffer sbuf = new StringBuffer();
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(readFile)));
            String lineStr = null;
            String[] subLineStr = null;
            while ((lineStr = bufferedReader.readLine()) != null){
                subLineStr = lineStr.split(splitRegex);
                for (int i = 0; i < subLineStr.length; i++) {

                    if(!condition.satisfy(subLineStr[i])){
                        continue;
                    }
                    if (!map.containsKey(subLineStr[i])){
                        map.put(subLineStr[i], 1);
                    }else{
                        int value = map.get(subLineStr[i]);
                        ++value;
                        map.put(subLineStr[i],value);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                bufferedReader.close();
                bufferedReader = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return map;
    }

    public static HashMap<String, Integer> extractKeyword(File readFile, String splitRegex){

        return extractKeyword(readFile, splitRegex, defaultExclusiveSet);
    }

    public static HashMap<String, Integer> extractKeyword(File readFile, Set<String> excludedSet){
        return extractKeyword(readFile, Constant.DEFAULT_REGEX_SPLIT, excludedSet);
    }

    public static HashMap<String, Integer> extractKeyword(File readFile){
        return extractKeyword(readFile, Constant.DEFAULT_REGEX_SPLIT, defaultExclusiveSet);
    }




    /**
     *  提取一个文件中的关键词和文件
     * @param file
     * @param splitRegex
     * @param map
     * @return
     */
    public static HashMap<String, List<File>> extractReverseIndexData(File file, String splitRegex, HashMap<String, List<File>> map){
        if (!file.exists()){
            throw new RuntimeException(file+" is not exists!");
        }
        List<File> list = null;
        HashSet<String> strSet = new HashSet<>();
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String lineStr = null;
            String[] subLineStr = null;
            while ((lineStr = bufferedReader.readLine()) != null){
                subLineStr = lineStr.split(splitRegex);
                for (int i = 0; i < subLineStr.length; i++) {

                    if(!condition.satisfy(subLineStr[i])){
                        continue;
                    }
                    strSet.add(subLineStr[i]);
                }
            }
            for (String keyword : strSet) {
                if (!map.containsKey(keyword)){
                    list = new ArrayList<>();
                    list.add(file);
                    map.put(keyword,list);
                }else {
                    list = map.get(keyword);
                    list.add(file);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                bufferedReader.close();
                bufferedReader = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return map;
    }

    /**
     *  属性值和行号有关，关键字所在行号对属性总数的余数即为属性的编号
     * @param file
     * @param splitRegex
     * @param combineSymbol
     * @param attributeList
     * @param map
     * @return
     */
    public static Map<String, List<File>> extractSingleFileReverseIndexDataWithAttribute(File file, String splitRegex, String combineSymbol, List<String> attributeList, Map<String, List<File>> map) {
        if (!file.exists()){
            throw new RuntimeException(file+" is not exists!");
        }
        List<File> list = null;
        HashSet<String> strSet = new HashSet<>();
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String lineStr = null;
            String[] subLineStr = null;
            int lineNumber = 0;
            int attributeNumber = attributeList.size();
            while ((lineStr = bufferedReader.readLine()) != null){
                ++lineNumber;
                subLineStr = lineStr.split(splitRegex);
                for (int i = 0; i < subLineStr.length; i++) {

                    if(!condition.satisfy(subLineStr[i])){
                        continue;
                    }
                    strSet.add(subLineStr[i] + combineSymbol + attributeList.get(lineNumber % attributeNumber));
                }
            }

            for (String keyword : strSet) {
                if (!map.containsKey(keyword)){
                    list = new ArrayList<>();
                    list.add(file);
                    map.put(keyword,list);
                }else {
                    list = map.get(keyword);
                    list.add(file);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                bufferedReader.close();
                bufferedReader = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return map;
    }


    public static HashMap<String, List<File>> extractReverseIndexData(File directory, String splitRegex){
        if (!directory.exists()){
            throw new RuntimeException(directory+" is not exists!");
        }
        HashMap<String, List<File>> map = new HashMap<>();
        File[] files = directory.listFiles();
        for (File file : files) {
            if(!file.isFile()){
                continue;
            }
            // map is used in common in the whole loop
            map = extractReverseIndexData(file, splitRegex, map);
        }
        return map;
    }


    public static Map<String, List<File>> extractMultiFilesReverseIndexDataWithAttribute(File directory, String splitRegex, String combineSymbol, List<String> attributeList){
        if (!directory.exists()){
            throw new RuntimeException(directory+" is not exists!");
        }
        Map<String, List<File>> map = new HashMap<>();
        File[] files = directory.listFiles();
        for (File file : files) {
            if(!file.isFile()){
                continue;
            }
            // map is used in common in the whole loop
            map = extractSingleFileReverseIndexDataWithAttribute(file, splitRegex, combineSymbol, attributeList, map);
//            extractReverseIndexDataWithAttribute()
        }
        return map;
    }

}