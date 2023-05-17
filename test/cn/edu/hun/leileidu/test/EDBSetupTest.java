package cn.edu.hun.leileidu.test;

import cn.edu.hun.leileidu.related.basestruct.EDB;
import cn.edu.hun.leileidu.related.others.edbsetup.EDBSetup;
import org.junit.Test;

/**
 * @author: Leilei Du
 * @Date: 2018/7/9 11:21
 */
public class EDBSetupTest {
    @Test
    public void fun1(){
        String dbDir = "F:\\Users\\Administrator\\IdeaProjects\\OXTImprovedataset\\partdataset";
        EDBSetup edbSetup = new EDBSetup();
        edbSetup.selectKey(false);
        edbSetup.parseDataBase(dbDir);
        edbSetup.initialize();
        edbSetup.buildArrayTAndXSet();
        EDB deb = edbSetup.tSetSetup();
        System.out.println(deb.getTset().toString());
    }
}