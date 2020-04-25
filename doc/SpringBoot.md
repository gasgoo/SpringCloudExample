java SPI 机制的使用

#@SpringBootApplication代表的含义：

1. 自动配置 @Confinguration IOS容器配置类 javaConfig配置类 注册bean定义
2.扫包启动并加载到ios容器中 @ComponentScan 默认是同级包和下面的子包范围 也可以指定范围 basePackages=""
3.@EnableAutoConfiguration
借助@Import的支持，收集和注册特定场景相关的bean定义。 所有符合自动配置条件的bean定义加载到IoC容器 
当前路径下的所有子路径（@AutoConfigurationPackage） AutoConfigurationImportSelector 
org.springframework.context.annotation.ImportSelector.selectImports返回String[] 数组内容为 
所有需要动态注入IOC容器的Bean类全路径。 
SpringFactoriesLoader实际过程; 从classpath中搜寻所有的META-INF/spring.factories配置
====为什么配置了META-INF/spring.factories配置文件就可以加载？这里才是springboot实现starter的关键点
 springboot的这种配置加载方式是一种类SPI（Service Provider Interface）的方式， 
 SPI可以在META-INF/services配置接口扩展的实现类，springboot中原理类似，只是名称换成了spring.factories而已。

org.springframework.boot.autoconfigure.EnableAutoConfiguration=com.example.xxx.Xxx 属性 key value配置方式 key是接口全名 value是接口的实现类全名称

这样在springboot启动时，才能正确加载自定义starter的配置 starter中简单来讲就是引入了一些相关依赖和一些初始化的配置

SpringFactoriesLoader工厂加载机制 loadFactories加载

@Configuration @EnableAutoConfiguration @ComponentScan

====SpringApplication的执行过程 SpringApplication.run();

SpringApplicationRunListener的environmentPrepared()的方法 
创建ApplicationContext对象 查找 ApplicationContextInitializer.initialize();

1）Springboot打成war包步骤；
pom文件添加打包插件配置 添加外部的tomcat依赖jar，
排除内置的tomcat org.springframework.boot spring-boot-starter-tomcat 2.1.3.RELEASE provided
 修改启动类继承SpringBootServletInitializer类重写configure方法 provided表示在编译和测试时使用 
 2） 整合mybatis使用druid连接池的步骤 1.添加 mybatis ,druid依赖jar包 
 2. yml文件中添加数据源配置 url中需要添加时区 url: jdbc:mysql://localhost:3306/boottest?useUnicode=true&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&characterEncoding=UTF-8&serverTimezone=GMT

3.添加SpringBootConfiguration 注解的 Bean读取配置

3） 虚拟机设置读取环境的配置 -Dspring.profiles.active=dev

4）SpringBoot添加拦截器的方式

写一个拦截器
写一个SpringBootConfiguration注解的 类继承 WebMvcConfigurationSupport重写 添加拦截器的方法。
注册中心 eureka启动 @SpringCloudApplication @EnableEurekaServer 配置文件 eureka.server.instance eureka.client.registerWithEureka
添加 服务提供者(客户端)到注册中心
@EnableDiscoveryClient  开启服务发现

feign 服务消费者 自带负载均衡功能
@EnableFeignClients 声名使用openFeign
Hystrix 组件，当特定的服务不可用达到一个阈值（Hystrix 默认 5 秒 20 次）

Spring cloud Config   @RefeshScope 自动刷新配置
Spring cloud bus 消息总线  实现在集群中传播一些状态变化
常和 config一起使用实现热部署  如配置自动刷新
Actuator 监控微服务
研究微服务链路追踪 zipKin