package cn.edu.hun.pisces.related.basestruct.stag;

/**
 * @author: Leilei Du
 * @Date: 2018/7/8 10:34
 */
public abstract class Stag<T> {
    protected T value = null;


    public Stag(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    /**
     * 要求继承的子类重写equals方法
     * 如果是靠子类内部的成员来进行equals，需要保证这些成员有正确的equals方法
     * @param tag
     * @return
     */
    public abstract boolean equals(Object tag);

    @Override
    public String toString() {
        return "Stag{" +
                "value=" + value +
                '}';
    }

    @Override
    public int hashCode() {
        int result = value != null ? value.hashCode() : 0;
        return result;
    }
}