package com.tantanwen.leecode.no1114.thread;

public interface Foo{
    public void first(Runnable p) throws InterruptedException;
    public void second(Runnable p) throws InterruptedException;
    public void third(Runnable p) throws InterruptedException;
}
