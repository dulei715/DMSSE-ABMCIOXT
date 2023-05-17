package cn.edu.hun.pisces.basestruct;

import cn.edu.hun.pisces.basestruct.Keyword;

import java.util.HashMap;
import java.util.List;
@Deprecated
public class ForwardIndexTable<T> {
    private HashMap<T, List<Keyword>> indexTable = null;
}