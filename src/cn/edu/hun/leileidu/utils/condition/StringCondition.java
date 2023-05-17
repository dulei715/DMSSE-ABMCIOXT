package cn.edu.hun.leileidu.utils.condition;

import java.util.Set;
@Deprecated
public class StringCondition extends Condition<String> {

    private Set<String> excludeSet = null;

    public StringCondition(Set<String> excludeSet){
        this.excludeSet = excludeSet;
    }

    public StringCondition(){ }


    public boolean satisfy(String s) {
        int slen = s.length();
        for (int i = 0; i < slen; i++) {
//            if (!Character.isLetterOrDigit(s.charAt(i))){
//                return false;
//            }
            if(!Character.isLetter(s.charAt(i))){
                return false;
            }
        }
        if (excludeSet != null && excludeSet.contains(s)){
            return false;
        }
        return true;
    }

    @Override
    public boolean satisfy(String s, Integer size) {
        return this.satisfy(s, null);
    }
}