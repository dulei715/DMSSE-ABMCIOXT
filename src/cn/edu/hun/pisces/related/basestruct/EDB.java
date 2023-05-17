package cn.edu.hun.pisces.related.basestruct;


/**
 * @author: Leilei Du
 * @Date: 2018/7/9 11:36
 */
public class EDB {
    private TSet tset = null;
    private XSet xset = null;

    public EDB(TSet tset, XSet xset) {
        this.tset = tset;
        this.xset = xset;
    }

    public EDB() {
    }

    public TSet getTset() {
        return tset;
    }

    public void setTset(TSet tset) {
        this.tset = tset;
    }

    public XSet getXset() {
        return xset;
    }

    public void setXset(XSet xset) {
        this.xset = xset;
    }
}