package cn.edu.hun.leileidu.utils;

import cn.edu.hun.leileidu.basestruct.Keyword;
import cn.edu.hun.leileidu.ospir_oxt.basestruct.AttributeKeyword;
import cn.edu.hun.leileidu.utils.ioutil.MyRead;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Leilei Du
 * @Date: 2018/11/22 15:07
 */
public class KeywordUtil {

    public static String combineSymbol = Constant.DEFAULT_COMBINE_SYMBOL;
    public static String extractAttributeKey(Keyword keyword){
        String keywordValue = keyword.getValue();
        return extractAttributeKey(keywordValue);
    }

    public static String extractAttributeKey(String keywordValue) {
        String[] split = keywordValue.split(combineSymbol);
        return split[0];
    }

    public static List<Keyword> readKeyword(String fileStr, int off, int keywordNumber){
        List<Keyword> keywordList = new ArrayList<>();
//        String fileStr = outFileNameList.get(4);
        BufferedReader bufr = null;
        try {
            bufr = new BufferedReader(new FileReader(fileStr));
            String line = null;
            for (int i = 0; i < off; i++) {
                bufr.readLine();
            }
            for (int i = off; i < keywordNumber + off; i++) {
                line = bufr.readLine();
                String keywordStr = line.split(MyRead.writeCountValueKeyValueSplitSymbol)[1];
                Keyword keyword = new Keyword(keywordStr);
                keywordList.add(keyword);
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
        return keywordList;
    }

    public static List<Keyword> readKeyword(String fileStr, int keywordNumber){
        return readKeyword(fileStr, 0, keywordNumber);
    }

    public static List<Keyword> readKeyword(String fileStr){
        List<Keyword> keywordList = new ArrayList<>();
        BufferedReader bufr = null;
        try {
            bufr = new BufferedReader(new FileReader(fileStr));
            String line = null;
            while ((line = bufr.readLine()) != null) {
                String keywordStr = line.split(MyRead.writeCountValueKeyValueSplitSymbol)[1];
                Keyword keyword = new Keyword(keywordStr);
                keywordList.add(keyword);
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
        return keywordList;
    }

    public static List<AttributeKeyword> toAttributeKeywordList(List<Keyword> keywordList){
        List<AttributeKeyword> attributeKeywordList = new ArrayList<>();
        AttributeKeyword tmpAttrKeyword = null;
        for (Keyword keyword : keywordList) {
            String keywordValue = keyword.getValue();
            String attrKey = extractAttributeKey(keywordValue);
            tmpAttrKeyword = new AttributeKeyword(attrKey, keywordValue);
            attributeKeywordList.add(tmpAttrKeyword);
        }
        return attributeKeywordList;
    }

    public static List<String> toKeywordValueList(List<Keyword> keywordList){
        List<String> valueList = new ArrayList<>();
        for (Keyword keyword : keywordList) {
            valueList.add(keyword.getValue());
        }
        return valueList;
    }

}