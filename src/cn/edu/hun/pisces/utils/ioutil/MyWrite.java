package cn.edu.hun.pisces.utils.ioutil;

import cn.edu.hun.pisces.utils.Constant;

import java.io.*;
import java.util.*;

/**
 * @author: Leilei Du
 * @Date: 2018/7/11 11:27
 */
public class MyWrite {
    private static final int SIZE = 1024;
    public static final String writeCountValueKeyValueSplitSymbol = "###";
    public static final String writeValueValueSplitSymbol = "$$";
    public static final int valueLowBound = Constant.KEYWORD_IND_LOW_BOUND;
    /**
     * 规定：
     *      1. 文件每行代表一个关键字和相关的文件标识符。
     *      2. 每行以“###”分割为三部分：
     *          (1) 第一部分是该关键字相关的文件数目；
     *          (2) 第二部分是该关键字的内容(形式为 属性名：属性值)；
     *          (3) 第三部分是改关键字的相关索引(文件名)，各个索引之间用“$$”隔开。
     *      3. 本函删去了关联文件数目小于 valueLowBound 的关键字。
     * @param outFile
     * @param map
     * @return count 返回总的关键词对应的总的文件数目。
     */
    public static int writeReverseIndex(File outFile, Map<String, List<File>> map){
        int count = 0;
        BufferedWriter bufw = null;
        Set<? extends Map.Entry<?, List<File>>> entries = map.entrySet();
        StringBuilder strb = new StringBuilder();
        try {
            bufw = new BufferedWriter(new FileWriter(outFile));
            int i = 0;
            for (Map.Entry<?, ?> entry : entries) {
                Object key = entry.getKey();
                List value = (List) entry.getValue();
                int valueSize = value.size();

                if(valueSize < valueLowBound){
                    continue;
                }
                count += valueSize;
                strb.append(valueSize + writeCountValueKeyValueSplitSymbol);

                strb.append(key).append(writeCountValueKeyValueSplitSymbol);

                int j = 0;
                for (; j < valueSize - 1; j++) {
                    strb.append(value.get(j)).append(writeValueValueSplitSymbol);
                }
                strb.append(value.get(j)).append(System.lineSeparator());

                ++i;
                if(i >= SIZE){
                    bufw.write(strb.toString());
                    strb.setLength(0);
                    i = 0;
                }
            }
            bufw.write(strb.toString());
            return count;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufw.close();
                bufw = null;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return count;
    }

    public static void writeMapKeyAndValueNum(File file, Map<?, ? extends Collection> map) {
        BufferedWriter bufw = null;
        Set<? extends Map.Entry<?, ? extends Collection>> entries = map.entrySet();
        StringBuilder strb = new StringBuilder();
        try {
            bufw = new BufferedWriter(new FileWriter(file));
            int i = 0;
            for (Map.Entry<?, ?> entry : entries) {
                Object key = entry.getKey();
                Collection value = (Collection) entry.getValue();
//            str += key + ": " + value.size();
                strb.append(key).append(": ").append(value.size()).append(System.lineSeparator());
                ++i;
                if(i >= SIZE){
                    bufw.write(strb.toString());
                    strb.setLength(0);
                    i = 0;
                }
            }
            bufw.write(strb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufw.close();
                bufw = null;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    public static void writeCollection(Collection<?> collection, String path){
        File outFile = new File(path);
        BufferedWriter bufw = null;
        StringBuilder sb = new StringBuilder();
        String line = "";
        Iterator<?> iterator = collection.iterator();
        int count = 0;
        try {
            bufw = new BufferedWriter(new FileWriter(outFile));
            while(iterator.hasNext()){
                Object next = iterator.next();
                count += 1;
                sb.append(next).append(System.lineSeparator());
                if(count >= SIZE){
                    bufw.write(sb.toString());
                    count = 0;
                    sb.setLength(0);
                }
            }
            bufw.write(sb.toString());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufw.close();
                bufw = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void writeLong2dimArray(String fileName, long[][] array){
        File outFile = new File(fileName);
        BufferedWriter bufw = null;
        StringBuilder sb = new StringBuilder();
        String lien = "";
        try {
            bufw = new BufferedWriter(new FileWriter(outFile));
            for (int i = 0; i < array.length; i++) {
                long[] temp = array[i];
                int j = 0;
                for (; j < temp.length - 1; j++) {
                    sb.append(temp[j]).append(" ");
                }
                sb.append(temp[j]).append(System.lineSeparator());
                bufw.write(sb.toString());
                sb.setLength(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}