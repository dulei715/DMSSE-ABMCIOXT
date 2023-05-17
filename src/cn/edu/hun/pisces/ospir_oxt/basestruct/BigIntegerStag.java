package cn.edu.hun.pisces.ospir_oxt.basestruct;

import cn.edu.hun.pisces.related.basestruct.stag.Stag;

import java.math.BigInteger;

/**
 * @author: Leilei Du
 * @Date: 2018/7/24 13:43
 */
public class BigIntegerStag extends Stag<BigInteger> {
    public BigIntegerStag(BigInteger value) {
        super(value);
    }

    @Override
    public boolean equals(Object tag) {
        if (this == tag) return true;
        if (tag == null || getClass() != tag.getClass()) return false;

        BigIntegerStag that = (BigIntegerStag) tag;

        return value != null ? value.equals(that.value) : that.value == null;
    }
}