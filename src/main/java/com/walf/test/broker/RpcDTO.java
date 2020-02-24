package com.walf.test.broker;

import java.io.Serializable;

public class RpcDTO implements Serializable {

    private String method;

    private String arg;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getArg() {
        return arg;
    }

    public void setArg(String arg) {
        this.arg = arg;
    }

    public String toString() {
        String res = "method: "+method+" arg: "+arg;
        return res;
    }
}
