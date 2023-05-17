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
     * ���ؼ�����ȡ��������д����Ӧ�ļ���
     * @param inPath
     * @param outPath
     * @return count �ؼ���������Ӧ���ļ�������
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
        // ���ݿ��зָ��������
        BufferedReader buf = null;
        Set<String> set = new HashSet<>();
        String line = null;
        StringBuilder sb = new StringBuilder();
        try {
            buf = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            //�����ֵ��
            for(line = buf.readLine().trim(); !"".equals(line); line = buf.readLine().trim()){
                //ɾȥ��Щ��ð�Ž�β�Ĺؼ��֡���Ϊ��Щ�ؼ��ֱ�ʾֻ����������û������ֵ��
                if(line.endsWith(":")){
                    continue;
                }
                //ɾȥ��Щ����ֵ
                if(!line.contains(":")){
                    continue;
                }
                set.add(line);
            }
            //������������
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