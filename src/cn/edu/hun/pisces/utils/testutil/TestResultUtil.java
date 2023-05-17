package cn.edu.hun.pisces.utils.testutil;

import cn.edu.hun.pisces.basestruct.FileList;
import cn.edu.hun.pisces.basestruct.Keyword;
import cn.edu.hun.pisces.basestruct.docfile.DocFile;
import cn.edu.hun.pisces.related.basestruct.ReverseIndexTable;

import java.io.File;
import java.util.*;

/**
 * @author: Leilei Du
 * @Date: 2018/11/22 15:38
 */
public class TestResultUtil {
    public static Set<DocFile> getIntersection(List<Keyword> keywordList, ReverseIndexTable reverseIndexTable){
        HashMap<Keyword, FileList> indexTable = reverseIndexTable.getIndexTable();
        Set<DocFile> resultSet = new HashSet<>();
        Iterator<Keyword> iterator = keywordList.iterator();
        Keyword firstKeyword = iterator.next();
        Keyword keyword = null;
        List<DocFile> firstKeywordFileList = indexTable.get(firstKeyword).getList();
        resultSet.addAll(firstKeywordFileList);

        List<DocFile> keywordFileList = null;
        while (iterator.hasNext()){
            keyword = iterator.next();
            keywordFileList = indexTable.get(keyword).getList();
//            for (DocFile docFile : resultSet) {
//                if(!keywordFileList.contains(docFile)){
//                    resultSet.remove(docFile);
//                }
//
//            }
            Iterator<DocFile> fileIterator = resultSet.iterator();
            while(fileIterator.hasNext()){
                DocFile docFile = fileIterator.next();
                if(!keywordFileList.contains(docFile)){
                    fileIterator.remove();
                }
            }
        }
        return resultSet;
    }
}