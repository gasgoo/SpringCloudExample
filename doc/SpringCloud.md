Spring Cloud生态

Spring Cloud Config 是分布式水平扩展集中式配置服务，使用本地存储、Git等存储配置； 通常体现未 application.yml配置文件。 只需要使用sprng-cloud-starter-config依赖构建Spring Boot应用，自动配置将会完成其它工作。 
只需要配合bootstrap.yml就可以完成自动配置工作。
API网关 系统的单个入口点，将请求路由到后端服务或聚合后端服务处理用户请求， @EnabledZuulProxy注解来启用它 
Zuul是Netflix出品的一个基于JVM路由和服务端的负载均衡器. 路由 Zuul配置 
zuul: 
    routes: notification-service: 
    path: /notifications/** 
    serviceId: notification-service 
    stripPrefix: false 
以上标识 所有 /notifications开头的请求都将路由到 notification-service处理。 
Zuul使用 服务发现机制 来定位通知服务实例以及断路器和负载均衡器

服务发现： Eureka 服务注册、续约、下线、注册中心之间服务同步、 消费者获取服务 
@EnableEurekaServer 服务端
 @EnableDisconveryClient 可以发现服务 
 注册中心： 服务下线、服务剔除、 自我保护 服务提供者: 服务注册-发送rest请求将自己注册到EurekaServer上，
 元数据存储在双层hashMap中，第一层是服务名称，第二层是实例名称 服务在多个注册中心之间同步；
  服务续约 服务提供者维护心跳告诉注册中心 服务在线。 续约服务间隔时间30秒，服务失效时间90秒。 
  服务消费者 : 从注册中心拉取服务提供列表（30秒更新） 缓存到本地 然后和服务提供者直接连接。 
  服务调用 默认轮询方式。

eureka.server.enable-self-preservation=fasle 自我保护关闭 服务不可用立刻从注册中心移除节点 
eureka.client.registerWithEureka 是否把自己注册到注册中心 
fetchRegistry 是否去同步其他服务

Eureka注册中心 ，自动检测服务实例的网络位置，动态分配地址; 
使用Spring Boot，可以使用spring-cloud-starter-eureka-server依赖、 
@EnabledEurekaServer注解和简单的配置属性轻松构建Eureka注册中心（Eureka Registry）

使用@EnabledDiscoveryClient注解和带有应用名称的bootstrap.yml来启用客户端支持：
 bootstrap.yml 内容： spring: application: name: notification-service

Ribbon:   是负载均衡器，很好的控制http和TCP客户端的行为，可以值连所需的服务；
它与Spring Cloud和服务发现是集成在一起的，可开箱即用。Eureka客户端提供了可用服务器的动态列表，因此Ribbon可以在它们之间进行平衡
@LoanBalanced修饰的RestTemplate 实现客户端负载均衡    LoanBalancerClient类
通过LoadBalancerInterceptor拦截器对RestTemplate请求拦截，然后利用SpringCloud的负载均衡器将服务名为host的俩转换成具体的服务实例地址。
负载均衡策略：
 随机选择
 线性轮询方式选择。如循环大于10次没有可用则告警。
 重试机制实例选择。
 根据实例的运行情况计算权重。
 先过滤在轮询选择
 最大可用策略
 区域感知策略
原理： 服务发现-能够自动发现所依赖的服务列表 服务监听-能够检测到失效的服务，并剔除失效的服务 负载策略选择-

Hystrix； 断路器 它可以通过网络访问依赖来控制延迟和故障。 服务降级、熔断、线程隔离、请求缓存 合并 监控。
  HystrixCommand或HystrixObservableCommand 类  
  每个服务依赖关系维护一个小的线程池或信号量，已满则拒绝父亲请求。
  调用超时则降级处理。
​Feign： 声明式htp客户端 与 Ribbon和Hystrix无缝集成，

@EnableFeignClients
spring-cloud-starter-feign依赖和@EnabledFeignClients注解，您可以使用一整套负载均衡器、断路器和HTTP客户端，并附带一个合理的的默认配置。

@FeignClient(value = "statistics-service")
public interface StatisticsServiceClient {
@RequestMapping(method = RequestMethod.PUT, value = "/statistics/{accountName}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
void updateStatistics(@PathVariable("accountName") String accountName, Account account);
} 
​ETCD: etcd作为一个受到ZooKeeper与doozer启发而催生的项目，除了拥有与之类似的功能外，
更专注于以下四点。 
简单：基于HTTP+JSON的API让你用curl就可以轻松使用。
 安全：可选SSL客户认证机制。 快速：每个实例每秒支持一千次写操作。
  可信：使用Raft算法充分实现了分布式。 应用场景有如下几类： 
  场景一：服务发现（Service Discovery） 
  场景二：消息发布与订阅 
  场景三：负载均衡 
  场景四：分布式通知与协调 
  场景五：分布式锁、分布式队列 
  场景六：集群监控与Leader竞选 kubernetes用etcd来存储docker集群的配置信息等。
  
##SpringCloud alibaba
nacos注册流程:
1.  client端启动tomcat发布事件 容器启动事件 WebServierInitializedEvent;初始化spring容器
ServletWebServerInitializedEvent implements WebServerInitializedEvent  web容器初始化事件

2.通过自动配置spring.factories中配置了一个AutoServiceRegistrationAutoConfiguration类
 注入实例NacosAutoServiceRegistration extents AbstractAutoServiceRegistration监听到容器启动事件则开始注册流程
 并且通过构造方法注入了ServiceRegistry接口的某个实现类. 
 实例NacosServiceRegistery implements ServiceRegistry.register()开始服务注册流程。

3. 注册过程中发布InstancePreRegisteredEvent 实例注册事件;然后实例化 NacosServiceRegistry对象开始注册流程,
    调用NacosNamingService#registerInstance()方法
4. 注册流程中主要是 发送两个http请求给nacos服务端,心跳请求、注册请求，把client实例信息封装成 Instance
5. 服务器端是先创建注册的服务、然后默认创建一个集群、然后集群中包含所有的实例列表。
6. 一个新的服务注册到nacos服务器后会开一个任务向其他节点同步新注册的服务。
服务消费者
7. 客户端启动后向nacos服务订阅需要的服务；然后再实际发生调用的时候查询服务列表，nacos则把当前客户端添加到内存的clientMap。
8. 服务端有事件发生时，nacos服务器会给客户端发送udp socket通知客户端事件内容，或新的服务列表。

Services 中包含心跳任务、集群ClusterMap 
DataStore  <String,datum>
nacos服务端处理注册流程:
nacos节点之间数据同步、客户端掉线重连后创建服务。

没有加@Configuration的注解的类也可以是配置类 只是不会使用cglib动态代理。

#nacos如何实现一致性？  支持AP和CP 可以切换但是不能同时存在
cp 强一致性  ap最终一致性 
一致性协议 ZAB(zk)   paxos  raft(nacos 的CP模型)   Distro（alibaba）弱一致性 nacos的AP模型



#nacos的注册中心包含负载均衡组件 ribbon
nacos-register服务名称如何变成 IP地址 192.168.4.168

restTemplate.getForObject()-LoadBalancerCommand-
实例化 SpringClientFactory Spring容器-读取配置类-bean定义-初始化bean

实例化了AnnoationConfigApplicationContext容器对象
 容器启动过程中会扫描 NacosRibbonClientConfiguration 相关的ribbon配置类然后 初始化
 LoadBalancer loadBalancer = this.getLoadBalancer(serviceId); 
 从Spring中得到负载均衡器实现类ZoneAwareLoadBalancer。
 
调用注册中心的查询服务实例的接口返回服务对应的实例信息列表;然后放到本地缓存;
每10秒更新一次查询服务注册列表更新map缓存;
ribbon实例化了多个Spring容器 容器根据不同的微服务区分，不同的服务放在不同容器中。
 

    

sentinel

 