package com.walf.test.broker.proxy;

import com.walf.test.broker.IProvider;
import com.walf.test.broker.RpcDTO;
import com.walf.test.broker.niotcp.Client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class LocalProxy implements IProvider {

    @Override
    public String testApi(String arg) {
        String returnValue = "";
        try {
           // StackTraceElement ss[] = Thread.currentThread().getStackTrace();
            String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
            System.out.println(methodName);
            RpcDTO rpcDTO = new RpcDTO();
            rpcDTO.setMethod(methodName);
            rpcDTO.setArg(arg);

            ObjectOutputStream oos = null;
            ByteArrayOutputStream baos = null;

            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(rpcDTO);

            byte[] bytes = baos.toByteArray();

            Client client = new Client(9911);
            returnValue = client.sendMsg(bytes);
        } catch (Exception e) {
        }

        return returnValue;
    }

}
