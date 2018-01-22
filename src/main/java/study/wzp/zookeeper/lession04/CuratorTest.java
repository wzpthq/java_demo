package study.wzp.zookeeper.lession04;

import org.apache.curator.RetryPolicy;
import org.apache.curator.RetrySleeper;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CreateBuilder;
import org.apache.curator.framework.api.ExistsBuilder;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.tools.ant.types.resources.comparators.Exists;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.junit.After;
import org.junit.Test;

import java.util.Arrays;

/**
 * Apache Curator zookeeper开源框架的使用
 *
 * [1] 支持zookeeper同步创建连接 和 重连
 * [2] 支持一次注册Watcher，重复使用；
 * [3] 支持递归创建节点；
 * [4] Fluent Api 风格，说白了就是一堆Builder
 */
public class CuratorTest {

    CuratorFramework client;


    NodeCache nodeCache;

    PathChildrenCache childrenCache;

    TreeCache treeCache;



    {
        String connectString = "127.0.0.1:2182,127.0.0.1:2183,127.0.0.1:2184";

        // 支持重连，RetryPolicy
        // ExponentialBackoffRetry 每隔一秒中，重试一次，最多重试3次
        client = CuratorFrameworkFactory.newClient(connectString, new ExponentialBackoffRetry(1000, 3));
        client.start();

        // 注册NodeCache
        nodeCache = new NodeCache(client, "/curator");
        try {
            nodeCache.start(true);
            nodeCache.getListenable().addListener(() -> {
                System.out.println("Path:" + nodeCache.getCurrentData().getPath() + ", Data: " + nodeCache.getCurrentData().getData() + ", State: " + nodeCache.getCurrentData().getStat());
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 注册子节点
        childrenCache = new PathChildrenCache(client, "/curator", true);
        try {
            childrenCache.start();
            childrenCache.getListenable().addListener((f, e) -> {
                System.out.println(e.getType() + " - " + e.getData().getPath() + " - " + new String(e.getData().getData(),"UTF-8"));
            });
        } catch (Exception e) {
            e.printStackTrace();
        }



    }


    // 通过CreateBuilder来创建
    @Test
    public void testCreateNode() {

        try {

            client.create()
                    .creatingParentsIfNeeded() // 支持递归创建
                    .withMode(CreateMode.EPHEMERAL)
                    .withACL(Arrays.asList(new ACL(ZooDefs.Perms.ALL, ZooDefs.Ids.ANYONE_ID_UNSAFE)))
                    .forPath("/curator/create-tmp", "tmp".getBytes());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // DeleteBuilder
    @Test
    public void testDeleteNode() {

        try {
            client.create()
                    .creatingParentsIfNeeded() // 支持递归创建
                    .withMode(CreateMode.EPHEMERAL)
                    .withACL(Arrays.asList(new ACL(ZooDefs.Perms.ALL, ZooDefs.Ids.ANYONE_ID_UNSAFE)))
                    .forPath("/curator/create-delete", "delete".getBytes());

            // curator guaranteed 表示安全删除

            assert client.checkExists().forPath("/curator/create-delete") != null;

            /**
             * 解决操作成了，但是连接断了，没有返回给client的情况。
             */
            client.delete().guaranteed().withVersion(0).forPath("/curator/create-delete");

            assert client.checkExists().forPath("/curator/create-delete") == null;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // SetBuilder
    @Test
    public void testSetNode() {
        try {
            client.create()
                    .creatingParentsIfNeeded() // 支持递归创建
                    .withMode(CreateMode.EPHEMERAL)
                    .withACL(Arrays.asList(new ACL(ZooDefs.Perms.ALL, ZooDefs.Ids.ANYONE_ID_UNSAFE)))
                    .forPath("/curator/create-set", "set".getBytes());

            client.setData().withVersion(0).forPath("/curator/create-set", "set1".getBytes());

            byte[] res = client.getData().forPath("/curator/create-set");
            assert new String(res, "UTF-8").equals("set1");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // GetBuilder
    @Test
    public void testGet() {
        try {
            client.create()
                    .creatingParentsIfNeeded() // 支持递归创建
                    .withMode(CreateMode.EPHEMERAL)
                    .withACL(Arrays.asList(new ACL(ZooDefs.Perms.ALL, ZooDefs.Ids.ANYONE_ID_UNSAFE)))
                    .forPath("/curator/create-get", "get".getBytes());

            byte[] res = client.getData().forPath("/curator/create-get");
            assert new String(res, "UTF-8").equals("get");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 使用GuratorWatcher 或者 原生的Watcher都可以，如果使用原生的Watcher，那么和之前一样
    // 否则就不一样
    /**
     * 1, NodeCache 监听节点
     * 2, PathChildrenCache 监听子节点
     * 3, TreeCache  监听上述两者
     *
     * 需要引入：curator-recipes
     */
    @Test
    public void testRegistWatcher() throws InterruptedException {
        Thread.sleep(1000000);
    }

    @After
    public void close() {
        client.close();
    }

}
