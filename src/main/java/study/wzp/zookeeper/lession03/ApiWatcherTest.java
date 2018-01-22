package study.wzp.zookeeper.lession03;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Watcher相关的操作测试
 */
public class ApiWatcherTest {

    // 缺省的Watcher
    private class DefaultWatcher implements Watcher {

        @Override
        public void process(WatchedEvent event) {
            System.out.println("Path: " + event.getPath() + ", State: " + event.getState() + ", Type: " + event.getType());
        }
    }

    ZooKeeper zooKeeper;

    {
        String connectString = "127.0.0.1:2182,127.0.0.1:2183,127.0.0.1:2184";
        try {
            zooKeeper = new ZooKeeper(connectString, 30000, new DefaultWatcher());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // 只会响应一次变化，之后变化不会响应，因为Watcher是注册一次，就使用一次，如果想再次使用，就需要
    // 继续注册.
    @Test
    public void testWatcherGetUseDefault() {
        try {
            zooKeeper.getData("/test_watcher", true, null);
            Thread.sleep(20000);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 数据watcher
    private class DataWatcher implements Watcher {

        @Override
        public void process(WatchedEvent event) {

            // 已成功连接
            if (event.getState() == Event.KeeperState.SyncConnected) {
                switch (event.getType()) {
                    case None:
                        break; // 表示连接成功，不做处理；
                    case NodeDataChanged:
                        // 打印数据，并继续注册watcher
                        try {
                            byte[] res = zooKeeper.getData(event.getPath(), new DataWatcher(), null);
                            System.out.println(new String(res, "UTF-8"));
                        } catch (KeeperException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    default:
                        break;
                }


            }
        }
    }

    @Test
    public void testWatcherGetUseDefine() {
        try {
            zooKeeper.getData("/test_watcher", new DataWatcher(), null);
            Thread.sleep(2000000);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



}
