package cn.edu.hun.pisces.ospir_oxt.basestruct;

import cn.edu.hun.pisces.basestruct.Keyword;

/**
 * @author: Leilei Du
 * @Date: 2018/7/22 10:21
 */
public class AttributeKeyword extends Keyword {
    protected String attribute;
    public AttributeKeyword(String attribute, String value) {
        super(value);
        this.attribute = attribute;
    }

    public AttributeKeyword(String attribute, String value, int fileNumber) {
        super(value, fileNumber);
        this.attribute = attribute;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        AttributeKeyword that = (AttributeKeyword) o;

        boolean judge1 =  attribute != null ? attribute.equals(that.attribute) : that.attribute == null;

        if(!judge1) return false;

        return value != null ? value.equals(that.value) : that.value == null;


    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (attribute != null ? attribute.hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        return "AttributeKeyword{" +
                "value='" + value + '\'' +
                ", fileNumber=" + fileNumber + ", " +
                "attribute='" + attribute +
                "'}";
    }
}