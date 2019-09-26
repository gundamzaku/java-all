package com.tantanwen.leecode.no1115.thread;
import java.util.concurrent.Semaphore;

public class FooBar {
    private int n;
    public static Semaphore one = new Semaphore(1);
    public static Semaphore two = new Semaphore(0);

    public FooBar(int n) {
        this.n = n;
    }

    public void foo() throws InterruptedException {

        PrintFoo printFoo = new PrintFoo();
        printFoo.setN(5);
        new Thread(printFoo).start();
        /*
        for (int i = 0; i < n; i++) {
            one.acquire();
            System.out.println("Foo");
            two.release();
        }*/
    }

    public void bar() throws InterruptedException {
        PrintBar printBar = new PrintBar();
        printBar.setN(5);
        new Thread(printBar).start();
    }
}

class PrintFoo implements Runnable{
    private int n = 0;
    public void setN(Integer n){
        this.n = n;
    }
    public void run(){
        for (int i = 0; i < n; i++) {
            try {
                FooBar.one.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("foo ");
            FooBar.two.release();
        }
    }
}

class PrintBar implements Runnable{

    private int n = 0;
    public void setN(Integer n){
        this.n = n;
    }
    public void run(){
        for (int i = 0; i < n; i++) {
            try {
                FooBar.two.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Bar ");
            FooBar.one.release();
        }
    }
}