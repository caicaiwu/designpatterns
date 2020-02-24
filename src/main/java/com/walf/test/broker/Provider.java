package com.walf.test.broker;

import com.walf.test.broker.IProvider;

public class Provider implements IProvider {

    @Override
    public String testApi(String arg) {
        System.out.println("this is provider api, arg is: " + arg);
        return "P " + arg;
    }

}
