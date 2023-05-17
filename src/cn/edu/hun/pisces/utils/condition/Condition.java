package cn.edu.hun.pisces.utils.condition;

public abstract class Condition<T> {
    public abstract boolean satisfy(T t, Integer size);
}