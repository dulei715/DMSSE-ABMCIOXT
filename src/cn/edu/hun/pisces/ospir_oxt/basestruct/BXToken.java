package cn.edu.hun.pisces.ospir_oxt.basestruct;

import cn.edu.hun.pisces.related.basestruct.XToken;

import java.util.List;

/**
 * @author: Leilei Du
 * @Date: 2018/7/24 10:15
 */
public class BXToken<T> extends XToken<T> {
    public BXToken(List<T> xtokenElementList) {
        super(xtokenElementList);
    }

    public BXToken() {
    }
}