package cn.edu.hun.pisces.related.run;

import cn.edu.hun.pisces.basestruct.Keyword;
import cn.edu.hun.pisces.basestruct.TSetElement;
import cn.edu.hun.pisces.related.basestruct.EDB;
import cn.edu.hun.pisces.related.basestruct.TSet;
import cn.edu.hun.pisces.related.basestruct.XSet;
import cn.edu.hun.pisces.related.basestruct.XToken;
import cn.edu.hun.pisces.related.role.Client;
import cn.edu.hun.pisces.related.role.DataOwner;
import cn.edu.hun.pisces.related.role.Server;
import cn.edu.hun.pisces.related.basestruct.stag.Stag;
import cn.edu.hun.pisces.utils.KeywordUtil;
import cn.edu.hun.pisces.utils.cryptography.BilinearUtil;
import cn.edu.hun.pisces.utils.ioutil.MyPrint;
import cn.edu.hun.pisces.utils.testutil.GenerateTokenTestUtil;
import it.unisa.dia.gas.jpbc.Field;

import java.util.*;


/**
 * @author: Leilei Du
 * @Date: 2018/7/11 10:18
 */
public class Main {
    public static BilinearUtil bilinearUtil = new BilinearUtil();
    public static Field zField = null;

    public static String outFileName = "F:\\Users\\Administrator\\IdeaProjects\\OXTImprovedataset\\out_reverse_index\\dataset_out_00.txt";

    public static DataOwner dataOwnerOXT = null;
    public static Client clientOXT = null;
    public static Server serverOXT = null;

    public static void init(){
        zField = bilinearUtil.getzField();

        // 初始化各个角色
        dataOwnerOXT = new DataOwner();
        clientOXT = new Client();
        serverOXT = new Server();


    }

    public static void initServer(EDB edb){
        TSet tset = edb.getTset();
        XSet xset = edb.getXset();
        serverOXT.setTset(tset);
        serverOXT.setXset(xset);


    }

    public static void main(String[] args) {
        init();
        /**
         * 构建 edb
         */
        EDB edbOXT = dataOwnerOXT.edbSetup(outFileName);
        System.out.println();

        List<Integer> tokenNumberList = GenerateTokenTestUtil.getDefaultTestTokenNumber();
        List<Keyword> keywordList = KeywordUtil.readKeyword(outFileName, 100);
        initServer(edbOXT);
        /**
         *  生成 token 并执行 search
         */
        for (int i = 0; i < tokenNumberList.size(); i++) {
            int tokenNum = tokenNumberList.get(i);

            List<Keyword> keywordListNow = keywordList.subList(0, tokenNum);

            MyPrint.showList(keywordListNow);
            System.out.println();

            System.out.println("use " + tokenNum + " keyword(s) to search... %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");

            /**
             *  测试OXT
             */
            System.out.println("oxt ********************************************************************");
            clientOXT.setStermAndXterm(keywordListNow);

            //  测试 stag 产生时间
            long startGenStag = System.currentTimeMillis();
            Stag stag = clientOXT.tSetGetTag();
            long endGenStag = System.currentTimeMillis();
            long genStagTime = endGenStag - startGenStag;

            //  测试搜索 stag 相关文件索引的时间
            long startTSetSearch = System.currentTimeMillis();
            List<TSetElement> tsetElementList = serverOXT.tSetRetrieve(stag);
            long endTSetSearch = System.currentTimeMillis();
            long searchTSetTime = endTSetSearch - startTSetSearch;

//            System.out.println(tsetElementList.size());

            //  测试 xtags 产生时间
            long startGenxtokens = System.currentTimeMillis();
            List<XToken> xTokenList = clientOXT.generateSearchXToken(tsetElementList.size());
            long endGenxtokens = System.currentTimeMillis();
            long genXtokenTime = endGenxtokens - startGenxtokens;

            //  测试索索 xtags 相关文件索引的时间
            long startXSetSearch = System.currentTimeMillis();
            List<String> encryptResult = serverOXT.searchXTokenListPart(tsetElementList, xTokenList);
            long endXSetSearch = System.currentTimeMillis();
            long searchXSetTime = endXSetSearch - startXSetSearch;

            System.out.println("stoken产生时间: " + genStagTime);
            System.out.println("xtokens产生时间: " + genXtokenTime);
            System.out.println("检索TSet时间: " + searchTSetTime);
            System.out.println("检索XSet时间: " + searchXSetTime);
            System.out.println("(genTokensTime, searchTime) = " + "(" + (genStagTime + genXtokenTime) + ", " + (searchTSetTime + searchXSetTime) + ")");

            System.out.println();
            System.out.println("The result:");
//            MyPrint.showList(encryptResult);

            List<String> result = clientOXT.decryptE(encryptResult);
            MyPrint.showList(result);

            break;
        }


    }
}