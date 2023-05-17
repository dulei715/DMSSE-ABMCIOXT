package cn.edu.hun.leileidu.improved.advanced.role;

import cn.edu.hun.leileidu.basestruct.TSetElement;
import cn.edu.hun.leileidu.improved.advanced.basestruct.AdvXToken;
import cn.edu.hun.leileidu.improved.advanced.basestruct.UXSet;
import cn.edu.hun.leileidu.improved.advanced.basestruct.XXSet;
import cn.edu.hun.leileidu.related.basestruct.TSet;
import cn.edu.hun.leileidu.related.basestruct.XToken;
import cn.edu.hun.leileidu.related.basestruct.stag.Stag;
import cn.edu.hun.leileidu.related.basestruct.stag.StringStag;
import cn.edu.hun.leileidu.utils.cryptography.BilinearUtil;
import it.unisa.dia.gas.jpbc.Element;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author: Leilei Du
 * @Date: 2018/7/18 8:24
 */
@SuppressWarnings("ALL")
public class Server {
    private TSet tset = null;
    private UXSet<String> uxset = null;
    private XXSet<String> xxset = null;

    private BilinearUtil bilinearUtil = new BilinearUtil();
//    private Element generateP = null;
//    private Element generateG = null;

    private BigInteger zFieldSize = null;

    public Server(TSet tset, UXSet<String> uxset, XXSet<String> xxset) {
        this.tset = tset;
        this.uxset = uxset;
        this.xxset = xxset;
    }

    /**
     *  测试用
     */
    public Server() {
    }

    public void setTset(TSet tset) {
        this.tset = tset;
    }

    public void setXset(UXSet<String> uxset, XXSet<String> xxset) {
        this.uxset = uxset;
        this.xxset = xxset;
    }

    /**
     * for test, public; for reality private
     * @param stag
     * @return 查询的stag对应的元素列表
     */
    public List<TSetElement> tSetRetrieve(Element stoken, Element publicKey){
        Element stagElem = this.bilinearUtil.pair(stoken, publicKey);
        Stag<String> stag = new StringStag(stagElem.toString());
        return tset.getElement(stag);
    }

    public List<TSetElement> tSetRetrieve(Element stoken, Element publicKey, int num){
        Element stagElem = this.bilinearUtil.pair(stoken, publicKey);
        Stag<String> stag = new StringStag(stagElem.toString());
        List<TSetElement> tsetElementList = tset.getElement(stag);
        if(num <= tsetElementList.size()){
            return tsetElementList.subList(0, num);
        }
        TSetElement tempElement = tsetElementList.get(0);
        for (int i = tsetElementList.size(); i < num; i++) {
            tsetElementList.add(tempElement);
        }
        return tsetElementList;
    }

    private boolean judgeLeftElement(XToken<Element> leftXToken) {
        List<Element> leftList = leftXToken.getXtokenElementList();
        for (Element element : leftList) {
            if(!uxset.contains(element.toString())){
                return false;
            }
        }
        return true;
    }

    private boolean judgeRightElement(XToken<Element> rightXToken, Element y){
        List<Element> rightList = rightXToken.getXtokenElementList();
        String rightElem = null;
        for (Element xtokenElem : rightList) {
//            elem = xtokenElem.modPow(y, this.primeP);
//            elem = this.bilinearUtil.pair(y, xtokenElem).toString();
            rightElem = xtokenElem.powZn(y).toString();
            if (!xxset.contains(rightElem)){
                return false;
            }
        }
        return true;
    }


    private List<String> searchRightXTokenListPart(List<TSetElement> elemList, List<XToken> rightXTokenList){
        List<String> eList = new ArrayList<>();
        if(rightXTokenList.size() == 0){
            for (TSetElement<String, Element> tSetElement : elemList) {
                eList.add(tSetElement.getE());
            }
            return eList;
        }
        Iterator<XToken> rightXTokenIterator = rightXTokenList.iterator();
        for (TSetElement<String, Element> tSetElement : elemList) {
            Element y =  tSetElement.getY();
            XToken<Element> rightXToken = rightXTokenIterator.next();
            if(this.judgeRightElement(rightXToken, y)){
                eList.add(tSetElement.getE());
            }
        }
        return eList;
    }

    @Deprecated
    public List<String> searchXTokenListPart(XToken<Element> leftXToken, List<TSetElement> elemList, List<XToken> rightXTokenList){
        if(!judgeLeftElement(leftXToken)){
            return new ArrayList<String>();
        }
        return searchRightXTokenListPart(elemList, rightXTokenList);
    }

    public List<String> searchXTokenListPart(List<TSetElement> elemList, AdvXToken advXToken){
        XToken<Element> leftXToken = advXToken.getLeftXToken();
        List<XToken> rightXTokenList = advXToken.getRightXTokenList();
        if(!judgeLeftElement(leftXToken)){
            return new ArrayList<String>();
        }
        return searchRightXTokenListPart(elemList, rightXTokenList);
    }

    //静态的 //暂不调用
    public List<String> search(Element stoken, XToken leftXToken, List<XToken> rightXTokenList, Element publicKey){
        List<TSetElement> elementList = tSetRetrieve(stoken, publicKey);
        List<String> eList = new ArrayList<>();
        if(this.judgeLeftElement(leftXToken)) {
            Iterator<XToken> rightXTokenIterator = rightXTokenList.iterator();
            for (TSetElement<String, Element> tSetElement : elementList) {
                Element y = tSetElement.getY();
                XToken<Element> xtoken = rightXTokenIterator.next();
                if(this.judgeRightElement(xtoken, y)){
                    eList.add(tSetElement.getE());
                }
            }
        }

        return eList;
    }

}