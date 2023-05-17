package cn.edu.hun.leileidu.basestruct;

import java.util.Comparator;

/**
 * @author: Leilei Du
 * @Date: 2018/7/9 17:19
 */
public class KeywordComparator implements Comparator<Keyword> {

    @Override
    public int compare(Keyword key1, Keyword key2) {
        if (key1.getValue().equals(key2.getValue())){
            return 0;
        }
        if(key1.getFileNumber() != key2.getFileNumber()){
            return key1.getFileNumber() - key2.getFileNumber();
        }
        return key1.getValue().compareTo(key2.getValue());
    }

}