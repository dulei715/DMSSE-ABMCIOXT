package cn.edu.hun.leileidu.related.basestruct;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Leilei Du
 * @Date: 2018/7/9 14:12
 */
public class XToken<T> {
    protected List<T> xtokenElementList = null;

    public XToken(List<T> xtokenElementList) {
        this.xtokenElementList = xtokenElementList;
    }

    public XToken() {
        xtokenElementList = new ArrayList<>();
    }

    public List<T> getXtokenElementList() {
        return xtokenElementList;
    }

    public void setXtokenElementList(List<T> xtokenElementList) {
        this.xtokenElementList = xtokenElementList;
    }

    @Override
    public String toString() {
        if(xtokenElementList == null){
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        int len = xtokenElementList.size() - 1;
        stringBuilder.append("[");
        for (; i < len; i++) {
            stringBuilder.append(xtokenElementList.get(i)).append(", ");
        }
        stringBuilder.append(xtokenElementList.get(i)).append("]");
        return stringBuilder.toString();
    }
}