package cn.edu.hun.leileidu.utils;

import java.io.*;
import java.util.*;

/**
 * @author: Leilei Du
 * @Date: 2018/7/19 15:50
 */
public class KeyUtil {
    public static void generateKeysToFile(String path, String[] keyNames, int eachSubkeyByteLength, int subkeyLength, Set<String> singleSet){
        OutputStream out = null;
        try {
            out = new FileOutputStream(new File(path));
            Random random = new Random();
            Properties properties = new Properties();
            StringBuilder stringBuilder = new StringBuilder();
            byte b;
            for (int i = 0; i < keyNames.length; i++) {
                String keyName = keyNames[i];
                byte[] bytes = new byte[eachSubkeyByteLength];
                int j = 0;
                int len = 0;
                if(!singleSet.contains(keyName)){
                    len = subkeyLength - 1;
                }
                for (; j < len; j++) {
                    random.nextBytes(bytes);
                    stringBuilder.append(ByteUtil.getString(bytes,",")).append(";");
                }
                random.nextBytes(bytes);
                stringBuilder.append(ByteUtil.getString(bytes,","));
                properties.setProperty(keyName, stringBuilder.toString());

                stringBuilder.setLength(0);
            }
            properties.store(out, "keys for encrypt keyword");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
                out = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Map<String, List<byte[]>> getKeysFromFile(String path){
        InputStream in = null;
        Map<String, List<byte[]>> map = null;
        List<byte[]> keyValueList = null;
        byte[] byteArr = null;
        try {
            in = new FileInputStream(path);
            map = new HashMap<>();
            Properties properties = new Properties();
            properties.load(in);
            for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                String keyName = (String)entry.getKey();
                String[] keyValueStrs = ((String)entry.getValue()).split(";");
                keyValueList = new ArrayList<>();
                for (int i = 0; i < keyValueStrs.length; i++) {
                    String[] keyValueStr = keyValueStrs[i].split(",");
                    byteArr = new byte[keyValueStr.length];
                    for (int j = 0; j < keyValueStr.length; j++) {
                        byteArr[j] = Byte.parseByte(keyValueStr[j]);
                    }
                    keyValueList.add(byteArr);
                }
                map.put(keyName, keyValueList);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                in = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

}