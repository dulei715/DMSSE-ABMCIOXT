package otherstest;

import cn.edu.hun.leileidu.basestruct.Keyword;
import cn.edu.hun.leileidu.basestruct.TSetElement;
import cn.edu.hun.leileidu.related.role.DataOwner;
import cn.edu.hun.leileidu.related.basestruct.stag.Stag;
import cn.edu.hun.leileidu.utils.ioutil.MyWrite;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author: Leilei Du
 * @Date: 2018/7/11 13:17
 */
public class MyWriteTest {
    @Test
    public void fun1(){
        String dbDir = "F:\\Users\\Administrator\\IdeaProjects\\OXTImprovedataset\\partdataset";
        String fileOut = "F:\\ttemp\\out.txt";
        File fileoutFile = new File(fileOut);

        DataOwner dataOwner = new DataOwner();
        dataOwner.edbSetup(dbDir);
        Map<Keyword, List<TSetElement>> kset = dataOwner.getArrayT();
        Map<Stag, List<TSetElement>> sset = dataOwner.getTSetMap();
        System.out.println(sset.size());
        MyWrite.writeMapKeyAndValueNum(fileoutFile, sset);

    }

    @Test
    public void fun2(){
        String dbDir = "F:\\Users\\Administrator\\IdeaProjects\\OXTImprovedataset\\partdataset";
        String fileOut = "F:\\ttemp\\out1.txt";
        File fileoutFile = new File(fileOut);

        DataOwner dataOwner = new DataOwner();
        dataOwner.edbSetup(dbDir);
        Map<Keyword, List<TSetElement>> kset = dataOwner.getArrayT();
        System.out.println(kset.size());
        MyWrite.writeMapKeyAndValueNum(fileoutFile, kset);
    }
}