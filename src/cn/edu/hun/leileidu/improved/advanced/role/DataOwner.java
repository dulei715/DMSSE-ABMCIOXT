package cn.edu.hun.leileidu.improved.advanced.role;

import cn.edu.hun.leileidu.basestruct.FileList;
import cn.edu.hun.leileidu.basestruct.Keyword;
import cn.edu.hun.leileidu.basestruct.TSetElement;
import cn.edu.hun.leileidu.basestruct.docfile.DocFile;
import cn.edu.hun.leileidu.improved.advanced.basestruct.EDB;
import cn.edu.hun.leileidu.improved.advanced.basestruct.UXSet;
import cn.edu.hun.leileidu.improved.advanced.basestruct.XXSet;
import cn.edu.hun.leileidu.improved.others.utils.ClientUtil;
import cn.edu.hun.leileidu.improved.others.utils.PrivilegeUtil;
import cn.edu.hun.leileidu.related.basestruct.ReverseIndexTable;
import cn.edu.hun.leileidu.related.basestruct.TSet;
import cn.edu.hun.leileidu.related.basestruct.stag.Stag;
import cn.edu.hun.leileidu.related.basestruct.stag.StringStag;
import cn.edu.hun.leileidu.utils.Constant;
import cn.edu.hun.leileidu.utils.fileutil.EmailKeywordSplitUtil;
import cn.edu.hun.leileidu.utils.KeyUtil;
import cn.edu.hun.leileidu.utils.cryptography.CryptoFunciton;
import cn.edu.hun.leileidu.utils.cryptography.DifferentRandom;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;

import java.io.*;
import java.util.*;

/**
 * @author: Leilei Du
 * @Date: 2018/7/13 14:52
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
    private UXSet<String> uxset = null;
    private XXSet<String> xxset = null;

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



    //为了测试，暂时将权限文件设置为和basic相同的文件。要求constructPrivilege()传入的参数设为false.
//    private static final String privilegeName = "improved/privilege.properties";
//    private static final String privilegeName = "privilege_advanced.properties";

    //内部edbSetup使用
//    private static final String policyPath = Constant.DEFAULT_PROGRAM_PATH + "datafile\\" + "improved\\privilege.properties";
//    private static final String clientPath = Constant.DEFAULT_PROGRAM_PATH + "datafile\\improved\\clients.properties";
//    private static final String keysPath = Constant.DEFAULT_PROGRAM_PATH + "datafile\\improved\\keys.properties";




    public DataOwner() {
        privelege = new HashMap<>();
    }

    public Map<Stag, List<TSetElement>> getTSetMap(){
        return this.tset.getTSetMap();
    }


    public TSet getTset() {
        return tset;
    }

    public UXSet<String> getUxset() {
        return uxset;
    }

    public XXSet<String> getXxset() {
        return xxset;
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

    public Map<String, List<String>> getPrivelege() {
        return privelege;
    }

    public void setPrivelege(Map<String, List<String>> privelege) {
        this.privelege = privelege;
    }


    //for test
    // dataowner提取所有的关键字和所有client的公钥
    public void constructPrivilege(boolean state, String filePath, boolean existAdmin){
        //        Set<Keyword> keywordSet = reverseIndexTable.getIndexTable().keySet();
        //TODO 为client建立权限（构建privilege映射）

        if(state){  // 需要重新分配privilege
            Set<Keyword> keywordSet = reverseIndexTable.getIndexTable().keySet();
            Map<Client, List<Keyword>> clientListMap = PrivilegeUtil.grantAdvancePrivilege(keywordSet, this.clientList, existAdmin);
            PrivilegeUtil.writeAdvancePrivilege(clientListMap, filePath);
        }
        this.privelege = PrivilegeUtil.readPrivilege(filePath);
    }

    public void readDataFromFile(String docDirPath){
        this.reverseIndexData = EmailKeywordSplitUtil.readReverseIndexFromFile(docDirPath);
    }

    public void parseDataBase(){
        this.reverseIndexTable = new ReverseIndexTable();
        this.reverseIndexTable.construct(this.reverseIndexData);
    }

    /**
     * 根据Constant中的配置构造Client和其分配的随机数
     */
    public void parseClients(boolean state, String filePath){
        if(state){
            ClientUtil.renewClients(Constant.DEFAULT_CLIENT_NUMBER, this.zField, filePath);
        }
        Map<Client, Element> clientRandomMap =  ClientUtil.getAdvanceClientRandomMap(this.zField, filePath);
        this.setClientRandomMap(clientRandomMap);
        Set<Client> clientSet = clientRandomMap.keySet();
        List<Client> clientList = new ArrayList<>(clientSet);
        this.setClientList(clientList);
    }



    //如果是换新的密钥，要在parseDataBase之后调用，保证reverseIndexTable不空
    public void selectKey(boolean change, String filePath){
//        String filePath = "datafile\\improved\\keys.properties";
        Set<Keyword> keywordSet = this.reverseIndexTable.getIndexTable().keySet();
        if(change){
            String[] keyNameArr = new String[]{"kt", "ks", "kx", "kz", "ki"};
            Set<String> singleSet = new HashSet<>();
            singleSet.add("ki");
            KeyUtil.generateKeysToFile(filePath, keyNameArr, Constant.DEFAULT_KEY_BYTE_LENGTH, keywordSet.size(), singleSet);
        }
        Map<String, List<byte[]>> keysFromFile = KeyUtil.getKeysFromFile(filePath);


        this.kt = constructMultiKey(keywordSet, "kt", keysFromFile);
        this.ks = constructMultiKey(keywordSet, "ks", keysFromFile);
        this.kx = constructMultiKey(keywordSet, "kx", keysFromFile);
        this.kz = constructMultiKey(keywordSet, "kz", keysFromFile);
        this.ki = constructSingleKey("ki", keysFromFile);
    }

    // selectKey的子函数
    public Map<Keyword, byte[]> constructMultiKey(Set<Keyword> keywordSet, String keyName, Map<String, List<byte[]>> listMap) {
        Map<Keyword, byte[]> map = new HashMap<>();
        List<byte[]> bytesList = listMap.get(keyName);
        if(bytesList.size() < keywordSet.size()){
            throw new RuntimeException("The bytes keys are note enough for keywords!");
        }
        int i = 0;
        for (Keyword keyword : keywordSet) {
            byte[] bytes = bytesList.get(i);
            map.put(keyword, bytes);
            ++i;
        }
        return map;
    }

    public byte[] constructSingleKey(String keyName, Map<String, List<byte[]>> listMap) {
        List<byte[]> bytesList = listMap.get(keyName);
        return bytesList.get(0);
    }


//    //for test
//    //为用户生成随机数r(使用默认随机数时，用户数不超过10个)
//    public void generateRandomNumber(boolean isNew){
//        this.differentRandom = new DifferentRandom(new HashSet<>(), this.zFieldSize);
//        int clientNum = this.clientList.size();
//        if(!isNew){
//            //使用默认的随机数
//            for (int i = 0; i < clientNum; i++) {
////                BigInteger random = Constant
//                Client client = this.clientList.get(i);
//            }
//        }else{
//            clientRandomMap = new HashMap<>();
//            for (int i = 0; i < clientNum; i++) {
//                BigInteger random = differentRandom.getDifferentPositiveBigInteger();
//                Client client = this.clientList.get(i);
//                this.clientRandomMap.put(client, random);
//            }
//        }
//    }

    private void initialize(){
        tset = new TSet();
        uxset = new UXSet<>();
        xxset = new XXSet();
    }

    private void buildArrayTAndXSet(){

        HashMap<Keyword, FileList> indexTable = this.reverseIndexTable.getIndexTable();
        Set<Map.Entry<Keyword, FileList>> entries = indexTable.entrySet();
        for (Map.Entry<Keyword, FileList> entry : entries) {
            Keyword keyword = entry.getKey();

            //计算得到Ke
            byte[] subKs = this.ks.get(keyword);
            byte[] ke = CryptoFunciton.func(subKs, keyword.getValue());
            byte[] subKt = this.kt.get(keyword);
//            BigInteger stagPow = CryptoFunciton.funcp(subKt, keyword.getValue(), this.zFieldSize, null);
            Element stagPow = CryptoFunciton.getHashValueFromZField(subKt, keyword.getValue(), this.zField);
            Element stagValue = this.bilinearUtil.getTPowValue(stagPow);
            Stag<String> stag = new StringStag(stagValue.toString());


            FileList value = entry.getValue();
            List<DocFile> list = value.getList();
            //在构建FileList的时候已经保证了File的顺序是随机的
            int c = 0;
            List<TSetElement> elementList = new ArrayList<>();

            byte[] subKz = kz.get(keyword);
            byte[] subKx = this.kx.get(keyword);
//            BigInteger xtrapInteger = CryptoFunciton.funcp(subKx, keyword.getValue(), this.zFieldSize, null);
            Element xtrap = CryptoFunciton.getHashValueFromZField(subKx, keyword.getValue(), this.zField);
            for (DocFile docFile: list) {
                ++c;
                String ind = docFile.getInd();

                Element xind = CryptoFunciton.getHashValueFromZField(this.ki, ind, this.zField);
                String e = CryptoFunciton.encrypt(ke,ind);

                Element z = CryptoFunciton.getHashValueFromZField(subKz, keyword.getValue().concat(String.valueOf(c)), this.zField);

                Element y = xind.mulZn(z.invert()).getImmutable();

                TSetElement<String, Element> element = new TSetElement(e,y);

                elementList.add(element);

                Element xxtag = this.bilinearUtil.getPPowValue(xind.mulZn(xtrap));

                xxset.add(xxtag.toString());

            }
            //对每个可以访问该关键字的用户，将用户的信息写入
            for (Client client : clientList) {

                String pkStr = client.getPublicKey().toString();

                if(!this.privelege.get(pkStr).contains(keyword.getValue())){
                    continue;
                }
                Element clientRandom = clientRandomMap.get(client);
                Element uxtag = client.getPublicKey().powZn(xtrap.mulZn(clientRandom)).getImmutable();

                uxset.add(uxtag.toString());
            }
            tset.putElement(stag, elementList);
        }



    }
    private EDB tSetSetup(){
        return new EDB(tset, uxset, xxset);
    }

//    public EDB edbSetup(/*String dbDir*/){
//        System.out.println("The DataBase is being constructed ......");
////        this.parseDataBase(dbDir);
//        this.selectKey(false);
//        this.initialize();
//        this.buildArrayTAndXSet();
////        this.tSetSetup();
//        System.out.println("The DataBase has been constructed!");
//        return new EDB(this.tset, this.uxset, this.xxset);
//    }

    public EDB edbSetup(String dbDir, boolean parseClientState, String clientPath, boolean selectKeyState, String keysPath, boolean constructPrivilegeState, String privilegePath, boolean existAdmin){
        System.out.println("The DataBase is being constructed ......");
        this.readDataFromFile(dbDir);
        this.parseClients(parseClientState, clientPath);

        long startTime = System.currentTimeMillis();

        this.parseDataBase();
        this.selectKey(selectKeyState, keysPath);

        long middleTime1 = System.currentTimeMillis();

        // 不算在内
        this.constructPrivilege(constructPrivilegeState, privilegePath, existAdmin);

        long middleTime2 = System.currentTimeMillis();

        this.initialize();
        this.buildArrayTAndXSet();

        long middleTime3 = System.currentTimeMillis();

        // 不算在内
        this.tSetSetup();

        long endTime = System.currentTimeMillis();

        System.out.println("The DataBase has been constructed!");
        long totalTime = endTime - startTime;
        long otherTime = middleTime2 - middleTime1 + endTime - middleTime3;
        long usedTime = totalTime - otherTime;
        System.out.println("(Total time, other time, Used time): " + "(" + totalTime + ", " + otherTime + " , " + usedTime + ")");

        return new EDB(this.tset, this.uxset, this.xxset);
    }

}