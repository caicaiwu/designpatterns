package com.walf.test.broker;

import com.walf.test.broker.proxy.LocalProxy;
import com.walf.test.broker.proxy.RemodeProxy;

import java.io.IOException;

public class Consumer {

    IProvider provider = new LocalProxy();

    public String test1() {
        return provider.testApi("my");
    }

    public static void main(String[] args) throws IOException {
        Consumer consumer = new Consumer();
        String res = consumer.test1();
        System.out.println("Consumer get: "+res);
    }
}
