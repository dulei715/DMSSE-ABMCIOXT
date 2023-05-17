package cn.edu.hun.leileidu.improved.advanced.basestruct;

import cn.edu.hun.leileidu.related.basestruct.XToken;
import it.unisa.dia.gas.jpbc.Element;

import java.util.List;

/**
 * @author: Leilei Du
 * @Date: 2018/11/16 19:12
 */
public class AdvXToken {
    private XToken<Element> leftXToken;
    private List<XToken> rightXTokenList;

    public AdvXToken() {
    }

    public AdvXToken(XToken<Element> leftXToken, List<XToken> rightXTokenList) {
        this.leftXToken = leftXToken;
        this.rightXTokenList = rightXTokenList;
    }

    public XToken<Element> getLeftXToken() {
        return leftXToken;
    }

    public void setLeftXToken(XToken<Element> leftXToken) {
        this.leftXToken = leftXToken;
    }

    public List<XToken> getRightXTokenList() {
        return rightXTokenList;
    }

    public void setRightXTokenList(List<XToken> rightXTokenList) {
        this.rightXTokenList = rightXTokenList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AdvXToken advXToken = (AdvXToken) o;

        if (leftXToken != null ? !leftXToken.equals(advXToken.leftXToken) : advXToken.leftXToken != null) return false;
        return rightXTokenList != null ? rightXTokenList.equals(advXToken.rightXTokenList) : advXToken.rightXTokenList == null;

    }

    @Override
    public int hashCode() {
        int result = leftXToken != null ? leftXToken.hashCode() : 0;
        result = 31 * result + (rightXTokenList != null ? rightXTokenList.hashCode() : 0);
        return result;
    }
}