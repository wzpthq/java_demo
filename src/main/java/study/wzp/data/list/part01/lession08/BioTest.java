package study.wzp.data.list.part01.lession08;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 这个没啥特别，默认实现都是
 */

public class BioTest {

    public final static String basePath = "/Users/zhiping.wangsh/workspace/java/data-structure/src/main/java/study/wzp/data/list/part01/lession08";

    /**
     * 文件内容复制
     */
    @Test
    public void testCopyFile() {

        String fileName = "/bio_src.txt";
        try(FileInputStream fis = new FileInputStream(basePath + fileName)) {
            byte[] buffer = new byte[fis.available()];
            int len = fis.read(buffer);
            FileOutputStream fos = new FileOutputStream(basePath + "/bio_dist.txt");
            fos.write(buffer);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用文件NIO实现文件复制
     *
     (
     */
    @Test
    public void testCopyFileWithNIO() {

        String fileName = "/bio_src.txt";
        try(FileInputStream fis = new FileInputStream(basePath + fileName)){

            FileOutputStream fos = new FileOutputStream(basePath + "/nio_dist.txt");

            // 先获取channel
            FileChannel inChannel = fis.getChannel();
            FileChannel outChannel = fos.getChannel();


            // 创建buffer
            ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024); // 1 KB

            while(inChannel.read(buffer) != -1) {

                // 进来就读取了一次数据, 可以先看看数据是否够了
                buffer.flip(); // 会将position=0，limit设置到上一次的position，实际就是读取的内容空间
                outChannel.write(buffer);

                // 在每次读取前，做一次清理，设置position=0,limit=capacity
                buffer.clear();
            }

            fos.flush();
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
