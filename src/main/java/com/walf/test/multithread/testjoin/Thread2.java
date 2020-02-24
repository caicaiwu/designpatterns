package com.walf.test.multithread.testjoin;

public class Thread2 extends Thread {
    public Thread2(String name) {
        this.setName(name);
    }

    public void run() {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {

        }
        System.out.println(Thread.currentThread().getName()+ " run");
    }
}
