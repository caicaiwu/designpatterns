package com.walf.test.broker.proxy;

import com.walf.test.broker.Provider;
import com.walf.test.broker.IProvider;
import com.walf.test.broker.niotcp.Server;

import java.io.IOException;

public class RemodeProxy {

    private IProvider provider = new Provider();

    public void start() {

        try {
            Server server = new Server(9911, provider);
            server.start();

        } catch (Exception e) {

        }
    }

    public static void main(String[] args) throws IOException {
        RemodeProxy remodeProxy = new RemodeProxy();
        remodeProxy.start();
    }
}
