package cn.edu.hun.pisces.utils.cryptography;

import cn.edu.hun.pisces.utils.BigIntegerUtil;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

/**
 * @author 
 * @since 2018��2��4��
 * @desc ÿ�β�����ͬ���������
 */
public class DifRandom {
    private Set<Integer> current = null;//����Ѿ����ɹ�������
    private static Random random = new Random();
    //���캯��
    DifRandom(){
    	this.current = new HashSet<Integer>();
    }
    DifRandom(Set<Integer> current){
    	this.current = current;
    }
    /**
     * ����һ�������
     * @param max ��0��max֮�����������[0,max)
     * @return �����������
     */
    public int nextInt(int max) {
    	//�Ѿ����ܲ����µ������
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
	 * ����128λ����ַ�������16�����ַ�����ʾ
	 */
	public static String randomString() {
		String str = UUID.randomUUID().toString();
        String temp = str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) + str.substring(24);
        return temp;
	}

}
