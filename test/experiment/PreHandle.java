package experiment;

import cn.edu.hun.pisces.experiment.utils.ExpUtil;
import cn.edu.hun.pisces.utils.Constant;
import cn.edu.hun.pisces.utils.ioutil.MyPrint;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Leilei Du
 * @Date: 2018/11/25 16:46
 */
public class PreHandle {

    public String dataDir = "F:\\Users\\Administrator\\IdeaProjects\\OXTImprovedataset\\out_reverse_index\\";
    public List<String> datasetStrList = new ArrayList<>();

    @Before
    public void initData(){
//        File fileDir = new File(dataDir);
//        File[] files = fileDir.listFiles();
//        for (int i = 1; i < files.length; i++) {
//            datasetStrList.add(files[i].getAbsolutePath());
//        }
        String dataSetStr = null;
        datasetStrList.add(dataDir + "dataset_out_00.txt");
        for (int i = 20; i <= 100; i += 20) {
            dataSetStr = dataDir + "dataset_out_" + i + ".txt";
            datasetStrList.add(dataSetStr);
        }
    }

    @Test
    public void testTest(){
        MyPrint.showList(datasetStrList);
    }



    @Test
    public void preOSPIR_OXT(){
        List<String> outFileKeysList = ExpUtil.getTestFileStrList("ospir-oxt", "keys.properties");
        List<String> outFileClientList = ExpUtil.getTestFileStrList("ospir-oxt", "client.properties");
        List<String> outFilePolicyList = ExpUtil.getTestFileStrList("ospir-oxt", "policy.properties");
        for (int i = 0; i < datasetStrList.size(); i++) {
            String datasetStr = datasetStrList.get(i);
            String keysPath = outFileKeysList.get(i);
            String clientPath = outFileClientList.get(i);
            String policyPath = outFilePolicyList.get(i);

            cn.edu.hun.pisces.ospir_oxt.role.DataOwner ospirOXTDataOwner = new cn.edu.hun.pisces.ospir_oxt.role.DataOwner(BigInteger.ONE);
//            ospirOXTDataOwner.edbSetup(datasetStr);
            ospirOXTDataOwner.readDataFromFile(datasetStr);
            ospirOXTDataOwner.parseDataBase();
            ospirOXTDataOwner.selectKey(true, keysPath);
            ospirOXTDataOwner.initialize();

            ospirOXTDataOwner.initializeClient(Constant.DEFAULT_CLIENT_NUMBER, Constant.DEFAULT_OSPIR_CLIENT_NUMBER_UP_BOUND,clientPath);
            ospirOXTDataOwner.initializePolicy(policyPath, true, true);
        }
    }

    @Test
    public void preABMCI_OXT(){
        List<String> outFileKeysList = ExpUtil.getTestFileStrList("abmci-oxt", "keys.properties");
        List<String> outFileClientList = ExpUtil.getTestFileStrList("abmci-oxt", "clients.properties");
        List<String> outFilePrivilege = ExpUtil.getTestFileStrList("abmci-oxt", "privilege.properties");
        for (int i = 0; i < datasetStrList.size(); i++) {
            String datasetStr = datasetStrList.get(i);
            String keysPath = outFileKeysList.get(i);
            String clientsPath = outFileClientList.get(i);
            String privilegePath = outFilePrivilege.get(i);

            cn.edu.hun.pisces.improved.advanced.role.DataOwner abmciOXTDataOwner = new cn.edu.hun.pisces.improved.advanced.role.DataOwner();
            abmciOXTDataOwner.readDataFromFile(datasetStr);
            abmciOXTDataOwner.parseClients(true, clientsPath);
            abmciOXTDataOwner.parseDataBase();
            abmciOXTDataOwner.selectKey(true, keysPath);
            abmciOXTDataOwner.constructPrivilege(true, privilegePath, true);

        }
    }




























}