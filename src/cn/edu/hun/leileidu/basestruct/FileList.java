package cn.edu.hun.leileidu.basestruct;

import cn.edu.hun.leileidu.basestruct.docfile.DocFile;
import cn.edu.hun.leileidu.basestruct.docfile.FileDocFile;
import cn.edu.hun.leileidu.utils.Constant;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 *  This is a list that keep the order of file in a random list.
 * @param
 */
public class FileList {
    private List<DocFile> list = new ArrayList<>();
    private String orderKey = null;
    private int listSize = 0;

    public FileList(Collection<File> collection, String orderKey) {
        this.orderKey = orderKey;
        listSize = collection.size();
        int[] randomOrder = getListOrder(orderKey, listSize);
        Iterator<File> iterator = collection.iterator();
        for (int i = 0; i < listSize; i++) {
            File file = iterator.next();
            DocFile docFile = new FileDocFile(file);
            list.add(randomOrder[i],docFile);
        }
    }

    public FileList(Collection<File> collection){
        this(collection,Constant.DEFAULT_ORDER_KEY);
    }

    //TODO 打乱keyword关联文档的顺序
    private int[] getListOrder(String orderKey, int size) {
        int[] order = new int[size];
        for (int i = 0; i < size; i++) {
            order[i] = i;
        }
        return order;
    }

    public List<DocFile> getList() {
        return list;
    }

    public int getListSize() {
        return listSize;
    }

}