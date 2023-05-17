package cn.edu.hun.leileidu.related.basestruct.stag;

/**
 * @author: Leilei Du
 * @Date: 2018/7/8 11:05
 */
public class IntegerStag extends Stag<Integer> {
    public IntegerStag(Integer value) {
        super(value);
    }

    //    Integer value
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IntegerStag that = (IntegerStag) o;

        return value != null ? value.equals(that.value) : that.value == null;
    }

}