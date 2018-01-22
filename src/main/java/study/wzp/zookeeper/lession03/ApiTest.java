package study.wzp.zookeeper.lession03;

import oracle.jvm.hotspot.jfr.ThreadStates;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

/**
 * 原生API的使用
 * 1、基本操作；
 * 2、异步创建连接；
 * 3、watcher注册使用即失效，如果要一直订阅变化，需要不断进行注册；
 * 4、异步操作基本操作；
 * 5、不支持递归创建节点;
 *
 * 主要test上述内容。
 *
 * 我们使用集群环境来操作
 *
 */
public class ApiTest {

    ZooKeeper zooKeeper;

    {
        String connectString = "127.0.0.1:2182,127.0.0.1:2183,127.0.0.1:2184";
        try {
            zooKeeper = new ZooKeeper(connectString, 30000, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    System.out.println("DefaultWatcher, Path: " + event.getPath() + ", State: " + event.getState() + ", EventType: " + event.getType());
                }
            });

            // 如果是同步创建连接，那么这里的连接状态应该是已连接，否则为正在连接中
            assert zooKeeper.getState() == ZooKeeper.States.CONNECTING;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建操作
     */
    @Test
    public void testCreate(){
        try {
            String nodeName = zooKeeper.create("/zkClusterCreate",
                    "create".getBytes(),
                    Arrays.asList(new ACL(ZooDefs.Perms.ALL, ZooDefs.Ids.ANYONE_ID_UNSAFE)),
                    CreateMode.EPHEMERAL
            );

            assert zooKeeper.exists("/zkClusterCreate", true) != null;

        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 异步创建操作
     */
    @Test
    public void testCreateAsync(){
        final String context = "CreateSync";
        zooKeeper.create(
                "/zkClusterCreateAsync",
                "createSync".getBytes(),
                Arrays.asList(new ACL(ZooDefs.Perms.ALL, ZooDefs.Ids.ANYONE_ID_UNSAFE)),
                CreateMode.EPHEMERAL, new AsyncCallback.StringCallback() {
                    /**
                     * 当创建节点成功后，会回调这个函数
                     * @param rc reason code, 根据这个返回的状态码，来确认是创建成功，还是其他的
                     * @param path zNode path
                     * @param ctx context object
                     * @param name zNode Name
                     */
                    @Override
                    public void processResult(int rc, String path, Object ctx, String name) {

                        if (rc == KeeperException.Code.OK.intValue()) { // 表示节点创建成功消息
                            assert path.equals("/zkClusterCreateAsync");
                            assert ctx.equals(context);
                            assert name.equals("/zkClusterCreateAsync");
                            System.out.println("Async Create Node success, rc: " + rc + ", path: " + path + ", ctx: " + ctx + ", name: " + name);
                        }
                    }
                }, context);

        try {
            assert zooKeeper.exists("/zkClusterCreateAsync", false) != null;
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试创建
     */
    @Test
    public void testGet() {
        try {
            zooKeeper.create("/zkClusterGet",
                    "zkClusterGet".getBytes(),
                    Arrays.asList(new ACL(ZooDefs.Perms.ALL, ZooDefs.Ids.ANYONE_ID_UNSAFE)),
                    CreateMode.EPHEMERAL);

            byte[] res = zooKeeper.getData("/zkClusterGet", true, null);

            assert new String(res, "UTF-8").equals("zkClusterGet");

        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试创建
     */
    @Test
    public void testGetAsync() {
        try {
            zooKeeper.create("/zkClusterGetAsync",
                    "zkClusterGetAsync".getBytes(),
                    Arrays.asList(new ACL(ZooDefs.Perms.ALL, ZooDefs.Ids.ANYONE_ID_UNSAFE)),
                    CreateMode.EPHEMERAL);

            zooKeeper.getData("/zkClusterGet", true, new AsyncCallback.DataCallback() {
                @Override
                public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
                    if (rc == KeeperException.Code.OK.intValue()) {
                        assert path.equals("/zkClusterGetAsync");
                        assert ctx == null;

                        try {
                            assert new String(data, "UTF-8").equals("zkClusterGetAsync");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, null);



        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试更新
     */
    @Test
    public void testSet() {

        try {
            zooKeeper.create("/zkClusterSet",
                    "zkClusterSet".getBytes(),
                    Arrays.asList(new ACL(ZooDefs.Perms.ALL, ZooDefs.Ids.ANYONE_ID_UNSAFE)),
                    CreateMode.EPHEMERAL);

            Stat stat = new Stat();
            byte[] res = zooKeeper.getData("/zkClusterSet", false, stat);
            assert new String(res, "UTF-8").equals("zkClusterSet");

            // 修改数据，为了防止修改错误，使用了版本号
            Stat stat1 = zooKeeper.setData("/zkClusterSet", "zkClusterSet-Str".getBytes(), stat.getVersion());

            // 修改之后，版本+1
            assert stat1.getVersion() == stat.getVersion() + 1;
            assert new String(zooKeeper.getData("/zkClusterSet", false, stat), "UTF-8").equals("zkClusterSet-Str");

        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }


    /**
     * 测试更新
     */
    @Test
    public void testSetAsync() {
        try {
            zooKeeper.create("/zkClusterSetSync", "zkClusterSync".getBytes(), Arrays.asList(new ACL(ZooDefs.Perms.ALL, ZooDefs.Ids.ANYONE_ID_UNSAFE)), CreateMode.EPHEMERAL);

            zooKeeper.setData("/zkClusterSetSync", "zkClusterSetSync-set".getBytes(), 0, new AsyncCallback.StatCallback(){

                @Override
                public void processResult(int rc, String path, Object ctx, Stat stat) {

                    if(rc == KeeperException.Code.OK.intValue()) {
                        assert path.equals("/zkClusterSetSync");
                        assert ctx == null;
                        assert stat.getVersion() == 1;
                    }

                }

            }, null);

        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试删除
     */
    @Test
    public void testDelete() {
        try {
            zooKeeper.create("/zkClusterDelete", "zkClusterDelete".getBytes(), Arrays.asList(new ACL(ZooDefs.Perms.ALL, ZooDefs.Ids.ANYONE_ID_UNSAFE)), CreateMode.EPHEMERAL);
            assert zooKeeper.exists("/zkClusterDelete", false) != null;

            zooKeeper.delete("/zkClusterDelete", 0);

            assert zooKeeper.exists("/zkClusterDelete", false) == null;

        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    /**
     * 测试删除
     */
    @Test
    public void testDeleteAsync() {
        try {
            zooKeeper.create("/zkClusterDeleteAsync", "zkClusterDeleteAsync".getBytes(), Arrays.asList(new ACL(ZooDefs.Perms.ALL, ZooDefs.Ids.ANYONE_ID_UNSAFE)), CreateMode.EPHEMERAL);
            assert zooKeeper.exists("/zkClusterDeleteAsync", false) != null;

            zooKeeper.delete("/zkClusterDeleteAsync", 0, new AsyncCallback.VoidCallback() {
                @Override
                public void processResult(int rc, String path, Object ctx) {
                    if(rc == KeeperException.Code.OK.intValue()){
                        assert path.equals("/zkClusterDeleteAsync");
                        assert ctx == null;
                    }
                }
            }, null);

            Thread.sleep(1000);

            assert zooKeeper.exists("/zkClusterDeleteAsync", false) == null;

        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    /**
     * 测试子节点
     */
    @Test
    public void testChildren() {

        try {

            // /zkClusterParent/zkClusterChildren
            if(zooKeeper.exists("/zkClusterParent", false) != null){
                zooKeeper.create("/zkClusterParent", "zkClusterParent".getBytes(), Arrays.asList(new ACL(ZooDefs.Perms.ALL, ZooDefs.Ids.ANYONE_ID_UNSAFE)), CreateMode.PERSISTENT);
            }

            if (zooKeeper.exists("/zkClusterParent/zkClusterChildren", false) != null) {
                zooKeeper.create("/zkClusterParent/zkClusterChildren", "zkClusterChildren".getBytes(), Arrays.asList(new ACL(ZooDefs.Perms.ALL, ZooDefs.Ids.ANYONE_ID_UNSAFE)), CreateMode.PERSISTENT);
            }

            if (zooKeeper.exists("/zkClusterParent/zkClusterChildren/c1", false) != null) {
                zooKeeper.create("/zkClusterParent/zkClusterChildren/c1", "zkClusterChildren-c1".getBytes(), Arrays.asList(new ACL(ZooDefs.Perms.ALL, ZooDefs.Ids.ANYONE_ID_UNSAFE)), CreateMode.PERSISTENT);
            }

            // 获取自节点
            List<String> childrens = zooKeeper.getChildren("/zkClusterParent", false);
            System.out.println(childrens);
            assert childrens.size() == 1;
            assert childrens.get(0).equals("zkClusterChildren");

        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试子节点
     */
    @Test
    public void testChildrenAsync() {
        try {

            // /zkClusterParent/zkClusterChildren
            if(zooKeeper.exists("/zkClusterParentAsync", false) != null){
                zooKeeper.create("/zkClusterParentAsync", "zkClusterParentAsync".getBytes(), Arrays.asList(new ACL(ZooDefs.Perms.ALL, ZooDefs.Ids.ANYONE_ID_UNSAFE)), CreateMode.PERSISTENT);
            }

            if (zooKeeper.exists("/zkClusterParentAsync/zkClusterChildrenAsync", false) != null) {
                zooKeeper.create("/zkClusterParentAsync/zkClusterChildrenAsync", "zkClusterChildrenAsync".getBytes(), Arrays.asList(new ACL(ZooDefs.Perms.ALL, ZooDefs.Ids.ANYONE_ID_UNSAFE)), CreateMode.PERSISTENT);
            }

            if (zooKeeper.exists("/zkClusterParentAsync/zkClusterChildrenAsync/c1", false) != null) {
                zooKeeper.create("/zkClusterParentAsync/zkClusterChildrenAsync/c1", "zkClusterChildren-c1".getBytes(), Arrays.asList(new ACL(ZooDefs.Perms.ALL, ZooDefs.Ids.ANYONE_ID_UNSAFE)), CreateMode.PERSISTENT);
            }

            // 获取自节点
            zooKeeper.getChildren("/zkClusterParent", false, new AsyncCallback.ChildrenCallback() {
                @Override
                public void processResult(int rc, String path, Object ctx, List<String> children) {
                    if (rc == KeeperException.Code.OK.intValue()) {
                        assert path.equals("/zkClusterParent");
                        assert ctx == null;
                        assert children.size() == 1;
                        assert children.get(0).equals("zkClusterChildrenAsync");
                    }
                }
            }, null);

            Thread.sleep(1000);

        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    @After
    public void afterClose() {
        try {
            zooKeeper.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
