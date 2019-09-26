package com.tantanwen.leecode.no1114;

import com.tantanwen.leecode.no1114.thread.Foo;
import com.tantanwen.leecode.no1114.thread.FooLatch;

/*
三个不同的线程将会共用一个 Foo 实例。

    线程 A 将会调用 one() 方法
    线程 B 将会调用 two() 方法
    线程 C 将会调用 three() 方法

请设计修改程序，以确保 two() 方法在 one() 方法之后被执行，three() 方法在 two() 方法之后被执行。
 */

public class code1114 {
    public static void main(String[] args) {

        Foo foo = new FooLatch();

        try {
            foo.first(new PrintFirst());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            foo.second(new PrintSecond());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            foo.third(new PrintThird());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
class PrintFirst implements Runnable{
    public void run(){
        System.out.println("one ");
    }
}
class PrintSecond implements Runnable{
    public void run(){
        System.out.println("two ");
    }
}
class PrintThird implements Runnable{
    public void run(){
        System.out.println("three ");
    }
}