package cn.edu.hun.leileidu.ospir_oxt.run;

import cn.edu.hun.leileidu.basestruct.Keyword;
import cn.edu.hun.leileidu.basestruct.TSetElement;
import cn.edu.hun.leileidu.ospir_oxt.basestruct.AttributeKeyword;
import cn.edu.hun.leileidu.ospir_oxt.basestruct.BXToken;
import cn.edu.hun.leileidu.ospir_oxt.role.Client;
import cn.edu.hun.leileidu.ospir_oxt.role.DataOwner;
import cn.edu.hun.leileidu.ospir_oxt.role.Server;
import cn.edu.hun.leileidu.ospir_oxt.utils.ClientUtil;
import cn.edu.hun.leileidu.related.basestruct.EDB;
import cn.edu.hun.leileidu.related.basestruct.TSet;
import cn.edu.hun.leileidu.related.basestruct.XSet;
import cn.edu.hun.leileidu.related.basestruct.stag.Stag;
import cn.edu.hun.leileidu.utils.Constant;
import cn.edu.hun.leileidu.utils.KeywordUtil;
import cn.edu.hun.leileidu.utils.cryptography.BilinearUtil;
import cn.edu.hun.leileidu.utils.cryptography.CryptoFunciton;
import cn.edu.hun.leileidu.utils.ioutil.MyPrint;
import cn.edu.hun.leileidu.utils.testutil.GenerateTokenTestUtil;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;

import java.math.BigInteger;
import java.util.List;


/**
 * @author: Leilei Du
 * @Date: 2018/7/22 9:33
 */
public class Main {

    public static BilinearUtil bilinearUtil = new BilinearUtil();
    public static Field zField = null;

    public static String outFileName = "F:\\Users\\Administrator\\IdeaProjects\\OXTImprovedataset\\out_reverse_index\\dataset_out_00.txt";
    public static String keyPath = Constant.DEFAULT_PROGRAM_PATH + "datafile\\ospir-oxt\\keys.properties";
    public static String policyPath = Constant.DEFAULT_PROGRAM_PATH + "datafile\\ospir-oxt\\policy.properties";
    public static String clientPath = Constant.DEFAULT_PROGRAM_PATH + "datafile\\ospir-oxt\\client.properties";

    public static DataOwner dataOwnerOSPIROXT = null;
    public static Client clientOSPIROXT = null;
    public static Server serverOSPIROXT = null;

    public static void init(){
        zField = bilinearUtil.getzField();

        // 初始化各个角色
        dataOwnerOSPIROXT = new DataOwner(BigInteger.ONE);
    }

    public static void initClient(String idStr){
        BigInteger id = new BigInteger(idStr);
        clientOSPIROXT = new Client(id);
    }


    public static void initServer(EDB edb){
        TSet tset = edb.getTset();
        XSet xset = edb.getXset();
        serverOSPIROXT = new Server(tset, xset);
    }

    public static void main(String[] args) {
        init();
        EDB edbOXT = dataOwnerOSPIROXT.edbSetup(outFileName, false, keyPath);
        dataOwnerOSPIROXT.initializeClient(Constant.DEFAULT_CLIENT_NUMBER, Constant.DEFAULT_OSPIR_CLIENT_NUMBER_UP_BOUND, clientPath);
        dataOwnerOSPIROXT.initializePolicy(policyPath, false, false);
        System.out.println();

        List<Integer> tokenNumberList = GenerateTokenTestUtil.getDefaultTestTokenNumber();
        List<Keyword> keywordList = KeywordUtil.readKeyword(outFileName, 100);
        String clientID = ClientUtil.getMostPowerfulClientIDFromPolicyFile(policyPath);
        initClient(clientID);
        initServer(edbOXT);
        /**
         *  生成 token 并执行 search
         */
        for (int i = 0; i < tokenNumberList.size(); i++) {
            int tokenNum = tokenNumberList.get(i);

            List<Keyword> keywordListNow = keywordList.subList(0, tokenNum);
            List<AttributeKeyword> attributeKeywordListNow = KeywordUtil.toAttributeKeywordList(keywordListNow);

            MyPrint.showList(attributeKeywordListNow);
            System.out.println();

            System.out.println("use " + tokenNum + " keyword(s) to search... %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");

            /**
             * 测试 ospir-oxt
             */
            System.out.println("ospir-oxt **************************************************************");
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
            String[] env = dataOwnerOSPIROXT.getEnv();  //
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
            Stag stag = serverOSPIROXT.getStag(bstag);
            List<TSetElement> tsetElementList = serverOSPIROXT.tSetRetrieve(stag);
            long endOSPIRTSetSearch = System.currentTimeMillis();
            long ospirTSetSearch = endOSPIRTSetSearch - startOSPIRTSetSearch;

            // 测试 client 与 server 交互 bxtoken 产生时间

            long startOSPIRGenXTokens = System.currentTimeMillis();
            List<BXToken> bxTokenList = clientOSPIROXT.generateBXTokenList(tsetElementList.size());
            long endOSPIRGenXTokens = System.currentTimeMillis();
            long ospirGenXtokens = endOSPIRGenXTokens - startOSPIRGenXTokens;


            // 测试 server 检索 XSet 时间
            long startOSPIRXSetSearch = System.currentTimeMillis();
            List<String> encryptResult = serverOSPIROXT.searchXTokenListPart(tsetElementList, bxTokenList);
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
            System.out.println("The result:");
            MyPrint.showList(encryptResult);
            System.out.println();

            List<String> result = clientOSPIROXT.decryptE(encryptResult);
            MyPrint.showList(result);


        }


    }



}