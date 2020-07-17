#redis 高逼哥指南

基于内存亦可持久化的 日志型 key-value的数据库。
是一个高性能的key-value数据库 支持持久化 多种数据类型 数据备份 主从 单线程 模型 并发能力强大主流的分布式缓存工具 5000亿/24小时 +++并发量 新浪
高性能的原因： 单线程 全部是内存操作，NIO 多路复用IO模型，高效的数据结构 如sds 动态字符串  resp简单协议
#redis特性
速度快 持久化 支持多种数据结构 多语言 功能丰富 主从复制 高可用 分布式

#常用命令
set key value
mset country china city shanghai
get key ========命令
key * 查看所有的key hgetall key 命令用于返回哈希表中，所有的字段和值。
flushdb 删除当前库的缓存 
flushAll 删除所有的缓存 
exists 命令 判断可以是否存在 返回1表示存在，0不存在。
expire 设置过期时间 单位秒 type 返回key对应的value的数据类型 
ttl 剩余有效时间
setnx name jack  1=成功 0=失败
ping 检查客户端是否连接成功 返回pong 
INCR Key 10 
DECR key 5 建议key的上下级关系用冒号分隔 key:subKey
watch 
multi
exec
discard 取消事物
move name db  把key 移动到其他的db
dump 
migrate 批量导入 迁移key
slave of ip:port 当前节点是ip的从节点

redis数据结构：String字符串 Hash哈希类型、List列表类型、 Set集合类型、 Zset有序集合类型

#List列表应用场景：社区发帖列表、简单的消息队列 最新新闻等。 数据结构是 双向链表 最多可以有 2的32次方减1个元素。 并行的请求转为串行的任务队列依次处理。 
List的操作命令： 
RPUSH key value 
LPUSH key value 
RPOP key 左边移除并删除 
LLen key 列表长度 
LINDEX key index 取列表index位置上的元素。

#Hash 方便管理key 避免命名冲突 节约内存
 hset key filed value hget key filed Hexists key filed 判断 key指向的Hash中filed是否存在。
 Hkeys key 获取所有的 hash key Hvals key 获取所有的value
 hset user:1 name lison age 38
 hget user:1 name
 hexists user:1 name
 

#Set自动去除重复的元素 Zset 维护一个score 从小到大的排序。
SADD key value SREM key value 
smembers key 显示所有元素
SISMEMBER key member 判断member元素是否为key
集合中的元素 SCARD key 集合的成员数 SMEMBERS key 列出所有元素

跳跃表 使用场景：
#ZSet有序集合底层实现; 集群节点中内部数据结构
ZskipList 跳跃表 Intset 整数集合 升级 降级

事物相关的命令: 
multi-- 事物开始 exec--事物提交 discardq--取消事物 watch 监控某个key 是否被修改过 
expire key seconds 失败返回0 否则返回1 ttl key 查看key的剩余时间 PTTL key 返回剩余毫秒数

redis性能测试
redis-benchmark -h 127.0.0.1 -p 6379 -c 100 0n 10000    100个并发连接 10000个请求
redis-benchmark -h 127.0.0.1 -p 6379  -q -d 100         测试存取大小为100字节数据包的性能 包含所有的添加数据的指令
redis-benchmark -h 127.0.0.1 -p 6379  -t set,get -n 10000 -q


#redis运行原理？ 单线程 NIO 建立连接
客户端发送命令到redis服务 发送命令-命令排队 -命令执行-返回结果 
单进程 单线程 内存操作 多路复用的IO模型 tcp协议 建立连接 发送 socket redis底层协议： resp协议 文件事件: 网络通信的抽象 时间事件：redis服务资源的管理

key.getBytes().length

#set 命令 客户端输出流
socket读取输入流写入redis服务端 
Socket socket=new Socket(ip,port); 
set(socket,key,value)
ServerSocket sc=new ServerSocket(6379);
Socket rec=sc.accept();
byte[] result=new byte[2048]; 
rec.getInputStream().read(result); 
system.out.println(new String(result));

jedis连接 Jedis jedis=new Jedis("127.0.0.1",6379); 
jedis.set("length","10");
 jedis.close();

把数据库表的数据导入redis
mysql -utest -ptest dbName --default-character-set=utf-8 --skip-column-names --raws < redis-cli -h ip -p port -a pwd --pipe

缓存一致性问题？ 缓存设置有效期、先更新库再删除缓存。 还有比较复杂的是订阅数据库的binlog然后解析日志更新缓存。

PipeLine 性能利器 减少网络延迟时间 批量删除 先组装数据到pipe redis实现乐观锁 watch指令提供cas机制

#持久化策略 AOF=操作日志备份 RDB=定时快照 恢复方便 ===========企业级容灾怎么处理？
save命令 阻塞客户端
bgsave命令 fork一个子进程异步保存生成rdb文件。

redis灾难恢复原理 RDB= redis默认的持久化方案，单位时间内执行指定的次数的写操作，
将数据从内存中写入磁盘 生成一个 dump.rdb文件，每次生成的文件都会覆盖之前的dump.rdb文件 
重启后会加载dump.rdb文件恢复数据。

>RDB持久化的优点： 适合大规模数据恢复 擅长冷备 适合对数据一致性要求不高的场景。
            缺点： 最后一次备份写文件前机器故障会导致 数据丢失； 备份时占用内存.

>AOF持久化： 弥补RDB的不足，解决数据不一致的问题，采用日志的形式记录每个写操作指令 生成文件appendonly.aof 。 
always 每次写入一条
everysec 每秒写一次 
no 数据写入磁盘 类似 mysql的 stament语句 binlog 二进制文件方式。
auto-aof-rewrite-percentage 100
重写aof文件，是为了缩小aof文件的大小，直接读取数据库现有的数据状态生成命令写入aof文件。
优点：数据一致性高 增量添加 缺点： 记录内容太多 文件很大 数据恢复也慢，读写性能下降

如果redis同时开启 AOF和RDB备份 以AOF的数据为基准; 恢复的时候优先加载AOF文件然后加载RDB文件。

#redis主从复制原理 一主多从  一主多从，树状结构
master收到slave的 同步命令sync/psync后 主服务器运行BGSAVE生成RDB文件保存到磁盘，
同时把新接收到的写命令存入缓冲区 文件持久化后会发送给slave，
然后把缓冲区的命令发送给slave，然后slave加载文件到内存，执行命令实现和master 同步。 
部分同步：偏移量、复制积压缓冲区、runId

#redis部署方式 高性能 分片 高可用 交叉备份 
单机 主从 主从+哨兵 哨兵会监控master节点，
master节点挂掉后会让slave升级会主节点,哨兵不能是单点。
 slaveOf masterIP port
 slave no one 自己为主机 
 哨兵: 判断主机是否下线，如判断为主观下线，则询问其他哨兵该主机是否下线，如果满足条件则判断主服务客观下现， 
 开始选举领头哨兵： 监视同一个master的多个在线Sentinel的都有可能成为领头哨兵;选举后配置纪元+1，
 所有哨兵在纪元内都只有一次将某个哨兵 设置为自己的领头哨兵，如一个哨兵收到多个其他哨兵要求把发送命令方设置为当前哨兵的领头哨兵，
 则先到先得。 目标哨兵设置后会返回 自己的runID和配置纪元告诉拉票哨兵。 成为领头哨兵需要满足过半机制。
 
#领头哨兵选出后 开始 故障转移 选举主服务器
故障转移:
监听master节点和master的从节点，在已下线的master的属下slave中选出一个从服务器设置为master
让下线的master的属下slave改为复制新的master
将下线的master设置为新的master的从服务器 
选主服务器的过程:
剔除下线或断开的从服务;剔除长时间没有回复哨兵info命令的从服务。
根据从服务器的优先级最高、复制偏移量最大的、runID最小的从服务为新的master
最后哨兵发送info命令根据回复确认是否选主服务成功。

info replication 查看集群配置信息
#集群 redis Cluster 多主多从 
Cluster meet 槽点指派 集群内部的数据结构ClusterState ClusterNode ClusterLink 
复制故障转移: 
1.master用于处理槽请求，slave则用于复制某个主节点并在master下线时代替master处理请求；
2.如果某个主节点下线，则其他master节点则会从下线的master的从节点中选出一个为新的master节点，  并接管下线master节点处理的槽点，继续处理客户端的命令; 
 cluster replicate node_id 将接受命令的节点设置为node_id的从节点。
3. 集群中的节点定期向其他节点发送ping消息；如果没有及时收到pong回复，则标记为主观下线； 
4. 集群中半数以上负责处理槽的主节点认为某个节点下线，那么该节点被标记为下线。 
5. 从节点发现自己的主节点下线后开始故障转移 从新选主节点。 slave no one 成为master

#redis内存管理和数据淘汰机制 maxmemory 100mb 最大内存设置 maxmemory-policy volatile-lru 设置淘汰策略
volatile-LRU  淘汰上次使用最早的且次数最少的，设定了有效期的。
allKeys-lru  LRU算法  所有的key都可以被淘汰
volatile-random  随机淘汰设定了有效期的数据 
volatile-ttl   淘汰剩余有效期最短的数据
redis的淘汰策略有哪些； 内存空间用满, 就会自动驱逐老的数据 过期时间 

noeviction: 不删除策略, 达到最大内存限制时, 如果需要更多内存, 直接返回错误信息。 
allkeys-lru: 所有key通用; 优先删除最近最少使用(less recently used ,LRU) 的 key。 
volatile-lru: 只限于设置了 expire 的部分; 优先删除最近最少使用(less recently used ,LRU) 的 key。 
allkeys-random: 所有key通用; 随机删除一部分 key。 
volatile-random: 只限于设置了 expire 的部分; 随机删除一部分 key。 
volatile-ttl: 只限于设置了 expire 的部分; 优先删除剩余时间(time to live,TTL) 短的key
这八种大体上可以分为4中，lru、lfu、random、ttl。


5）redis的数据添加过程是怎样的：哈希槽； 一致性hash算法，
对key和节点的某个属性做hash得到value 二进制位 2048个字节 2048*8=16384位 
哈希槽指派 CRC16(key)%16384 得到槽点 
集群中命令的执行过程: 
客户端发送key，接受key的服务节点会计算key属于那个槽，如果这个槽是当前节点负责则执行命令，
 否则返回moved错误指引客户端再次发送命令。

#redis实现分布式锁

数据库加锁 insert动作时添加lock 需要实现lock接口
2 redis实现分布式锁 setnx （set if not eXists）不存在则设value加锁 否则不做

class redisLock implements Lock{ @Resource private redisConnectionFactory factory;

private ThreadLocal local=new ThreadLocal<>();

public boolean tryLock(){ 
Jedis jedis=factory.getconnection.getNativeConnection(); 
String uuid=UUID.randomUUID.toString(); local.set(uuid); 
String ret=jedis.set("key",uuid,"nx","px",3000); 
if(reg!=null && ret.equals("ok")){ return true; } return false; }

public void unlock(){ 
String script=FileUtils.readFile("path"); 
Jedis jedis=factory.getconnection.getNativeConnection(); 
String uuid=local.get();
 jedis.eval(script,Arrays.asLIst("ok"),Arrays.asLIst(uuid));

} }

实际场景应用： 多个订单服务下单扣减库存（需要控制一个一个去扣减）

jvm线程锁具有哪些特点 排他性 只有一个线程能获取到锁 （数据库 文件系统锁 缓存 zookeeper 等都有排他性） 阻塞性 其他未抢到的线程阻塞，直到锁被释放出来 再抢 可重入 线程获取锁后 再次重复获取该锁 不会阻塞自己。

数据库在数万QPS的场景中如何处理？ 转移数据库的压力 ====小型网站 小型网站静态化架构 用户访问html 页面模板从DB中加载数据展示给用户 提高了访问速度 优点：搭建简单 快速 缺点：模板改变必须重构，产品多就构建速度太慢。

#大型网站 多级缓存详解 查询过程： 用户查询本地缓存 Nginx本地缓存 分布式缓存 服务的本地缓存 数据库查询

>缓存穿透：缓存穿透是指查询一个一定不存在的数据，由于缓存是不命中时被动写的，并且出于容错考虑，如果从存储层查不到数据则不写入缓存 ，
    这将导致这个不存在的数据每次请求都要到存储层去查询 布隆过滤器解决 
    
   BloomFilter: 对结果判断有误差，不存在的可能会提示可能存在(hash碰撞导致的)，但是提示不存在的就一定不存在。

>缓存击穿：热点数据key在某个瞬间失效，请求打到DB中。 互斥锁   LRU算法 把经常怼的热键缓存在map中 不走redis直接map返回。
>缓存雪崩：缓存有效期同时大面积失效 导致缓存命中率下降 压力都落到数据库服务， 有效期分布设置、同步锁控制更新。
  缓存过期算法： LRU 最近最少使用的淘汰- 双向链表 新数据put 链表头部 被访问过的数据也移动到头部，
  链表装满 淘汰尾部数据 LFU 历史访问频率进行淘汰，过去被访问过的将来被访问的也更高。 队列 新数据尾插，count++, FIFO 先进先出 队列 淘汰头部数据

##Redis实现分布式session功能
redis介绍：  key-value存储系统，支持数据持久化 内存中保存到磁盘，
支持数据类型：string hash list set zset 
服务端拿着用户的cookie作为key去存储里找对应的value(session)。

实现session共享的原理: 业务系统发起请求 把sessionID 当做key存储用户等信息到redis中，把sessionID 返回给
业务系统，并存放到cookie中。下次请求从cookie中取sessionId 根据ID到redis中取用户数据。

#缓存一致性问题处理
1、缓存设置过期时间，保证最终一致性。
2、先更新数据库，再删除缓存。 读取数据的时候加锁设置缓存。  强一致性
3. 数据准实时更新同步， 更新数据库后 发布消息 然后订阅消息更新缓存  准一致性

#redis场景设计

