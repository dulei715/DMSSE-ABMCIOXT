package cn.edu.hun.pisces.improved.others.utils;

import cn.edu.hun.pisces.improved.basic.role.Client;
import cn.edu.hun.pisces.utils.Constant;
import cn.edu.hun.pisces.utils.cryptography.BilinearUtil;
import cn.edu.hun.pisces.utils.cryptography.CryptoFunciton;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

/**
 * @author: Leilei Du
 * @Date: 2018/11/17 19:28
 */
public class ClientUtil {
    public static BilinearUtil bilinearUtil = new BilinearUtil();
//    public static final String filePath = Constant.DEFAULT_PROGRAM_PATH + "datafile\\improved\\clients.properties";

//    private static String getFigureFilePath(){
//        return ClientUtil.class.getResource(fileRecord).toString();
//    }

    public static void renewClients(int clientNumber, Field zField, String filePath){
        Properties properties = new Properties();
//        String path = getFigureFilePath();
        FileOutputStream fileOut = null;
        Element secretKey = null;
        Element rValue = null;
        Set<String> removeRepeatSet = new HashSet<>();

        for (int i = 0; i < clientNumber; i++) {
            secretKey = CryptoFunciton.getNonZeroRandomElement(zField);
            while(removeRepeatSet.contains(secretKey.toString())){
                secretKey = CryptoFunciton.getNonZeroRandomElement(zField);
            }
            removeRepeatSet.add(secretKey.toString());
            rValue = CryptoFunciton.getNonZeroRandomElement(zField);
            properties.setProperty(secretKey.toString(), rValue.toString());
        }
        try {
            fileOut = new FileOutputStream(filePath);
            properties.store(fileOut, "clientKey: clientRandomR");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileOut.close();
                fileOut = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static Map<Client, Element> getBaseClientRandomMap(Field zField, String filePath){
//        BigInteger[] randomNumber = Constant.DEFAULT_CLIENTS_RANDOM_R;
//        BigInteger[] secreteKeyArray = Constant.DEFAULT_CLIENT_SECRETE_KEY;
        Map<Client, Element> clientRandomMap = new HashMap<>();
        Client client = null;
        Properties properties = new Properties();
//        String path = getFigureFilePath();
        FileInputStream fileIn = null;
        try {
            fileIn = new FileInputStream(filePath);
            properties.load(fileIn);
            for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                String secretKeyStr = (String)entry.getKey();
                String rStr = (String)entry.getValue();
                Element secretKey = zField.newElement().set(Integer.valueOf(secretKeyStr)).getImmutable();
                Element r = zField.newElement().set(Integer.valueOf(rStr)).getImmutable();
                Element gr = bilinearUtil.getGPowValue(r);
                client = new Client(secretKey, gr);
                clientRandomMap.put(client, r);
            }
            return clientRandomMap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileIn.close();
                fileIn = null;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return clientRandomMap;
    }

    public static Map<cn.edu.hun.pisces.improved.advanced.role.Client, Element> getAdvanceClientRandomMap(Field zField, String filePath) {
        Map<cn.edu.hun.pisces.improved.advanced.role.Client, Element> clientRandomMap = new HashMap<>();
        cn.edu.hun.pisces.improved.advanced.role.Client client = null;
        Properties properties = new Properties();
//        String path = getFigureFilePath();
        FileInputStream fileIn = null;
        try {
            fileIn = new FileInputStream(filePath);
            properties.load(fileIn);
            for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                String secretKeyStr = (String) entry.getKey();
                String rStr = (String) entry.getValue();
                Element secretKey = zField.newElement().set(Integer.valueOf(secretKeyStr));
                Element r = zField.newElement().set(Integer.valueOf(rStr));
                Element gr = bilinearUtil.getGPowValue(r);
                client = new cn.edu.hun.pisces.improved.advanced.role.Client(secretKey, gr);
                clientRandomMap.put(client, r);
            }
            return clientRandomMap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileIn.close();
                fileIn = null;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return clientRandomMap;
    }
}