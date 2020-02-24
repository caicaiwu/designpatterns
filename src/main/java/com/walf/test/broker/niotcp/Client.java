package com.walf.test.broker.niotcp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
public class Client {
    // 信道选择器
    private Selector selector;
    // 与服务器通信的信道
    SocketChannel socketChannel;

    private String returnValue;

    public Client(int port)throws IOException{
        selector = Selector.open();
        socketChannel = SocketChannel.open(new InetSocketAddress(InetAddress.getLocalHost(),port));
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
        // 启动读取线程
        new Thread(new ClientReadThread()).start();
    }
    //发送字符串到服务器
    public String sendMsg(byte[] bytes) throws IOException{
        ByteBuffer writeBuffer = ByteBuffer.wrap(bytes);
        socketChannel.write(writeBuffer);

        try {
            Thread.sleep(5000);
        } catch (Exception e) {
        }

        return returnValue;
    }


    class ClientReadThread implements Runnable{
        public void run() {
            try {
                while (selector.select() > 0) {
                    // 遍历每个有可用IO操作Channel对应的SelectionKey
                    for (SelectionKey sk : selector.selectedKeys()) {
                        // 如果该SelectionKey对应的Channel中有可读的数据
                        if (sk.isReadable()) {
                            // 使用NIO读取Channel中的数据
                            SocketChannel sc = (SocketChannel) sk.channel();
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            sc.read(buffer);
                            buffer.flip();
                            // 将字节转化为为UTF-16的字符串
                            String receivedString = Charset.forName("UTF-16").newDecoder().decode(buffer).toString();

                            returnValue = receivedString;
                            // 控制台打印出来
                            System.out.println("接收到来自服务器" + sc.socket().getRemoteSocketAddress() + "的信息:" + receivedString);
                            // 为下一次读取作准备
                            sk.interestOps(SelectionKey.OP_READ);
                        }
                        // 删除正在处理的SelectionKey
                        selector.selectedKeys().remove(sk);
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /*public static void main(String[] args) throws IOException{
        Client client = new Client(9911);
        client.sendMsg("你好!Nio!");
    }*/
}
