package cn.edu.hun.leileidu.utils.condition;

import java.util.Set;

public class StringNewCondition extends Condition<String> {

    private Set<String> excludeSet = null;

    public StringNewCondition(Set<String> excludeSet){
        this.excludeSet = excludeSet;
    }

    public StringNewCondition(){ }


    @Override
    public boolean satisfy(String s, Integer size) {
        if(s.length() < size){
            return false;
        }
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
}