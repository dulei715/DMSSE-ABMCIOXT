package cn.edu.hun.pisces.improved.others.basestruct.stag;

import cn.edu.hun.pisces.related.basestruct.stag.Stag;
import it.unisa.dia.gas.jpbc.Element;

/**
 * @author: Leilei Du
 * @Date: 2018/7/15 15:06
 */
@Deprecated
public class ElementStag extends Stag<Element> {
    public ElementStag(Element value) {
        super(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        ElementStag that = (ElementStag) obj;

        return value != null ? value.equals(that.value) : that.value == null;
    }

    @Override
    public int hashCode() {
        return this.value != null ? value.toString().hashCode() : 0;
    }
}