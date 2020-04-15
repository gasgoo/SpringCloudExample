# SpringCloudExample
 SpringCloud 学习过程

##SpringBoot整合 mybaits 使用druid连接池案例

##Eureka 注册中心案例
服务提供者注册到 eureka
服务网关 zuul 服务的统一入口 gatway
 spring.cloud.gateway.discovery.locator.enabled：
表示是否与服务发现组件（register）进行结合，通过 serviceId（必须设置成大写）
转发到具体的服务实例。默认为 false，设为 true 便开启通过服务中心的自动根据 serviceId 创建路由的功能。
路由访问方式：http://localhost:8090/EUREKASERVER/hello
其中微服务应用名默认大写访问

##添加 feign组件 
@EnableFeignClients，如果我们要使用 OpenFeign 声明式 HTTP 客户端，必须要在启动类加入这个注解，以开启 OpenFeign。

Hystrix 组件，当特定的服务不可用达到一个阈值（Hystrix 默认 5 秒 20 次）
启动类中添加 @EnableHystrixDashboard

实现分布式锁的方案
###利用 SETNX 和 SETEX 做分布式锁

SETNX(SET If Not Exists)：当且仅当 Key 不存在时，则可以设置，否则不做任何动作。
SETEX：可以设置超时时间
DistributedLockHandler    存在的问题：
 高并发多个线程同时进入循环 加锁失败； 
SETNX 是一个耗时操作，因为它需要判断 Key 是否存在，因为会存在性能问题
改成 RedLock实现分布式锁

##通过 Redlock 实现分布式锁比其他算法更加可靠
Redisson  连接补上redis需要 修改redis配置
vi redis.conf
protected-mode yes 改成 protected-mode no
##基于数据库的分布式锁
1.基于数据库表
基本原理和 Redis 的 SETNX 类似，其实就是创建一个分布式锁表，加锁后
我们就在表增加一条记录，释放锁即把该数据删掉，
2.乐观锁
乐观锁一般通过 version 来实现，也就是在数据库表创建一个 version 字段，每次更新成功，则 version+1，读取数据时，我们将 version 字段一并读出，每次更新时将会对版本号进行比较，
如果一致则执行此操作，否则更新失败！
3. 悲观锁
##基于ZooKeeper分布式锁---配置维护、域名服务、分布式同步、组服务等。
实现的分布式锁是严格的按照顺序访问的并发锁。

##前端你请求参数签名方式
将 Token、Timstamp 和接口需要的参数按照 ASCII 升序排列，
拼接成 url=key1=value1&key2=value2，如 name=xxx&timestamp=xxx&token=xxx，进行 MD5（url+salt），
得到 Signature，将 Token、Signature、Timestamp 放到请求头传给服务端，如 header(“token”,token)、header(“timestamp”,timestamp),header(“signature”,signature)。

##整合RocketMq
两种方式：
1. 构建DefaultMQProducer 和DefaultMQPushConsumer/DefaultMQPullConsumer
2. 采用RocketMQTemplate的方式生产消息，用监听的方式消费消息RocketMQMessageListener 

##整合quartz
引入quartz依赖 添加配置 配置数据存储方式 有 内存memory和数据库jdbc方式
用jobDetail包装job添加到触发器中。
 
##添加基于注解的策略模式
参考 @HandlerType








