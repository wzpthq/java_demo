package study.wzp.zookeeper.lession02;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

/**
 * Zookeeper Acl
 * 格式： Schema:id:permission
 * schema: world, auth, digest, ip, super
 * id: anyone,username:password,ip
 * permission: c(create)r(read)d(delete)w(write)a(admin)
 */

public class ZkAclTest {

    ZooKeeper zooKeeper;

    {
        try {
            zooKeeper = new ZooKeeper("localhost:2181", 3000, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    System.out.println("path: " + event.getPath());
                    System.out.println("keeperState: " + event.getState());
                    System.out.println("eventType: " + event.getType());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testGetAcl() {

        List<ACL> acls = Arrays.asList(new ACL(ZooDefs.Perms.ALL, ZooDefs.Ids.ANYONE_ID_UNSAFE));
        try {
            zooKeeper.create("/acl_node", "test".getBytes(), acls, CreateMode.EPHEMERAL);
            List<ACL> aclNodeAcls = zooKeeper.getACL("/acl_node", null);

            ACL acl = aclNodeAcls.get(0);

            // crdwa
            assert acl.getPerms() == ZooDefs.Perms.ALL && acl.getId().getScheme().equals("world") && acl.getId().getId().equals("anyone");

        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void testSetAcl() {
        List<ACL> acls = Arrays.asList(new ACL(ZooDefs.Perms.ALL, ZooDefs.Ids.ANYONE_ID_UNSAFE));
        try {
            Stat stat = new Stat();
            if ((stat = zooKeeper.exists("/acl_node", true)) == null) {
                zooKeeper.create("/acl_node", "test".getBytes(), acls, CreateMode.PERSISTENT);

                List<ACL> aclNodeAcls = zooKeeper.getACL("/acl_node", null);
                ACL acl = aclNodeAcls.get(0);
                // crdwa
                assert acl.getPerms() == ZooDefs.Perms.ALL && acl.getId().getScheme().equals("world") && acl.getId().getId().equals("anyone");

            }
            Stat stat1 = zooKeeper.setACL("/acl_node", Arrays.asList(new ACL(ZooDefs.Perms.WRITE | ZooDefs.Perms.ADMIN | ZooDefs.Perms.READ , ZooDefs.Ids.ANYONE_ID_UNSAFE)), stat.getAversion());
            List<ACL> zAcls = zooKeeper.getACL("/acl_node", null);

            ACL acl = zAcls.get(0);
            System.out.println(acl);
            assert acl.getPerms() == (ZooDefs.Perms.WRITE | ZooDefs.Perms.ADMIN | ZooDefs.Perms.READ) && acl.getId().getScheme().equals("world") && acl.getId().getId().equals("anyone");

        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAuthAcl() {
        try {
            Stat stat = new Stat();
            if ((stat = zooKeeper.exists("/acl_node", true)) == null) {
                zooKeeper.create("/acl_node", "test".getBytes(), Arrays.asList(new ACL(ZooDefs.Perms.ALL, ZooDefs.Ids.ANYONE_ID_UNSAFE)), CreateMode.PERSISTENT);
                zooKeeper.getACL("/acl_node", stat);
            }

            ACL acl = new ACL(ZooDefs.Perms.ALL, new Id("auth", "admin:admin"));

            // 授权
            zooKeeper.addAuthInfo("digest", "admin:admin".getBytes());

            // 在设置权限；
            zooKeeper.setACL("/acl_node", Arrays.asList(acl), stat.getAversion());

            byte[] res = zooKeeper.getData("/acl_node", false, stat);

            assert new String(res, "UTF-8").equals("test");

        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDigestAcl() {
        try {
            Stat stat = new Stat();
            if ((stat = zooKeeper.exists("/acl_node_digest", true)) == null) {
                zooKeeper.create("/acl_node_digest", "test".getBytes(), Arrays.asList(new ACL(ZooDefs.Perms.ALL, ZooDefs.Ids.ANYONE_ID_UNSAFE)), CreateMode.PERSISTENT);
                zooKeeper.getACL("/acl_node_digest", stat);
            }

            String idPassword = DigestAuthenticationProvider.generateDigest("admin:admin");

            ACL acl = new ACL(ZooDefs.Perms.ALL, new Id("digest", idPassword));

            // 在设置权限；
            stat = zooKeeper.setACL("/acl_node_digest", Arrays.asList(acl), stat.getAversion());

            // 授权
            zooKeeper.addAuthInfo("digest", "admin:admin".getBytes());

            byte[] res = zooKeeper.getData("/acl_node_digest", false, stat);

            assert new String(res, "UTF-8").equals("test");

        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testIpAcl() {
        try {
            Stat stat = new Stat();
            if ((stat = zooKeeper.exists("/acl_node_ip", true)) == null) {
                zooKeeper.create("/acl_node_ip", "test".getBytes(), Arrays.asList(new ACL(ZooDefs.Perms.ALL, ZooDefs.Ids.ANYONE_ID_UNSAFE)), CreateMode.PERSISTENT);
                zooKeeper.getACL("/acl_node_ip", stat);
            }

            ACL acl = new ACL(ZooDefs.Perms.ALL, new Id("ip", "127.0.0.1"));

            // 在设置权限；
            stat = zooKeeper.setACL("/acl_node_ip", Arrays.asList(acl), stat.getAversion());

            byte[] res = zooKeeper.getData("/acl_node_ip", false, stat);
            assert new String(res, "UTF-8").equals("test");

        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSuperAcl() {

    }

}
