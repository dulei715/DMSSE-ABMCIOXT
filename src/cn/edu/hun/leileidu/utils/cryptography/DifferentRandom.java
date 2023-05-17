package cn.edu.hun.leileidu.utils.cryptography;

import cn.edu.hun.leileidu.utils.BigIntegerUtil;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * @author: Leilei Du
 * @Date: 2018/7/15 22:41
 */
public class DifferentRandom {
    private Set<BigInteger> current = null;
    private BigInteger upBound = null;

    public DifferentRandom(Set<BigInteger> current, BigInteger upBount) {
        this.current = current;
        this.upBound = upBount;
    }

    public DifferentRandom() {
        current = new HashSet<>();
        upBound = BigInteger.ZERO;
    }

    public Set<BigInteger> getCurrent() {
        return current;
    }

    public void setCurrent(Set<BigInteger> current) {
        this.current = current;
    }

    public BigInteger getUpBound() {
        return upBound;
    }

    public void setUpBound(BigInteger upBound) {
        this.upBound = upBound;
    }

    public void clearSet(){
        this.current.clear();
    }

    public BigInteger getDifferentPositiveBigInteger(){
        BigInteger pos = null;
        while(true){
            pos = BigIntegerUtil.nextPositiveBigInteger(this.upBound, new Random(), this.current);
            if(!this.current.contains(pos)){
                break;
            }
        }
        this.current.add(pos);
        return pos;
    }

    public BigInteger getDifferentBigInteger(){
        BigInteger pos = null;
        while(true){
            pos = BigIntegerUtil.nextBigInteger(this.upBound, new Random(), this.current);
            if(!this.current.contains(pos)){
                break;
            }
        }
        this.current.add(pos);
        return pos;
    }

}