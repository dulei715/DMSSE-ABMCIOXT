package cn.edu.hun.pisces.parallel.test;

import java.util.Set;

/**
 * @author: Leilei Du
 * @Date: 2018/11/20 14:25
 */
public class MyThread extends Thread {
    private int index = 0;
    private Set<Integer> set = null;

    public MyThread(int index, Set<Integer> set) {
        this.index = index;
        this.set = set;
    }

    @Override
    public void run() {
        for (int i = index; i < index + 10; i++) {
            this.set.add(i);
        }
    }
}