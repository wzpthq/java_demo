package study.wzp.data.list.part01.lession08;

import com.sun.org.apache.bcel.internal.generic.Select;
import sun.nio.ch.SelectorImpl;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * NIO的Selector测试
 *
 * 原理就是：
 *
 * 一个线程，可以通知支持多个Channel，每个channel其实就是一个tcp，
 *
 * 分别使用NIO和AIO实现一个Server。接收客户端的一个四则混合运算表达式，计算结果后，将其返回给Client。提交这2个代码，以及运行后的截图
 *
 */

public class NioClientTest {


    /**
     * 计算器
     */
    static class Calculator {

        private final static ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");

        public static Object cal(String expression) throws ScriptException {
            return engine.eval(expression);
        }

    }


    /**
     * 客服端
     */
    static class Client implements Runnable {

        private String ip;

        private int port;

        private SocketChannel socketChannel;

        private Selector selector;

        private ByteBuffer cache = ByteBuffer.allocate(1024);

        private volatile boolean isReady = false;

        private String message;

        public Client(String ip, int port) {
            this.ip = ip;
            this.port = port;
            init();
        }

        private void init() {

            try {
                selector = Selector.open();
            } catch (IOException e) {
                System.out.println("Init Socket Selector Exception: " + e);
            }

            try {
                socketChannel = SocketChannel.open(new InetSocketAddress(ip, port));
                socketChannel.configureBlocking(false);
                //socketChannel.register(selector, SelectionKey.OP_READ);
            } catch (IOException e) {
                System.out.println("init Socket Channel Exception: " + e);
            }



            //System.out.println(Thread.currentThread() + " : client started");

        }

        @Override
        public void run() {

            try {
                doConnect(); // 如果连接成功不做任何处理，否则注册连接事件
            } catch (IOException e) {
                e.printStackTrace();
            }

            for(;;) {
                try {
                    if (isReady) {
                        // 获取channel
                        selector.select();

                        // 获取事件集合
                        Iterator<SelectionKey> keys = selector.selectedKeys().iterator();

                        while(keys.hasNext()) {
                            SelectionKey key = keys.next();
                            keys.remove();
                            handleEvent(key);
                        }

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        // 处理事件
        private void handleEvent(SelectionKey key) {

            if (key.isValid()){ // 事件有效

                if (key.isConnectable()){ // 连接事件
                    doConnect(key);

                } else if(key.isReadable()) { // 读事件，来自服务端的消息
                    doRead(key);
                }
            }

        }

        public void sendMessage(String message) throws ClosedChannelException {
            this.message = message;
            this.isReady = true;

            // 设置读
            socketChannel.register(selector, SelectionKey.OP_READ);
            // 并写入数据
            doWrite();
        }



        public void reset() {
            isReady = false;
        }

        private void doConnect() throws IOException {
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
        }

        private void doConnect(SelectionKey key) {
            SocketChannel channel = (SocketChannel) key.channel();
            try {
                if(!channel.finishConnect()) {
                    System.exit(1);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void doRead(SelectionKey key) {
            SocketChannel channel = (SocketChannel) key.channel();

            try {
                StringBuffer buffer = new StringBuffer("");
                cache.clear();
                while(channel.read(cache) > 0){
                    cache.flip();
                    byte[] bytes = new byte[cache.remaining()];
                    cache.get(bytes);
                    buffer.append(new String(bytes, "UTF-8"));
                    cache.clear();
                }

                System.out.println(Thread.currentThread() + " : " + buffer.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    key.cancel();
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void doWrite() {
            try {
                cache.clear();
                cache.put(this.message.getBytes());
                cache.flip();
                socketChannel.write(cache);
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Send Message:" + this.message);
        }

    }



    public static void main(String[] args) throws ScriptException, ClosedChannelException, InterruptedException {



        Client client = new Client("localhost", 6666);
        new Thread(client).start();

        for (int i = 0; i < 10; i ++) {
            client.sendMessage("1 + 2 + 3 + " + i);
        }

    }
}
