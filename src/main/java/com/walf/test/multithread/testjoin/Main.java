package com.walf.test.multithread.testjoin;

public class Main {
    public static void main(String[] args) {
        Thread t1 = new Thread1("Thread1");
        Thread t2 = new Thread2("Thread2");
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (Exception e) {

        }

        System.out.println("Main thread close");

    }
}
