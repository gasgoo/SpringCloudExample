spring-boot配置
http://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html
https://www.cnblogs.com/ITtangtang/p/3978349.html 源码解读

##Spring容器初始化 

 定位-加载-注册-初始化
  实例化Spring容器--> 扫描类-->解析类-->实例化BeanDefinition-->注册BeanDefinition 放到一个map中(beanName,DeanDef)
  调用bean工厂的后置处理器--验证
  验证:如判断这个bean是否是Lazy，是否prototype多列模式，是否是抽象类等。
  
  
#bean周期开始*** 实例化过程
AbstractAutowireCapableBeanFactory.createBean()
AbstractAutowireCapableBeanFactory.doCreateBean()

AbstractAutowireCapableBeanFactory.createBeanInstance()---
AbstractAutowireCapableBeanFactory.instantiateUsingFactoryMethodfatcory()-method工厂的实例化对象、
AbstractAutowireCapableBeanFactory.determineConstructorsFromBeanPostProcessors() 构造函数的处理
首次调用beanPostProcessor,会扫描找出所有带注解的构造函数然后完成装配
  有参和无参构造函数的实例化
AbstractAutowireCapableBeanFactory.applyMergedBeanDefinitionPostProcessors()
 ---后置处理器执行  收集各种带有注解的方法或属性  放入InjectionMetaData的一个set容器中。
 ---循环依赖处理---只会出现在单例无参构造函数实例化的情况  **非单例 有参的循环依赖都会抛出异常**
 addSingletonFactory() --从创建中的bean对象缓存中取对象注入依赖的对象
   正在创建的对象缓存(singletonsCurrentlyInCreation)---作用 用来阻断循环依赖  
   
 ---属性注入  根据上面收集的各种注解 如@PostStructured @Autowired
 AbstractAutowireCapableBeanFactory.populateBean() 依赖注册 属性填充
 ---初始化  initializeBean()  顺序如下 
 aware类型的接口调用、@PostContruct 、InitializingBean.AfterPropertiesSet() 、 initMethod()
 InitializingBean  类实例化之后做一些事情的接口 加载预热

 ---注册对象销毁
单例对象放入对象销毁池 disposableBeans  注册一个销毁bean的对象 DisposableBeanAdapter
@PreDestory
---
##bean周期开始end

推断构造方法-通过反射实例化对象- 收集注解信息-暴露一个bean工厂--判断bean是否需要完成属性的注入
完成属性注入- 回调Aware接口--生命周期回调方法(@PostConstruct)初始化--完成代理AOP--put单例池 ---销毁对象

实例化---属性填充---初始化---销毁

#@Autowired注解原理  依赖注入
Spring容器启动时会注册 AutowiredAnnoationBeanPostProcessor对象；
扫描所有bean如果带有@Autowired注解，将该bean和注解信息封装到InjectionMetadata对象中
bean在实例化和初始化时，会调用BeanPostProcessor对bean初始化 然后注入依赖的bean；
如果注入的时引用类型则会触发 getBean()调用

##Spring容器启动细节: refreshBeanFactory  其中涉及 模板模式 委托模式、装饰者模式
org.springframework.context.support.AbstractApplicationContext.refresh

1.实例化bean工厂 DefaultListableBeanFactory=createBeanFactory()
2.加载xml配置文件 XmlBeanDefinitionReader.loadBeanDefinitions() 
ResourceLoader加载配置文件抽象为Resource资源,资源编码为InputResource
经过 XmlBeanDefintionReader.registerBeanDefinitions()解析文件资源变为Document  
BeanDefinitionDocumentReader.registerBeanDefinitions()
解析xml文件 DefaultBeanDefinitionDocumentReader.registerBeanDefinitions()委托给
BeanDefinitionParserDelegate.parseBeanDefinitionElement()
 自定义标签和默认标签
3. xml标签解析  
DefaultBeanDefinitionDocumentReader.parseBeanDefinitions
解析其他标签设置BeanDefinition的属性 如 meta、 lookup-method、replace-method最后返回 bd对象。
BeanDefinitionParserDelegate.parseMetaElements
parseLookupOverrideSubElements()
 bean标签的子标签解析出来后设置到 BeanDefinitin 中使用了装饰者模式。
4.解析出来的bean标签放入BeanDefinitionMap中,beanName添加到beanDefinitionNames
DefaultListableBeanFactory.registerBeanDefinition()
5. BeanDefinitin 对象有了可以开始 Bean的实例化

#自定义标签解析 Namespacehandler接口
每个标签都对应一个解析类。 spring.handlers文件中配置了命名空间uri和标签处理类的对应
加载配置文件 spi的思想。
解析到标签然后通过标签取到对应的标签处理类。
ContextNamespaceHandler 自定义的一些标签和对应的处理类
最终要调用NameSpaceHandler.init()方法，其中表明了标签对应的标签处理类。
<context:component-scan base-package="com.xx">

## @ComponentScan  @Bean @import 导入一个类   @importResources导入一个xml文件  注解的支持
@Bean =Factory-mehtod 
ConfigurationClassPostProcessor

#AOP 原理解读
<aop:aspectj-autoproxy >开启注解aop配置
@EnableAspectJAutoProxy 注解中 @Import(AspectJAutoProxyRegistrar.class)
  AbstractAutoProxyCreator
  AOP入口类是什么时候注入到容器中的?
  1. 解析自定义标签  aopNameSpacesHandler init()方法中初始化了标签对应的解析类。
  AspectJAwareAdvisorAutoProxyCreator 基于xml aop:config标签
  2. aop注解 @EnableAspectJAutoProxy的解析类
  AnnotationAwareAspectJAutoProxyCreator 注册了这个类到容器中。
   代理实例放入一级缓存,并不是被代理bean
ap过程:   
1.找到所有的beanDefinition对象的beanName，拿到class对象判断上面是否有切面@AspectJ注解有则是收集的。
2.遍历收集的Class在类中搜索带有 通知类型注解的方法如@Before @Around @After @AfterReturning @AfterThrowing
并且把注解信息 如表达式 argnames 注解类型等信息封装成AspectJAnnotation；
3. 创建pointCut切入点对象，然后再创建Advice增强；不同的通知类型有不同的增强。  把PointCut和Advice封装成 Advisor对象。
4. 找到拦截当前bean的切面；即通过PointCut表达式匹配 如果匹配上则创建当前bean的代理对象。
  
  JDKDynamicAopProxy类实现了 InvokeHandler接口，代理类最终是通过执行invoke方法执行代理类的方法。
  @EnableAspectJAutoProxy---import(AspectJAutoProxyRegistrar)
  注解aop配置类    AnnotationAwareAspectJAutoProxyCreator
 代理实例放入一级缓存,并不是被代理bean.
  
  AnnotationTransactionAttributeSource  封装事物属性
  DataSourceTransactionManager对象 -Spring事物对象
  
ProxyTransactionManagementConfiguration 
##aop end

##Spring Transaction
@EnableTransactionManagement注解 中导入 @Import TransactionManagementConfigurationSelector类。
Selector类中 注册了 
ProxyTransactionManagementConfiguration 事物管理配置类
事物传播属性: Propagation
Required  当前没有事物则new 事物，如果已经存在则 加入事物中   1 default
supports  支持当前事物，没有事物则以非事物方式运行
Manadtory  使用当前事物，如没有则异常
required_new 新建事物 如果存在事物 把当前事物挂起    2
Not_supports   非事物方式运行
NEVER     非事物方式运行 存在事物则异常   
Nested    支持嵌套事物            3  同一个连接
 
编程事物： 手动开启一个事物  可以自己控制锁的粒度
DefaultTransactionDefinition  db=new DefaultTransactionDefinition();
db.setPropagationBehavior(0)设置传播属性
PlatformTransactionManager.getTransacation(db);

## Transaction end

IOC体系结构  
1) BeanFactory Bean工厂--帮助开发者管理对象间的依赖关系提供了基础服务
   BeanFactory 的中定义了 基本的行为 如  getBean()  getType()
DefaultListableBeanFactory  默认实现 实现了其他的三个接口

 具体的生产bean对象的细节则有不同的IOC容器实现 如
 XmlBeanFactory
 ClasspathXmlApplicationContext
 ApplicationContext   高级的IOC容器

# BeanDefinition  Spring扫描类解析类信息实例化 BeanDefiniton对象，
IOC管理了我们定义的各种bean对象和对象的关系，bean对象在Spring中是以BeanDefinition来描述的；
bean解析就是对配置文件的解析， 涉及到的类：
 BeanDefinitionReader
 AbstractBeanDefinitionReader
 BeanDefinitionDocumentReader
 DefaultBeanDefinitionDocumentReader
 XmlBeanDefinitionReader


FileSystemXmlApplication
ClassPathXmlApplication
XmlWebApplication
上下文可以嵌套，先检查当前上下文，然后逐级向父级查找bean
Class 在Spring容器中变为 bean的过程
Class-beanDefinition-put到Map中---BeanDefinition--new --bean



#AOP原理：
  编译器织入
  类加载时织入
  运行时织入（AOP是运行时织入） 如何实现？代理模式
  代理模式
  静态代理：
  动态代理：
jdk代理 实现InvocationHandler接口 利用反射生成代理对象字节码文件。 只能针对有接口的类做代理。
 Proxy.newProxyInstance获取代理对象
 Cglib代理：实现MethodInterceptor接口  生成被代理类的子类
      不能对static private 方法代理。
Spring如何创建动态代理Bean？？
		1. 如果目标对象实现了接口则默认采用JDK代理
		2.如果没有实现接口则采用Cglib代理。
		3. 如果实现了接口且强制使用cglib则使用cglib代理。
多个AOP如何叠加调用？？
   责任链模式

 @Around("com.demo.*.*.(..)")
 @Around("@Annoation(注解包全路径)") 




#SpringMvc也是一个map



spi思想读取配置文件 META-INF/services 文件中的SpringServletContainerInitializer类然后执行 OnStartUp方法
分别启动 ContextLoaderListener  和DispatcherServlet 两个父子容器

DispatcherServlet extents ---Servlet 启动初始化 HttpServletBean.init()最终到子类
org.springframework.web.servlet.FrameworkServlet.initWebApplicationContext方法中
configureAndRefreshWebApplicationContext()方法-容器启动方法 refresh();


运行流程：  DispatchServlet 是调度中枢
 1. 用户发送请求到DispatchServlet前端控制器
 2. DispatcherServlet收到请求调用handlerMapping处理器映射器
 3. 处理器映射器(根据注解url xml配置查询)把url映射到处理器（controller）对象和拦截器返回给DispatchServlet
 4. DispatchServlet请求HandlerAdapter把url映射到方法执行handler
 5. 执行contrller后端处理器(handler)
 6. controller返回ModelAndView
 7. HandlerAdapter将ModelAndView返回给DispatcherServlet
 8. DispatchServlet将视图对象发送给ViewReslover试图解析器
 9. DispatchServlet对view进行渲染填充model数据转为response 返回给用户。


SpringMVC设计思路：
1. 读取web.xml配置 
2. 扫描用户配置包下面所有的类
3. 拿到扫描的类，通过反射实例化 放到ioc容器中 map(beanName,bean)
4. 初始化handlerMapping 把url和method对应放到Map中供后续使用
5. 通过反射调用方法

DispatcherServlet机制和原理
@Contrller   @requestMapping	@requestParam
@Service @Component 是把类注入到IOC容器中

#Springmvc end
org.springframework.web.SpringServletContainerInitializer    spi加载 tomcat容器初始化
@HandlerTypes注解会加载所有实现了WebApplicationInitializer 接口的类 然后调用 
   AbstractContextLoaderListenerInitializer.onStartup()
   AbstractDispatcherServletInitializer.onStartUp方法
AnnotationConfigWebApplicationContext
mvc启动流程 
1.ContextLoader.initWebApplicationContext  启动Spring容器 注册 ContextLoaderListener监听器
2. Servlet的初始化 HttpServlet的 init()方法 创建 web容器注册 DispatcherServlet


参数解析器 HandlerMethodArgumentResolver 很多不同的参数解析实例


#如何把一个对象bean交给Spring管理
1.@Bean
2.FactoryBean
3. beanFactory.registerSingleton()
4. xml配置bean标签方式 不推荐使用
5. 常用的注解@Component @Service @Controller等间接方式


FactoryBean 是Spring提供的工厂bean的一个接口 定制化bean的创建逻辑。
 FactoryBean接口有三个方法用来创建对象
 getObject(); 返回这个FactoryBean所创建的对象
 getObjectType(); 返回这个FactoryBean所创建的对象的类型
 isSingleton()
 
 factoryBean 和BeanFactory的区别？
FactoryBean是个bean，在IOC容器的基础上给Bean的实现加上了一个简单工厂模式和装饰模式，是一个可以生产对象和装饰对象的工厂bean，
由spring管理后，生产的对象是由getObject()方法决定的（从容器中获取到的对象不是“ FactoryBean ” 对象）。
getObject('name')返回工厂中的实例
getObject('&name')返回工厂本身的实例


BeanFactory是个bean 工厂，是一个工厂类(接口)， 它负责生产和管理bean的一个工厂
是ioc 容器最底层的接口，是个ioc容器，是spring用来管理和装配普通bean的ioc容器（这些bean成为普通bean）。




Spring 
BeanDefinitionRegistryPostProcessor implements BeanFactoryPostProcessor
BeanFactoryPostProcessor 和 BeanPostProcessor 这两个接口都是初始化bean时对外暴露的入口之一
 bean工厂的后置处理容器，说通俗一些就是可以管理我们的bean工厂内所有的beandefinition（未实例化）数据，可以随心所欲的修改属性。
 BeanFactoryPostProcessor   容器初始化之后  实例化之前 这期间。
 在应用程序上下文的标准初始化之后修改它的内部bean工厂  
 是实现spring容器功能扩展的重要接口，例如修改bean属性值，实现bean动态代理等
 可以再容器未实例化任何bean之前读取修改bean属性， 可以设置 order定义多个 BeanFactoryPostProcessor的
 执行顺序。
 postProcessBeanFactory()方法只会执行一次。


 bean 生命周期====总结上述过程， 我们可以得到以下执行顺序 ：  
 BeanFactoryPostProcessor ---> 普通Bean构造方法 ---> 设置依赖或属性(属性注入) 
 --->BeanPostProcessor--->@PostConstruct(初始化注解) ---> InitializingBean接口 ---> xml配置中的initMethod
  

 BeanPostProcessor  SpringBean后置处理器,IOC容器完成bean实例化后，在初始化前后添加一些逻辑。
 接口有两个方法  初始化之前和之后做一些逻辑处理
 postProcessBeforeInitialization(Object bean,String beanName);
 postProcessAfterInitialization(Object bean,String beanName);



