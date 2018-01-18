package study.wzp.zookeeper.lession02;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Watcher机制，在zookeeper中，我们通过watcher机制来实现发布/订阅，已经机器之间的消息传递等；
 * 在这一节中，我们需要了解：
 * 1、如何注册Watcher；
 * 2、如何使用Watcher；
 * 3、Watcher机制实现；
 */

public class ZkWatcherTest {

    /**
     * Default Watcher, 在构建Zookeeper对象时传递的Watcher就是默认Watcher；
     * 会设置到ZKWatcherManager的defaultWatcher中
     */
    private class DefaultWatcher implements  Watcher {
        @Override
        public void process(WatchedEvent event) {
            System.out.println("path: " + event.getPath() + ", state: " + event.getState() + ", eventType: " + event.getType());
        }
    }


    List<ACL> acls = new ArrayList<>();

    ZooKeeper zooKeeper;

    {
        try {
            zooKeeper = new ZooKeeper("localhost:2181", 3000, new DefaultWatcher());

        } catch (IOException e) {
            e.printStackTrace();
        }

        acls.add(new ACL(ZooDefs.Perms.ALL, ZooDefs.Ids.ANYONE_ID_UNSAFE));


    }


    /**
     * 测试defaultWatcher的使用，这里的目的是测试连接成功时事件
     */
    @Test
    public void testDefaultWatcherForSyncConnected() {
        try {
            // 因为连接是异步创建的，因此我们sleep操作一下
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * 使用DefaultWatcher
     */
    @Test
    public void testDefaultWatcherForGetData() {
        try {
            zooKeeper.create("/lession_watcher", "lession_watcher".getBytes(), acls, CreateMode.PERSISTENT);
            Stat stat = new Stat();
            // watch=true, 表示使用DefaultWatcher处理/lession_watcher节点的变化
            // 第一次变化，会调用DefaultWatcher处理；
            // 第二次变化，不会接受消息；因为Watcher注册一次，只能一次使用
            // 注意getData只是关注/lession_watcher的变化，并不会关注其子节点的变化，如果需要关注，那么使用其他的函数
            byte[] res = zooKeeper.getData("/lession_watcher", true, stat);

            Thread.sleep(100000);

        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用自己定义的Watcher来处理事件，这里也是一次性的注册，
     */
    @Test
    public void testDefineWatcherGetData() {

        Stat stat = new Stat();
        try {
            zooKeeper.getData("/lession_watcher", new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    System.out.println("testDefineWatcherGetData <<< " + ",path: " + event.getPath() + ", state: " + event.getState() + ", eventType: " + event.getType());
                }

            }, stat);
            Thread.sleep(100000);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    class WatcherLoop implements Watcher {
        @Override
        public void process(WatchedEvent event) {
            System.out.println("testDefineWatcherGetDataLoop <<< " + ",path: " + event.getPath() + ", state: " + event.getState() + ", eventType: " + event.getType());

            /**
             * 如果要确保一直监听到事件变化，就需要每次都进行注册。
             */

            Watcher watcher = new WatcherLoop();
            try {
                zooKeeper.getData(event.getPath(), watcher, null);
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 一直监听事件(也就是确保每次结束操作后，都会注册一个)
     */
    @Test
    public void testDefineWatcherGetDataLoop() {

        Stat stat = new Stat();
        try {
            zooKeeper.getData("/lession_watcher", new WatcherLoop(), stat);
            Thread.sleep(100000);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
