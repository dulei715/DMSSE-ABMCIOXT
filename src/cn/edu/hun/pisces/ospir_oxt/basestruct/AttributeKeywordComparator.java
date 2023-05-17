package cn.edu.hun.pisces.ospir_oxt.basestruct;

import cn.edu.hun.pisces.basestruct.Keyword;
import cn.edu.hun.pisces.basestruct.KeywordComparator;

import java.util.Comparator;

/**
 * @author: Leilei Du
 * @Date: 2018/7/26 9:46
 */
public class AttributeKeywordComparator extends KeywordComparator {
    @Override
    public int compare(Keyword keyA, Keyword keyB) {
        AttributeKeyword key1 = (AttributeKeyword) keyA;
        AttributeKeyword key2 = (AttributeKeyword) keyB;
        if (key1.getValue().equals(key2.getValue()) && key1.getAttribute().equals(key2.getAttribute())){
            return 0;
        }
        if(key1.getFileNumber() != key2.getFileNumber()){
            return key1.getFileNumber() - key2.getFileNumber();
        }
//        int preResult = key1.getValue().compareTo(key2.getValue());
        if(!key1.getValue().equals(key2.getValue())){
            return key1.getValue().compareTo(key2.getValue());
        }
        return key1.getAttribute().compareTo(key2.getAttribute());
    }
}