# zookeeper 学习

## lession01：zookeeper基本介绍

```text
zookeeper：是一个高性能、分布式、应用协调服务；可以做：同步，配置管理、集群管理、名空间等。
```
* 疑问
```text
1、zookeeper如何协调分布式系统；（多节点一起完成一个动作）；
例如：
集群成员管理(例如，自动发现集群成员加入和退出等)
锁，例如分布式锁的实现；
选主，集群管理的主从情况，选择主节点
同步，例如：数据一致性，数据发生变化的通知，
发布/订阅，也是数据同步的一种方式，可以用来实现配置及时修改，及时变化通知到其
他的节点；

publisher --- publish message --> Zk Server <------ Watch ------\ 
                                           \                     \
                                            \-----Update Notice--> Client
当订阅者通过watch订阅数据之后，数据发生变化通知Client，之间建立的连接是长连接。

```

* zookeeper 结构
```text

是一个树形结构，每个节点叫做zNode,每个zNode都有数据（byte[]）,也可以有子节点
[1] 节点路径：
      /
      |
     Zoo
  /   |    \
Duck  Goat Cow

例如：/Zoo/Duck 节点

[2] 集群角色
leader:为客户提供读写服务
follower:提供读服务，所有写服务都转交给leader角色，参与选举；
observer:提供读服务，不参与选举过程，一般是为了增强zk集群的读请求并发能力；

[3] 会话

[4] 数据节点(ZNode) 
不是机器的意思，是一个数据节点存储数据的地方。
1、持久性节点：会存储在ZNode上；
2、临时节点：与session生命周期一致；
3、顺序节点：创建节点时，会增加一个整形字段；

[5] 版本
version: zNode版本；
Cversion: 子节点的版本；
Aversion: 当前Znode的acl（访问控制）版本；

[6] Watcher
作用于zNode节点上，用于事件通知。
```
* 

* 发布/订阅
```text

```
* 名服务(name service)

```text

```
* 分布式协调通知
```text

```

* 集群管理
```text
```

* Master选举
```text

```

* 分布式锁
```text

```

* 分布式队列
```text

```
### 单机zookeeper安装 
* 下载zookeeper
```text
url: http://zookeeper.apache.org/
```
* 安装
```text
解压：zookeeper-**.tar.gz 文件 
```

* 修改配置
```text
文件：conf/zoo.cfg

tickTime=2000 客户端与zk之间的心跳检查间隔（session）
dataDir=/tmp/zookeeper 数据节点数据存放的文件目录
clientPort=2181 client访问的端口号


```

* 启动zookeeper server
```text
zkServer.sh start
```

* client 连接
```text
zkCli.sh -server 127.0.0.1:2181
```

* 创建一个数据节点
```text
[zk: localhost:2181(CONNECTED) 2] create /zk_test test_str
Created /zk_test
[zk: localhost:2181(CONNECTED) 7] ls /
[zookeeper, zk_test]
```
* 查看数据节点的数据
```text
[zk: localhost:2181(CONNECTED) 8] get /zk_test
test_str
cZxid = 0x4
ctime = Mon Jan 15 18:00:50 CST 2018
mZxid = 0x4
mtime = Mon Jan 15 18:00:50 CST 2018
pZxid = 0x4
cversion = 0
dataVersion = 0
aclVersion = 0
ephemeralOwner = 0x0
dataLength = 8
numChildren = 0
```

* 修改数据节点数据
```text
[zk: localhost:2181(CONNECTED) 9] set /zk_test test02
cZxid = 0x4
ctime = Mon Jan 15 18:00:50 CST 2018
mZxid = 0x5
mtime = Mon Jan 15 18:04:03 CST 2018
pZxid = 0x4
cversion = 0
dataVersion = 1
aclVersion = 0
ephemeralOwner = 0x0
dataLength = 6
numChildren = 0

[zk: localhost:2181(CONNECTED) 10] get /zk_test
test02
cZxid = 0x4
ctime = Mon Jan 15 18:00:50 CST 2018
mZxid = 0x5
mtime = Mon Jan 15 18:04:03 CST 2018
pZxid = 0x4
cversion = 0
dataVersion = 1             {dataVersion修改了一次，+1}
aclVersion = 0
ephemeralOwner = 0x0
dataLength = 6
numChildren = 0
```

* 删除数据节点
```text
[zk: localhost:2181(CONNECTED) 12] delete /zk_test
[zk: localhost:2181(CONNECTED) 13] ls /
[zookeeper]
```

## lession02 

### 创建数据节点

```text
在zk中，节点并不是机器节点的意思，而是一个逻辑概念，一个树形结构，形式是路径形式来表示。
例如：
根目录 /
zk_test节点目录 /zk_test

```

#### 临时节点
```text
生命周期与session一致的节点，通过create -e <zNode> <data> 来创建，如果session销毁了，就会删除该节点。
```

```text
client1:

[zk: localhost:2181(CONNECTED) 20] create -e /znode_test node_test
Created /znode_test
[zk: localhost:2181(CONNECTED) 21] ls /
[zookeeper, znode_test, zk_test]
[zk: localhost:2181(CONNECTED) 22]

client2:
[zk: localhost:2181(CONNECTED) 16] ls /
[zookeeper, znode_test, zk_test]

当client1未销毁时，znode_test时可以查看到的，如果此时关闭client1，client2就无法查看到了，因为已经删除了
client1 close
2018-01-17 14:07:29,089 - INFO  [main:ZooKeeper@544] - Session: 0x160f93ed19f0010 closed
[zk: localhost:2181(CLOSED) 23] 2018-01-17 14:07:29,089 - INFO  [main-EventThread:ClientCnxn$EventThread@516] - EventThread shut down

client2 
[zk: localhost:2181(CONNECTED) 17] ls /
[zookeeper, zk_test]

```

#### 持久化节点
```text
会存储在zk的文件系统上，create <zNode> <data>

[client1]
[zk: localhost:2181(CONNECTED) 27] create /znode_p node_test
Created /znode_p

[client2]
[zk: localhost:2181(CONNECTED) 18] ls /
[zookeeper, znode_p, zk_test]

[client1]
[zk: localhost:2181(CONNECTED) 28] close
2018-01-17 14:11:34,754 - INFO  [main:ZooKeeper@544] - Session: 0x160f93ed19f0012 closed
[zk: localhost:2181(CLOSED) 29] 2018-01-17 14:11:34,754 - INFO  [main-EventThread:ClientCnxn$EventThread@516] - EventThread shut down

[client2]
[client2]
[zk: localhost:2181(CONNECTED) 18] ls /
[zookeeper, znode_p, zk_test]

不会因为session关闭，而销毁；
```

#### 序列节点
```text
顾名思义，就是创建的节点会增加一个整型序号。
```
* 临时序号节点
```text

create -s -e <zNode> <data>

[client1]
[zk: localhost:2181(CONNECTED) 30] create -s -e /znode_se se
Created /znode_se0000000012

[client2]
[zk: localhost:2181(CONNECTED) 20] ls /
[zookeeper, znode_p, znode_se0000000012, zk_test]

[client1]
[zk: localhost:2181(CONNECTED) 31] close
2018-01-17 14:17:18,305 - INFO  [main:ZooKeeper@544] - Session: 0x160f93ed19f0013 closed
[zk: localhost:2181(CLOSED) 32] 2018-01-17 14:17:18,305 - INFO  [main-EventThread:ClientCnxn$EventThread@516] - EventThread shut down

[client2]
[zk: localhost:2181(CONNECTED) 21] ls /
[zookeeper, znode_p, zk_test]

在创建的节点上，自动增加了一个整数型后缀。

```

 * 持久化序号节点
 
```text

create -s <zNode> <data>

[client1]
[zk: localhost:2181(CONNECTED) 33] create -s /znode_s s
Created /znode_s0000000014

[client2]
[zk: localhost:2181(CONNECTED) 22] ls /
[znode_s0000000014, zookeeper, znode_p, zk_test]

[client1]
2018-01-17 14:20:22,033 - INFO  [main:ZooKeeper@544] - Session: 0x160f93ed19f0014 closed
[zk: localhost:2181(CLOSED) 36] 2018-01-17 14:20:22,033 - INFO  [main-EventThread:ClientCnxn$EventThread@516] - EventThread shut down

[client2]
[zk: localhost:2181(CONNECTED) 23] ls /
[znode_s0000000014, zookeeper, znode_p, zk_test]

```

### Watcher机制

```text
信息发生变更，如何进行通知，zk是通过Watcher机制来实现的发布/订阅.
```
* Questions
```
1、客户端如何与zk服务器建立watch关系；
2、zk服务器发生变化如何通知客户端；
```
* 客户端向服务端注册Watcher

```text
1. 设置默认的Watcher
new Zookeeper(connectString, sessionTimeouts, Watcher)
这个watcher是默认的watcher，会存储到ZKWatchManager.defaultWatcher中；

2. 其他设置watcher
例如：getData, getChildren等，都有watcher参数，
a、第一种方式，使用默认的watcher;
b、第二种方式，传递定义的Watcher，不使用defaultWatcher

```

* 服务端发送消息到客户端

```text
服务端的变更了，并不会发送变更数据到客户端，而是发送变更事件类型到客户端。
如果客户端需要去获取数据，那么需要重新获取；
```

* 处理变更消息

```text
变更消息封装到了WatcherEvent中，分为：
1、KeeperState: 表示连接状态
例如：SyncConnected, Expired等；表示的是客户端与服务端的连接情况；
2、EventType: 表示事件类型；
例如：NodeCreated, NodeDeleted, NodeDataChanged, NodeChildrenChanged
默认是:None。

```

###  ACL

```text
Zookeeper的权限控制结构，

[设置结构]
schema:id:permission

[schema]：检测策略
[id]:对象（可以是IP或者用户）
[permission]: 权限，c(create)r(read)d(delete)w(write)a(admin) 

```

* 设置节点权限

```text
[******************** client1 设置权限]
setAcl /acl_node world:anyone:r
cZxid = 0x8f
ctime = Thu Jan 18 15:09:32 CST 2018
mZxid = 0x8f
mtime = Thu Jan 18 15:09:32 CST 2018
pZxid = 0x8f
cversion = 0
dataVersion = 0
aclVersion = 1
ephemeralOwner = 0x160f93ed19f002c
dataLength = 3
numChildren = 0
[zk: localhost:2181(CONNECTED) 73] getAcl /acl_node
'world,'anyone
: r

[********************** client2 修改]
[zk: localhost:2181(CONNECTED) 17] set /acl_node test
Exception in thread "main" org.apache.zookeeper.KeeperException$NoAuthException: KeeperErrorCode = NoAuth for /acl_node
	at org.apache.zookeeper.KeeperException.create(KeeperException.java:104)
	at org.apache.zookeeper.KeeperException.create(KeeperException.java:42)
	at org.apache.zookeeper.ZooKeeper.setData(ZooKeeper.java:1044)
	at org.apache.zookeeper.ZooKeeperMain.processZKCmd(ZooKeeperMain.java:686)
	at org.apache.zookeeper.ZooKeeperMain.processCmd(ZooKeeperMain.java:581)
	at org.apache.zookeeper.ZooKeeperMain.executeLine(ZooKeeperMain.java:353)
	at org.apache.zookeeper.ZooKeeperMain.run(ZooKeeperMain.java:311)
	at org.apache.zookeeper.ZooKeeperMain.main(ZooKeeperMain.java:270)

```


* 查看节点权限

```text
[zk: localhost:2181(CONNECTED) 71] getAcl /acl_node
'world,'anyone
: cdrwa

```

* 修改节点权限
```text
修改节点权限，必须确保节点权限包含a，或者是super权限。

```

* username:password

```text
通常我们会设置用户名和密码来访问，而不是world操作。
有2种方式：
1. schema=auth, auth:username:password:permission 用户和密码都有明文
2. schema=digest, digest:username:password:permission 密码是密文

```

```text
auth:
[======================== client1: 设置]
[zk: localhost:2181(CONNECTED) 2] setAcl /acl_auth auth:admin:admin:crwda
cZxid = 0xa9
ctime = Thu Jan 18 15:33:43 CST 2018
mZxid = 0xa9
mtime = Thu Jan 18 15:33:43 CST 2018
pZxid = 0xa9
cversion = 0
dataVersion = 0
aclVersion = 1
ephemeralOwner = 0x0
dataLength = 4
numChildren = 0
{如果没有提前，addauth digest admin:admin 会失败，需要先添加用户，才能设置权限}

[====================== client2: 未授权用户 ]
[zk: localhost:2181(CONNECTED) 1] get /acl_auth
Exception in thread "main" org.apache.zookeeper.KeeperException$NoAuthException: KeeperErrorCode = NoAuth for /acl_auth
	at org.apache.zookeeper.KeeperException.create(KeeperException.java:104)
	at org.apache.zookeeper.KeeperException.create(KeeperException.java:42)
	at org.apache.zookeeper.ZooKeeper.getData(ZooKeeper.java:927)
	at org.apache.zookeeper.ZooKeeper.getData(ZooKeeper.java:956)
	at org.apache.zookeeper.ZooKeeperMain.processZKCmd(ZooKeeperMain.java:694)
	at org.apache.zookeeper.ZooKeeperMain.processCmd(ZooKeeperMain.java:581)
	at org.apache.zookeeper.ZooKeeperMain.executeLine(ZooKeeperMain.java:353)
	at org.apache.zookeeper.ZooKeeperMain.run(ZooKeeperMain.java:311)
	at org.apache.zookeeper.ZooKeeperMain.main(ZooKeeperMain.java:270)

[====================== client2: 授权用户 ]
[zk: localhost:2181(CONNECTED) 0] addauth digest admin:admin
[zk: localhost:2181(CONNECTED) 1] get /acl_auth
auth
cZxid = 0xa9
ctime = Thu Jan 18 15:33:43 CST 2018
mZxid = 0xa9
mtime = Thu Jan 18 15:33:43 CST 2018
pZxid = 0xa9
cversion = 0
dataVersion = 0
aclVersion = 1
ephemeralOwner = 0x0
dataLength = 4
numChildren = 0
```

```text
digest: 与auth区别在于密码是密文
appendStr: BASE64(SHA1(username:password)

第一点不同：不需要先addauth才能setAcl
第二点不同：
1. 设置节点权限：setAcl /acl_digest1 digest:admin:x1nq8J5GOJVPY6zgzhtTtA9izLc=:crwda
2. 授权：addauth digest admin:admin
才能访问。
```

* IP Schema

```text
id: ip address， schema:id:permission

1. setAcl /acl_ip ip:127.0.0.1:crwda
2. setAcl /acl_ip ip:192.168.1.1:crwda

不是这个IP就不能访问 
```
* Super

```text
zkCli.sh -Dzookeeper.DigestAuthenticationProvider.superDigest=admin:015uTByzA4zSglcmseJsxTo7n3c=
可以做任意修改
```














