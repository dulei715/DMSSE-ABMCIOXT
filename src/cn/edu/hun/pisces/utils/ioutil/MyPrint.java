package cn.edu.hun.pisces.utils.ioutil;

import java.util.*;

import static javafx.scene.input.KeyCode.K;

@SuppressWarnings("ALL")
public class MyPrint {
    public static void  showBytes(byte[] buf, int begin, int len, String split){
        int end = begin + len - 1;
        int j;
        for (j = begin; j < end; j++) {
            System.out.print(buf[j]+split);
        }
        System.out.println(buf[j]);
    }

    public static void showBytes(byte[] buf, int i, int c) {
        showBytes(buf, i, c, " ");
    }

    public static void showBytes(byte[] buf) {
        showBytes(buf, 0, buf.length, " ");
    }

    public static void showBytes(byte[] buf, String split) {
        showBytes(buf, 0, buf.length, split);
    }

    public static void showStrings(String[] buf, int begin, int len, String split){
        int end = begin + len - 1;
        int j;
        for (j = begin; j < end; j++) {
            System.out.print(buf[j]+split);
        }
        System.out.println(buf[j]);
    }

    public static void showStrings(String[] buf, String split){
        showStrings(buf, 0, buf.length, split);
    }

    public static void showStrings(String[] buf){
        showStrings(buf, 0, buf.length, " ");
    }

    public static void showList(List list){
        for (Object obj : list) {
            System.out.println(obj);
        }
    }

    public static void showSet(Set set){
        for (Object obj : set) {
            System.out.println(obj);
        }
    }

    public static void showMap(Map map){
        Set<Map.Entry> entrys = map.entrySet();
        for (Map.Entry entry : entrys) {
            Object key = entry.getKey();
            Object value = entry.getValue();
            System.out.print(key+": ");
            System.out.println(value);
        }
    }

    public static void showMapArrayValue(Map map){
        Set<Map.Entry> entrys = map.entrySet();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{").append(System.lineSeparator());
        for (Map.Entry entry : entrys) {
            Object key = entry.getKey();
            Object[] value = (Object[])entry.getValue();
            int len = value.length - 1;
            int i = 0;
            stringBuilder.append("\t(key=");
            stringBuilder.append(key).append("; value=[");
            for (; i < len; i++) {
                stringBuilder.append(value[i]).append(", ");
            }
            stringBuilder.append(value[i]).append("])").append(System.lineSeparator());
        }
        stringBuilder.append("}").append(System.lineSeparator());
        System.out.println(stringBuilder.toString());
    }

    public static void showMapArraybyteValue(Map map){
        Set<Map.Entry> entrys = map.entrySet();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{").append(System.lineSeparator());
        for (Map.Entry entry : entrys) {
            Object key = entry.getKey();
            byte[] value = (byte[])entry.getValue();
            int len = value.length - 1;
            int i = 0;
            stringBuilder.append("\t(key=");
            stringBuilder.append(key).append("; value=[");
            for (; i < len; i++) {
                stringBuilder.append(value[i]).append(", ");
            }
            stringBuilder.append(value[i]).append("])").append(System.lineSeparator());
        }
        stringBuilder.append("}").append(System.lineSeparator());
        System.out.println(stringBuilder.toString());
    }

    public static void showMapCollecitonValueArraybyteValueValue(Map map){
        Set<Map.Entry> entrys = map.entrySet();
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry entry : entrys) {
            Object key = entry.getKey();
            Collection valueCollection = (Collection) entry.getValue();
            int len = valueCollection.size() - 1;
            Iterator iterator = valueCollection.iterator();
            int i = 0;
            stringBuilder.append(key).append(": {");
            for (; i < len; i++) {
                // 如果是基本数据类型，根据需要转换
                byte[] arr = (byte[]) iterator.next();
                stringBuilder.append("[");
                int j = 0;
                int lenlen = arr.length - 1;
                for (; j < lenlen; j++) {
                    stringBuilder.append(arr[j]).append(", ");
                }
                stringBuilder.append(arr[j]).append("], ");
            }

            byte[] arr = (byte[]) iterator.next();
            stringBuilder.append("[");
            int j = 0;
            int lenlen = arr.length - 1;
            for (; j < lenlen; j++) {
                stringBuilder.append(arr[j]).append(", ");
            }
            stringBuilder.append(arr[j]).append("]}").append(System.lineSeparator());
        }
//        stringBuilder.append("}").append(System.lineSeparator());
        System.out.println(stringBuilder.toString());
    }

    public static void showMapCollecitonValueArrayValueValue(Map map){
        Set<Map.Entry> entrys = map.entrySet();
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry entry : entrys) {
            Object key = entry.getKey();
            Collection valueCollection = (Collection) entry.getValue();
            int len = valueCollection.size() - 1;
            Iterator iterator = valueCollection.iterator();
            int i = 0;
            stringBuilder.append(key).append(": {");
            for (; i < len; i++) {
                // 如果是基本数据类型，根据需要转换
                Object[] arr = (Object[]) iterator.next();
                stringBuilder.append("[");
                int j = 0;
                int lenlen = arr.length - 1;
                for (; j < lenlen; j++) {
                    stringBuilder.append(arr[j]).append(", ");
                }
                stringBuilder.append(arr[j]).append("], ");
            }

            Object[] arr = (Object[]) iterator.next();
            stringBuilder.append("[");
            int j = 0;
            int lenlen = arr.length - 1;
            for (; j < lenlen; j++) {
                stringBuilder.append(arr[j]).append(", ");
            }
            stringBuilder.append(arr[j]).append("]}").append(System.lineSeparator());
        }
//        stringBuilder.append("}").append(System.lineSeparator());
        System.out.println(stringBuilder.toString());
    }


    public static void showTime(long timeMillis){
        long remain = 0L;
        long temp = 0L;
        String rest = String.valueOf(timeMillis % 1000);
        remain = timeMillis / 1000;
        temp = remain % 60;
        String second = String.valueOf(temp);
        if (temp < 10){
            second = "0" + second;
        }

        remain /= 60;
        temp = remain % 60;
        String minute = String.valueOf(temp);
        if(temp < 10){
            minute = "0" + minute;
        }
        temp = remain / 60;
        String hour = String.valueOf(temp) ;
        if(temp < 10){
            hour = "0" + hour;
        }

        System.out.println(hour + ":" + minute + ":" + second + ":" + rest);
    }

}