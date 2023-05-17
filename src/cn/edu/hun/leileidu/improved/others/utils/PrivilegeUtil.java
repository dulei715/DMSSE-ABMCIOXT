package cn.edu.hun.leileidu.improved.others.utils;

import cn.edu.hun.leileidu.basestruct.Keyword;

import java.io.*;
import java.util.*;

/**
 * @author: Leilei Du
 * @Date: 2018/11/17 18:29
 */
@SuppressWarnings("ALL")
public class PrivilegeUtil {

    public static final String keywordsKeywordsCombineSymbol = "###";

    //existAdmin 为true, 则保证至少有一个client有访问所有关键字的权限
    public static Map<cn.edu.hun.leileidu.improved.basic.role.Client, List<Keyword>> grantBasePrivilege(Set<Keyword> keywordSet, List<cn.edu.hun.leileidu.improved.basic.role.Client> clientList, boolean existAdmin) {
        int keywordNum = keywordSet.size();
        int tenPartKeywordNum = keywordNum / 10;
        Random random = new Random();
        Map<cn.edu.hun.leileidu.improved.basic.role.Client, List<Keyword>> map = new HashMap<>();
        for (int i = 0; i < clientList.size(); ++i) {
            cn.edu.hun.leileidu.improved.basic.role.Client client = clientList.get(i);
            List<Keyword> list = null;
            if (!map.containsKey(client)) {
                list = new ArrayList<>();
                map.put(client, list);
            }
            list = map.get(client);
            for (Keyword keyword : keywordSet) {
                if(existAdmin && i == 0){
                    list.add(keyword);
                }else{
                    int rand = random.nextInt(keywordNum);
                    if (rand <= tenPartKeywordNum) {
                        list.add(keyword);
                    }
                }
            }
        }


        return map;
    }

    public static Map<cn.edu.hun.leileidu.improved.advanced.role.Client, List<Keyword>> grantAdvancePrivilege(Set<Keyword> keywordSet, List<cn.edu.hun.leileidu.improved.advanced.role.Client> clientList, boolean existAdmin) {
        int keywordNum = keywordSet.size();
        int tenPartKeywordNum = keywordNum / 10;
        Random random = new Random();
        Map<cn.edu.hun.leileidu.improved.advanced.role.Client, List<Keyword>> map = new HashMap<>();
        for (int i = 0; i < clientList.size(); ++i) {
            cn.edu.hun.leileidu.improved.advanced.role.Client client = clientList.get(i);
            List<Keyword> list = null;
            if (!map.containsKey(client)) {
                list = new ArrayList<>();
                map.put(client, list);
            }
            list = map.get(client);
            for (Keyword keyword : keywordSet) {
                if(existAdmin && i == 0){
                    list.add(keyword);
                }else{
                    int rand = random.nextInt(keywordNum);
                    if (rand <= tenPartKeywordNum) {
                        list.add(keyword);
                    }
                }
            }
        }
        return map;
    }

    public static void writeBasePrivilege(Map<cn.edu.hun.leileidu.improved.basic.role.Client, List<Keyword>> clientListMap, String fileName) {
//        String fileName = Constant.DEFAULT_PROGRAM_PATH + "datafile\\improved\\privilege.properties";
        Properties properties = new Properties();

        for (Map.Entry<cn.edu.hun.leileidu.improved.basic.role.Client, List<Keyword>> entry : clientListMap.entrySet()) {
            cn.edu.hun.leileidu.improved.basic.role.Client client = entry.getKey();
            String clientPK = client.getPublicKey().toString();

            List<Keyword> list = entry.getValue();

            StringBuilder stringBuilder = new StringBuilder();
            int len = list.size() - 1;
            int i = 0;
            for (; i < len; i++) {
                stringBuilder.append(list.get(i).getValue()).append(keywordsKeywordsCombineSymbol);
            }
            stringBuilder.append(list.get(i).getValue());
//            properties.put(clientPK, stringBuilder.toString());
            properties.setProperty(clientPK, stringBuilder.toString());
        }

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(fileName);
            properties.store(out, "privilege");
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

    public static void writeAdvancePrivilege(Map<cn.edu.hun.leileidu.improved.advanced.role.Client, List<Keyword>> clientListMap, String fileName) {
//        String fileName = Constant.DEFAULT_PROGRAM_PATH +  "datafile\\improved\\privilege_advanced.properties";
        Properties properties = new Properties();

        for (Map.Entry<cn.edu.hun.leileidu.improved.advanced.role.Client, List<Keyword>> entry : clientListMap.entrySet()) {
            cn.edu.hun.leileidu.improved.advanced.role.Client client = entry.getKey();
            String clientPK = client.getPublicKey().toString();

            List<Keyword> list = entry.getValue();
            StringBuilder stringBuilder = new StringBuilder();
            int len = list.size() - 1;
            int i = 0;
            for (; i < len; i++) {
                stringBuilder.append(list.get(i).getValue()).append(keywordsKeywordsCombineSymbol);
            }
            stringBuilder.append(list.get(i).getValue());
//            properties.put(clientPK, stringBuilder.toString());
            properties.setProperty(clientPK, stringBuilder.toString());
        }

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(fileName);
            properties.store(out, "privilege");
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

    public static Map<String, List<String>> readPrivilege(String privilegePath) {
        Properties properties = new Properties();
        Map<String, List<String>> privilege = new HashMap<>();
//        String policyPath = PrivilegeUtil.class.getResource("/" + privilegeFileName).getPath();
//        String policyPath = Constant.DEFAULT_PROGRAM_PATH +"datafile\\" + privilegeFileName;
        File file = new File(privilegePath);
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            properties.load(inputStream);
            List<String> kStrList = null;
            Set<Map.Entry<Object, Object>> entries = properties.entrySet();
            for (Map.Entry<Object, Object> entry : entries) {
                String pkStr = (String) entry.getKey();
                String[] keywordsValue = ((String) entry.getValue()).split(keywordsKeywordsCombineSymbol);
                kStrList = new ArrayList<>();
                for (int i = 0; i < keywordsValue.length; i++) {
                    String s = keywordsValue[i];
                    kStrList.add(s);
                }
                privilege.put(pkStr, kStrList);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                inputStream = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return privilege;
    }

}