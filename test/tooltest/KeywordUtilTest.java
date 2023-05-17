package tooltest;

import cn.edu.hun.leileidu.basestruct.Keyword;
import cn.edu.hun.leileidu.utils.KeywordUtil;
import cn.edu.hun.leileidu.utils.ioutil.MyPrint;
import org.junit.Test;

import java.util.List;

/**
 * @author: Leilei Du
 * @Date: 2018/11/22 15:13
 */
public class KeywordUtilTest {
    @Test
    public void fun1(){
        String filePath = "F:\\Users\\Administrator\\IdeaProjects\\OXTImprovedataset\\out_reverse_index\\dataset_out_00.txt";
        List<Keyword> keywordList = KeywordUtil.readKeyword(filePath, 100);
        MyPrint.showList(keywordList);
    }
}