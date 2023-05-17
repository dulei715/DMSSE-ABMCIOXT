package cn.edu.hun.leileidu.ospir_oxt.utils;

import cn.edu.hun.leileidu.ospir_oxt.role.DataOwner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

/**
 * @author: Leilei Du
 * @Date: 2018/11/23 9:14
 */
public class ClientUtil {
//    public static String policyPath = "F:\\Users\\Administrator\\IdeaProjects\\MutiClientOXTImprove\\datafile\\ospir-oxt\\policy.properties";
    public static String getClientIDFromPolicyFile(int lineNumber, String policyPath){
        Properties properties = new Properties();
        InputStream inputStream = null;
        String result = null;
        int i = 0;
        try {
            inputStream = new FileInputStream(policyPath);
            properties.load(inputStream);
            Enumeration<Object> keys = properties.keys();
            while (keys.hasMoreElements() && i < lineNumber){
                keys.nextElement();
                ++i;
            }
            result = (String)keys.nextElement();
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
    public static String getMostPowerfulClientIDFromPolicyFile(String policyPath){
        Properties properties = new Properties();
        InputStream inputStream = null;
        String result = null;
        int attrNum = 0;
        int i = 0;
        try {
            inputStream = new FileInputStream(policyPath);
            properties.load(inputStream);
            for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                String value = (String)entry.getValue();
                String[] values = value.split(DataOwner.policySeperateSymbol);
                if(values.length > attrNum){
                    result = (String)entry.getKey();
                    attrNum = values.length;
                }
            }
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
}