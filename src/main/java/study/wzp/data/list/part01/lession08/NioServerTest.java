package study.wzp.data.list.part01.lession08;

import javax.script.ScriptException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

public class NioServerTest {
    /**
     * 服务器
     */
    static class Server implements Runnable {

        private int port;

        private ServerSocketChannel serverSocketChannel;

        private Selector selector;

        private ByteBuffer cache = ByteBuffer.allocate(1024);

        public Server(int port) {
            this.port = port;
            init();
        }

        // 初始化
        private void init() {

            try {
                selector = Selector.open();
            } catch (IOException e) {
                System.out.println("Init Selector Exception: " + e);
            }


            try {
                serverSocketChannel = ServerSocketChannel.open();
                serverSocketChannel.socket().bind(new InetSocketAddress(port), 1024);
                serverSocketChannel.configureBlocking(false);

                // 只能绑定OP_ACCEPT
                serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            } catch (IOException e) {
                // e.printStackTrace();
                System.out.println("Init ServerSockt/Channel Exception: " + e);
            }


            System.out.println("启动服务器, 端口：" + port);

        }

        @Override
        public void run() {
            for (;;) {
                try {
                    selector.select();

                    Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
                    while (keys.hasNext()) {
                        SelectionKey key = keys.next();
                        keys.remove();
                        handleEvent(key);


                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void handleEvent(SelectionKey key) {

            if(key.isValid()) {

                if (key.isAcceptable()){
                    doAccept(key);

                } else if (key.isReadable()) {
                    doRead(key);
                }



            }
        }

        private void doAccept(SelectionKey key) {
            // 注意：accept的key是Server的，如果accept获取的就是client
            ServerSocketChannel channel = (ServerSocketChannel)key.channel();

            try {
                SocketChannel socketChannel = channel.accept();
                socketChannel.configureBlocking(false);
                // 获取客服端的ScoketChannel之后，注册可读事件
                socketChannel.register(selector, SelectionKey.OP_READ);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void doRead(SelectionKey key) {
            // 注意：accept的key是Server的，如果accept获取的就是client
            SocketChannel channel = (SocketChannel) key.channel();

            try {
                StringBuffer buffer = new StringBuffer();
                cache.clear();
                while(channel.read(cache) > 0) {
                    cache.flip();
                    byte[] bytes = new byte[cache.remaining()];
                    cache.get(bytes);

                    buffer.append(new String(bytes, "UTF-8"));

                    cache.clear();
                }

                String expression = buffer.toString();
                String result = (NioClientTest.Calculator.cal(expression)).toString();

                // 发送数据
                doWrite(channel, result);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ScriptException e) {
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

        private void doWrite(SocketChannel channel, String result) throws IOException {
            ByteBuffer buffer = ByteBuffer.allocate(result.getBytes().length);
            buffer.put(result.getBytes());
            buffer.flip();
            channel.write(buffer);

            System.out.println(channel.getLocalAddress() + ":" + result);
        }

        public static void main(String[] args) throws ScriptException, ClosedChannelException, InterruptedException {

            new Thread(new Server(6666)).start();

        }
    }
}
