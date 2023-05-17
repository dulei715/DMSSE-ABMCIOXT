package cn.edu.hun.pisces.parallel;

import cn.edu.hun.pisces.parallel.test.MyThread;
import cn.edu.hun.pisces.parallel.test.SetHandle;
import cn.edu.hun.pisces.utils.ioutil.MyPrint;

import java.util.HashSet;
import java.util.Set;

/**
 * @author: Leilei Du
 * @Date: 2018/11/20 13:41
 */
public class Main {
//    public static void main0(String[] args) {
//        Set<Integer> set = new HashSet<>();
//        SetHandle setHandle = new SetHandle(1);
//        Thread threadA = new Thread(setHandle, "Thread1");
//        Thread threadB = new Thread(setHandle, "Thread2");
//        Thread threadC = new Thread(setHandle, "Thread3");
//        Thread threadD = new Thread(setHandle, "Thread4");
//
//        threadA.start();
//        threadB.start();
//        threadC.start();
//        threadD.start();
//
//
////        System.out.println(1);
//        try {
//            Thread.sleep(4000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println(setHandle.getSet());
//
//    }

    public static void main(String[] args) {
        Set<Integer> set = new HashSet<>();
        MyThread threadA = new MyThread(1, set);
        MyThread threadB = new MyThread(100, set);
        MyThread threadC = new MyThread(200, set);
        MyThread threadD = new MyThread(300, set);

        threadA.start();
        threadB.start();
        threadC.start();
        threadD.start();

        try {
            threadA.join();
            threadB.join();
            threadC.join();
            threadD.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(set);
    }
}