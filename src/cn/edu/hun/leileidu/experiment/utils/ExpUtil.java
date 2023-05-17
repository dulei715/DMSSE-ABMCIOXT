package cn.edu.hun.leileidu.experiment.utils;

import cn.edu.hun.leileidu.utils.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Leilei Du
 * @Date: 2018/11/26 11:20
 */
public class ExpUtil {
    public static List<String> getTestFileStrList(String dirName, String fileName){

        List<String> outFileStrList = new ArrayList<>();
        String outSuperDir = Constant.DEFAULT_PROGRAM_PATH + "datafile\\test\\";
        String tempDir = null;
        String dir = null;

        // for test
        tempDir = "dataset_00";
        dir = outSuperDir + tempDir + "\\" + dirName + "\\" + fileName;
        outFileStrList.add(dir);

        for (int i = 20; i <= 100; i+=20) {
            tempDir = "dataset_" + i;
            dir = outSuperDir + tempDir + "\\" + dirName + "\\" + fileName;
            outFileStrList.add(dir);
        }
        return outFileStrList;
    }
}