package tooltest;

import cn.edu.hun.leileidu.basestruct.Keyword;
import cn.edu.hun.leileidu.basestruct.docfile.DocFile;
import cn.edu.hun.leileidu.related.basestruct.EDB;
import cn.edu.hun.leileidu.related.role.DataOwner;
import cn.edu.hun.leileidu.utils.KeywordUtil;
import cn.edu.hun.leileidu.utils.ioutil.MyPrint;
import cn.edu.hun.leileidu.utils.testutil.TestResultUtil;
import org.junit.Test;

import java.util.List;
import java.util.Set;

/**
 * @author: Leilei Du
 * @Date: 2018/11/22 15:57
 */
public class TestResultUtilTest {
    public static String outFileName = "F:\\Users\\Administrator\\IdeaProjects\\OXTImprovedataset\\out_reverse_index\\dataset_out_00.txt";
    @Test
    public void fun1(){
        DataOwner dataOwnerOXT = new DataOwner();
        EDB edb = dataOwnerOXT.edbSetup(outFileName);
        List<Keyword> keywordList = KeywordUtil.readKeyword(outFileName, 2);
        MyPrint.showList(keywordList);
        System.out.println();
        Set<DocFile> result = TestResultUtil.getIntersection(keywordList, dataOwnerOXT.getReverseIndexTable());
//        System.out.println(result);
        MyPrint.showSet(result);
    }
}