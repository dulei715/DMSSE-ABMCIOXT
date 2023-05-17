package cn.edu.hun.leileidu.utils.condition;

public abstract class Condition<T> {
    public abstract boolean satisfy(T t, Integer size);
}