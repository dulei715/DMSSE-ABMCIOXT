package cn.edu.hun.pisces.improved.basic.run;

import cn.edu.hun.pisces.basestruct.Keyword;
import cn.edu.hun.pisces.basestruct.TSetElement;
import cn.edu.hun.pisces.improved.basic.role.Client;
import cn.edu.hun.pisces.improved.basic.role.DataOwner;
import cn.edu.hun.pisces.improved.basic.role.Server;
import cn.edu.hun.pisces.improved.others.utils.ClientPolicyUtil;
import cn.edu.hun.pisces.related.basestruct.EDB;
import cn.edu.hun.pisces.related.basestruct.TSet;
import cn.edu.hun.pisces.related.basestruct.XSet;
import cn.edu.hun.pisces.related.basestruct.XToken;
import cn.edu.hun.pisces.utils.Constant;
import cn.edu.hun.pisces.utils.KeywordUtil;
import cn.edu.hun.pisces.utils.cryptography.BilinearUtil;
import cn.edu.hun.pisces.utils.ioutil.MyPrint;
import cn.edu.hun.pisces.utils.testutil.GenerateTokenTestUtil;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;

import java.util.List;

/**
 * @author: Leilei Du
 * @Date: 2018/7/16 10:18
 */
@SuppressWarnings("Duplicates")
public class Main {
    public static BilinearUtil bilinearUtil = new BilinearUtil();
    public static Field zField = null;

    public static String outFileName = "F:\\Users\\Administrator\\IdeaProjects\\OXTImprovedataset\\out_reverse_index\\dataset_out_00.txt";
    public static String privilegePath = Constant.DEFAULT_PROGRAM_PATH + "datafile\\improved\\privilege.properties";
    public static String clientPath = Constant.DEFAULT_PROGRAM_PATH + "datafile\\improved\\clients.properties";


    public static DataOwner dataOwnerBMCIOXT = null;
    public static Client clientBMCIOXT = null;
    public static Server serverBMCIOXT = null;

    public static void init(){
        zField = bilinearUtil.getzField();

        // 初始化各个角色
        dataOwnerBMCIOXT = new DataOwner();
    }

    public static void initClient(Client client){
        if(client != null){
            clientBMCIOXT = client;
            return;
        }
        System.out.println("没有用户有权限可以查这么多关键字");
        List<String> stringList = ClientPolicyUtil.readClientsProperties(0, clientPath);
        String skStr = stringList.get(0);
        String rStr = stringList.get(1);
        Element secretKey = zField.newElement().set(Integer.valueOf(skStr)).getImmutable();
        Element randomR = zField.newElement().set(Integer.valueOf(rStr));
        Element gr = bilinearUtil.getGPowValue(randomR);
        clientBMCIOXT = new Client(secretKey, gr);
    }

    public static void initServer(EDB edb){
        TSet tset = edb.getTset();
        XSet xset = edb.getXset();
        serverBMCIOXT = new Server(tset, xset);
    }

    public static void main(String[] args) {
        init();
        EDB edbOXT = dataOwnerBMCIOXT.edbSetup(outFileName, false, clientPath, false, false, privilegePath, false);
        System.out.println();

//        initClient();
        initServer(edbOXT);
        List<Integer> tokenNumberList = GenerateTokenTestUtil.getDefaultTestTokenNumber();
        List<Keyword> keywordList = KeywordUtil.readKeyword(outFileName, 100);

        for (int i = 0; i < tokenNumberList.size(); i++) {
            int tokenNum = tokenNumberList.get(i);

            List<Keyword> keywordListNow = keywordList.subList(0, tokenNum);

            List<String> keywordValueList = KeywordUtil.toKeywordValueList(keywordListNow);
            Client client = ClientPolicyUtil.getBasicClientParamByprivilege(keywordValueList, clientPath, privilegePath);
            initClient(client);
            clientBMCIOXT.setStermAndXterm(keywordListNow);

            MyPrint.showList(keywordListNow);
            System.out.println();

            System.out.println("use " + tokenNum + " keyword(s) to search... %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");


            // 测试 client 生成 stoken
            long startGenStoken = System.currentTimeMillis();
            Element stoken = clientBMCIOXT.getSToken();
            long endGenStoken = System.currentTimeMillis();
            long bmciGenStokenTime = endGenStoken - startGenStoken;

            // 测试生成 stag 和 检索 tset
            long startBMCITSetSearch = System.currentTimeMillis();
            List<TSetElement> tsetElementList = serverBMCIOXT.tSetRetrieve(stoken, clientBMCIOXT.getPublicKey());
            long endBMCITSetSearch = System.currentTimeMillis();
            long bmciTSetSearchTime = endBMCITSetSearch - startBMCITSetSearch;

            // 测试 client 生成 xtokens
            long startBMCIGenXtokens = System.currentTimeMillis();
            List<XToken> xTokenList = clientBMCIOXT.generateSearchXTokens(tsetElementList.size());
            long endBMCIGenXtokens = System.currentTimeMillis();
            long genBMCIXtokensTime = endBMCIGenXtokens - startBMCIGenXtokens;

            // 测试 server 检索xset
            long startBMCIXSetSearch = System.currentTimeMillis();
            List<String> resultStr = serverBMCIOXT.searchXTokenListPart(tsetElementList, xTokenList);
            long endBMCIXSetSearch = System.currentTimeMillis();
            long bmciXSetSearchTime = endBMCIXSetSearch - startBMCIXSetSearch;

            System.out.println("stoken产生时间: " + bmciGenStokenTime);
            System.out.println("xtokens产生时间: " + genBMCIXtokensTime);
            System.out.println("检索TSet时间: " + bmciTSetSearchTime);
            System.out.println("检索XSet时间: " + bmciXSetSearchTime);
            System.out.println("(genTokensTime, searchTime) = " + "(" + (bmciGenStokenTime + genBMCIXtokensTime) + ", " + (bmciTSetSearchTime + bmciXSetSearchTime) + ")");

            MyPrint.showList(resultStr);
            System.out.println();
            List<String> result = clientBMCIOXT.decryptE(resultStr);
            MyPrint.showList(result);

            break;

        }
    }

}