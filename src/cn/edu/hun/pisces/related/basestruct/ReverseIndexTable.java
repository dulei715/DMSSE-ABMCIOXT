package cn.edu.hun.pisces.related.basestruct;

import cn.edu.hun.pisces.basestruct.FileList;
import cn.edu.hun.pisces.basestruct.Keyword;
import cn.edu.hun.pisces.basestruct.docfile.DocFile;
import cn.edu.hun.pisces.utils.Constant;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class ReverseIndexTable {
    /**
     *  the default value of indexTable will be set as new HashMap
     *  the default value of orderKeys will be set as Constant.DEFAULT_ORDER_KEY_ARRAY
     */
    protected HashMap<Keyword, FileList> indexTable = null;
    protected String[] orderKeys = null;

    public ReverseIndexTable(HashMap<Keyword, FileList> indexTable, String[] orderKeys) {
        this.indexTable = indexTable;
        this.orderKeys = orderKeys;
    }
    public ReverseIndexTable(String[] orderKeys){

        this(new HashMap<>(), orderKeys);
    }
    public ReverseIndexTable(HashMap<Keyword, FileList> indexTable){
        this(indexTable,Constant.DEFAULT_ORDER_KEY_ARRAY);
    }
    public ReverseIndexTable(){
        this(new HashMap<>(), Constant.DEFAULT_ORDER_KEY_ARRAY);
    }

    public HashMap<Keyword, FileList> getIndexTable() {
        return indexTable;
    }

    public String[] getOrderKeys() {
        return orderKeys;
    }

    public void setIndexTable(HashMap<Keyword, FileList> indexTable) {
        this.indexTable = indexTable;
    }

    public void setOrderKeys(String[] orderKeys) {
        this.orderKeys = orderKeys;
    }

    /**
     *
     * After construct0 a new ReverseIndexTable instance, this method is ought to be
     * invoked to construct0 a full reverse index table.
     *
     * @param indexDataTable a HashMap with Keyword's value as key and collection of
     *                       its relevant files
     *
     */
    public void construct(Map<String, ? extends Collection<File>> indexDataTable){
        Keyword keyword = null;
        Collection<File> collection = null;
        FileList fileList = null;
        int i = 0, orderKeySize = this.orderKeys.length;
        for (Map.Entry<String,? extends Collection<File>> entry : indexDataTable.entrySet()) {
            String keyStr = entry.getKey();
            keyword = new Keyword(keyStr);
            collection = entry.getValue();

            fileList = new FileList(collection, this.orderKeys[i]);

            //use the order key array circularly
            i = (i+1)%orderKeySize;
            keyword.setFileNumber(collection.size());
            this.indexTable.put(keyword,fileList);
        }
    }

    @Override
    public String toString() {
        return "ReverseIndexTable{" +
                "indexTable=" + indexTable +
                ", orderKeys=" + Arrays.toString(orderKeys) +
                '}';
    }
}