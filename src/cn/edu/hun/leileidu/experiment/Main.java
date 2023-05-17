package cn.edu.hun.leileidu.experiment;

import cn.edu.hun.leileidu.related.role.DataOwner;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Leilei Du
 * @Date: 2018/11/21 9:33
 */
public class Main {
    public static String primaryDirName = "F:\\Users\\Administrator\\IdeaProjects\\OXTImprovedataset\\";
    public static List<String> dirNameList = new ArrayList<>();
    public static List<String> outFileNameList = new ArrayList<>();
    public static String outDirName = "F:\\Users\\Administrator\\IdeaProjects\\OXTImprovedataset\\out_reverse_index\\";
    public static DataOwner dataOwnerOXT = null;
    public static cn.edu.hun.leileidu.ospir_oxt.role.DataOwner dataOwnerOSPIROXT = null;
    public static cn.edu.hun.leileidu.improved.basic.role.DataOwnerParallel dataOwnerParallelBMCIOXT = null;
    public static cn.edu.hun.leileidu.improved.basic.role.DataOwner dataOwnerBMCIOXT = null;
    public static cn.edu.hun.leileidu.improved.advanced.role.DataOwner dataOwnerAMCIOXT = null;

    public static String resultDir = "F:\\Users\\Administrator\\IdeaProjects\\OXTImprovedataset\\result\\result.txt";

    public static void initialFile(){
        for (int i = 20; i <= 100; i += 20) {
            dirNameList.add(primaryDirName + "dataset_" + i);
            outFileNameList.add(outDirName + "dataset_out_" + i + ".txt");
        }
        dataOwnerOXT = new DataOwner();
        dataOwnerOSPIROXT = new cn.edu.hun.leileidu.ospir_oxt.role.DataOwner(BigInteger.ONE);
        dataOwnerParallelBMCIOXT = new cn.edu.hun.leileidu.improved.basic.role.DataOwnerParallel();
        dataOwnerBMCIOXT = new cn.edu.hun.leileidu.improved.basic.role.DataOwner();
        dataOwnerAMCIOXT = new cn.edu.hun.leileidu.improved.advanced.role.DataOwner();

    }

    public static void main(String[] args) {

//        for (int i = 1; i < outFileNameList.size(); i++) {
//            System.out.println(20*(i+1) + "% data:::::::::::::::::::::::::::::::::::::::::::::::::::\n");
//            String outFileName = outFileNameList.get(i);
//            System.out.println(20*(i+1) + "%: OXT########################################");
//            dataOwnerOXT.edbSetup(outFileName);
//            System.out.println();
//
//            System.out.println(20*(i+1) + "%: OSPIT-OXT##################################");
//            dataOwnerOSPIROXT.edbSetup(outFileName);
//            System.out.println();
//
//            System.out.println(20*(i+1) + "%: Parallel-BMCI-OXT###################################");
//            dataOwnerParallelBMCIOXT.edbSetup(outFileName);
//            System.out.println();
//
//            System.out.println(20*(i+1) + "%: BMCI-OXT###################################");
//            dataOwnerBMCIOXT.edbSetup(outFileName);
//            System.out.println();
//
//            System.out.println(20*(i+1) + "%: AMCI-OXT###################################");
//            dataOwnerAMCIOXT.edbSetup(outFileName);
//            System.out.println();
//            System.out.println();
//
//            break;
//        }


    }
}