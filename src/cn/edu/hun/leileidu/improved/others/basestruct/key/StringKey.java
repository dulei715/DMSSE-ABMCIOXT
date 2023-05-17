package cn.edu.hun.leileidu.improved.others.basestruct.key;

/**
 * @author: Leilei Du
 * @Date: 2018/7/15 14:16
 */
@Deprecated
public class StringKey extends KeyAbstract<String> {
    public StringKey(String value) {
        super(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        StringKey that = (StringKey) obj;

        return value != null ? value.equals(that.value) : that.value == null;
    }

    @Override
    public String toHexByteString(int byteNum) {
        // TODO: 2018/7/15
        return null;
    }
}