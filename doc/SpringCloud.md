#SOA  服务治理
优点
1.动态扩容、运行时干预、健康检查
2.团队分工明确、代码管理简单、迭代可以快速
缺点
1. 管理的服务包很多、需要持续集成工具支撑运维
2. 分布式事务问题 幂等性问题
3. 跨系统调用的超时问题

Spring Cloud生态

Spring Cloud Config 是分布式水平扩展集中式配置服务，使用本地存储、Git等存储配置； 通常体现未 application.yml配置文件。 只需要使用sprng-cloud-starter-config依赖构建Spring Boot应用，自动配置将会完成其它工作。 
只需要配合bootstrap.yml就可以完成自动配置工作。

#Eureka用户认证:
 导入 spring-seurity包
 修改配置文件: security.basic.enabled=true
 spring.security.user.name=
 spring.security.user.password=
 
 mvn -package -Dskip.test=true
 
## Eureka 服务注册、续约、下线、注册中心之间服务同步、 消费者获取服务 
@EnableEurekaServer 服务端
 @EnableDisconveryClient 可以发现服务 
 注册中心： 服务下线、服务剔除、 自我保护 服务提供者: 服务注册-发送rest请求将自己注册到EurekaServer上，
 元数据存储在双层hashMap中，第一层是服务名称，第二层是实例名称 服务在多个注册中心之间同步；
  服务续约: 服务提供者维护心跳告诉注册中心 服务在线。 续约服务间隔时间30秒，服务失效时间90秒。
         下线服务90秒时间内可能有不可用的服务接收到请求，需要手动调用接口触发服务立即下线
         delete http://localhost:8761/eureka/apps/eurekaServerName/clientname
  服务消费者 : 从注册中心拉取服务提供列表（30秒更新） 缓存到本地 然后和服务提供者直接连接。
   
  服务调用 默认轮询方式。
#高可用流程:   注册、续约心跳、剔除下线、状态更新
  客户端启动监听容器启动事件，开始客户端注册流程，JAX-SR规范http请求eureka服务端，把客户端节点保存到服务端的Map中并把节点复制到其他服务端节点。
  
  
  多个eureka之间两两相互注册
  provider启动都是向一个注册中心注册，eureka会在多个eurekaServer中复制privoder信息;
  
eureka.server.enable-self-preservation=fasle 自我保护关闭 服务不可用立刻从注册中心移除节点 
eureka.client.registerWithEureka 是否把自己注册到注册中心 
fetchRegistry 是否去同步其他服务

#Ribbon:   是负载均衡器，很好的控制http和TCP客户端的行为，可以值连所需的服务；
 @Bean
 @LoadBalacned
 new RestTemplate() .forEntity()  .postForObject()
它与Spring Cloud和服务发现是集成在一起的，可开箱即用。Eureka客户端提供了可用服务器的动态列表，
因此Ribbon可以在它们之间进行平衡@LoanBalanced修饰的RestTemplate 实现客户端负载均衡    LoanBalancerClient类
>>>通过LoadBalancerInterceptor拦截器对RestTemplate请求拦截，然后利用SpringCloud的负载均衡器将服务名为host的俩转换成具体的服务实例地址。
容器启动的时候注册了一个RibbonClientConfiguration类，该类中的ribbonLoanBalancer方法
返回一个负债均衡的ILoadBalanceer类型的实例对象ZoneAwareLoadBalancer开始请求行为。
>>>
负载均衡策略：
 随机选择
 线性轮询方式选择。如循环大于10次没有可用则告警。
 重试机制实例选择。
 根据实例的运行情况计算权重。
 先过滤在轮询选择
 最大可用策略
 区域感知策略
原理： 服务发现-能够自动发现所依赖的服务列表 服务监听-能够检测到失效的服务，并剔除失效的服务 负载策略选择-

#Hystrix 服务隔离 降级 熔断 监控   信号量、线程池
@EnableCircuitBreaker 启用断路器
 一段时间内请求次数达到20次(默认)请求失败率超过50% 则触发熔断开启，之后的请求没法到达后续的接口。
 熔断器处于半开状态。 之后请求成功了则变为关闭状态。

HystrixCommand或HystrixObservableCommand 类  
  每个服务依赖关系维护一个小的线程池或信号量，已满则拒绝父亲请求。
  调用超时则降级处理。
  @HystrixCommand()
服务隔离解决的问题--大并发情况下防止某个节点问题影响整个调用链条，从而设置每个接口同时可接收的线程请求数。
服务降级--配置 fallbackMethod= 回调方法，方法中根据实际业务定义具体的降级逻辑。 异常的友好封装；降级方法也是有线程池处理的。
服务熔断-- 开启状态=请求被拦截了走降级方法、半开状态=服务不能达到后续接口，一段时间后在请求、关闭状态=请求可以到后续接口

扫描有HystrixCommand注解的方法的类生成Aop切面增强，最总再切面中完成拦截逻辑处理。 HystrixCommandAspect

#feign   声明式http客户端 与 Ribbon和Hystrix无缝集成 再封装 

@EnableFeignClients
spring-cloud-starter-feign依赖和@EnabledFeignClients注解，您可以使用一整套负载均衡器、断路器和HTTP客户端，并附带一个合理的的默认配置。
@FeignClient(value = "statistics-service")
public interface StatisticsServiceClient {
@RequestMapping(method = RequestMethod.PUT, value = "/statistics/{accountName}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
void updateStatistics(@PathVariable("accountName") String accountName, Account account);
} 
>>>@EnableFeignClients 注解中引入了FeignClientsRegistrar类，构造方法中注入BeanDefinition
扫描有@FeignClient注解的接口 最终 FeignClientFactoryBean类中生成有注解接口的代理类 getObject方法
代理实例直接调用到 HystrixInvoacationHandler的invoke()  invoke中会有负载均衡的逻辑处理，从服务列表中选择一个服务调用。
>>>


#网关  
>zuul  
1. 入口 zuulServlet 继承HttpServlet 
2. 调用链是由ZuulFilter来组成，对于用户而言，只需要关心如果构建自定义的ZuulFilter以及它们之间的顺序。
       FilterRegistry用户内存中管理过滤器的
3. zuulServlet的servie方法中有 前置过路由preRoute()方法 route()  postRoute()  后置路由
4. 由于底层是servlet，Zuul处理的是http请求
  Zuul的抽象写的非常简单易懂，易于扩展，易于debug
  提供了两种特殊的抽象类，用户使用起来，比较灵活
  zuul-core包不依赖Spring，依赖的包很少
  没有提供异步支持
  流控等均由hystrix支持
  
>gateway
1. 底层还是Servlet 基于WebFlux流式编程 GatewayAutoConfiguration、支持异步、集成了负载均衡过滤器 
2. filter、handler、locator  GlobalFilter
3. RateLimiter是一个接口，用户可以自行实现想要的限流策略及实现方式
提供了异步支持
提供函数式编程api，使用起来方便快捷
提供了抽象流控，并默认实现了RedisRateLimiter
提供了抽象负载均衡
支持HttpClient、WebClient代理请求

>区别





#分布式配置中心
加密接口： http://localhost:8761/encrypt?data=123456 
解密接口：http://localhost:8761/decrypt
SpringCloudConfig 不能动态刷新配置，需要手动调用/actuator/refresh接口，而且多台服务需要调用多次。
故 SpringCloud有bus消息总线的组件 用来发布订阅配置更新。
@RefreshScope注解动态刷新配置的原理是 每次扫描到有这个注解的类后重新实例化一个bean重新给属性赋值，从而实现
对象属性更新。  refresh作用域 自定义作用域scope

#权限认证    
认证模式：认证服务器审核客户端资质 得到 APPId appSecurt  
token获取方式
 1. 客户端  带上 APPId appSecurt 和scope
 2. 密码  通过用户名和密码获取token 和用户绑定关系。 比客户端的认证模式粒度更细
 3. 授权码  根据用户名和密码获取授权码code; 然后再根据授权码code获取token。  比上面两种方式安全性更高。
 
OAuth2.0 token      
1.客户端——getToken——认证服务器——返回token;
2. 客户端带上token请求网关-网关把请求路由到下游系统带上token
3. 下游系统把token提交到认证服务器认证token,认证通过返回用户信息对象 Principal；check接口放行。
#jwt  json web token---token 信息带上了用户信息
无状态的权限认证方式、






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
 实例 NacosServiceRegistry implements ServiceRegistry.register()开始服务注册流程。
 NacosServiceRegistry中构造方法注入了NamingService接口的实现类 NacosNamingService；
 NacosNamingService 中的init()方法中开启了一个HostReactor实例，该实例中开启了一个PushReceiver线程
 监听nacos服务端发送的udp消息，消息包括服务端 注册实例的变化、配置信息的变化实现配置实施生效。
 
 NacosNamingService服务中包含 
 心跳线程BeatReactor、  HostReactor接受服务端的消息线程、服务注册 registerInstance()

3. 注册过程中发布InstancePreRegisteredEvent 实例注册事件;然后实例化 NacosServiceRegistry对象开始注册流程,
    调用NacosNamingService#registerInstance()方法
4. 注册流程中主要是 发送两个http请求给nacos服务端,心跳请求、注册请求，把client实例信息封装成 Instance
5. 服务器端是先创建注册的服务、然后默认创建一个集群、然后集群中包含所有的实例列表。
6. 一个新的服务注册到nacos服务器后会开一个任务向其他节点同步新注册的服务。
服务消费者
7. 客户端启动后向nacos服务订阅需要的服务；然后再实际发生调用的时候查询服务列表，nacos则把当前客户端添加到内存的clientMap。
8. 服务端有事件发生时，nacos服务器会给客户端发送udp socket通知客户端事件内容，或新的服务列表。
9. 客户端每10秒读取nacos服务端查询服务注册列表更新map缓存; 同时服务端会把当前client添加到消息发送列表。
-----nacos服务端需要做的几件事:
10. nacos server接收到服务注册请求后数据写入，然后异步的通知其他节点，实现同步。
11. server节点启动是会判断集群节点是否大于1，如果大于1则会执行同步，否则等待1秒继续判断是否有其他节点。
12. distro协议判断一个请求属于某个节点是采用非常简单的hash算法，如果某个节点挂了会重新hash;可以采用一致性hash算法。
13. 客户端注册到server如果发送失败会自动切换到其他server节点。 
14. 健康检查 每个server节点 主动发送checkHealth 给其他节点，如果有response则标识响应节点是健康。

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
客户端每10秒读取nacos服务端查询服务注册列表更新map缓存;
ribbon实例化了多个Spring容器 容器根据不同的微服务区分，不同的服务放在不同容器中。
 
java -Dserver.port=8090 -Dcsp.sentinel.dashboard.server=localhost:8090 -Dproject.name=sentinel-dashboard -jar sentinel-dashboard.jar
#sentinel
  blockHandler和FallBack的降级区别
  blockHandler是触发流控规则后的降级响应。
  FallBack是系统异常后的降级响应。
 
# seata 分布式事物解决方案
缺课 5.12 5.14

