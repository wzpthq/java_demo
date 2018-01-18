package study.wzp.zookeeper.lession01;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Zookeeper HelloWorld
 */

public class HelloZookeeper {

    /**
     * 1. Client向Server注册Watcher
     *
     * 1) new Zookeeper() ; 默认session都使用这个传入的watcher；
     * 2）其他的方法，会有两种类型一种是boolean watch, 还有一种就是传入Watcher
     * a、boolean watch：true：使用默认的watcher；false：不使用默认的watcher，如果没有替代的watcher，那么当事件通知时，不会有任何变化；
     * b、直接传入watcher: 那么就会替换默认的watcher；
     */

    ZooKeeper zooKeeper = null;

    List<ACL> acls = new ArrayList<>();

    public HelloZookeeper() throws IOException {

        zooKeeper = new ZooKeeper("localhost:2181", 3000, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println("path: " + event.getPath());
                System.out.println("type: " + event.getType());
                System.out.println("state: " + event.getState());
            }
        });

        // schema:id:permission
        // id(schema, id)
        // permission(Perms)
        acls.add(new ACL(ZooDefs.Perms.ALL, new Id("world", "anyone")));

    }


    /**
     * 测试创建zNode
     */
    @Test
    public void testCreate() {

        try {
            zooKeeper.create(
                    "/lession01",
                    "lession01".getBytes(),
                    acls,
                    CreateMode.PERSISTENT
            );
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试查询znode
     */
    @Test
    public void testGet() {
        try {
            Stat stat = new Stat();
            byte[] res = zooKeeper.getData("/lession01", true, stat);
            System.out.println("data:" + new String(res,"UTF-8") + ", stat:" + stat);

        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试设置（更新）zNode
     */
    @Test
    public void testSet() {

        try {
            // notice: version, 是期望修改的版本数据
            Stat stat = zooKeeper.setData("/lession01", "lession01".getBytes(),0);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * 测试删除zNode
     */
    @Test
    public void testDelete() {
        try {
            zooKeeper.delete("/lession01", 1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }
}
