package cn.edu.hun.leileidu.ospir_oxt.role;

import cn.edu.hun.leileidu.basestruct.FileList;
import cn.edu.hun.leileidu.basestruct.Keyword;
import cn.edu.hun.leileidu.basestruct.TSetElement;
import cn.edu.hun.leileidu.basestruct.docfile.DocFile;
import cn.edu.hun.leileidu.ospir_oxt.basestruct.AttributeKeyword;
import cn.edu.hun.leileidu.ospir_oxt.basestruct.AttributeReverseIndexTable;
import cn.edu.hun.leileidu.related.basestruct.EDB;
import cn.edu.hun.leileidu.related.basestruct.TSet;
import cn.edu.hun.leileidu.related.basestruct.XSet;
import cn.edu.hun.leileidu.related.basestruct.stag.StringStag;
import cn.edu.hun.leileidu.utils.Constant;
import cn.edu.hun.leileidu.utils.fileutil.EmailKeywordSplitUtil;
import cn.edu.hun.leileidu.utils.cryptography.CryptoFunciton;
import cn.edu.hun.leileidu.utils.cryptography.DifferentRandom;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;

import java.io.*;
import java.math.BigInteger;
import java.util.*;

/**
 * @author: Leilei Du
 * @Date: 2018/7/22 9:56
 */
@SuppressWarnings("ALL")
public class DataOwner extends Client {

    /**
     * 该数据结构只是为了做实验使用。分离文件读取和文件处理。
     */
    private Map<String, List<File>>  attributeReverseIndexData = new HashMap<>();

    public static final String policySeperateSymbol = "###";

    private Field gField = super.bilinearUtil.getgField();
    private Field zField = super.bilinearUtil.getzField();

    /**
     *  密钥
     */
    private Element ks = null;
    // 关键字的属性和kt子密钥之间的映射
    private Map<String, Element> kt = null;
    // 关键字的属性和kx子密钥之间的映射
    private Map<String, Element> kx = null;
    private byte[] ki = null;
    private byte[] km = null;

    private TSet tset = null;
    private XSet<String> xset = null;


    /**
     *  属性
     */
    private List<String> attributeList = null;
    private List<AttributeKeyword> attributeKeywordList = null;
    private AttributeReverseIndexTable attributeReverseIndexTable = null;

    public DataOwner(BigInteger clientID) {
        super(clientID);
    }


    /**
     *
     *
     */


    public Element getKs() {
        return ks;
    }

    public Map<String, Element> getKt() {
        return kt;
    }

    public Map<String, Element> getKx() {
        return kx;
    }

    public byte[] getKi() {
        return ki;
    }

    public byte[] getKm() {
        return km;
    }

    public List<Element> getpBlind() {
        return pBlind;
    }

    public TSet getTset() {
        return tset;
    }

    public void setTset(TSet tset) {
        this.tset = tset;
    }

    public XSet<String> getXset() {
        return xset;
    }

    public void setXset(XSet<String> xset) {
        this.xset = xset;
    }

    public List<String> getAttributeList() {
        return attributeList;
    }

    public void setAttributeList(List<String> attributeList) {
        this.attributeList = attributeList;
    }

    public List<AttributeKeyword> getAttributeKeywordList() {
        return attributeKeywordList;
    }

    public void setAttributeKeywordList(List<AttributeKeyword> attributeKeywordList) {
        this.attributeKeywordList = attributeKeywordList;
    }

    public AttributeReverseIndexTable getAttributeReverseIndexTable() {
        return attributeReverseIndexTable;
    }

    public void setAttributeReverseIndexTable(AttributeReverseIndexTable attributeReverseIndexTable) {
        this.attributeReverseIndexTable = attributeReverseIndexTable;
    }

    public Map<BigInteger, List<String>> getPolicyMap() {
        return policyMap;
    }

    public void setPolicyMap(Map<BigInteger, List<String>> policyMap) {
        this.policyMap = policyMap;
    }

    public List<String> getClientAttributeVector() {
        return clientAttributeVector;
    }

    public void setClientAttributeVector(List<String> clientAttributeVector) {
        this.clientAttributeVector = clientAttributeVector;
    }


    /**
     * 构造代码块，初始化默认密钥, 初始化数TSet, XSet据结构
     */
    {
//        ks =
//        kt =;
//        kx = ;
        ki = Constant.DEFAULT_K_I;
        km = Constant.DEFAULT_K_M;
//        attributeList = new ArrayList<>();
    }


    /**
     *
     * @param keywordStringSet 形式为 “属性名： 属性值”
     */
    public void initializeAttribute(Set<String> keywordStringSet){
        Set<String> resultSet = new HashSet<>();
        this.attributeList = new ArrayList<>();
        for (String s : keywordStringSet) {
            String elem = s.split(Constant.DEFAULT_COMBINE_SYMBOL)[0];

            // for test
//            if(elem.equals("X-From")){
//                System.out.println("haha");
//            }

            if(!resultSet.contains(elem)){

                this.attributeList.add(elem);
                resultSet.add(elem);
            }
        }
    }


    /******************************************************************************
     *
     * Key Generation
     * 注意：与其他的不同，该方法必须在parseDataBase之后调用，否则attributeList为空！
     *
     */

    public void selectKey(boolean change, String keyPath){
//        String keyPath = "datafile\\ospir-oxt\\keys.properties";
        InputStream in = null;
        Properties properties = new Properties();
        if(change){
            OutputStream out = null;
            try {
                out = new FileOutputStream(new File(keyPath));
//                BigInteger ksValue = BigIntegerUtil.nextBigInteger(this.eulerPrimeP, this.random);
                Element ksValue = this.zField.newRandomElement().getImmutable();
                properties.setProperty("ks", ksValue.toString());
                StringBuilder ktSb = new StringBuilder();
                StringBuilder kxSb = new StringBuilder();
                int i = 0;
                int len = this.attributeList.size() - 1;
                for (; i < len; i++) {
//                    ktSb.append(BigIntegerUtil.nextBigInteger(this.eulerPrimeP, random)).append(";");
                    ktSb.append(CryptoFunciton.getNonZeroRandomElement(this.zField)).append(";");
//                    kxSb.append(BigIntegerUtil.nextBigInteger(this.eulerPrimeP, random)).append(";");
                    kxSb.append(CryptoFunciton.getNonZeroRandomElement(this.zField)).append(";");
                }
//                ktSb.append(BigIntegerUtil.nextBigInteger(this.eulerPrimeP, random));
//                kxSb.append(BigIntegerUtil.nextBigInteger(this.eulerPrimeP, random));
                ktSb.append(CryptoFunciton.getNonZeroRandomElement(this.zField));
                kxSb.append(CryptoFunciton.getNonZeroRandomElement(this.zField));

                properties.setProperty("kt", ktSb.toString());
                properties.setProperty("kx", kxSb.toString());
                properties.store(out, "keys");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    out.close();
                    out = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else{
            try {
                in = new FileInputStream(keyPath);
                properties.load(in);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                    in = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        this.ks = zField.newElement().set(Integer.valueOf(properties.getProperty("ks"))).getImmutable();
        this.kt = new HashMap<>();
        this.kx = new HashMap<>();
        String[] subKtStrs = properties.getProperty("kt").split(";");
        String[] subKxStrs = properties.getProperty("kx").split(";");
        for (int i = 0; i < attributeList.size(); i++) {
//            kt.put(attributeList.get(i), new BigInteger(subKtStrs[i]));
//            kx.put(attributeList.get(i), new BigInteger(subKxStrs[i]));
            kt.put(attributeList.get(i), zField.newElement().set(Integer.valueOf(subKtStrs[i])).getImmutable());
            kx.put(attributeList.get(i), zField.newElement().set(Integer.valueOf(subKxStrs[i])).getImmutable());
        }
    }

//    /**
//     *  初始化数据库为倒排索引，关键字的可重复(根据索引的不同)
//     */
//    @Deprecated
//    public void parseDataBase0(String docDirPath){
//        File dir = new File(docDirPath);
//        Map<String, List<File>> attributeReverseIndexData = FileSplitUtil.extractMultiFilesReverseIndexDataWithAttribute(dir, Constant.DEFAULT_REGEX_SPLIT, Constant.DEPRECATED_DEFAULT_COMBINE_SYMBOL, this.attributeList);
//        this.attributeReverseIndexTable = new AttributeReverseIndexTable();
//        this.attributeReverseIndexTable.construct0(attributeReverseIndexData, Constant.DEPRECATED_DEFAULT_COMBINE_SYMBOL);
//    }

    public void readDataFromFile(String docDirPath){
        this.attributeReverseIndexData = EmailKeywordSplitUtil.readReverseIndexFromFile(docDirPath);
    }

    public void parseDataBase(){
        this.initializeAttribute(this.attributeReverseIndexData.keySet());
        this.attributeReverseIndexTable = new AttributeReverseIndexTable();
        this.attributeReverseIndexTable.construct(attributeReverseIndexData, Constant.DEFAULT_COMBINE_SYMBOL);
    }

    public void initialize(){
        xset = new XSet();
//        arrayT = new HashMap<>();
        tset = new TSet();
    }

    private void buildArrayTAndXSet(){

        HashMap<Keyword, FileList> indexTable = this.attributeReverseIndexTable.getIndexTable();
        Set<Map.Entry<Keyword, FileList>> entries = indexTable.entrySet();
        for (Map.Entry<Keyword, FileList> entry : entries) {
            AttributeKeyword attributeKeyword = (AttributeKeyword)entry.getKey();
            String keywordValue = attributeKeyword.getValue();


            String keywordAttribute = attributeKeyword.getAttribute();

            //for test
//            if(keywordValue.equals("Content: better")){
//                System.out.println("haha");
//            }


//            BigInteger keywordFunctionValue = CryptoFunciton.funHash(keywordValue, this.primeP, this.excludedElementSet);
            Element keywordFunctionValue = CryptoFunciton.getHashValueFromGroupField(keywordValue, this.gField);
//            BigInteger strap = keywordFunctionValue.modPow(ks, this.primeP);
            Element strap = keywordFunctionValue.powZn(ks).getImmutable();
//            BigInteger subKt = kt.get(keywordAttribute);
            Element subKt = kt.get(keywordAttribute);
//            BigInteger stagBInt = keywordFunctionValue.modPow(subKt, this.primeP);
            Element stag = keywordFunctionValue.powZn(subKt).getImmutable();
//            Stag<BigInteger> stag = new BigIntegerStag(stagBInt);

//             for test
//            if(stag.toString().equals("45296392,12952083,0")){
//                System.out.println("haha");
//            }



            byte[] kz = CryptoFunciton.functao(strap, 1, this.securityParameter);
            byte[] ke = CryptoFunciton.functao(strap, 2, this.securityParameter);

            FileList value = entry.getValue();
            List<DocFile> list = value.getList();
            //在构建FileList的时候已经保证了File的顺序是随机的
            int c = 0;

            List<TSetElement> elementList = new ArrayList<>();
            for (DocFile docFile: list) {

                String ind = docFile.getInd();

                //for test
//                if(keywordValue.equals("Content: attorney") && ind.endsWith("45.txt")){
//                    System.out.println("haha");
//                }
//                if(keywordValue.equals("Content: better") && ind.endsWith("45.txt")){
//                    System.out.println("haha");
//                }

                String rdk = CryptoFunciton.getRDK(ind);

                String indRdk = ind + " | " + rdk;

                String e = CryptoFunciton.encrypt(ke, indRdk);
//                BigInteger xind = CryptoFunciton.funcp(this.ki, ind, this.eulerPrimeP, null);
                Element xind = CryptoFunciton.getHashValueFromZField(this.ki, ind, this.zField);

                ++c;

//                BigInteger z = CryptoFunciton.funcp(kz, String.valueOf(c), this.eulerPrimeP, this.eulerPrimeP);
                Element z = CryptoFunciton.getHashValueFromZField(kz, String.valueOf(c), this.zField);
//                BigInteger y = z.modPow(new BigInteger("-1"), this.eulerPrimeP).multiply(xind).mod(this.eulerPrimeP);
//                BigInteger y = z.modInverse(this.eulerPrimeP).multiply(xind).mod(this.eulerPrimeP);
                Element y = z.invert().mulZn(xind).getImmutable();

                TSetElement element = new TSetElement(e,y);

                elementList.add(element);


//                BigInteger xtrap = CryptoFunciton.funcp(this.kx, keyword.getValue(), this.eulerPrimeP, null);
//                BigInteger xtag = generateElementG.modPow(xtrap.multiply(xind), this.primeP);

                Element subKx = this.kx.get(keywordAttribute);

//                BigInteger xtag = keywordFunctionValue.modPow(subKx.multiply(xind), this.primeP);
                Element xtag = keywordFunctionValue.powZn(subKx.mulZn(xind)).getImmutable();
                xset.add(xtag.toString());
            }
//            arrayT.put(stag, elementList);
            tset.putElement(new StringStag(stag.toString()), elementList);
        }

    }

    public EDB tSetSetup(){
        return new EDB(this.tset, this.xset);
    }


//    public EDB edbSetup(/*String dbDir*/){
//        System.out.println("The DataBase is being constructed ......");
//        this.selectKey(false);
////        this.parseDataBase(dbDir);
//        this.initialize();
//        this.buildArrayTAndXSet();
//        this.tSetSetup();
//        System.out.println("The DataBase has been constructed!");
//        return new EDB(this.tset, this.xset);
//    }

    public EDB edbSetup(String dbDir, boolean selectKeyState, String keyPath){
//        String keyPath = "datafile\\ospir-oxt\\keys.properties";
        System.out.println("The DataBase is being constructed ......");
        this.readDataFromFile(dbDir);

        long startTime = System.currentTimeMillis();

        this.parseDataBase();
        this.selectKey(selectKeyState, keyPath);
        this.initialize();
        this.buildArrayTAndXSet();

        long middleTime = System.currentTimeMillis();

        this.tSetSetup();

        long endTime = System.currentTimeMillis();

        System.out.println("The DataBase has been constructed!");
        long totalTime = endTime - startTime;
        long tsetTime = endTime - middleTime;
        long usedTime = middleTime - startTime;
        System.out.println("(Total time, TSet time, Used time): " + "(" + totalTime + ", " + tsetTime + " , " + usedTime + ")");

        return new EDB(this.tset, this.xset);
    }


    /******************************************************************************
     *
     *  GenToken Protocol
     *  Data Owner
     *
     */

    /**
     *  Client 列表
     */
    private List<Client> clientList = null;

    /**
     *  Policy
     *  用来标识client的ID可以访问的关键字String的属性
     */
    private Map<BigInteger, List<String>> policyMap = null;

    /**
     *  用来记录当前 client 发来的 av
     */
    private List<String> clientAttributeVector = null;

    /**
     *  data owner 有随机数 pBlind = (p1, p2,..., pn), 用来生成 bstag'
     */
    private List<Element> pBlind  = null;

    public void setClientList(List<Client> clientList){
        this.clientList = clientList;
    }

    public List<Client> getClientList() {
        return clientList;
    }

    /**
     *  初始化clientList
     *  @param num 表示client的数量, 小于0 代表使用默认文件中的值
     */
    public void initializeClient(int num, BigInteger idUpBound, String clientPath){
//        String clientPath = "datafile\\ospir-oxt\\client.properties";
        Properties properties = new Properties();
        if(num >= 0){
            OutputStream out = null;
            DifferentRandom differentRandom = new DifferentRandom();
            try {
                out = new FileOutputStream(new File(clientPath));
                differentRandom.setUpBound(idUpBound);
                BigInteger clientID = null;
                for (int i = 1; i <= num; i++) {
                    clientID = differentRandom.getDifferentPositiveBigInteger();
                    properties.setProperty(String.valueOf(i), clientID.toString());
                }
                properties.store(out, "clients");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    out.close();
                    out = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else{
            InputStream in = null;
            try {
                in = new FileInputStream(clientPath);
                properties.load(in);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                    in = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Collection<Object> clientIDCollection = properties.values();
        this.clientList = new ArrayList<>();
        Iterator<Object> iterator = clientIDCollection.iterator();
        while (iterator.hasNext()){
            String clientIDStr = (String) iterator.next();
            BigInteger clientID = new BigInteger(clientIDStr);
            Client client = new Client(clientID);
            this.clientList.add(client);
        }
    }


    /**
     *  初始化 policyMap, 如果参数为true, 表示变更, 要先初始化属性和client列表, 如果第三个参数是true, 表示第一个写入的client获取所有的权限。
     */
    public void initializePolicy(String policyPath, boolean change, boolean firstAdmin){
//        String policyPath = "datafile\\ospir-oxt\\policy.properties";
        InputStream in = null;
        Properties properties = new Properties();
        if(change){
            OutputStream out = null;
            DifferentRandom differentRandom = null;
            try {
                out = new FileOutputStream(new File(policyPath));
                StringBuilder stringBuilder = new StringBuilder();
                for (int j = 0; j < clientList.size(); ++j) {
                    Client client = clientList.get(j);
                    differentRandom = new DifferentRandom();
                    BigInteger clientID = client.getClientID();
                    int attrListSize = this.attributeList.size();
                    int randomNum = this.random.nextInt(attrListSize);
                    differentRandom.setUpBound(new BigInteger(String.valueOf(attrListSize)));
                    int i = 0;
                    int intValue;
                    String attribute = null;

                    if(j==0 && firstAdmin){
                        System.out.println("client: " + client.getClientID());
                        randomNum = attrListSize - 1;
                        for (; i < randomNum; ++i){
                            attribute = this.attributeList.get(i);
                            stringBuilder.append(attribute).append(policySeperateSymbol);
                        }
                        attribute = this.attributeList.get(i);
                        stringBuilder.append(attribute);
                    }else{
                        for (; i < randomNum; i++) {
                            intValue = differentRandom.getDifferentBigInteger().intValue();
                            attribute = this.attributeList.get(intValue);
                            stringBuilder.append(attribute).append(policySeperateSymbol);
                        }
                        intValue = differentRandom.getDifferentBigInteger().intValue();
                        attribute = this.attributeList.get(intValue);
                        stringBuilder.append(attribute);
                    }
                    properties.setProperty(clientID.toString(), stringBuilder.toString());
                    stringBuilder.setLength(0);
                }
                properties.store(out, "policy");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    out.close();
                    out = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else{
            try {
                in = new FileInputStream(policyPath);
                properties.load(in);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                    in = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        this.policyMap = new HashMap<>();
        Set<Map.Entry<Object, Object>> entries = properties.entrySet();
        List<String> attrList = null;
        for (Map.Entry<Object, Object> entry : entries) {
            attrList = new ArrayList<>();
            String keyStr = (String) entry.getKey();
            BigInteger key = new BigInteger(keyStr);
            String[] attributes = ((String) entry.getValue()).split(policySeperateSymbol);
            for (int i = 0; i < attributes.length; i++) {
                attrList.add(attributes[i]);
            }
            policyMap.put(key, attrList);
        }
    }

    /**
     *  判断 client 查询的属性是否合法
     * @param client
     * @param clientAttributeList
     * @return
     */
    public boolean judgeAttribute(Client client, List<String> clientAttributeList){
        BigInteger clientID = client.getClientID();
        List<String> rightAttributeList = this.policyMap.get(clientID);
        if(rightAttributeList == null){
            return false;
        }
        for (String attribute : clientAttributeList) {
            if(!rightAttributeList.contains(attribute)){
                return false;
            }
        }
        this.clientAttributeVector = clientAttributeList;
        return true;
    }


    /**
     *  获取 pBlind
     */
    public List<Element> generatePBlind(int ternNum){
        Element p = null;
        this.pBlind = new ArrayList<>();
        for (int i = 0; i < ternNum; i++) {
//            p = BigIntegerUtil.nextCoprimeBigInteger(this.primeP, this.random, this.eulerPrimeP, this.excludedElementSet);
            p = CryptoFunciton.getNonZeroRandomElement(this.zField);
            this.pBlind.add(p);
        }
        return this.pBlind;
    }

    /**
     *  根据 client 发来的blinded queries a1, 获取 stap'
     * @param a1
     * @return
     */
    public Element getTempStrap(Element a1){
//        return a1.modPow(ks, this.primeP);
        return a1.powZn(ks).getImmutable();
    }

    /**
     *  获取 bstag'
     * @param a1
     * @return
     */
    public Element getTempBstag(Element a1){
        String attribute1 = this.clientAttributeVector.get(0);
        Element subKt = kt.get(attribute1);
//        return a1.modPow(subKt.multiply(this.pBlind.get(0)), this.primeP);
        return a1.powZn(subKt.mulZn(this.pBlind.get(0))).getImmutable();
    }

    /**
     *  获取 bxtrap'
     *  alist里含有sterm，在第一个位置。该元素不使用。
     * @param aList
     * @return
     */
    public List<Element> getTempBxtrap(List<Element> aList){
        List<Element> tempBxtrapList = new ArrayList<>();
        Element tempBxtrap = null;
        Element subKx = null;
        String attribute = null;
        for (int i = 1; i < aList.size(); i++) {
            attribute = this.clientAttributeVector.get(i);
            subKx = kx.get(attribute);
            tempBxtrap = aList.get(i).powZn(subKx.mulZn(this.pBlind.get(i))).getImmutable();
            tempBxtrapList.add(tempBxtrap);
        }
        return tempBxtrapList;
    }

    public String[] getEnv(){
        StringBuilder sb = new StringBuilder();
        int i = 0;
        int len = pBlind.size() - 1;
        for (; i < len; i++) {
            sb.append(pBlind.get(i)).append(",");
        }
        sb.append(pBlind.get(i));
        return CryptoFunciton.authEnc(this.km, sb.toString());
    }

//    /**
//     * 初始化用户列表和权限信息，不计算时间.
//     * 在帮助client生成token之前调用
//     * 1. 要保证属性列表已经初始化
//     * 2. 要初始化client列表
//     * 3. 要初始化权限列表
//     */
//    public void initializeBeforeToken(){
//        // 初始化client数量为20，实际中可以一次生成，多次读文件
//        initializeClient(Constant.DEFAULT_CLIENT_NUMBER, BigInteger.valueOf(20000));
//        // 后期改为false
//        initializePolicy(true);
//    }

}