package com.walf.test.multithread.testjoin;

public class Thread1 extends Thread {
    public Thread1(String name) {
        this.setName(name);
    }
    public void run() {
        try {
            Thread.sleep(5000);
        } catch (Exception e) {

        }
        System.out.println(Thread.currentThread().getName()+ " run");
    }
}
