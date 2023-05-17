package experiment;

import cn.edu.hun.leileidu.basestruct.Keyword;
import cn.edu.hun.leileidu.basestruct.TSetElement;
import cn.edu.hun.leileidu.experiment.utils.ExpUtil;
import cn.edu.hun.leileidu.improved.advanced.basestruct.AdvXToken;
import cn.edu.hun.leileidu.improved.others.utils.ClientPolicyUtil;
import cn.edu.hun.leileidu.ospir_oxt.basestruct.AttributeKeyword;
import cn.edu.hun.leileidu.ospir_oxt.basestruct.BXToken;
import cn.edu.hun.leileidu.ospir_oxt.utils.ClientUtil;
import cn.edu.hun.leileidu.related.basestruct.EDB;
import cn.edu.hun.leileidu.related.basestruct.XToken;
import cn.edu.hun.leileidu.related.basestruct.stag.Stag;
import cn.edu.hun.leileidu.utils.KeywordUtil;
import cn.edu.hun.leileidu.utils.cryptography.BilinearUtil;
import cn.edu.hun.leileidu.utils.cryptography.CryptoFunciton;
import cn.edu.hun.leileidu.utils.fileutil.EmailKeywordSplitUtil;
import cn.edu.hun.leileidu.utils.testutil.GenerateTokenTestUtil;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author: Leilei Du
 * @Date: 2018/11/17 14:44
 */
@SuppressWarnings("Duplicates")
public class ExperimentTest {

    public BilinearUtil bilinearUtil = null;
    public Field zField = null;
    public Element defaultSecretKey = null;

    public String primaryDirName = "F:\\Users\\Administrator\\IdeaProjects\\OXTImprovedataset\\";
    public List<String> dirNameList = new ArrayList<>();
    public List<String> outFileNameList = new ArrayList<>();
    public String outDirName = "F:\\Users\\Administrator\\IdeaProjects\\OXTImprovedataset\\out_reverse_index\\";

    cn.edu.hun.leileidu.related.role.DataOwner dataOwnerOXT = null;

    cn.edu.hun.leileidu.ospir_oxt.role.DataOwner dataOwnerOSPIROXT = null;

    cn.edu.hun.leileidu.improved.basic.role.DataOwnerParallel dataOwnerParallelBMCIOXT = null;

    cn.edu.hun.leileidu.improved.basic.role.DataOwner dataOwnerBMCIOXT = null;

    cn.edu.hun.leileidu.improved.advanced.role.DataOwner dataOwnerAMCIOXT = null;

    //client

    cn.edu.hun.leileidu.related.role.Client clientOXT = null;

    cn.edu.hun.leileidu.ospir_oxt.role.Client clientOSPIROXT = null;

    cn.edu.hun.leileidu.improved.basic.role.Client clientBMCIOXT = null;

    cn.edu.hun.leileidu.improved.advanced.role.Client clientAMCIOXT = null;


    //server
    cn.edu.hun.leileidu.related.role.Server serverOXT = null;

    cn.edu.hun.leileidu.ospir_oxt.role.Server serverOSPIROXT = null;

    cn.edu.hun.leileidu.improved.basic.role.Server serverBMCIOXT = null;

    cn.edu.hun.leileidu.improved.advanced.role.Server serverAMCIOXT = null;


    public Properties readClientRandom() {
        String path = "F:\\Users\\Administrator\\IdeaProjects\\MutiClientOXTImprove\\datafile\\improved\\clients.properties";
        File file  = new File(path);
//        System.out.println(file.exists());
        FileInputStream fin = null;
        Properties properties = new Properties();
        try {
            fin = new FileInputStream(path);
            properties.load(fin);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    @Before
    public void beforeFun() throws UnsupportedEncodingException {
        bilinearUtil = new BilinearUtil();
        zField = bilinearUtil.getzField();
        String defaultStr = "s;dfoitujkfdknsdfkjlajnx";
        defaultSecretKey = zField.newElement().setFromHash(defaultStr.getBytes("utf-8"), 0, defaultStr.length());

        //for test
        dirNameList.add(primaryDirName + "dataset_00");
        outFileNameList.add(outDirName + "dataset_out_00.txt");

        for (int i = 20; i <= 100; i += 20) {
            dirNameList.add(primaryDirName + "dataset_" + i);
            outFileNameList.add(outDirName + "dataset_out_" + i + ".txt");
        }
//        dataOwnerOXT = new cn.edu.hun.pisces.related.role.DataOwner();
//        dataOwnerOSPIROXT = new cn.edu.hun.pisces.ospir_oxt.role.DataOwner(BigInteger.ONE);
//        dataOwnerParallelBMCIOXT = new cn.edu.hun.pisces.improved.basic.role.DataOwnerParallel();
//        dataOwnerBMCIOXT = new cn.edu.hun.pisces.improved.basic.role.DataOwner();
//        dataOwnerAMCIOXT = new cn.edu.hun.pisces.improved.advanced.role.DataOwner();

//        clientOXT = new cn.edu.hun.pisces.related.role.Client();
//        clientOSPIROXT = new cn.edu.hun.pisces.ospir_oxt.role.Client(BigInteger.ONE);
//        clientBMCIOXT = new cn.edu.hun.pisces.improved.basic.role.Client();
//        clientAMCIOXT = new cn.edu.hun.pisces.improved.advanced.role.Client();

        serverOXT = new cn.edu.hun.leileidu.related.role.Server();

        serverOSPIROXT = new cn.edu.hun.leileidu.ospir_oxt.role.Server();

        serverBMCIOXT = new cn.edu.hun.leileidu.improved.basic.role.Server();

        serverAMCIOXT = new cn.edu.hun.leileidu.improved.advanced.role.Server();



    }

    public Object getClient(String name, String clientPath, String pPath){
        if(name.equals("oxt")){
            return new cn.edu.hun.leileidu.related.role.Client();
        }
        if(name.equals("ospir-oxt")){
            String clientID = ClientUtil.getMostPowerfulClientIDFromPolicyFile(pPath);
            return new cn.edu.hun.leileidu.ospir_oxt.role.Client(new BigInteger(clientID));
        }
        if(name.equals("bmci-oxt")){
            return ClientPolicyUtil.getBasicPowerfulClientByPrivilege(clientPath, pPath);
        }
        if(name.equals("amci-oxt")){
            return ClientPolicyUtil.getAdvancedPowerfulClientByPrivilege(clientPath, pPath);
        }
        return null;
    }

    /**
     *  分别用数据集的 20%, 40%, 60%, 80% 和 整个数据集来提取倒排索引，并将它们分别存储在相应的文件中。
     */
    @Test
    public void parseDB(){
        for (int i = 0; i < dirNameList.size(); i++) {
//            System.out.println(dirNameList.get(i) + " ------ " + outFileNameList.get(i));
            int count = EmailKeywordSplitUtil.splitAndWriteToFile(dirNameList.get(i), outFileNameList.get(i));
            System.out.println(dirNameList.get(i) + ": " + count);
        }
    }

    @Test
    public void parsePartDB(){
        String dirPath = "F:\\Users\\Administrator\\IdeaProjects\\OXTImprovedataset\\dataset_00\\";
        String outFilePath = "F:\\Users\\Administrator\\IdeaProjects\\OXTImprovedataset\\out_reverse_index\\dataset_out_00.txt";
        int count = EmailKeywordSplitUtil.splitAndWriteToFile(dirPath, outFilePath);
        System.out.println(dirPath + ": " + count);
    }

    @Test
    public void partEDBSetup(){
        String outFileName = "F:\\Users\\Administrator\\IdeaProjects\\OXTImprovedataset\\out_reverse_index\\dataset_out_00.txt";
//        System.out.println("OXT=========================================================");
//        System.out.print("\t");
//        dataOwnerOXT.edbSetup(outFileName);
//        System.out.println();
//
//        System.out.println("OSPIR-OXT===================================================");
//        System.out.print("\t");
//        dataOwnerOSPIROXT.edbSetup(outFileName);
//        dataOwnerOSPIROXT.initializeBeforeToken();
//        System.out.println();

//        System.out.println("BMCI-OXT====================================================");
//        System.out.print("\t");
//        dataOwnerBMCIOXT.edbSetup(outFileName);
//        System.out.println();

//        System.out.println("AMCI-OXT====================================================");
//        System.out.print("\t");
//        dataOwnerAMCIOXT.edbSetup(outFileName);
//        System.out.println();


    }



    @Test
    public void edbSetup(){

//        for (int i = 1; i < outFileNameList.size(); i++) {
//            System.out.println(20*(i+1) + "% data:::::::::::::::::::::::::::::::::::::::::::::::::::");
//            String outFileName = outFileNameList.get(i);
//            System.out.println("OXT########################################");
//            dataOwnerOXT.edbSetup(outFileName);
//            System.out.println("OSPIT-OXT##################################");
//            dataOwnerOSPIROXT.edbSetup(outFileName);
//            dataOwnerParallelBMCIOXT.edbSetup(outFileName);
//            System.out.println("BMCI-OXT###################################");
//            dataOwnerBMCIOXT.edbSetup(outFileName);
//            System.out.println("AMCI-OXT###################################");
//            dataOwnerAMCIOXT.edbSetup(outFileName);
//            break;
//        }
    }



//    public void initBAMCOXTClient(){
//        Properties properties = readClientRandom();
//        String skStr = (String) properties.keys().nextElement();
//        String rStr = properties.getProperty(skStr);
//        Element sk = zField.newElement().set(Integer.valueOf(skStr));
//        Element r = zField.newElement().set(Integer.valueOf(rStr));
//
//        clientBMCIOXT.setSecretKey(sk);
//        clientBMCIOXT.setGr(this.bilinearUtil.getGPowValue(r));
//
//        clientAMCIOXT.setSecretKey(sk);
//        clientAMCIOXT.setGr(this.bilinearUtil.getGPowValue(r));
//    }

    public void initServers(EDB edbOXT, EDB edbOSPIROXT, EDB edbBMCIOXT, cn.edu.hun.leileidu.improved.advanced.basestruct.EDB edbAMCIOXT) {
        this.serverOXT.setTset(edbOXT.getTset());
        this.serverOXT.setXset(edbOXT.getXset());

        this.serverOSPIROXT.setTset(edbOSPIROXT.getTset());
        this.serverOSPIROXT.setXset(edbOSPIROXT.getXset());

        this.serverBMCIOXT.setTset(edbBMCIOXT.getTset());
        this.serverBMCIOXT.setXset(edbBMCIOXT.getXset());

        this.serverAMCIOXT.setTset(edbAMCIOXT.getTset());
        this.serverAMCIOXT.setXset(edbAMCIOXT.getUxset(), edbAMCIOXT.getXxset());
    }

    private void initOXTDataOwner(){
        this.dataOwnerOXT = new cn.edu.hun.leileidu.related.role.DataOwner();
    }

    private void initOSPIROXTDataOwner(){
        this.dataOwnerOSPIROXT = new cn.edu.hun.leileidu.ospir_oxt.role.DataOwner(BigInteger.ONE);
    }

    private void initBMCIOXTDataOwnerParallel(){
        this.dataOwnerParallelBMCIOXT = new cn.edu.hun.leileidu.improved.basic.role.DataOwnerParallel();
    }

    private void initBMCIOXTDataOwner(){
        this.dataOwnerBMCIOXT = new cn.edu.hun.leileidu.improved.basic.role.DataOwner();
    }

    private void initAMCIOXTDataOwner(){
        this.dataOwnerAMCIOXT = new cn.edu.hun.leileidu.improved.advanced.role.DataOwner();
    }




    @Test
    public void totalTest() throws FileNotFoundException {



        /**
         * 测试 EDBSetup
         */

        String ospirOXTKeyPath = null;
        String ospirOXTClientPath = null;
        String ospirOXTPolicyPath = null;

        String abmciOXTClientPath = null;
        String abmciOXTKeyPath = null;
        String abmciOXTPrivilegePath = null;

        List<String> ospirOXTKeyPathList = ExpUtil.getTestFileStrList("ospir-oxt", "keys.properties");
        List<String> ospirOXTClientPathList = ExpUtil.getTestFileStrList("ospir-oxt", "client.properties");
        List<String> ospirOXTPolicyPathList = ExpUtil.getTestFileStrList("ospir-oxt", "policy.properties");

        List<String> abmciOXTClientsPathList = ExpUtil.getTestFileStrList("abmci-oxt", "clients.properties");
        List<String> abmciOXTKeysPathList = ExpUtil.getTestFileStrList("abmci-oxt", "keys.properties");
        List<String> abmciOXTPrivilegePathList = ExpUtil.getTestFileStrList("abmci-oxt", "privilege.properties");

        EDB edbOXT = null;
        EDB edbOSPIROXT = null;
        EDB edbBMCIOXT = null;
        cn.edu.hun.leileidu.improved.advanced.basestruct.EDB edbAMCIOXT = null;
        for (int i = 0; i < outFileNameList.size(); i++) {

            if(i < 5){
                continue;
            }

            System.out.println(20*(i) + "% data:::::::::::::::::::::::::::::::::::::::::::::::::::");
            String outFileName = outFileNameList.get(i);
            System.out.println();


//            System.out.println("OXT########################################");
//            initOXTDataOwner();
//            edbOXT = dataOwnerOXT.edbSetup(outFileName);
//            System.out.println();

//            System.out.println("OSPIT-OXT##################################");
//            ospirOXTKeyPath = ospirOXTKeyPathList.get(i);
//            ospirOXTClientPath = ospirOXTClientPathList.get(i);
//            ospirOXTPolicyPath = ospirOXTPolicyPathList.get(i);
//            initOSPIROXTDataOwner();
//            edbOSPIROXT = dataOwnerOSPIROXT.edbSetup(outFileName, false, ospirOXTKeyPath);
//            dataOwnerOSPIROXT.initializeClient(-1, Constant.DEFAULT_OSPIR_CLIENT_NUMBER_UP_BOUND, ospirOXTClientPath);
//            dataOwnerOSPIROXT.initializePolicy(ospirOXTPolicyPath, false, false);
//            System.out.println();


            abmciOXTClientPath = abmciOXTClientsPathList.get(i);
            abmciOXTKeyPath = abmciOXTKeysPathList.get(i);
            abmciOXTPrivilegePath = abmciOXTPrivilegePathList.get(i);
            System.out.println("BMCI-OXT-parallel###################################");
            initBMCIOXTDataOwnerParallel();
            dataOwnerParallelBMCIOXT.edbSetup(outFileName, false, abmciOXTClientPath, false, false, abmciOXTPrivilegePath, false);
            System.out.println();

            System.out.println("BMCI-OXT###################################");
            initBMCIOXTDataOwner();
            edbBMCIOXT = dataOwnerBMCIOXT.edbSetup(outFileName, false, abmciOXTClientPath, false, false, abmciOXTPrivilegePath, false);
            System.out.println();

            System.out.println("AMCI-OXT###################################");
            initAMCIOXTDataOwner();
            edbAMCIOXT = dataOwnerAMCIOXT.edbSetup(outFileName, false, abmciOXTClientPath, false, abmciOXTKeyPath, false, abmciOXTPrivilegePath, false);
            System.out.println("\n");
//            break;

        }

        /**
         * 初始化client
         */
        clientOXT = (cn.edu.hun.leileidu.related.role.Client)this.getClient("oxt", "", "");
        clientOSPIROXT = (cn.edu.hun.leileidu.ospir_oxt.role.Client)this.getClient("ospir-oxt", ospirOXTClientPath, ospirOXTPolicyPath);
        clientBMCIOXT = (cn.edu.hun.leileidu.improved.basic.role.Client)this.getClient("bmci-oxt", abmciOXTClientPath, abmciOXTPrivilegePath);
        clientAMCIOXT = (cn.edu.hun.leileidu.improved.advanced.role.Client)this.getClient("amci-oxt", abmciOXTClientPath, abmciOXTPrivilegePath);


        /**
         * 测试 GenerateToken 和 Search
         */

        //1, 2, 5, 10, 100
        List<Integer> numberList = GenerateTokenTestUtil.getDefaultTestTokenNumber();

        // TODO:修改
        String fileStr = outFileNameList.get(numberList.size() - 1);
//        String fileStr = outFileNameList.get(0);

        List<Keyword> keywordList = KeywordUtil.readKeyword(fileStr, 100);

//        System.out.println(list);
//        initBAMCOXTClient();
        initServers(edbOXT, edbOSPIROXT, edbBMCIOXT, edbAMCIOXT);

        /*
         *  一些基本变量
         */
        Stag stag = null;

        for (int i = 0; i < numberList.size(); i++) {
            int tokenNum = numberList.get(i);

            List<Keyword> keywordListNow = keywordList.subList(0, tokenNum);

            System.out.println("\n");
            System.out.println("use " + tokenNum + " keyword(s) to search... %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
            System.out.println();

            /**
             *  测试OXT
             */
            System.out.println("oxt ********************************************************************");
//            clientOXT = (cn.edu.hun.pisces.related.role.Client)this.getClient("oxt", "");
            clientOXT.setStermAndXterm(keywordListNow);

            //  测试 stag 产生时间
            long startGenStag = System.currentTimeMillis();
            stag = clientOXT.tSetGetTag();
            long endGenStag = System.currentTimeMillis();
            long genStagTime = endGenStag - startGenStag;

            //  测试搜索 stag 相关文件索引的时间
            long startTSetSearch = System.currentTimeMillis();
            List<TSetElement> tsetElementList = serverOXT.tSetRetrieve(stag);
            long endTSetSearch = System.currentTimeMillis();
            long searchTSetTime = endTSetSearch - startTSetSearch;

            //  测试 xtags 产生时间
            long startGenxtokens = System.currentTimeMillis();
            List<XToken> xTokenList = clientOXT.generateSearchXToken(tsetElementList.size());
            long endGenxtokens = System.currentTimeMillis();
            long genXtokenTime = endGenxtokens - startGenxtokens;

            //  测试索索 xtags 相关文件索引的时间
            long startXSetSearch = System.currentTimeMillis();
            serverOXT.searchXTokenListPart(tsetElementList, xTokenList);
            long endXSetSearch = System.currentTimeMillis();
            long searchXSetTime = endXSetSearch - startXSetSearch;

            System.out.println("stoken产生时间: " + genStagTime);
            System.out.println("xtokens产生时间: " + genXtokenTime);
            System.out.println("检索TSet时间: " + searchTSetTime);
            System.out.println("检索XSet时间: " + searchXSetTime);
            System.out.println("(genTokensTime, searchTime) = " + "(" + (genStagTime + genXtokenTime) + ", " + (searchTSetTime + searchXSetTime) + ")");
            System.out.println();


            /**
             * 测试 ospir-oxt
             */
            System.out.println("ospir-oxt **************************************************************");
            List<AttributeKeyword> attributeKeywordListNow = KeywordUtil.toAttributeKeywordList(keywordListNow);
//            clientOSPIROXT = this.getClient("ospir-oxt", )
            clientOSPIROXT.setStermAndXterm(attributeKeywordListNow);

            // 测试 client 与 data owner 交互的 ablind 和 av 产生时间
            long startCDFirstRoundGenTokens = System.currentTimeMillis();
            List<Element> aBlind = clientOSPIROXT.getABlind();
            List<String> av = clientOSPIROXT.getAttributeVector();
            long endCDFirstRoundGenTokens = System.currentTimeMillis();
            long cdFirstRoundGenTokensTime = endCDFirstRoundGenTokens - startCDFirstRoundGenTokens;

            // 测试 client 与 data owner 交互的 tempStrap, tempBstag 以及 tempBxtrap 的产生时间
            dataOwnerOSPIROXT.setClientAttributeVector(av);
            long startDCGenTemp = System.currentTimeMillis();
            /*
                测试av是否属于P
                TODO
            */
            dataOwnerOSPIROXT.generatePBlind(aBlind.size());
            Element tempStrap = dataOwnerOSPIROXT.getTempStrap(aBlind.get(0));
            Element tempBstag = dataOwnerOSPIROXT.getTempBstag(aBlind.get(0));
            List<Element> tempBxtrapList = dataOwnerOSPIROXT.getTempBxtrap(aBlind);
            String[] env = dataOwnerOSPIROXT.getEnv();
            long endDCGenTemp = System.currentTimeMillis();
            long dcGenTemp = endDCGenTemp - startDCGenTemp;

            // 测试 client 与 data owner 交互的 strap, bstag 以及 bxtrap 的产生时间
            long startCDSecondRoundGenTokens = System.currentTimeMillis();
            Element strap = clientOSPIROXT.getStrap(tempStrap);
            Element bstag = clientOSPIROXT.getBstag(tempBstag);
            List<Element> bxtrapList = clientOSPIROXT.getBxtrap(tempBxtrapList);
            long endCDSecondRoundGenTokens = System.currentTimeMillis();
            long cdSecondRoundGenTokens = endCDSecondRoundGenTokens - startCDSecondRoundGenTokens;

            // 测试 client 与 server 交互 bstag 时间
            //直接获取即可，不用时间

            // 测试 server 检索 TSet 时间
            long startOSPIRTSetSearch = System.currentTimeMillis();
            boolean haveRight = serverOSPIROXT.checkRight(env);
            if(!haveRight){
                System.out.println("ρ 被修改了！");
                return;
            }
            serverOSPIROXT.decryptAndGetPBlind(CryptoFunciton.getEnvData(env));
            stag = serverOSPIROXT.getStag(bstag);
            tsetElementList = serverOSPIROXT.tSetRetrieve(stag);
            long endOSPIRTSetSearch = System.currentTimeMillis();
            long ospirTSetSearch = endOSPIRTSetSearch - startOSPIRTSetSearch;

            // 测试 client 与 server 交互 bxtoken 产生时间
            long startOSPIRGenXTokens = System.currentTimeMillis();
            List<BXToken> bxTokenList = clientOSPIROXT.generateBXTokenList(tsetElementList.size());
            long endOSPIRGenXTokens = System.currentTimeMillis();
            long ospirGenXtokens = endOSPIRGenXTokens - startOSPIRGenXTokens;

            // 测试 server 检索 XSet 时间
            long startOSPIRXSetSearch = System.currentTimeMillis();
            List<String> strings = serverOSPIROXT.searchXTokenListPart(tsetElementList, bxTokenList);
            long endOSPIRXSetSearch = System.currentTimeMillis();
            long ospirXSetSearch = endOSPIRXSetSearch - startOSPIRXSetSearch;

            System.out.println("aBlind 和 av 产生时间: " + cdFirstRoundGenTokensTime);
            System.out.println("各个 temp 产生时间: " + dcGenTemp);
            System.out.println("btokens 产生时间: " + cdSecondRoundGenTokens);

            System.out.println("TSet 检索时间: " + ospirTSetSearch);
            System.out.println("xtokens 产生时间: " + ospirGenXtokens);
            System.out.println("XSet 检索时间: " + ospirXSetSearch);

            System.out.println("(genTokensTime, searchTime) = (" + (cdFirstRoundGenTokensTime + dcGenTemp + cdSecondRoundGenTokens + ospirGenXtokens) + ", " + (ospirTSetSearch + ospirXSetSearch) + ")");
            System.out.println();


            /**
             * 测试 BMCI-OXT
             */
            clientBMCIOXT.setStermAndXterm(keywordListNow);

            // 测试 client 生成 stoken
            long startGenStoken = System.currentTimeMillis();
            Element stoken = clientBMCIOXT.getSToken();
            long endGenStoken = System.currentTimeMillis();
            long bmciGenStokenTime = endGenStoken - startGenStoken;

            // 测试生成 stag 和 检索 tset
            long startBMCITSetSearch = System.currentTimeMillis();
            tsetElementList = serverBMCIOXT.tSetRetrieve(stoken, clientBMCIOXT.getPublicKey());
            long endBMCITSetSearch = System.currentTimeMillis();
            long bmciTSetSearchTime = endBMCITSetSearch - startBMCITSetSearch;

            // 测试 client 生成 xtokens
            long startBMCIGenXtokens = System.currentTimeMillis();
            xTokenList = clientBMCIOXT.generateSearchXTokens(tsetElementList.size());
            long endBMCIGenXtokens = System.currentTimeMillis();
            long genBMCIXtokensTime = endBMCIGenXtokens - startBMCIGenXtokens;

            // 测试 server 检索xset
            long startBMCIXSetSearch = System.currentTimeMillis();
            serverBMCIOXT.searchXTokenListPart(tsetElementList, xTokenList);
            long endBMCIXSetSearch = System.currentTimeMillis();
            long bmciXSetSearchTime = endBMCIXSetSearch - startBMCIXSetSearch;

            System.out.println("stoken产生时间: " + bmciGenStokenTime);
            System.out.println("xtokens产生时间: " + genBMCIXtokensTime);
            System.out.println("检索TSet时间: " + bmciTSetSearchTime);
            System.out.println("检索XSet时间: " + bmciXSetSearchTime);
            System.out.println("(genTokensTime, searchTime) = " + "(" + (bmciGenStokenTime + genBMCIXtokensTime) + ", " + (bmciTSetSearchTime + bmciXSetSearchTime) + ")");
            System.out.println();


            /**
             *  测试 AMCI-OXT
             */
            clientAMCIOXT.setStermAndXterm(keywordListNow);
            clientAMCIOXT.setKeys(dataOwnerAMCIOXT.getKs(), dataOwnerAMCIOXT.getKx(), dataOwnerAMCIOXT.getKi(), dataOwnerAMCIOXT.getKz(), dataOwnerAMCIOXT.getKt());

            // 测试生成 stoken
            long startAMCIGenStoken = System.currentTimeMillis();
            stoken = clientAMCIOXT.getSToken();
            long endAMCIGenStoken = System.currentTimeMillis();
            long amciGenStokenTime = endAMCIGenStoken - startAMCIGenStoken;

            // 测试生成 stag 和 检索 tset
            long startAMCITSetSearch = System.currentTimeMillis();
            tsetElementList = serverAMCIOXT.tSetRetrieve(stoken, clientAMCIOXT.getPublicKey());
            long endAMCITSetSearch = System.currentTimeMillis();
            long amciTSetSearchTime = endAMCITSetSearch - startAMCITSetSearch;

            // 测试生成 xtokens
            long startAMCIGenXtokens = System.currentTimeMillis();
            AdvXToken advXToken = clientAMCIOXT.generateXToken(tsetElementList.size());
            long endAMCIGenXtokens = System.currentTimeMillis();
            long amciGenXtokensTime = endAMCIGenXtokens - startAMCIGenXtokens;

            // 测试检索 UXSet 和 XXSet
            long startAMCIXSetSearch = System.currentTimeMillis();
            serverAMCIOXT.searchXTokenListPart(tsetElementList, advXToken);
            long endAMCIXSetSearch = System.currentTimeMillis();
            long amciXSetSearchTime = endAMCIXSetSearch - startAMCIXSetSearch;

            System.out.println("stoken产生时间: " + amciGenStokenTime);
            System.out.println("xtokens产生时间: " + amciGenXtokensTime);
            System.out.println("检索TSet时间: " + amciTSetSearchTime);
            System.out.println("检索XSet时间: " + amciXSetSearchTime);
            System.out.println("(genTokensTime, searchTime) = " + "(" + (amciGenStokenTime + amciGenXtokensTime) + ", " + (amciTSetSearchTime + amciXSetSearchTime) + ")");
            System.out.println("\n");

//            break;
        }

    }




}