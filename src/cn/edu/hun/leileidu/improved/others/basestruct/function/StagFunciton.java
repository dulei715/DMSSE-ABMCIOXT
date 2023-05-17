package cn.edu.hun.leileidu.improved.others.basestruct.function;

import cn.edu.hun.leileidu.basestruct.Keyword;
import cn.edu.hun.leileidu.improved.others.basestruct.key.KeyAbstract;
import cn.edu.hun.leileidu.related.basestruct.stag.Stag;
import cn.edu.hun.leileidu.utils.Constant;

/**
 * @author: Leilei Du
 * @Date: 2018/7/15 13:02
 */
@Deprecated
public class StagFunciton implements FunctionInterface<KeyAbstract, Keyword, Stag<String>> {
    private static int defaultKeyLength = Constant.DEFAULT_KEY_BYTE_LENGTH;
    @Override
    public Stag getFunctionValue(KeyAbstract key, Keyword keyword) {
//        String keyStr = key.toHexByteString(defaultKeyLength);
//        String hash = Hash.getMD5StrJava(keyStr + keyword.getValue());
//        long left = new BigInteger(hash.substring(0,defaultKeyLength/2), 16).longValue();
//        long right = new BigInteger(hash.substring(defaultKeyLength/2,defaultKeyLength), 16).longValue();
//        long seed = (left + right) / 2;
//        Random random = new Random(seed);
        //保证产生的小于p的随机数不是0
//        StringUtil.byteArrayToHexString(CryptoFunciton.func(key.getValue(), keyword.getValue()));
//        Stag<String> stag = new StringStag(result);
        return null;
    }
}