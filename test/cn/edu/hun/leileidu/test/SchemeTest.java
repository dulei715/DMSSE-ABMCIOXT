package cn.edu.hun.leileidu.test;

import cn.edu.hun.leileidu.basestruct.TSetElement;
import cn.edu.hun.leileidu.related.basestruct.XSet;
import cn.edu.hun.leileidu.utils.Constant;
import cn.edu.hun.leileidu.utils.cryptography.CryptoFunciton;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Leilei Du
 * @Date: 2018/7/12 12:25
 */
public class SchemeTest {
    private BigInteger generateElementG = Constant.DEFAULT_GENERATE_ELEMENT;
    private BigInteger primeP = Constant.DEFAULT_BIG_PRIME;
    private BigInteger eulerPrimeP = Constant.DEFAULT_BIG_EULER_PRIME;
    private byte[] ks = Constant.DEFAULT_K_S;
    private byte[] kx = Constant.DEFAULT_K_X;
    private byte[] ki = Constant.DEFAULT_K_I;
    private byte[] kz = Constant.DEFAULT_K_Z;
    private byte[] kt = Constant.DEFAULT_K_T;

    private XSet readXSet(String path) throws IOException {
        XSet xset = new XSet();
        BufferedReader bufr = new BufferedReader(new FileReader(path));
        String line = null;
        BigInteger bigInteger = null;
        while((line = bufr.readLine()) != null){
            bigInteger = new BigInteger(line);
            xset.add(bigInteger);
        }
        bufr.close();
        return xset;
    }

    @Test
    public void fun1() throws IOException {
        String path = "F:\\ttemp\\xset.txt";
        XSet xset = this.readXSet(path);
        List<TSetElement> list = new ArrayList<>();

        int count = 0;


        String sterm = "th";
        String xterm = "cc";
        List<String> stermIndexList = new ArrayList<>();
        
        int c = 1;

        for (int ind = 1; ind <= 100; ind++) {
            c++;
            BigInteger xind = CryptoFunciton.funcp(this.ki, ind+".txt", this.eulerPrimeP, null);
            BigInteger z = CryptoFunciton.funcp(this.kz, sterm.concat(String.valueOf(c)), this.eulerPrimeP, this.eulerPrimeP);
            BigInteger y = z.modPow(new BigInteger("-1"), this.eulerPrimeP).multiply(xind).mod(this.eulerPrimeP);

            BigInteger xtrap = CryptoFunciton.funcp(this.kx, sterm, this.eulerPrimeP, null);
            BigInteger xtag = this.generateElementG.modPow(xtrap.multiply(xind), this.primeP);
//            System.out.println(xtag);
            if(xset.contains(xtag)){
                list.add(new TSetElement(sterm, y));
                stermIndexList.add(ind+".txt");
                ++count;
//                System.out.println(ind+".txt");
            }
        }
//        System.out.println(count);

        String[] inds = new String[3];
        BigInteger[] xtokens = new BigInteger[3];
        inds[0] = "28.txt";
        inds[1] = "54.txt";
        inds[2] = "85.txt";

        BigInteger xtrap = CryptoFunciton.funcp(this.kx, xterm, this.eulerPrimeP, null);
        for (int i = 1; i <= xtokens.length; i++) {
            BigInteger z = CryptoFunciton.funcp(this.kz, sterm.concat(String.valueOf(i)), this.eulerPrimeP, this.eulerPrimeP);
            xtokens[i-1] = this.generateElementG.modPow(z.multiply(xtrap), this.primeP);

        }

        for (int i = 1; i <= xtokens.length; i++) {
            BigInteger xind = CryptoFunciton.funcp(this.ki, inds[i-1], this.eulerPrimeP, null);
            BigInteger z = CryptoFunciton.funcp(this.kz, sterm.concat(String.valueOf(i)), this.eulerPrimeP, this.eulerPrimeP);
            BigInteger y = z.modPow(new BigInteger("-1"), this.eulerPrimeP).multiply(xind).mod(this.eulerPrimeP);
            BigInteger result = xtokens[i - 1].modPow(y, this.primeP);
            BigInteger real = generateElementG.modPow(xind.multiply(xtrap), this.primeP);
            System.out.println("result: " + result);
            System.out.println("real: " + real);
            System.out.println("result.equals(real): " + result.equals(real));
            System.out.println(xset.contains(result));
            System.out.println("*********************************************");
        }



    }

    @Test
    public void fun2(){
        BigInteger xtrap = new BigInteger("844550196"); // F_p(kx, w)
        BigInteger xind = new BigInteger("777876486");
        BigInteger z = new BigInteger("569861361");
        BigInteger y = z.modPow(new BigInteger("-1"), this.primeP).multiply(xind).mod(this.primeP);
//        BigInteger
    }

    @Test
    public void fun3(){
        BigInteger g = new BigInteger("3");
        BigInteger p = new BigInteger("5");
        BigInteger result = g.modPow(new BigInteger("-1"), p);
        System.out.println(result);
    }
}