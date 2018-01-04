package study.wzp.data.list.part01.lession08;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.Future;

/**
 * AIO 异步IO，与NIo的区别在于，NIO是准备好了，在获取事件操作；而AIO是事件完成了回调通知
 */

public class AioServerTest {

    /**
     * 计算器
     */
    static class Calculator {

        private final static ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");

        public static Object cal(String expression) throws ScriptException {
            return engine.eval(expression);
        }

    }

    static class Server implements Runnable {

        private int port;

        private AsynchronousServerSocketChannel asyncServerSocketChannel;

        public Server(int port) {
            this.port = port;
            init();
        }

        public void init() {

            try {
                asyncServerSocketChannel = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(port));
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Server start....");

        }

        @Override
        public void run() {

            while(true) {
                asyncServerSocketChannel.accept(this, new AcceptHandler());
            }

        }

        public AsynchronousServerSocketChannel getAsyncServerSocketChannel() {
            return asyncServerSocketChannel;
        }
    }

    // 处理accept 操作
    static class AcceptHandler implements CompletionHandler<AsynchronousSocketChannel, Server> {

        @Override
        public void completed(AsynchronousSocketChannel result, Server attachment) {
            attachment.getAsyncServerSocketChannel().accept(attachment, this);
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            result.read(buffer, buffer, new ReadHandler(result));
        }

        @Override
        public void failed(Throwable exc, Server attachment) {

        }
    }

    // 处理读操作
    static class ReadHandler implements CompletionHandler<Integer, ByteBuffer> {

        private AsynchronousSocketChannel channel;

        public ReadHandler(AsynchronousSocketChannel channel) {
            this.channel = channel;
        }

        @Override
        public void completed(Integer result, ByteBuffer attachment) {
            attachment.flip();
            byte[] bytes = new byte[attachment.remaining()];
            attachment.get(bytes);
            try {
                String r = Calculator.cal(new String(bytes, "UTF-8")).toString();
                // 计算完了写操作
                doWrite(r);
            } catch (ScriptException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void failed(Throwable exc, ByteBuffer attachment) {

        }

        private void doWrite(String r) {
            ByteBuffer buffer = ByteBuffer.allocate(r.getBytes().length);
            buffer.put(r.getBytes());
            buffer.flip();
            channel.write(buffer);
        }
    }

}
