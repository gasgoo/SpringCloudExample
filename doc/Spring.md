spring-boot配置
http://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html
https://www.cnblogs.com/ITtangtang/p/3978349.html 源码解读
https://springcloud.cc/  


spring IOC本质上就是一个大的map  
ioc：把自己创建对象和维护依赖对象的过程交给外部容器。
DI：依赖注入； 在对象运行期间动态的将依赖的对象注入到对象中。
方式：
构造器和setter方法注入方式
注解注入
IOC的核心部分是 解析XML文件，注册bean  ，对于有依赖对象的bean则利用反射给setter方法赋值。
  xml配置解析+反射
  
  
##Spring容器初始化 

 定位-加载-注册-初始化
  实例化Spring容器--> 扫描类-->解析类-->实例化BeanDefinition-->注册BeanDefinition 放到一个map中(beanName,DeanDef)
  调用bean工厂的后置处理器--验证
  验证:如判断这个bean是否是Lazy，是否prototype多列模式，是否是抽象类等。
  
 
  
***bean周期开始***

推断构造方法-通过反射实例化对象- 缓存、注解信息-暴露一个bean工厂--判断bean是否需要完成属性的注入
完成属性注入- 回调Aware接口--生命周期回调方法(@PostConstruct)初始化--完成代理AOP--put单例池 ---销毁对象

实例化---属性填充---初始化---销毁

Spring中bean的生成步骤:
1. 扫描指定的包路劲下的class
2.根据class信息生成beanDefinition 注册到BeanDefinion的Map中 可以用工厂后置处理修改BD
3. 根据BeanDefinition生成bean实例
4.生成的bean注册put到IOC容器中


IOC体系结构  
1) BeanFactory Bean工厂--帮助开发者管理对象间的依赖关系提供了基础服务
   BeanFactory 的中定义了 基本的行为 如  getBean()  getType()
DefaultListableBeanFactory  默认实现 实现了其他的三个接口

 具体的生产bean对象的细节则有不同的IOC容器实现 如
 XmlBeanFactory
 ClasspathXmlApplicationContext
 ApplicationContext   高级的IOC容器

2) BeanDefinition  Spring扫描类解析类信息实例化 BeanDefiniton对象，
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



===============AOP原理：
  编译器织入
  类加载时织入
  运行时织入（AOP是运行时织入） 如何实现？代理模式
  代理模式
  静态代理：
  动态代理：
		jdk代理 实现InvocationHandler接口 利用反射生成代理对象字节码文件。 只能针对有接口的类做代理。
  Proxy.newProxyInstance获取代理对象
  Cglib代理：实现MethodInterceptor接口
      不能对static private 方法代理。
Spring如何创建动态代理Bean？？
		1. 如果目标对象实现了接口则默认采用JDK代理
		2.如果没有实现接口则采用Cglib代理。
		3. 如果实现了接口且强制使用cglib则使用cglib代理。
多个AOP如何叠加调用？？
   责任链模式

 @Around("com.demo.*.*.(..)")
 @Around("@Annoation(注解包全路径)") 




===SpringMvc也是一个map
===

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

===如何把一个对象bean交给Spring管理
1.@Bean
2.FactoryBean

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



