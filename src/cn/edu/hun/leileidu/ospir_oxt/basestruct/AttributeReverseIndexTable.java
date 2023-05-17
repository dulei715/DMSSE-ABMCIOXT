package cn.edu.hun.leileidu.ospir_oxt.basestruct;

import cn.edu.hun.leileidu.basestruct.FileList;
import cn.edu.hun.leileidu.related.basestruct.ReverseIndexTable;

import java.io.File;
import java.util.Collection;
import java.util.Map;

/**
 * @author: Leilei Du
 * @Date: 2018/7/24 15:36
 */
public class AttributeReverseIndexTable extends ReverseIndexTable {


    /**
     *  传入的参数的String必须是关键字的值和属性值的连接
     * @param indexDataTable
     * @param combineSymbol
     */
    @Deprecated
    public void construct0(Map<String, ? extends Collection<File>> indexDataTable, String combineSymbol) {
        AttributeKeyword attributeKeyword = null;
        Collection<File> collection = null;
        FileList fileList = null;
        int i = 0, orderKeySize = this.orderKeys.length;
        for (Map.Entry<String,? extends Collection<File>> entry : indexDataTable.entrySet()) {
            String keyStr = entry.getKey();
            String[] keySplit = keyStr.split(combineSymbol);

            attributeKeyword = new AttributeKeyword(keySplit[1],keySplit[0]);
            collection = entry.getValue();

            fileList = new FileList(collection, this.orderKeys[i]);

            //use the order key array circularly
            i = (i+1)%orderKeySize;
            attributeKeyword.setFileNumber(collection.size());
            this.indexTable.put(attributeKeyword,fileList);
        }
    }
    public void construct(Map<String, ? extends Collection<File>> indexDataTable, String combineSymbol){
        AttributeKeyword attributeKeyword = null;
        Collection<File> collection = null;
        FileList fileList = null;
        int i = 0, orderKeySize = this.orderKeys.length;
        for (Map.Entry<String,? extends Collection<File>> entry : indexDataTable.entrySet()) {
            String keyStr = entry.getKey();
            String[] keySplit = keyStr.split(combineSymbol);

            String attributeKey = keySplit[0];
//            String attributeValue = "";
//            int j = 1;
//            for (;j < keySplit.length - 1; j++) {
//                attributeValue = attributeValue.concat(keySplit[j]).concat(Constant.DEFAULT_COMBINE_SYMBOL);
//            }
//            attributeValue = attributeValue.concat(keySplit[j]);

            attributeKeyword = new AttributeKeyword(attributeKey,keyStr);
            collection = entry.getValue();

            fileList = new FileList(collection, this.orderKeys[i]);

            //use the order key array circularly
            i = (i+1)%orderKeySize;
            attributeKeyword.setFileNumber(collection.size());
            this.indexTable.put(attributeKeyword,fileList);
        }
    }
}