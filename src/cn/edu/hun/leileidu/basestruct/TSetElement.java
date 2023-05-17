package cn.edu.hun.leileidu.basestruct;

/**
 * 代表论文中的t
 * @author: Leilei Du
 * @Date: 2018/7/7 18:26
 */
public class TSetElement<E, Y> {
    private E e = null;
    private Y y = null;

    public TSetElement(E e, Y y) {
        this.e = e;
        this.y = y;
    }

    public E getE() {
        return e;
    }

    public void setE(E e) {
        this.e = e;
    }

    public Y getY() {
        return y;
    }

    public void setY(Y y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" +
                "e='" + e + '\'' +
                ", y=" + y +
                ')';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TSetElement that = (TSetElement) o;

        if (e != null ? !e.equals(that.e) : that.e != null) return false;
        return y != null ? y.equals(that.y) : that.y == null;

    }

    @Override
    public int hashCode() {
        int result = e != null ? e.hashCode() : 0;
        result = 31 * result + (y != null ? y.hashCode() : 0);
        return result;
    }
}