package com.walf.test.broker.niotcp;


import com.walf.test.broker.IProvider;
import com.walf.test.broker.RpcDTO;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
public class Server{
    private final Selector selector;
    private final ServerSocketChannel serverSocketChannel;

    IProvider provider;

    public Server(int port, IProvider provider) throws IOException{
        // 创建选择器
        selector = Selector.open();
        // 打开监听信道
        serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress adress = new InetSocketAddress(InetAddress.getLocalHost(),port);
        //与本地端口绑定
        serverSocketChannel.socket().bind(adress);
        // 设置为非阻塞模式
        serverSocketChannel.configureBlocking(false);
        // 注册选择器.并在注册过程中指出该信道可以进行Accept操作
        serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);

        this.provider = provider;
    }
    public void start() {
        System.out.println("the server is started......");
        while (true) {
            try {
                int nKeys = selector.select();
                if (nKeys > 0){
                    // selectedKeys()中包含了每个准备好某一I/O操作的信道的SelectionKey
                    Set<SelectionKey> scSet = selector.selectedKeys();
                    Iterator<SelectionKey> iter = scSet.iterator();
                    while (iter.hasNext()) {
                        SelectionKey key = (SelectionKey) iter.next();
                        iter.remove();
                        dispatch(key);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void dispatch(SelectionKey key) {
        // 有客户端连接请求时
        //if ((key.readyOps() & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT) {
        if (key.isAcceptable()) {
            try {
                System.out.println("Key is acceptable");
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                SocketChannel socket = (SocketChannel) ssc.accept();
                socket.configureBlocking(false);
                socket.register(selector, SelectionKey.OP_READ);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 从客户端读取数据
        //else if ((key.readyOps() & SelectionKey.OP_READ) == SelectionKey.OP_READ) {
        else if (key.isReadable()) {
            System.out.println("the key is readable");
            // new Thread(new ReadeHandler(key)).start();
            try {
                SocketChannel socket = (SocketChannel) key.channel();
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                int bytesRead = socket.read(buffer);
                if (bytesRead > 0) {
                    buffer.flip();
                    // 将字节转化为为UTF-16的字符串
                    //String receivedString = Charset.forName("UTF-16").newDecoder().decode(buffer).toString();

                    byte[] bytes = new byte[buffer.remaining()];
                    buffer.get(bytes);

                    ByteArrayInputStream bais = null;
                    bais = new ByteArrayInputStream(bytes);
                    ObjectInputStream ois = new ObjectInputStream(bais);

                    RpcDTO rpcDTO = (RpcDTO)ois.readObject();


                    // 控制台打印出来
                    System.out.println("接收到来自"
                            + socket.socket()
                            .getRemoteSocketAddress()
                            + "的信息:" + rpcDTO.toString());

                    Method method2 = provider.getClass().getMethod(rpcDTO.getMethod(), Class.forName("java.lang.String"));
                    String sendString = (String)method2.invoke(provider, rpcDTO.getArg());
                    //String sendString = provider.testApi("te");

                    // 准备发送的文本
                    //String sendString = "你好,客户端. @"
                    //        + new Date().toString() + "，已经收到你的信息:"
                    //        + receivedString;

                    buffer = ByteBuffer.wrap(sendString
                            .getBytes("UTF-16"));
                    socket.write(buffer);
                    // 设置为下一次读取或是写入做准备
                    key.interestOps(SelectionKey.OP_READ);
                }
            } catch (Exception e) {
                //客户端断开连接，所以从Selector中取消注册
                key.cancel();
                if(key.channel() != null)
                    try {
                        key.channel().close();
                        System.out.println("the client socket is closed!");
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
            }
        }
        // 客户端可写时
        else if (key.isWritable()) {
            System.out.println("tHe key is writable");
            //new Thread(new WriteHandler(key)).start();
            //do something
        }
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server(9911, null);
        server.start();
    }
}