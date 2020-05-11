===MySQL============默认 可重复读取 oracle 默认是 读已提交。

select @@global.tx_isolation 查看 隔离级别： ====主从同步原理 异步串行化 二进制日志文件 binary log master将改变的数据记录再本地的二进制文件，主库的 binary log写(同步)到从库 通过IO线程写入 Relav log 然后从库通过sql线程把RelavLog 写入database; 其中涉及 一个IO线程 一个SQL线程。

auto_increment mysql自增 mysql -uroot -proot

mysql体系: 自上而下 连接池组件-管理服务和工具组件- (sql接口组件-查询分析器-优化器-缓冲组件)-存储引擎-物理存储文件。 mysql逻辑分层： 连接：提供与客户端的连接 服务：提供各种用户使用的接口 select update； 提供sql优化器 引擎：提供了各种存储数据的方式 存储：数据存储 事物管理

show engines; 查看引擎 存储引起是基于表的非数据库。 select * from information_schema.engines;

innodb ：事物优先，适合高并发场景 大量的 insert update 行锁 OLTP myisam : 性能效率优先 适合大量的select 表锁 不支持事物 支持全文索引 主要适用OLAP 缓冲索引文件 NDB: 集群存储引擎 索引数据全部在内存中，行锁 主键查询数据极快 缺点：join操作时数据库层完成不是存储引擎层完成需要巨大的网络开销。 memory: 数据存于内存，重启会丢失数据， 临时表适用 默认适用 哈希索引 非B+树索引，表锁。 Archive: 归档存储引擎，只支持insert和select操作；数据行压缩存储. Maria: myIsam引擎的升级版本，支持缓存数据和索引文件，行锁，MVCC 支持事物可选，BLOB字符串类型的兼容。

innodb存储引擎详解: 后台线程-内存缓冲池-磁盘文件

后台线程的作用：负债刷新内存池中的数据，保证缓冲池的数据是最新的数据; 最新修改刷新到磁盘文件。
1.线程类型:
    Master Thread-缓存池数据异步刷盘，保证数据一致性，包括脏页、合并插入缓冲、undo页的回收
    IO Thread 
    Purge Thread  合并undo log
 2.内存结构:
 a.缓冲池对象： 数据页、索引页、插入缓冲、锁信息、自适应哈希、
  管理算法：LRU 最近最少使用算法  最少使用的放尾端，空间不够了就释放尾端的页。
 b.重做日志缓冲:redo log buffer 重做日志先放入缓冲区，然后按一定频率刷新到重做日志文件;一般每秒都会刷新
 c.额外内存池:
 DML语句的写过程:  日志先行即 当事物提交时，先写重做redo log、回滚undo log 缓冲-日志刷盘，在修改缓冲池中的数据页-数据刷盘。
    Buffer pool -log buffer -(fsyc)-os buffer - log file 
3.innodb关键特性：
 Insert Buffer: 提高非聚集索引的插入更新效率，先判断插入的索引页是否在缓冲中，在则更新，不在则先放入InsertBuffer后期在子节点合并。
 change Buffer: InsertBuffer 的升级  本质上也是一颗B+树结构。
 两次写: doubleWrite 保证数据可靠性   
    查看 doubleWrite运行情况 Global status like 'innodb_dblwr'\G;
    如果刷盘过程中发生崩溃，可以从共享表空间doublewrie中找到当页的一个副本，复制到表空间，再用重做日志文件恢复。
 自适应哈希 
 
慢查询日志  找到查询时间最长的10条sql: mysqldumpslow -s al -n 10 xx.log  
二进制日志格式级别:  基于SQL语句(statement)、基于数据修改行（ROW）、MIXED(前面两种情况都有)
动态修改binlog_format : set @@session.binlog_format='ROW';  

表空间文件
重做日志文件 redo log  记录事物日志 重要属性  日志文件大小 innodb_log_file_size 保证了事物持久性
为什么MyISAM会比Innodb 的查询速度快？？？

INNODB在做SELECT的时候，要维护的东西比MYISAM引擎多很多；
1）数据块，INNODB要缓存，MYISAM只缓存索引块，  这中间还有换进换出的减少； 
2）innodb寻址要映射到块，再到行，MYISAM 记录的直接是文件的OFFSET，定位比INNODB要快
3）INNODB还需要维护MVCC一致；虽然你的场景没有，但他还是需要去检查和维护
====innodb替代 MYLsam的原因？ MylSAM只支持表锁，不支持事物。 innodb支持行锁，支持事物;看具体的适用场景。

RC读已提交 隔离级别下的锁情况 
a.字段没有索引，则表中所有记录都会加锁，变成了表锁。 所以修改数据需要尽可能的走索引。 
b. 条件字段是唯一索引字段时则只锁一条记录。
 c. 条件字段非唯一索引则会锁满足条件的所有记录。

==可重复读(RR)的级别下 innodb引入了间隙锁解决了 幻读的问题。 
a.非索引字段做条件的当前读，不但会把每条记录添加锁，还会再每个间隙添加间隙锁。 其他事物不能做写操作。
 b. 唯一索引当条件添加for update 锁，则只锁当前这一条记录。
  c. 非唯一索引会锁满足条件的记录，并且会添加间隙锁。

优化的原因： 性能低 执行时间长 sql语句欠佳 索引失效 服务器参数设置不合理 编写过程： select.. from..join.. on.. where ..group by ..having..order by ..limit 解析过程： from..on..join..where ..group by..having ..select..order by ..limit SQl优化主题：就是索引优化 索引===提高数据访问速度的数据结构（B树、hash树） 影响 insert update delete的效率 索引的弊端： 1. 索引本身很大 占用空间，2. 不是所有情况下都使用， 少量数据 频繁更新的字段，不常使用的字段。 优势： 提高查询效率降低IO、降低CPU使用率 B+树 默认就有排序功能 只要遍历出来就有排序

 InnoDB支持的索引：B+树索引、全文索引、哈希索引	  
 B+树索引找到数据所在的页并不能找到具体的行;
 聚聚索引-根据key找到叶子节点中的数据。
 辅助索引-根据key在辅助索引树中找到对应的主键，然后根据主键再找到数据行。
 
 B+树索引的优势: 提高数据访问的IO效率， 提高范围查找的效率，叶子节点是有序的。
 
Cardinality预估某个索引的数据行数，行数大的更适合建立索引。
hash树 ——id 硬件地址 B+树 数据存在叶子几点 左小右大 查询任意数的次数是 树的高度

为什么使用B+树？
类型：单值索引、唯一索引、复合索引 联合索引 idx_a_b_c(a,b,c) 相当于 (a) 、(a,b) 、(a,b,c) 三种索引,称为联合索引最左原则

mysql中 utf-8编码 一个字符三个字节 gbk编码 一个字符两个字节 late 一个字符一个字节 null 1个字节

查看执行计划 explain sql语句 id select_type table type possible_keys key key_len ref rows extra type : system 、const 都只有一条数据 eq_ref 和索引查询的一致 ref 0条或多条 range 范围 > < between and
index all

Extra可能的value using index 、using where 、using fileSort、 using tempory、 using join buffer

in会让索引失效; 查询字段的顺序需保持和索引顺序一致。（最佳左前缀） 多表连接查询把数据量小的表放左边，索引建立再关联的字段上。 小表驱动大表

避免索引失效的原则： 复合索引不要跨列使用或无序使用(最佳左边缀) 左边失效了 右边全部失效 复合索引尽量使用全索引匹配 复合索引不能使用不等于(<> !=)大于或 is null（is not null） ,否则自身及右侧全部失效。 like 尽量以常量开头不要%开头 不要再索引上做计算操作(如 索引列 加减乘除操作) 尽量不要使用类型转换，否则索引失效。 不要再索引列上使用函数 尽量不使用or，否则索引失效。 order by中多个字段排序 不能 desc 和dsc 混用。

聚簇索引是 索引值和数据行保存在叶子节点。 非聚簇索引 索引值和主键key保存在叶子节点，然后再根据主键去主索引B+树搜索得到数据行。 ======普通索引和唯一索引的区别？ 数据修改时普通索引使用插入缓冲 Change buffer ，唯一索引不行； 修改数据时，唯一索引值需要唯一，在RR隔离级别下更容易出现死锁。 普通索引查找到满足条件的第一条记录还需要继续查找，直到不满足条件; 唯一索引查找到一条记录就返回了。

====为什么主键通常建议使用自增id？ 聚簇索引的数据的物理存放顺序于索引顺序是一致的， 只要索引是相邻的，那么数据一定也是相邻的。 如主键不是自增的，会不断的调整数据的物理地址 分页，因此会降低查询效率。

每张innodb表都有一个聚集索引，但不一定是主键。 聚集索引不一定是主键，但主键一定是聚集索引; ---建议主键自增 并且无业务意义。 主键自增 则保证了聚集索引的有序性，每次都在聚集索引的后面增加，不用在数据页分裂时维护有序性。 也是高效率的一种手段。

优化方法： exist和in 主查询数据量大用in；子查询数据量大则用exist；

====================锁========================== 锁是DB区别于文件系统最大的一个特征；用于管理对共享资源的访问。 全局加锁： Flush tables with read lock; 表加锁: lock tables t1 read/write; 行加X锁: select * from table where id=1 for update; S锁 select * from table where id =1 lock in share mode; innodb实现的锁 共享锁、排他锁

        非锁定的一致性读，不用加S锁可以读取，读取的数据快照，行记录通常会有多个快照版本-即 行多版本并发控制。
        MVCC：多版本并发控制 为每一行额外添加两个隐藏的列   数据版本号 指向undo log的指针等 在应对重复读的隔离级别的产生的幻读用到mvcc
        最大有优点是 读数据不加锁，因此读写不冲突,性能好。
        修改时，复制出当前版本随意修改，各事物间互不干扰，保存时比较版本号 commit成功则覆盖原记录。
        由于写操作会加排他锁，故MVCC对写事物没有效果。
锁定一致性读 读数据加S锁 锁的三种算法： 行锁 record Lock 、 Gap Lock 间隙锁、 Next-key Lock 锁定一个范围并锁定记录， 可以解决 幻读(不可重复读)问题。 锁的问题：

不可重复度：同一个事物不同时段读取的结果不一致。  读取的是其他事物已经提交的数据 
幻读：事物A中按照同样的条件先后两次查询数据库得到的数据记录数不一致，其他事物有新增记录。

    脏读- 读取到其他事物没有提交的数据；和脏页不是同一个概念。
    幻读-不可重复读- 即执行两次同样的sql结果可能不同，读取的是其他事物已经提交的数据
    丢失更新-一个事物的更新会被另一个事物的更新覆盖，丢失了第一个事物的更新，理论上是不会有问题的，因为修改都需要先加锁;
    第二个事物需要等第一个事物释放锁后才能修改。 可能存在多用户系统中，多个用户修改同一个资源数据的时候。
死锁：两个事物以上占用同一个资源互相等待； 系统中事物的数量越多发生的概率越大； Innodb不存在锁升级-锁的粗粒度降低，会带来效率的提高，但是会降低并发度。 命令查看系统当前锁的情况： show engine innodb status; select * from innodb_trx,Innodb_locks,innodb_lock_waits;

====================lock end==================== ====================事物 start==================== 事物ACID属性 原子性、一致性、隔离性、持久性 redo log (重做日志) log 保证持久性和原子性 D 对新数据做日志备份
undo log (回滚日志)保证事物一致性A 基础 对现有数据备份 锁机制 写事物之间的隔离 I
事物 保证一致性 C

事物commit必须先持久化redo log

redo log 重做日志 两部分组成：重做日志缓冲-易丢失的、重做日志文件-持久的;顺序写 innodb层的日志 物理格式的日志 对页的修改。
 日志缓冲 fsync 刷盘到日志文件持久化的性能是由磁盘性能决定的，故决定了事物提交的性能从而影响DB的性能。 
 innodb_flush_log_at_trx_commit 参数控制重做日志刷盘策略 默认为1 参数值 0、1、2 1=提交一次事物立即刷盘; 
 0=事物提交不刷盘，由master线程每隔一秒刷一次; 2=刷新到日志缓存并没有到文件。
  redo log 结构 头部12字节-body 492字节--尾部8字节。 
  log block 日志块 重做日志块大小512字节；追加写入。 
  log buffer 日志缓冲 中存储的是 log block LSN Log Sequence Number 日志序列号，可表示重做日志的总量 页的版本

undo log 回滚日志 事物回滚 MVCC功能 逻辑日志 随机读写;存放在表空间的undo段；
 rollback segemnt 中放 1024个undo log段 undo 页可以重用，事物提交时，将undo log放入链表中，判断是否可以重用，可以则记录undo log。
 undo log 保留了行的历史快照数据，从而实现MVCC非锁定读；保证了隔离性。
  RC和RR隔离级别下，如果查询一些被其他事物正在更新的行，看到的是这些记录被更新前的值。
  MVCC实现的。 行数据有一个隐藏的回滚指针，用于指向修改前的最后一个历史版本，保存在undo log中。

binlog 二进制日志 数据库层产生的、逻辑操作日志 sql语句；事物提交完成后一次性写入。 作用： 恢复、复制、审计 sync_binlog 来控制累积多少个事务后才将二进制日志 fsync 到磁盘。

mysql事物数据修改之前先写入事物日志； 事物日志的记录过程： 事物日志写入 日志缓存 log buffer ；然后操作系统调用 fsync()把文件从 os buffer同步到 磁盘的log file中。

log buffer --buffer pool---os buffer--log file

现在来看看MySQL数据库为我们提供的四种隔离级别isolation： 　　
① Serializable (串行化)：可避免脏读、不可重复读、幻读(insert)的发生。 　
　② Repeatable read (可重复读)：可避免脏读、不可重复读的发生(修改)。 　　
③ Read committed (读已提交)：可避免脏读的发生。 　
　④ Read uncommitted (读未提交)：最低级别，任何情况都无法保证。

Spring事物的传播propagation行为：默认 REQUIRED
SUPPORTS MANDATORY REQUIRES_NEW NOT_SUPPORTED NEVER NESTED 注解事物实例 设置传播级别 隔离级别 
产生异常时不回滚 @Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.DEFAULT,rollbackFor=Exception.class)

====================transaction end==================== mysql query cache 查询缓存 开启配置 在配置文件 my.cnf 中设置： query_cache_type = 1 query_cache_size = 50M