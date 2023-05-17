package cn.edu.hun.leileidu.improved.basic.role;

import cn.edu.hun.leileidu.basestruct.FileList;
import cn.edu.hun.leileidu.basestruct.Keyword;
import cn.edu.hun.leileidu.basestruct.TSetElement;
import cn.edu.hun.leileidu.basestruct.docfile.DocFile;
import cn.edu.hun.leileidu.improved.others.utils.ClientUtil;
import cn.edu.hun.leileidu.related.basestruct.EDB;
import cn.edu.hun.leileidu.related.basestruct.ReverseIndexTable;
import cn.edu.hun.leileidu.related.basestruct.TSet;
import cn.edu.hun.leileidu.related.basestruct.XSet;
import cn.edu.hun.leileidu.related.basestruct.stag.Stag;
import cn.edu.hun.leileidu.related.basestruct.stag.StringStag;
import cn.edu.hun.leileidu.improved.others.utils.PrivilegeUtil;
import cn.edu.hun.leileidu.utils.Constant;
import cn.edu.hun.leileidu.utils.fileutil.EmailKeywordSplitUtil;
import cn.edu.hun.leileidu.utils.cryptography.CryptoFunciton;
import cn.edu.hun.leileidu.utils.cryptography.DifferentRandom;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;

import java.io.*;
import java.util.*;

/**
 * @author: Leilei Du
 * @Date: 2018/7/15 15:53
 */
@SuppressWarnings("ALL")
public class DataOwner extends Client {
    /**
     * 该数据结构只是为了做实验使用。分离文件读取和文件处理。
     */
    private Map<String, List<File>>  reverseIndexData = new HashMap<>();

    private Field zField = super.bilinearUtil.getzField();
    /**
     * TSet数据结构
     */
    private TSet tset = null;
    /**
     * XSet数据结构
     */
    private XSet<String> xset = null;

    //    private ForwardIndexTable<DocFile> forwardIndexTable = null;
    private ReverseIndexTable reverseIndexTable = null;
//    private Map<Keyword, List<TSetElement>> arrayT = null;

    private List<Client> clientList = null;

    private DifferentRandom differentRandom = null;
//    private List<BigInteger> clientRandomNumberList = null
    private Map<Client, Element> clientRandomMap = null;
//    private Map<Keyword, List<Client>> privilege = null;
    //用户公钥和关键字值的映射(公钥用字符串表示)
    private Map<String, List<String>> privelege = null;

    //内部edbSetup使用
//    private static final String policyPath = Constant.DEFAULT_PROGRAM_PATH + "datafile\\" + "improved\\privilege.properties";
//    private static final String clientPath = Constant.DEFAULT_PROGRAM_PATH + "datafile\\improved\\clients.properties";


    public DataOwner() {
//        privelege = new HashMap<>();
    }

    //for test
    public Map<Stag, List<TSetElement>> getTSetMap(){
        return this.tset.getTSetMap();
    }


    public TSet getTset() {
        return tset;
    }

    public XSet getXset() {
        return xset;
    }



    public List<Client> getClientList() {
        return clientList;
    }

    // for test
    public ReverseIndexTable getReverseIndexTable() {
        return reverseIndexTable;
    }
    // for test
    public void setClientRandomMap(Map<Client, Element> clientRandomMap) {
        this.clientRandomMap = clientRandomMap;
    }

    public void setClientList(List<Client> clientList) {
        this.clientList = clientList;
    }

    public DifferentRandom getDifferentRandom() {
        return differentRandom;
    }

    public void setDifferentRandom(DifferentRandom differentRandom) {
        this.differentRandom = differentRandom;
    }

//    public Map<Keyword, List<Client>> getPrivilege() {
//        return privilege;
//    }


//    public void setPrivilege(Map<Keyword, List<Client>> privilege) {
//        this.privilege = privilege;
//    }


    public Map<String, List<String>> getPrivelege() {
        return privelege;
    }

    public void setPrivelege(Map<String, List<String>> privelege) {
        this.privelege = privelege;
    }

    /**
     * 选择各个密钥
     * @param change
     */
    private void selectKey(boolean change){
        if(change){
            CryptoFunciton.getKeyBytes(this.ks);
            CryptoFunciton.getKeyBytes(this.kx);
            CryptoFunciton.getKeyBytes(this.ki);
            CryptoFunciton.getKeyBytes(this.kz);
        }
    }

    public void readDataFromFile(String docDirPath){
        this.reverseIndexData = EmailKeywordSplitUtil.readReverseIndexFromFile(docDirPath);
    }

    /**
     * 构建正向索引和倒排索引
     * @param docDirPath
     */
    public void parseDataBase(){
        //倒排索引

        this.reverseIndexTable = new ReverseIndexTable();
        this.reverseIndexTable.construct(this.reverseIndexData);

    }

    /**
     * 根据Constant中的配置构造Client和其分配的随机数
     */
    public void parseClients(boolean stag, String filePath){
        if(stag){
            ClientUtil.renewClients(Constant.DEFAULT_CLIENT_NUMBER, this.zField, filePath);
        }
        Map<Client, Element> clientRandomMap = ClientUtil.getBaseClientRandomMap(this.zField, filePath);
        this.setClientRandomMap(clientRandomMap);
        Set<Client> clientSet = clientRandomMap.keySet();
        List<Client> clientList = new ArrayList<>(clientSet);
        this.setClientList(clientList);
    }




    /**
     * 要在parseDataBase之后调用
     * @param state
     */

    public void constructPrivilege(boolean state, String privilegePath, boolean existAdmin){
//        Set<Keyword> keywordSet = reverseIndexTable.getIndexTable().keySet();
        //TODO 为client建立权限（构建privilege映射）

        if(state){  // 需要重新分配privilege
            Set<Keyword> keywordSet = reverseIndexTable.getIndexTable().keySet();
            Map<Client, List<Keyword>> clientListMap = PrivilegeUtil.grantBasePrivilege(keywordSet, this.clientList, existAdmin);
            PrivilegeUtil.writeBasePrivilege(clientListMap, privilegePath);
        }

        this.privelege = PrivilegeUtil.readPrivilege(privilegePath);

    }


    private void initialize(){
        xset = new XSet();
//        arrayT = new HashMap<>();
        tset = new TSet();
    }




    private void buildArrayTAndXSet(){

        HashMap<Keyword, FileList> indexTable = this.reverseIndexTable.getIndexTable();
        Set<Map.Entry<Keyword, FileList>> entries = indexTable.entrySet();
        for (Map.Entry<Keyword, FileList> entry : entries) {
            Keyword keyword = entry.getKey();
            //计算得到Ke
            byte[] ke = CryptoFunciton.func(this.ks, keyword.getValue());
//            String stagStr = CryptoFunciton.funcs(this.kt, keyword.getValue());
//            BigInteger stagPow = CryptoFunciton.funcp(this.kt, keyword.getValue(), this.zFieldSize, null);
            Element stagPow = CryptoFunciton.getHashValueFromZField(this.kt, keyword.getValue(), this.zField);
//            Stag<String> stag = new StringStag(this.bilinearUtil.getTPairString(stagPow));
            String stagStr = this.bilinearUtil.getTPowValue(stagPow).toString();
            Stag<String> stag = new StringStag(stagStr);

            FileList value = entry.getValue();
            List<DocFile> list = value.getList();
            //在构建FileList的时候已经保证了File的顺序是随机的
            int c = 0;
            List<TSetElement> elementList = new ArrayList<>();

//            BigInteger xtrap = CryptoFunciton.funcp(this.kx, keyword.getValue(), this.zFieldSize, null);
            Element xtrap = CryptoFunciton.getHashValueFromZField(this.kx, keyword.getValue(), this.zField);
            for (DocFile docFile: list) {
                String ind = docFile.getInd();
//                BigInteger xindBigInteger = CryptoFunciton.funcp(this.ki, ind, this.zFieldSize, null);
//                Element xind = this.bilinearUtil.getZrElement(xindBigInteger);
                Element xind  = CryptoFunciton.getHashValueFromZField(this.ki, ind, this.zField);
                String e = CryptoFunciton.encrypt(ke,ind);
                ++c;
//                BigInteger zBigInteger = CryptoFunciton.funcp(this.kz, keyword.getValue().concat(String.valueOf(c)), this.zFieldSize, null);
//                Element z = this.bilinearUtil.getZrElement(zBigInteger);
                Element z = CryptoFunciton.getHashValueFromZField(this.kz, keyword.getValue().concat(String.valueOf(c)), this.zField);

//                BigInteger y = z.modPow(new BigInteger("-1"), this.eulerPrimeP).multiply(xind).mod(this.eulerPrimeP);
//                Element y = this.generateP.powZn(xind.mul(z.pow(BigInteger.ONE.negate())));
                Element y = this.bilinearUtil.getPPowValue(xind.mulZn(z.invert()));

                TSetElement<String, Element> element = new TSetElement(e,y);

                elementList.add(element);


                //对每个可以访问该关键字的用户，将用户的信息写入
                for (Client client : clientList) {
                    String pkStr = client.getPublicKey().toString();


                    if(!this.privelege.get(pkStr).contains(keyword.getValue())){
                        continue;
                    }
                    Element left = this.bilinearUtil.getPPowValue(xind.mulZn(xtrap));
//                    BigInteger clientRandom = clientRandomMap.get(client);
                    Element clientRandom = clientRandomMap.get(client);

//                    Element right = client.getPublicKey().pow(clientRandom).getImmutable();
                    Element right = client.getPublicKey().powZn(clientRandom).getImmutable();
                    Element xtag = this.bilinearUtil.pair(left, right);
                    xset.add(xtag.toString());
                }
            }
//            arrayT.put(keyword, elementList);
            tset.putElement(stag, elementList);
        }

    }
    private EDB tSetSetup(){
        return new EDB(tset, xset);
    }


//    public EDB edbSetup(/*String dbDir*/){
//        System.out.println("The DataBase is being constructed ......");
//        this.selectKey(false);
////        this.parseDataBase(dbDir);
//        this.initialize();
//        this.buildArrayTAndXSet();
//        System.out.println("The DataBase has been constructed!");
//        return new EDB(this.tset, this.xset);
//    }

    public EDB edbSetup(String dbDir, boolean parseClientState, String clientPath, boolean selectKeyState, boolean constructPrivilegeState, String privilegePath, boolean existAdmin){
        System.out.println("The DataBase is being constructed ... ...!");
        this.readDataFromFile(dbDir);
        this.parseClients(parseClientState, clientPath);
        System.out.println("The clients have been initialized... ...");

        long startTime = System.currentTimeMillis();

        this.selectKey(selectKeyState);
        this.parseDataBase();
        System.out.println("The keys have been initialized... ...");

        long middleTime1 = System.currentTimeMillis();

        // 不算在内
        this.constructPrivilege(constructPrivilegeState, privilegePath, existAdmin);
        System.out.println("The privilege has been constructed... ...");

        long middleTime2 = System.currentTimeMillis();

        this.initialize();
        this.buildArrayTAndXSet();
        System.out.println("The structure has be constructed... ...");

        long middleTime3 = System.currentTimeMillis();

        // 不算在内
        this.tSetSetup();

        long endTime = System.currentTimeMillis();

        System.out.println("The DataBase has been constructed!");
        long totalTime = endTime - startTime;
        long otherTime = middleTime2 - middleTime1 + endTime - middleTime3;
        long usedTime = totalTime - otherTime;
        System.out.println("(Total time, other time, Used time): " + "(" + totalTime + ", " + otherTime + " , " + usedTime + ")");

        return new EDB(this.tset, this.xset);
    }


}