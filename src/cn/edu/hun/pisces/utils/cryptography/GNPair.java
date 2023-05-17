package cn.edu.hun.pisces.utils.cryptography;

import java.math.BigInteger;

/**
 * 存储生成元g和模数N的类
 * @author: Leilei Du
 * @Date: 2018/7/7 17:28
 */
public class GNPair {
    private BigInteger g = new BigInteger("71");
    private BigInteger n = new BigInteger("867988061");

    public GNPair() {

    }

    public GNPair(BigInteger g, BigInteger n) {
        this.g = g;
        this.n = n;
    }

    public BigInteger getG() {
        return g;
    }

    public void setG(BigInteger g) {
        this.g = g;
    }

    public BigInteger getN() {
        return n;
    }

    public void setN(BigInteger n) {
        this.n = n;
    }

}