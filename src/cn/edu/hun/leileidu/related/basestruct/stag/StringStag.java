package cn.edu.hun.leileidu.related.basestruct.stag;

/**
 * @author: Leilei Du
 * @Date: 2018/7/8 10:55
 */
public class StringStag extends Stag<String>{

    public StringStag(String value) {
        super(value);
    }

    public StringStag() {
        super("");
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StringStag that = (StringStag) o;

        return value != null ? value.equals(that.value) : that.value == null;

    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}