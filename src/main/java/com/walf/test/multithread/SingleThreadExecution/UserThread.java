package com.walf.test.multithread.SingleThreadExecution;

public class UserThread extends Thread {
    private final Gate gate;
    private final String myname;
    private final String myaddress;

    public UserThread(Gate gate, String myname, String myaddress) {
        this.gate = gate;
        this.myname = myname;
        this.myaddress = myaddress;
    }

    public void run() {
        System.out.println(myaddress + " BEGIN");
        while (true) {
            gate.pass(myname, myaddress);
            System.out.println(myaddress + " TEST: " + gate.toString());
        }
    }
}
