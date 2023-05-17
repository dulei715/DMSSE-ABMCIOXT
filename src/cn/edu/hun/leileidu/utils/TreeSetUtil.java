package cn.edu.hun.leileidu.utils;

import java.util.*;

/**
 * @author: Leilei Du
 * @Date: 2018/7/11 22:00
 */
public class TreeSetUtil {
    /**
     * 注意：返回会返回treeSet的元素，而不是collection中的元素
     * @param treeSet
     * @param collection
     * @param <T>
     * @return
     */
    public static <T> T getFirstAppearElement(TreeSet<T> treeSet, Collection<T> collection, Comparator<? super T> comparator){
        TreeSet<T> orderTreeSet = new TreeSet<>(comparator);
        Object[] objects = treeSet.toArray();
        Iterator<T> iterator = collection.iterator();
        while (iterator.hasNext()){
            T t = iterator.next();
            for(int i = 0; i < objects.length; ++i){
                T tTemp = (T) objects[i];
                if(tTemp.equals(t)){
                    orderTreeSet.add(tTemp);
                    break;
                }
            }
        }
        return orderTreeSet.first();
    }
}