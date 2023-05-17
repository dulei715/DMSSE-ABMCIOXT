package cn.edu.hun.pisces.utils.cryptography;

import cn.edu.hun.pisces.utils.BigIntegerUtil;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

/**
 * @author 
 * @since 2018年2月4日
 * @desc 每次产生不同的随机整数
 */
public class DifRandom {
    private Set<Integer> current = null;//存放已经生成过的整数
    private static Random random = new Random();
    //构造函数
    DifRandom(){
    	this.current = new HashSet<Integer>();
    }
    DifRandom(Set<Integer> current){
    	this.current = current;
    }
    /**
     * 产生一个随机数
     * @param max 在0和max之间生成随机数[0,max)
     * @return 产生的随机数
     */
    public int nextInt(int max) {
    	//已经不能产生新的随机数
    	if(current.size()==max) {
    		return -1;
    	}
    	else {
    		int intt = random.nextInt(max);
    		while(current.contains(Integer.valueOf(intt))) {
    			intt = random.nextInt(max);
    		}
    		current.add(Integer.valueOf(intt));
    		return intt;
    	}
    }
	
    /**
	 * 生成128位随机字符串，用16进制字符串表示
	 */
	public static String randomString() {
		String str = UUID.randomUUID().toString();
        String temp = str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) + str.substring(24);
        return temp;
	}

}
