package cn.edu.hun.pisces.improved.advanced.run;

import cn.edu.hun.pisces.basestruct.Keyword;
import cn.edu.hun.pisces.basestruct.TSetElement;
import cn.edu.hun.pisces.improved.advanced.basestruct.AdvXToken;
import cn.edu.hun.pisces.improved.advanced.basestruct.EDB;
import cn.edu.hun.pisces.improved.advanced.basestruct.UXSet;
import cn.edu.hun.pisces.improved.advanced.basestruct.XXSet;
import cn.edu.hun.pisces.improved.advanced.role.Client;
import cn.edu.hun.pisces.improved.advanced.role.DataOwner;
import cn.edu.hun.pisces.improved.advanced.role.Server;
import cn.edu.hun.pisces.improved.others.utils.ClientPolicyUtil;
import cn.edu.hun.pisces.related.basestruct.TSet;
import cn.edu.hun.pisces.utils.Constant;
import cn.edu.hun.pisces.utils.KeywordUtil;
import cn.edu.hun.pisces.utils.ioutil.MyPrint;
import cn.edu.hun.pisces.utils.cryptography.BilinearUtil;
import cn.edu.hun.pisces.utils.testutil.GenerateTokenTestUtil;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;

import java.util.*;

@SuppressWarnings("ALL")
public class Main {
    public static BilinearUtil bilinearUtil = new BilinearUtil();
    public static Field zField = null;

    public static String outFileName = "F:\\Users\\Administrator\\IdeaProjects\\OXTImprovedataset\\out_reverse_index\\dataset_out_00.txt";
    public static String privilegePath = Constant.DEFAULT_PROGRAM_PATH + "datafile\\improved\\privilege.properties";
    public static String clientPath = Constant.DEFAULT_PROGRAM_PATH + "datafile\\improved\\clients.properties";
    public static String keysPath = Constant.DEFAULT_PROGRAM_PATH + "datafile\\improved\\keys.properties";



    public static DataOwner dataOwnerAMCIOXT = null;
    public static Client clientAMCIOXT = null;
    public static Server serverAMCIOXT = null;

    public static void init(){
        zField = bilinearUtil.getzField();

        // 初始化各个角色
        dataOwnerAMCIOXT = new DataOwner();
    }

    public static void initClient(Client client){
        if(client != null){
            clientAMCIOXT = client;
            return;
        }
        System.out.println("没有用户有权限可以查这么多关键字");
        List<String> stringList = ClientPolicyUtil.readClientsProperties(0,privilegePath);
        String skStr = stringList.get(0);
        String rStr = stringList.get(1);
        Element secretKey = zField.newElement().set(Integer.valueOf(skStr)).getImmutable();
        Element randomR = zField.newElement().set(Integer.valueOf(rStr));
        Element gr = bilinearUtil.getGPowValue(randomR);
        clientAMCIOXT = new Client(secretKey, gr);
    }

    public static void initServer(EDB edb){
        TSet tset = edb.getTset();
        UXSet uxset = edb.getUxset();
        XXSet xxset = edb.getXxset();
        serverAMCIOXT = new Server(tset, uxset, xxset);
    }

    public static void main(String[] args) {
        init();
        EDB edbOXT = dataOwnerAMCIOXT.edbSetup(outFileName, false, clientPath, false, keysPath, false, privilegePath, false);
        System.out.println();

//        initClient();
        initServer(edbOXT);
        List<Integer> tokenNumberList = GenerateTokenTestUtil.getDefaultTestTokenNumber();
        List<Keyword> keywordList = KeywordUtil.readKeyword(outFileName, 100);
        for (int i = 0; i < tokenNumberList.size(); i++){
            int tokenNum = tokenNumberList.get(i);

            List<Keyword> keywordListNow = keywordList.subList(0, tokenNum);

            List<String> keywordValueList = KeywordUtil.toKeywordValueList(keywordListNow);
            Client client = ClientPolicyUtil.getAdvancedClientParamByprivilege(keywordValueList, clientPath, privilegePath);
            initClient(client);
            clientAMCIOXT.setStermAndXterm(keywordList);

            MyPrint.showList(keywordListNow);
            System.out.println();

            System.out.println("use " + tokenNum + " keyword(s) to search... %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");

            /**
             *  测试 AMCI-OXT
             */
            clientAMCIOXT.setStermAndXterm(keywordListNow);
            clientAMCIOXT.setKeys(dataOwnerAMCIOXT.getKs(), dataOwnerAMCIOXT.getKx(), dataOwnerAMCIOXT.getKi(), dataOwnerAMCIOXT.getKz(), dataOwnerAMCIOXT.getKt());

//            // for test
//            Map<Keyword, byte[]> tempKx = dataOwnerAMCIOXT.getKx();
//            Keyword keywordTmp = new Keyword("Content: attorney");
//            byte[] subKxTmp = tempKx.get(keywordTmp);


            // 测试生成 stoken
            long startAMCIGenStoken = System.currentTimeMillis();
            Element stoken = clientAMCIOXT.getSToken();
            long endAMCIGenStoken = System.currentTimeMillis();
            long amciGenStokenTime = endAMCIGenStoken - startAMCIGenStoken;

            // 测试生成 stag 和 检索 tset
            long startAMCITSetSearch = System.currentTimeMillis();
            List<TSetElement> tsetElementList = serverAMCIOXT.tSetRetrieve(stoken, clientAMCIOXT.getPublicKey());
            long endAMCITSetSearch = System.currentTimeMillis();
            long amciTSetSearchTime = endAMCITSetSearch - startAMCITSetSearch;

            // 测试生成 xtokens
            long startAMCIGenXtokens = System.currentTimeMillis();
            AdvXToken advXToken = clientAMCIOXT.generateXToken(tsetElementList.size());
            long endAMCIGenXtokens = System.currentTimeMillis();
            long amciGenXtokensTime = endAMCIGenXtokens - startAMCIGenXtokens;

            // 测试检索 UXSet 和 XXSet
            long startAMCIXSetSearch = System.currentTimeMillis();
            List<String> resultStr = serverAMCIOXT.searchXTokenListPart(tsetElementList, advXToken);
            long endAMCIXSetSearch = System.currentTimeMillis();
            long amciXSetSearchTime = endAMCIXSetSearch - startAMCIXSetSearch;

            System.out.println("stoken产生时间: " + amciGenStokenTime);
            System.out.println("xtokens产生时间: " + amciGenXtokensTime);
            System.out.println("检索TSet时间: " + amciTSetSearchTime);
            System.out.println("检索XSet时间: " + amciXSetSearchTime);
            System.out.println("(genTokensTime, searchTime) = " + "(" + (amciGenStokenTime + amciGenXtokensTime) + ", " + (amciTSetSearchTime + amciXSetSearchTime) + ")");


            MyPrint.showList(resultStr);
            System.out.println();
            List<String> result = clientAMCIOXT.decryptE(resultStr);
            MyPrint.showList(result);

            break;

        }
    }


}