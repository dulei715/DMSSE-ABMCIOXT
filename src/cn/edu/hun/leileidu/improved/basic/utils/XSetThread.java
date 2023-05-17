package cn.edu.hun.leileidu.improved.basic.utils;

import cn.edu.hun.leileidu.improved.basic.role.Client;
import cn.edu.hun.leileidu.related.basestruct.XSet;
import cn.edu.hun.leileidu.utils.cryptography.BilinearUtil;
import cn.edu.hun.leileidu.utils.cryptography.CryptoFunciton;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author: Leilei Du
 * @Date: 2018/11/20 15:13
 */
public class XSetThread extends Thread {
    private BilinearUtil bilinearUtil = new BilinearUtil();
    private Field zField = this.bilinearUtil.getzField();

    private Client client = null;
    private XSet xset = null;
    private Element clientRandom = null;
    private List<String> keywordValueList = null;

    private Map<String, List<File>> reverseIndexData = null;
    private byte[] kx = null;
    private byte[] ki = null;

    public XSetThread(XSet xset, Client client, Element clientRandom, List<String> keywordValueList, Map<String, List<File>> reverseIndexData, byte[] kx, byte[] ki) {
        this.client = client;
        this.xset = xset;
        this.clientRandom = clientRandom;
        this.keywordValueList = keywordValueList;
        this.reverseIndexData = reverseIndexData;
        this.kx = kx;
        this.ki = ki;
    }

    @Override
    public void run() {
        buildXSet();
    }
    private void buildXSet(){

//        String pkStr = client.getPublicKey().toString();
//        Element clientRandom = clientRandomMap.get(client);
        Element right = client.getPublicKey().powZn(clientRandom).getImmutable();
//        List<String> keywordValueList = this.privelege.get(pkStr);
        for (String keywordValue : keywordValueList) {
            List<File> fileList = this.reverseIndexData.get(keywordValue);
            Element xtrap = CryptoFunciton.getHashValueFromZField(this.kx, keywordValue, this.zField);
            for (File file : fileList) {
                String ind = file.getAbsolutePath();
                Element xind = CryptoFunciton.getHashValueFromZField(this.ki, ind, this.zField);
                Element left = this.bilinearUtil.getPPowValue(xind.mulZn(xtrap));
                Element xtag = this.bilinearUtil.pair(left, right);
                xset.add(xtag.toString());
            }
        }
    }
}