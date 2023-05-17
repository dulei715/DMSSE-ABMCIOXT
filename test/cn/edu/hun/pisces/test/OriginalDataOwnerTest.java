package cn.edu.hun.pisces.test;

import cn.edu.hun.pisces.related.role.DataOwner;
import org.junit.Before;
import org.junit.Test;

/**
 * @author: Leilei Du
 * @Date: 2018/7/9 16:35
 */
public class OriginalDataOwnerTest {
    private String dirPath = null;
    DataOwner dataOwner = null;
    @Before
    public void beforeFun(){
        dirPath = "F:\\Users\\Administrator\\IdeaProjects\\OXTImprovedataset\\partdataset";
        dataOwner = new DataOwner();
        dataOwner.edbSetup(dirPath);
    }

    @Test
    public void fun2(){
        System.out.println(dataOwner.getSterm());
        System.out.println(dataOwner.getXtermList());
    }
}