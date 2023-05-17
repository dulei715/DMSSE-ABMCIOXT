package cn.edu.hun.pisces.improved.others.utils;

import cn.edu.hun.pisces.improved.others.utils.ClientUtil;
import cn.edu.hun.pisces.improved.others.utils.PrivilegeUtil;
import cn.edu.hun.pisces.utils.cryptography.BilinearUtil;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author: Leilei Du
 * @Date: 2018/11/23 20:03
 */
public class ClientPolicyUtil {
    private static BilinearUtil bilinearUtil = null;
    private static Field zField = null;
    static {
        bilinearUtil = new BilinearUtil();
        zField = bilinearUtil.getzField();
    }
//    public static String skAndRPath = "F:\\Users\\Administrator\\IdeaProjects\\ABMCIOXT\\datafile\\improved\\clients.properties";
//    public static String policyPath = "F:\\Users\\Administrator\\IdeaProjects\\ABMCIOXT\\datafile\\improved\\privilege.properties";
//    private static final String privilegeName = "improved/privilege.properties";
    public static List<String> readClientsProperties(int lineNumber, String skAndRPath){
        InputStream inputStream = null;
        Properties properties = new Properties();
        List<String> result = new ArrayList<>();
        try {
            inputStream = new FileInputStream(skAndRPath);
            properties.load(inputStream);
            int i = 0;
            Enumeration<Object> keys = properties.keys();
            while(i < lineNumber){
                keys.nextElement();
            }
            Object keyObj = keys.nextElement();
            String skStr = (String) keyObj;
            String rStr = (String) properties.get(keyObj);
            result.add(skStr);
            result.add(rStr);
            return result;
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
        return result;
    }


    public static cn.edu.hun.pisces.improved.basic.role.Client getBasicClientParamByprivilege(List<String> subKeywordValueList, String clientPath, String privilegePath){
        Map<cn.edu.hun.pisces.improved.basic.role.Client, Element> clientRandomMap = ClientUtil.getBaseClientRandomMap(zField, clientPath);
        Map<String, List<String>> privilegeMap = PrivilegeUtil.readPrivilege(privilegePath);
        for (Map.Entry<cn.edu.hun.pisces.improved.basic.role.Client, Element> clientElementEntry : clientRandomMap.entrySet()) {
            cn.edu.hun.pisces.improved.basic.role.Client client = clientElementEntry.getKey();
            String clientPKStr = client.getPublicKey().toString();
            List<String> keywordList = privilegeMap.get(clientPKStr);
            if(keywordList.containsAll(subKeywordValueList)){
                return client;
            }
        }
        return null;
    }

    public static cn.edu.hun.pisces.improved.basic.role.Client getBasicPowerfulClientByPrivilege(String clientPath, String privilegePath){
        Map<cn.edu.hun.pisces.improved.basic.role.Client, Element> clientRandomMap = ClientUtil.getBaseClientRandomMap(zField, clientPath);
        Map<String, List<String>> privilegeMap = PrivilegeUtil.readPrivilege(privilegePath);
        int countNum = 0;
        cn.edu.hun.pisces.improved.basic.role.Client tmpClient = null;
        for (Map.Entry<cn.edu.hun.pisces.improved.basic.role.Client, Element> clientElementEntry : clientRandomMap.entrySet()) {
            cn.edu.hun.pisces.improved.basic.role.Client client = clientElementEntry.getKey();
            String clientPKStr = client.getPublicKey().toString();
            List<String> keywordList = privilegeMap.get(clientPKStr);
//            if(keywordList.containsAll(subKeywordValueList)){
//                return client;
//            }
            if(keywordList.size() > countNum){
                countNum = keywordList.size();
                tmpClient = client;
            }
        }
        return tmpClient;
    }

    public static cn.edu.hun.pisces.improved.advanced.role.Client getAdvancedClientParamByprivilege(List<String> subKeywordValueList, String clientPath, String privilegePath){
        Map<cn.edu.hun.pisces.improved.advanced.role.Client, Element> clientRandomMap = ClientUtil.getAdvanceClientRandomMap(zField, clientPath);
        Map<String, List<String>> privilegeMap = PrivilegeUtil.readPrivilege(privilegePath);
        for (Map.Entry<cn.edu.hun.pisces.improved.advanced.role.Client, Element> clientElementEntry : clientRandomMap.entrySet()) {
            cn.edu.hun.pisces.improved.advanced.role.Client client = clientElementEntry.getKey();
            String clientPKStr = client.getPublicKey().toString();
            List<String> keywordList = privilegeMap.get(clientPKStr);
            if(keywordList.containsAll(subKeywordValueList)){
                return client;
            }
        }
        return null;
    }

    public static cn.edu.hun.pisces.improved.advanced.role.Client getAdvancedPowerfulClientByPrivilege(String clientPath, String privilegePath){
        Map<cn.edu.hun.pisces.improved.advanced.role.Client, Element> clientRandomMap = ClientUtil.getAdvanceClientRandomMap(zField, clientPath);
        Map<String, List<String>> privilegeMap = PrivilegeUtil.readPrivilege(privilegePath);
        int countNum = 0;
        cn.edu.hun.pisces.improved.advanced.role.Client tmpClient = null;
        for (Map.Entry<cn.edu.hun.pisces.improved.advanced.role.Client, Element> clientElementEntry : clientRandomMap.entrySet()) {
            cn.edu.hun.pisces.improved.advanced.role.Client client = clientElementEntry.getKey();
            String clientPKStr = client.getPublicKey().toString();
            List<String> keywordList = privilegeMap.get(clientPKStr);
//            if(keywordList.containsAll(subKeywordValueList)){
//                return client;
//            }
            if(keywordList.size() > countNum){
                countNum = keywordList.size();
                tmpClient = client;
            }
        }
        return tmpClient;
    }

}