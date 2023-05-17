package cn.edu.hun.pisces.improved.advanced.basestruct;

import cn.edu.hun.pisces.related.basestruct.TSet;

/**
 * @author: Leilei Du
 * @Date: 2018/7/19 22:44
 */
public class EDB {
    private TSet tset = null;
    private UXSet uxset = null;
    private XXSet xxset = null;

    public EDB(TSet tset, UXSet uxset, XXSet xxset) {
        this.tset = tset;
        this.uxset = uxset;
        this.xxset = xxset;
    }

    public EDB() {
    }

    public TSet getTset() {
        return tset;
    }

    public void setTset(TSet tset) {
        this.tset = tset;
    }

    public UXSet getUxset() {
        return uxset;
    }

    public void setUxset(UXSet uxset) {
        this.uxset = uxset;
    }

    public XXSet getXxset() {
        return xxset;
    }

    public void setXxset(XXSet xxset) {
        this.xxset = xxset;
    }
}