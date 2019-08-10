package com.walf.test.com.walf.test.multithread.Immutable;

public class Main {
    public static void main(String[] args) {
        Persion alice = new Persion("Alice", "Alaska");
        new PringPersionThread(alice).start();
        new PringPersionThread(alice).start();
        new PringPersionThread(alice).start();
    }
}
