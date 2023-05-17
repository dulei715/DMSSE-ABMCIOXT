package usefultest;

import cn.edu.hun.pisces.basestruct.Keyword;
import cn.edu.hun.pisces.improved.basic.role.Client;
import cn.edu.hun.pisces.related.basestruct.ReverseIndexTable;
import cn.edu.hun.pisces.utils.Constant;
import cn.edu.hun.pisces.utils.fileutil.FileSplitUtil;
import cn.edu.hun.pisces.utils.cryptography.BilinearUtil;
import it.unisa.dia.gas.jpbc.Element;
import org.junit.Test;

import java.io.*;
import java.math.BigInteger;
import java.util.*;

/**
 * @author: Leilei Du
 * @Date: 2018/7/16 14:09
 */
public class GenerateClientPrivilegeTest {

    private BilinearUtil bilinearUtil = new BilinearUtil();
    private List<Client> clientList = new ArrayList<>();

    public Set<Keyword> getKeywordSet(String fileStr){
        File file = new File(fileStr);
        HashMap<String, List<File>> stringListHashMap = FileSplitUtil.extractReverseIndexData(file, Constant.DEFAULT_REGEX_SPLIT);
        ReverseIndexTable reverseIndexTable = new ReverseIndexTable();
        reverseIndexTable.construct(stringListHashMap);
        return reverseIndexTable.getIndexTable().keySet();
    }

//    public void setClientList(){
//        BigInteger[] randomNumber = Constant.DEFAULT_CLIENTS_RANDOM_R;
//        BigInteger[] secreteKeyArray = Constant.DEFAULT_CLIENT_SECRETE_KEY;
//        int num = secreteKeyArray.length;
//        Client client = null;
//        for (int i = 0; i < num; i++) {
//            Element gr = bilinearUtil.getGPowValue(randomNumber[i]);
//            client = new Client(secreteKeyArray[i], gr);
//            clientList.add(client);
//        }
//    }

    public Map<Client, List<Keyword>> generatePrivilege(Set<Keyword> keywordSet, List<Client> clientList){
        int keywordNum = keywordSet.size();
        int tenPartKeywordNum = keywordNum / 10;
        Random random = new Random();
        Map<Client, List<Keyword>> map = new HashMap<>();
        for (Client client : clientList) {
            List<Keyword> list = null;
            if(!map.containsKey(client)){
                list = new ArrayList<>();
                map.put(client, list);
            }
            list = map.get(client);
            for (Keyword keyword : keywordSet) {
                int rand = random.nextInt(keywordNum);
                if(rand <= tenPartKeywordNum){
                    list.add(keyword);
                }
            }
        }
        return map;
    }



//    @Test
//    public void fun1() throws IOException {
//        String dbDir = "F:\\Users\\Administrator\\IdeaProjects\\OXTImprovedataset\\partdataset";
//        String fileName = "datafile\\privilege.properties";
//        Set<Keyword> keywordSet = getKeywordSet(dbDir);
//        this.setClientList();
//        Map<Client, List<Keyword>> clientListMap = this.generatePrivilege(keywordSet, this.clientList);
//
//        Properties properties = new Properties();
//
//        for (Map.Entry<Client, List<Keyword>> entry : clientListMap.entrySet()) {
//            Client client = entry.getKey();
//            String clientPK = client.getPublicKey().toString();
//
//            List<Keyword> list = entry.getValue();
//            StringBuilder stringBuilder = new StringBuilder();
//            int len = list.size() - 1;
//            int i = 0;
//            for (; i < len; i++) {
//                stringBuilder.append(list.get(i).getValue()).append(";");
//            }
//            stringBuilder.append(list.get(i).getValue());
////            properties.put(clientPK, stringBuilder.toString());
//            properties.setProperty(clientPK, stringBuilder.toString());
//        }
//
//        FileOutputStream out = new FileOutputStream(fileName);
//        properties.store(out, "privilege");
//        out.close();
//
//    }

//    @Test
//    public void fun2(){
//        String dbDir = "F:\\Users\\Administrator\\IdeaProjects\\OXTImprovedataset\\partdataset";
//        String fileName = "datafile\\privilege.properties";
//        Set<Keyword> keywordSet = getKeywordSet(dbDir);
//        this.setClientList();
//        Map<Client, List<Keyword>> clientListMap = this.generatePrivilege(keywordSet, this.clientList);
//
//        Properties properties = new Properties();
//
//        int count = 0;
//        StringBuilder stringBuilder = new StringBuilder();
//        for (Map.Entry<Client, List<Keyword>> entry : clientListMap.entrySet()) {
//            Client client = entry.getKey();
//            stringBuilder.append(client.getPublicKey()).append(": ");
//            List<Keyword> list = entry.getValue();
//            int i = 0;
//            int len = list.size()-1;
//            for (; i < len; i++) {
//                stringBuilder.append(list.get(i)).append("; ");
//                if(list.get(i).getValue().equals("")){
//                    ++count;
//                }
//            }
//            stringBuilder.append(list.get(i).getValue()).append(System.lineSeparator());
//        }
//        System.out.println(stringBuilder);
//        System.out.println();
//        System.out.println(count);
//    }

//    @Test
//    public void fun3() throws IOException {
//        String dbDir = "F:\\Users\\Administrator\\IdeaProjects\\OXTImprovedataset\\partdataset";
//        String fileName = "datafile\\privilege.properties";
//        generateClientPrivilegeToFile(dbDir, fileName);
//    }

//    private void generateClientPrivilegeToFile(String dbDir, String fileName) throws IOException {
//        Set<Keyword> keywordSet = getKeywordSet(dbDir);
//        this.setClientList();
//        Map<Client, List<Keyword>> clientListMap = this.generatePrivilege(keywordSet, this.clientList);
//
////        Properties properties = new Properties();
////        FileOutputStream out = new FileOutputStream(fileName);
//        BufferedWriter bufw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName)));
//
//        for (Map.Entry<Client, List<Keyword>> entry : clientListMap.entrySet()) {
//            Client client = entry.getKey();
//            StringBuilder stringBuilder = new StringBuilder();
//
//            stringBuilder.append("#").append(System.lineSeparator());
//
//            //fortest 在文件中输出用户的随机数gr
//            String clientGr = client.getGr().toString();
//            stringBuilder.append("#").append("gr: ").append(clientGr).append(System.lineSeparator());
//
//            //fortest 在文件中年输出用户获得的随机数r
//            BigInteger[] randomRArr = Constant.DEFAULT_CLIENTS_RANDOM_R;
//            for (int i = 0; i < randomRArr.length; i++) {
//                String temp = this.bilinearUtil.getGPowValue(randomRArr[i]).toString();
//                if(temp.equals(clientGr)){
//                    stringBuilder.append("#").append("r: ").append(randomRArr[i]).append(System.lineSeparator());
//                }
//            }
//
//            //for test 在文件中输出用户密钥，实际中去掉即可
//            String clientSK = client.getSecretKey().toString();
//            stringBuilder.append("#").append("sk: ").append(clientSK).append(System.lineSeparator());
//
//            String clientPK = client.getPublicKey().toString();
//            stringBuilder.append(clientPK).append("=");
//
//            List<Keyword> list = entry.getValue();
//            int len = list.size() - 1;
//            int i = 0;
//            for (; i < len; i++) {
//                stringBuilder.append(list.get(i).getValue()).append(";");
//            }
//            stringBuilder.append(list.get(i).getValue()).append(System.lineSeparator());
////            properties.put(clientPK, stringBuilder.toString());
////            properties.setProperty(clientPK, stringBuilder.toString());
//            bufw.write(stringBuilder.toString());
//            bufw.flush();
//        }
//        bufw.close();
//    }

//    @Test
//    public void fun4() throws IOException {
//        String dbDir = "F:\\Users\\Administrator\\IdeaProjects\\OXTImprovedataset\\partpartdataset";
//        String fileName = "datafile\\privilege_advanced.properties";
//        generateClientPrivilegeToFile(dbDir, fileName);
//    }


}