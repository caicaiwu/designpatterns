package com.walf.test.multithread.ProducerConsumer;

public class Table {
    private final String[] buffer;
    private int tail;
    private int head;
    private int count;

    public Table(int count) {
        this.buffer = new String[count];
        this.head = 0;
        this.tail = 0;
        this.count = 0;
    }

    public synchronized void put(String cake) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " puts " + cake);
        while (count >= buffer.length) {
            wait();
        }
        System.out.println(Thread.currentThread().getName() + " puts 1" + cake);

        buffer[tail] = cake;
        tail = (tail + 1) % buffer.length;
        count++;
        System.out.println(Thread.currentThread().getName() + " put head "+head);
        notifyAll();
    }

    public synchronized String take() throws InterruptedException {
        while (count <= 0) {
            wait();
        }

        System.out.println(Thread.currentThread().getName() + " take head "+head);
        String cake = buffer[head];
        head = (head + 1) % buffer.length;
        count--;
        notifyAll();
        System.out.println(Thread.currentThread().getName() + " takes " + cake);
        return cake;

    }
}
