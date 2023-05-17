package cn.edu.hun.leileidu.related.basestruct;

import cn.edu.hun.leileidu.basestruct.TSetElement;
import cn.edu.hun.leileidu.related.basestruct.stag.Stag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TSet {
    //TODO 更改 stag 为 ? extends stag
    private Map<Stag, List<TSetElement>> tsetmap;

    public TSet(Map<Stag, List<TSetElement>> tsetmap) {
        this.tsetmap = tsetmap;
    }

    public TSet() {
        tsetmap = new HashMap<>();
    }

    public Map<Stag, List<TSetElement>> getTSetMap(){
        return this.tsetmap;
    }

    public void putElement(Stag stag, List<TSetElement> tmap){
        tsetmap.put(stag, tmap);
    }

    public List<TSetElement> getElement(Stag stag){
        return tsetmap.get(stag);
    }


    @Override
    public String toString() {
        String result = "";
        Set<Map.Entry<Stag, List<TSetElement>>> entries = tsetmap.entrySet();
        for (Map.Entry<Stag, List<TSetElement>> entry : entries) {
            Stag key = entry.getKey();
            List<TSetElement> value = entry.getValue();
            result +=  key + ": " + value + System.lineSeparator();
        }
        return result;
    }
}