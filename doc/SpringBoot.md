java SPI 机制的使用 加载外部文件 

#SpringBoot容器对象
AnnotationConfigServletWebServerApplicationContext

#@SpringBootApplication代表的含义：

1. 自动配置 @Confinguration IOS容器配置类 javaConfig配置类 注册bean定义

2.扫包启动并加载到ios容器中 @ComponentScan 默认是同级包和下面的子包范围 也可以指定范围 basePackages=""
ConfigurationClassPostPrccessor.class 处理自动扫描的逻辑

3.@EnableAutoConfiguration  开启自动配置功能
借助@Import的支持，收集和注册特定场景相关的bean定义。 所有符合自动配置条件的bean定义加载到IoC容器 
当前路径下的所有子路径（@AutoConfigurationPackage） AutoConfigurationImportSelector 
org.springframework.context.annotation.ImportSelector.selectImports返回String[] 数组内容为 
所有需要动态注入IOC容器的Bean类全路径。 


SpringFactoriesLoader实际过程; 从classpath中搜寻所有的META-INF/spring.factories配置
##为什么配置了META-INF/spring.factories配置文件就可以加载？这里才是springboot实现starter的关键点
 springboot的这种配置加载方式是一种类SPI（Service Provider Interface）的方式， 
 getSpringFactoriesInstances 加载spi配置文件中的配置类
 
 SPI可以在META-INF/services配置接口扩展的实现类，springboot中原理类似，只是名称换成了spring.factories而已。

org.springframework.boot.autoconfigure.EnableAutoConfiguration=com.example.xxx.Xxx 属性 key value配置方式 key是接口全名 value是接口的实现类全名称

这样在springboot启动时，才能正确加载自定义starter的配置 starter中简单来讲就是引入了一些相关依赖和一些初始化的配置

第三方的jar 
SpringFactoriesLoader工厂加载机制 loadFactories加载 把类Class加载到ioc容器。

@Configuration @EnableAutoConfiguration @ComponentScan

#自定义starter启动器

#SpringApplication的执行过程 SpringApplication.run();  启动容器、部署到tomcat、发布启动事件ApplicationStartedEvent

SpringApplicationRunListener的environmentPrepared()的方法 
创建ApplicationContext对象 查找 ApplicationContextInitializer.initialize();

org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext.createWebServer
ServletWebServerFactory.getWebServer()
OnRefresh方法冲 new tomact 然后阻塞 await()




1）Springboot打成war包步骤；
pom文件添加打包插件配置 添加外部的tomcat依赖jar，
排除内置的tomcat org.springframework.boot spring-boot-starter-tomcat 2.1.3.RELEASE provided
 修改启动类继承SpringBootServletInitializer类重写configure方法 provided表示在编译和测试时使用 
 2） 整合mybatis使用druid连接池的步骤 1.添加 mybatis ,druid依赖jar包 
 2. yml文件中添加数据源配置 url中需要添加时区 url: jdbc:mysql://localhost:3306/boottest?useUnicode=true&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&characterEncoding=UTF-8&serverTimezone=GMT

3.添加SpringBootConfiguration 注解的 Bean读取配置

3） 虚拟机设置读取环境的配置 -Dspring.profiles.active=dev

4）SpringBoot添加拦截器的方式

#拦截器案例
写一个SpringBootConfiguration注解的 类继承 WebMvcConfigurationSupport重写 添加拦截器的方法。

#Springboot整合https   生产环境需要申请
1. 生成本地安全证书  cd 到jdk的 bin目录执行命令
keytool -genkey -alias spring -keypass 1123456 -keyalg RSA -keySIZE 1024 -validity 365 -keystore
E:/springboot.keystore -storepass 123456

2. application.yml文件中配置
  server.ssl.key-password=123456
  server.ssl.key-store=path
  server.ssl.key-alias=spring

# atomikos  操作多个数据源 分布式事物



