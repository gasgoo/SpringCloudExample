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








