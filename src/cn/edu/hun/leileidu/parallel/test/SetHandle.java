package cn.edu.hun.leileidu.parallel.test;

import java.util.Set;

/**
 * @author: Leilei Du
 * @Date: 2018/11/20 13:42
 */
public class SetHandle implements Runnable {
    private int startInt = 0;
    private Set<Integer> set = null;

    public SetHandle(int startInt, Set<Integer> set) {
        this.startInt = startInt;
        this.set = set;
    }

    public int getStartInt() {
        return startInt;
    }

    public Set<Integer> getSet() {
        return set;
    }

    @Override
    public void run() {
        for (int i = startInt; i < startInt + 10; i++) {
            this.set.add(i);
        }
    }
}