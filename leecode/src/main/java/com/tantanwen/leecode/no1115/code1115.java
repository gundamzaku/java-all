package com.tantanwen.leecode.no1115;

import com.tantanwen.leecode.no1115.thread.FooBar;

public class code1115 {

    public static void main(String[] args) {
        FooBar fooBar = new FooBar(5);
        try {
            fooBar.bar();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            fooBar.foo();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}