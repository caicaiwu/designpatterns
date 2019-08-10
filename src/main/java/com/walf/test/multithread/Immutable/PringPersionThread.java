package com.walf.test.multithread.Immutable;

public class PringPersionThread extends Thread {
    private Persion persion;

    public PringPersionThread(Persion persion) {
        this.persion = persion;
    }

    public void run() {
        while (true) {
            System.out.println(Thread.currentThread().getName() + " prints " + persion);
        }
    }
}
