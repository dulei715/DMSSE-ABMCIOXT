package cn.edu.hun.leileidu.test;

import cn.edu.hun.leileidu.utils.ioutil.MyPrint;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

public class FileSplitUtilTest {
    StringBuffer sbuff = null;
    @Before
    public void beforeFun(){
        sbuff = new StringBuffer();
        sbuff.append("Hello My name is Du Leilei");
        sbuff.append(System.lineSeparator());
        sbuff.append("Hello !!!");
        sbuff.append("Hahaha\thuohuohuo\r\nhehe .");
    }

    @Test
    public void fun1(){
        String result = sbuff.toString();
        System.out.println(result);
    }

    @Test
    public void fun2(){
        String result = sbuff.toString();
        String[] parts = result.split("\\s+");
        MyPrint.showStrings(parts, ", ");
        System.out.println(parts.length);
    }

    @Test
    public void fun3(){
        String str = "abcdefg";
        String subStr = str.substring(0,4);
        System.out.println(subStr);
    }

    @Test
    public void fun4() throws IOException {
        String filePath = "datafile\\data1.txt";
        StringBuffer stringBuffer = new StringBuffer();
        File file = new File(filePath);
        BufferedInputStream bufin = new BufferedInputStream(new FileInputStream(file));
        byte[] buff = new byte[100];
        String temp = null;
        int len;
        while ((len = bufin.read(buff)) != -1){
            temp = new String(buff,0,len);
            stringBuffer.append(temp);
        }
        System.out.println(stringBuffer.toString());
    }

    @Test
    public void fun5() throws IOException {
        String filePath = "datafile\\data1.txt";
        StringBuffer stringBuffer = new StringBuffer();
        File file = new File(filePath);
        BufferedReader bufr = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String lineStr = null;
        while((lineStr = bufr.readLine()) != null){
            stringBuffer.append(lineStr);
        }
        System.out.println(stringBuffer.toString());
    }


}