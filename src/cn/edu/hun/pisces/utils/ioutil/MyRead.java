package cn.edu.hun.pisces.utils.ioutil;

import java.io.*;
import java.util.*;

/**
 * 保证格式和 MyWrite.writeReverseIndex(...)中要求的格式一致
 * @author: Leilei Du
 * @Date: 2018/11/15 14:55
 */
public class MyRead {
    public static final String writeCountValueKeyValueSplitSymbol = MyWrite.writeCountValueKeyValueSplitSymbol;
    public static final String writeValueValueSplitSymbol = "\\$\\$";
    public static Map<String, List<File>> readReverseIndex(File file){
        BufferedReader bufr = null;
        Map<String, List<File>> map = new HashMap<>();
        List<File> fileList = null;
        try {
            bufr = new BufferedReader(new FileReader(file));
            String line = null;
            while((line = bufr.readLine()) != null){
                String[] threePartStr = line.split(writeCountValueKeyValueSplitSymbol);
                String[] valueArray = threePartStr[2].split(writeValueValueSplitSymbol);
                fileList = new ArrayList<>();
                for (int i = 0; i < valueArray.length; i++) {
                    String fileFullName = valueArray[i];
                    File f = new File(fileFullName);
                    fileList.add(f);
                }
                map.put(threePartStr[1], fileList);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufr.close();
                bufr = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
}