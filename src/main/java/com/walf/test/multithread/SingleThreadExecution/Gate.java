package com.walf.test.multithread.SingleThreadExecution;

/*
* pass必须声明为synchronized，否则可能会出现name和address
* 不一致的情况。toString在本例中不声明为synchronized也没问题，
* 但对于多线程的public方法，必须声明为synchronized以保证线程安全
 */
public class Gate {
    private int counter = 0;
    private String name = "Nobody";
    private String address = "Nowhere";

    public synchronized void pass(String name, String address) {
        this.counter++;
        this.name = name;
        this.address = address;
        check();
    }

    public synchronized String toString() {
        return "No. " + counter + ": " + name + ", " + address;
    }

    private void check() {
        if(name.charAt(0) != address.charAt(0)) {
            System.out.println("****** BROKEN ******" + toString());
        }
    }
}
