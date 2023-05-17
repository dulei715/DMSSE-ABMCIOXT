package cn.edu.hun.leileidu.improved.others.basestruct.element;

/**
 * @author: Leilei Du
 * @Date: 2018/7/15 12:39
 */
@Deprecated
public abstract class BaseElement {
    protected Object value;

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract int hashCode();



}