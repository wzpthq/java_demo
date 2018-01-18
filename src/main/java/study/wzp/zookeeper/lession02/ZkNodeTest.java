package study.wzp.zookeeper.lession02;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * znode相关内容测试
 *
 * 总结一点
 *
 */
public class ZkNodeTest {

    ZooKeeper zooKeeper;

    List<ACL> acls = new ArrayList<>();

    {
        try {
            // zookeeper的创建连接是异步的过程，这个我们后续的章节中再做详细介绍和测试
            zooKeeper = new ZooKeeper("localhost:2181", 3000, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    System.out.println("path: " + event.getPath());
                    System.out.println("keeperState: " + event.getState());
                    System.out.println("eventType: " + event.getType());
                }
            });

            acls.add(new ACL(ZooDefs.Perms.ALL, new Id("world", "anyone")));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 测试创建临时节点
     */
    @Test
    public void testCreateEmpNode() {
        try {
            String res = zooKeeper.create("/lession02_tmp", "/lession02_tmp_test".getBytes(), acls, CreateMode.EPHEMERAL);
            assert res.equals("/lession02_tmp");
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试创建临时序列节点
     */
    @Test
    public void testCreateEmpSeqNode() {

        try {
            String nodeName = zooKeeper.create(
                    "/lession02_tmp_seq",
                    "lession02_tmp_seq_test".getBytes(),
                    acls,
                    CreateMode.EPHEMERAL_SEQUENTIAL);

            assert !nodeName.equals("/lession02_tmp_seq");

        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * 创建持久化节点
     */
    @Test
    public void testCreatePresistNode() {
        try {
            String nodeName = zooKeeper.create("/lession02_pr", "lession02_pr_test".getBytes(), acls, CreateMode.PERSISTENT);
            assert nodeName.equals("/lession02_pr");
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ;
    }

    /**
     * 创建持久化序列节点
     */
    @Test
    public void testCreatePresistSeqNode() {
        try {
            String nodeName = zooKeeper.create("/lession02_pr_seq", "lession02_pr_seq_test".getBytes(), acls, CreateMode.PERSISTENT_SEQUENTIAL);
            assert !nodeName.equals("/lession02_pr_seq");
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试异步创建节点
     */
    @Test
    public void testAsynCreateNode() {

        // 1.上下文对象，如果我们期望将一些数据传递到prcessResult中处理，那么可以通过这种方式传递；
        // 2.或者使用final变量，也是可以使用的。
        String ctx = "测试上下文对象";
        zooKeeper.create("/lession02_async", "lession02_async_test".getBytes(), acls, CreateMode.PERSISTENT, new AsyncCallback.StringCallback() {

            // 当节点创建成功时，会回调这个函数
            @Override
            public void processResult(int rc, String path, Object ctx, String name) {
                System.out.println("rc:" + rc + ",path:" + path + ",ctx:"+ ctx + ",name:" + name);
                // 表示创建成功
                if (rc == KeeperException.Code.OK.intValue() ) {
                    assert path.equals("/lession02_async");
                    assert ctx.equals("测试上下文对象");
                    assert name.equals("/lession02_async");
                }

            }
        }, ctx);

        // 如果我们这里马上检查节点是否存在，可能会是不存在，如果节点创建完之前我们就执行了检查节点是否存在，那么
        // 就会出现节点不存在，这也就是异步的问题。

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
