package study.wzp.data.list.part01.lession08;

import org.junit.Test;

import java.nio.Buffer;
import java.nio.ByteBuffer;

public class NioTest {

    /**
     * 测试Buffer的mark属性
     *
     * mark实际就是标记的作用，默认mark=-1，如果标记了那么会设置mark=position
     * 那么使用reset时，就会重新回溯到position=mark
     */
    @Test
    public void testBufferOfMark() {

        // 分配10个字节
        ByteBuffer buffer = ByteBuffer.allocate(10);
        // mark=-1, position=0, limit=10, capacity=10
        System.out.println("init:" + buffer);

        /** 设置position */
        buffer.position(5); // 设置到第5个字节位置
        // mark=-1, position=5, limit=10, capacity=10
        System.out.println("position:" + buffer);

        /** mark标记 */
        buffer.mark();
        // mark=5, position=5, limit=10, capacity=10
        System.out.println("mark:" + buffer);

        /** reset */
        buffer.reset();
        // mark=-1, position=5, limit=10, capacity=10
        System.out.println("reset:" + buffer);

        /** rewind */
        buffer.rewind();  // 设置postion=0, mark=-1, 重绕
        // mark=-1, position=0, limit=5, capacity=10
        System.out.println("flip:" + buffer);

        /** flip */
        buffer.flip();
        // mark=-1, position=0, limit=5, capacity=10
        System.out.println("flip:" + buffer);

        /** clear */
        buffer.clear();
        // mark=-1, position=0, limit=10, capacity=10
        System.out.println("clear:" + buffer);

        // hasRemaining 做一个一个字节的遍历
        // if position < limit
        // buffer.compact(); 当数据未读完时，实际上就是取出position --- limit之间的数据的小ByteBuffer

    }

}
