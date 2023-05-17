package cn.edu.hun.leileidu.improved.others.basestruct.key;

/**
 * @author: Leilei Du
 * @Date: 2018/7/15 13:18
 */
@Deprecated
public abstract class KeyAbstract<T> {

    protected T value = null;

    public KeyAbstract(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public int hashCode() {
        int result = value != null ? value.hashCode() : 0;
        return result;
    }

    public abstract String toHexByteString(int byteNum);
}