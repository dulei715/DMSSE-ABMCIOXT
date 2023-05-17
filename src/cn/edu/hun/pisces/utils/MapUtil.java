package cn.edu.hun.pisces.utils;

import java.util.Collection;
import java.util.Map;

/**
 * @author: Leilei Du
 * @Date: 2018/11/28 19:40
 */
public class MapUtil {
    public static int getMapValueTotalSize(Map<?, ? extends Collection> map){
        int result = 0;
        for (Map.Entry<?, ? extends Collection> objectEntry : map.entrySet()) {
            Collection collection = objectEntry.getValue();
            result += collection.size();
        }
        return result;
    }
}