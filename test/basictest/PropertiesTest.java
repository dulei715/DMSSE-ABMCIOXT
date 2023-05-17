package basictest;

import cn.edu.hun.leileidu.improved.others.utils.PrivilegeUtil;
import cn.edu.hun.leileidu.utils.Constant;
import cn.edu.hun.leileidu.utils.ioutil.MyPrint;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * @author: Leilei Du
 * @Date: 2018/11/17 20:07
 */
public class PropertiesTest {
    @Test
    public void fun1(){
        String fileName = Constant.DEFAULT_PROGRAM_PATH + "\\datafile\\improved\\privilege.properties";
        Map<String, List<String>> stringListMap = PrivilegeUtil.readPrivilege(fileName);
        MyPrint.showMap(stringListMap);
    }
}