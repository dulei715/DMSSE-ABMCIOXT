package cn.edu.hun.pisces.utils;

import java.math.BigInteger;
import java.util.Random;
import java.util.Set;

/**
 * @author: Leilei Du
 * @Date: 2018/7/7 11:20
 */
public class BigIntegerUtil {
    public static final BigInteger NEGATIVE_ONE = new BigInteger("-1");
    public static BigInteger powBigInteger(BigInteger radix, BigInteger exponent) {
        BigInteger result = BigInteger.ONE;
        char[] charArray = exponent.toString(2).toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] == '0')
                result = result.pow(2);
            else if (charArray[i] == '1')
                result = result.pow(2).multiply(radix);
            else
                throw new RuntimeException("exponent is error!");
        }
        return result;
    }

    /**
     * 获取给定大整数的欧拉函数
     * @param bigInteger
     * @return
     */
    public static BigInteger getEulerValue(BigInteger bigInteger){
        BigInteger result = bigInteger;
        BigInteger change = new BigInteger(bigInteger.toString());

//        System.out.println(result.equals(change));

        for (BigInteger i = new BigInteger("2"); i.multiply(i).compareTo(change) <= 0; i = i.add(BigInteger.ONE)) {
            if(change.mod(i).equals(BigInteger.ZERO)){
                result = result.divide(i).multiply(i.add(NEGATIVE_ONE));
                while(change.mod(i).equals(BigInteger.ZERO)){
                    change = change.divide(i);
                }
            }
        }
        if(change.compareTo(BigInteger.ONE) > 0){
            result = result.divide(change).multiply(change.add(NEGATIVE_ONE));
        }
        return result;
    }

    /**
     * 求给定范围内的与给定数互素的大整数
     * @param bint  范围上限
     * @param random 随机数
     * @param coprime 与结果互素的数
     * @return 大整数
     */
    public static BigInteger nextCoprimeBigInteger(BigInteger bint, Random random, BigInteger coprime){
        //获取欧拉函数
        BigInteger eulerValue = getEulerValue(bint);
        BigInteger i = BigInteger.ZERO;
        BigInteger candidate = null;
        while(!i.equals(eulerValue)){
            candidate = nextPositiveBigInteger(bint, random);
            if(coprimeWith(candidate, coprime)){
                return candidate;
            }
            i = i.add(BigInteger.ONE);
        }
        throw new RuntimeException("Can not find coprime big integer!");
    }

    public static BigInteger nextCoprimeBigInteger(BigInteger bint, Random random, BigInteger coprime, Set<BigInteger> excludedSet){
        //获取欧拉函数
        BigInteger eulerValue = getEulerValue(bint);
        BigInteger i = BigInteger.ZERO;
        BigInteger candidate = null;
        while(!i.equals(eulerValue)){
            candidate = nextPositiveBigInteger(bint, random);
            if(coprimeWith(candidate, coprime) && !excludedSet.contains(candidate)){
                return candidate;
            }
            i = i.add(BigInteger.ONE);
        }
        throw new RuntimeException("Can not find coprime big integer!");
    }

    /**
     * 判断两个数是否互素
     * @param bint1
     * @param bint2
     * @return
     */
    public static boolean coprimeWith(BigInteger bint1, BigInteger bint2) {
        return bint1.gcd(bint2).equals(BigInteger.ONE);
    }

    public static BigInteger nextBigInteger(BigInteger bint, Random random){
        //TODO 生成大整数保证等概率
        int bitLength = bint.bitLength();
        BigInteger bigInteger = null;
        while ((bigInteger = new BigInteger(bitLength, random)).compareTo(bint) >= 0);
        return bigInteger;
    }

    public static BigInteger nextPositiveBigInteger(BigInteger bint, Random random){
        //TODO 生成大整数保证等概率
        int bitLength = bint.bitLength();
        BigInteger bigInteger = null;
        while ((bigInteger = new BigInteger(bitLength, random)).compareTo(bint) >= 0 || bigInteger.compareTo(BigInteger.ZERO) == 0);
        return bigInteger;
    }

    public static BigInteger nextPositiveBigInteger(BigInteger bint, Random random, Set<BigInteger> excludedSet){
        int bitLength = bint.bitLength();
        BigInteger bigInteger = null;
        while((bigInteger = new BigInteger(bitLength, random)).compareTo(bint) >= 0 || bigInteger.compareTo(BigInteger.ZERO) ==0 || excludedSet.contains(bigInteger));
        return bigInteger;
    }

    public static BigInteger nextBigInteger(BigInteger bint, long seed, Set<BigInteger> excludedSet){
        //TODO 生成大整数保证等概率
        Random random = new Random(seed);
        int bitLength = bint.bitLength();
        BigInteger bigInteger = null;
        while ((bigInteger = new BigInteger(bitLength, random)).compareTo(bint) >= 0 || excludedSet.contains(bigInteger));
        return bigInteger;
    }

    public static BigInteger nextBigInteger(BigInteger bint, Random random, Set<BigInteger> excludedSet){
        int bitLength = bint.bitLength();
        BigInteger bigInteger = null;
        while ((bigInteger = new BigInteger(bitLength, random)).compareTo(bint) >= 0 || excludedSet.contains(bigInteger));
        return bigInteger;
    }

}