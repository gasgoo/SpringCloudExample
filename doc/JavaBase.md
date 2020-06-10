HashMap的实现原理和底层数据结构 ？？
HashMap和Hashtable的底层实现都是ArrayList数组+LinkedList链表结构实现  
除开HashMap和Hashtable外，还有一个hash集合HashSet，
有所区别的是HashSet不是key value结构，仅仅是存储不重复的元素，
相当于简化版的HashMap，只是包含HashMap中的key而已


hashMap put方法 根据 key做hash算法 得到 一个hashcode  
然后 hashCode%16 就会得到一个 0-15中的一个值 刚好 hashMap的初始长度是16（arrayList默认初始长度）.
key.hashCode()%16   & 运算 
key.hashCode>>>16  异或运算 得到一个位置 存储 entrySet
高16和低16位进行异或运算  

n-1&hash   =====hash%n（0-15）
都用二进制表示 	  保证node均匀分散

为什么是 resize 2倍？  需要是2的n次方 保证 node位置的分散性
resize对数组进行了实例化操作
原本数组下标为空 
链表大于8就转为红黑树   链表位置 要么

时间复杂度  O(n)
多线程扩容后进行rehash时会形成闭环链表 ？？  rehash的时候相同位置的节点和原来的顺序是相反的 

HashMap怎么解决碰撞问题的？

conCurrentHashMap(线程安全的高效的)
ConcurrentHashMap如何保证 线程安全？  内部有一个Segment段，它将大的HashMap切分成若干个段（小的HashMap），然后让数据在每一段上Hash，这样多个线程在不同段上的Hash操作一定是线程安全的
TreeMap



java的多态表现在哪里；
多态是基于继承关系的，就是把父对象设置为一个或多个子类对象的技术，主要实现方式：重载和重写


== 比较的是两个对象的地址   equals比较的是两个value

Java创建对象的几种方式:
(1) 用new语句创建对象，这是最常见的创建对象的方法。
(2) 运用反射手段,调用java.lang.Class或者java.lang.reflect.Constructor类的newInstance()实例方法。
(3) 调用对象的clone()方法。
(4) 运用反序列化手段，调用java.io.ObjectInputStream对象的 readObject()方法。
(1)和(2)都会明确的显式的调用构造函数 ；(3)是在内存上对已有对象的影印，所以不会调用构造函数 ；(4)是从文件中还原类的对象，也不会调用构造函数。


OutOfMemoryError异常的常见原因有以下几种:
1.一次从DB中查询太多的数据量； 代码中有死锁或重复生成大量的对象
2.集合中有对象的使用未清空，GC无法回收。
3. 启动参数的内存value设置太小。
	

PermGen space的全称是Permanent Generation space,是指内存的永久保存区域,	

消息通信:  同步的IO:  BIO=阻塞IO   NIO=非阻塞IO 事件驱动
异步IO:  AIO

IOC是指程序中对象的获取方式发生反转，由最初的new方式创建，转变为由第三方框架创建，注入。第三方框架一般是通过配置方式指定注入哪一个具体实现，从而降低了对象之间的耦合度 
Spring容器是采用DI方式实现了IOC控制，IOC是Spring容器的基础和核心 
DI全称是Dependency  Injection,被译为依赖注入 
DI主要有两种注入方式，即Setter注入和构造器注入 
静态工厂的方法注入   实例工厂方法

AOP:面向切面编程   AOP核心概念  需要额外引入的两个jar 
 aopalliance.jar  aspectjweaver.jar
 AOP底层使用动态代理实现。包括两种方式：

（1）使用JDK动态代理实现。

（2）使用cglib 来实现 

1、横切关注点  对哪些方法进行拦截，拦截后怎么处理
2、切面（aspect）
4、切入点（pointcut） 对连接点进行拦截的定义
5、通知（advice）   所谓通知指的就是指拦截到连接点之后要执行的代码，通知分为前置、后置、异常、最终、环绕通知
参考 aop.xml配置切面


Spring注解
@Autowired(required=false)当找不到需要注入的bean时不报错 可以快速启动
@Qualifier(value="beanName")当存在多个相同类型的bean时用这个 指定需要注入的bean 消除歧义


@Resource和@Autowired注解的区别
auto默认是根据类型注入的 结合@Qualifier 实现按名称注入

@Resource(name="baseDao")  按名称注入  ——属于j2ee的可以不用结合spring

@Autowired,@Inject,@Resource以及@Value等注解都是通过Spring的BeanPostProcessor的实现来实现自动装载的，也就意味着开发者不能够通过自己的BeanPostProcessor或者BeanFactoryPostProcessor来应用这些注解。开发者自己的类型必须通过明确的XML或者Spring@Bean方法来定义。

@PostConstruct 构造方法之后 init方法之前执行 

@PreDestroy 在servlet卸载之前 detroy之后执行
分页查询：
@ResponsPage参数 返回对象列表

@RequestParam(defaultValue="") String username 自动获取前端输入的参数  如前面的实例

@ModelAttribute 绑定请求参数到命令对象； 暴露功能处理方法的返回值为模型数据，用于视图页面展示时使用。
				

自定义注解方式：
@Interface 声明一个注解
public @Interface LogAnnotin{
}
@target 定义注解用在什么地方 可选的@Target(ElementType.method/FIELD/paramerter/package)
@Retention 定义注解的生命周期
如 @Retentin(RetentinPolicy.RUNTIME/CLASS/SOURCE)
@Inherited 子类是否能继承该注解

注解和反射有什么关系？？

反射）
  在运行时状态下 获取字节码Class对象，然后通过class获取对象的属性和方法并调用。
获取Class对象的方式:
 a. Object getClass()方法。
 b. 任何数据类型都有一个静态的 class属性
 c.Class的静态方法 forName(String classname)

查看系统可用cpu资源  Runtime.getRuntime().availableProcessors()

2）进程通讯的方式：消息队列，共享内存，信号量，socket通讯等；

3）用过并发包的哪些类；  底层就实现是volatile和CAS。整个并发包其实都是由这两种思想构成的。

6）为什么要用线程池；ExecutorService 线程池接口
服务端要求在短时间内处理 巨量的 耗时的线程，如果为每个请求新建线程然后销毁线程 这个过程很浪费资源，通过线程池去
管理线程的新建和销毁，减少 这个过程的次数，实现线程可复用，自动调节线程的数量。

7）volatile关键字的用法：使多线程中的变量可见；提供内存屏障
 保证可见性，某些情况下提供原子性  如 volatile 型的 long 或 double 变量的读写是原子操作。
 保证有序性  通过内存屏障禁止指令重排序。
 Synchronized 保证三个。
并发的 原子性 可见性 有序性


Java 中用到的线程调度算法是什么？ 如何上下文切换
抢占式调度算法 根据优先级 线程饥饿情况等数据算出一个总的优先级并分配下一个时间片给某个线程执行。

10）RPC原理和RPC实例分析：

11）SpringWeb项目启动 Listner filter servlet 启动
 Web.xml文件中的 配置DispatchServlet类路径

HandelMapping: URl和程序进行映射关联 先扫描出相关联的类  basePacking

13)例如：在浏览器地址栏键入URL，按下回车之后会经历以下流程：

　　1、浏览器向 DNS 服务器请求解析该 URL 中的域名所对应的 IP 地址;

　　2、解析出 IP 地址后，根据该 IP 地址和默认端口 80，和服务器建立TCP连接;

　　3、浏览器发出读取文件(URL 中域名后面部分对应的文件)的HTTP 请求，该请求报文作为 TCP 三次握手的第三个报文的数据发送给服务器;

　　4、服务器对浏览器请求作出响应，并把对应的 html 文本发送给浏览器;

　　5、释放 TCP连接;

　　6、浏览器将该 html 文本并显示内容; 　　


Http三次握手建立连接

第一次握手：建立连接时，客户端发送syn包（syn=j）到服务器，并进入SYN_SENT状态，等待服务器确认；SYN：同步序列编号（Synchronize Sequence Numbers）。
第二次
第二次握手：服务器收到syn包，必须确认客户的SYN（ack=j+1），同时自己也发送一个SYN包（syn=k），即SYN+ACK包，此时服务器进入SYN_RECV状态；
第三次
第三次握手：客户端收到服务器的SYN+ACK包，向服务器发送确认包ACK(ack=k+1），此包发送完毕，客户端和服务器进入ESTABLISHED（TCP连接成功）状态，完成三次握手。
完成三次握手，客户端与服务器开始传送数据，在上述过程中，还有一些重要的概念：




7） 分布式数据如何分开存放   并且需要比较均衡的分布到不同的节点上
 方法1:hash(key)%节点数    example:   key=raogugen  value=99999   hash(raogugen)%3=2

  方法2：一致性hash，hash算法结果是整形值，把整形值的范围作为圆环。+ 虚拟节点（150个虚拟节点）   保证平衡性 分散
			对节点某个属性做hash算法得到一个value
			再对key做hash算法得到value1  将这些value都映射到一个圆环上 按顺时针方向 key距离
			那个节点hash近，就分配到那个节点。  这样避免添加 删除节点影响所有的节点数据。


  接口和抽象类的区别？
  接口是定义动作的抽象，抽象类是定义对象的本质。
  接口中的属性只能是静态私有的，抽象类中的属性可以是普通的。
  接口中不能有非抽象的方法，抽象类中可以有抽象的和费抽象的方法。
  接口可以继承多个接口，但是抽象类只能继承一个类，类继承是单继承的。

1）内部类的获取方式
 a.如果内部类是静态内部类
直接 new innerClass()
 b.  TestInnerClass outerClass=new TestInnerClass();
        InnerClass innerClass=outerClass.getInnerClass();
        innerClass.hi();
c. InnerClass innerClass=new OuterClass().new InnerClass();

==和equals的区别
==比较的是堆内存的地址  地址相等则表明引用同一个对象。
equals比较的是对象或变量的内容

String str=new String("abc"); 
实例过程：
 堆中创建对象 abc,让str引用指向该对象。
 再字符串常量池中查看是否有abc字符串对象，没有则创建。
 如存在字符串abc，则将new出来的对象和常量池中的对象连接起来。

##JVM模型 
GC Roots 可达性分析  判断对象是否存活。
哪些对象可以当作根对象:  1. 虚拟机栈中引用的对象；2.方法区中的静态变量引用的对象  常量的引用对象。  3.本地方法栈中引用的对象。

引用计数器算法: 被用一次计数器就+1，但是循环依赖的对象没法回收。
---线程共享
方法区： 常量、类信息、静态变量、即时编译后的代码   永久代/元数据
堆:  对象实例 数组    年轻代(1/3)  年老代(2/3)
javap 反编译附加信息
---线程私有
虚拟机栈: 每个方法对应一个栈帧，栈帧中的内容：局部变量表、操作数栈、动态链接、返回地址
程序计数器：当前线程正在执行的字节码指令的地址
本地方法栈


#CMS收集器和G1收集器的区别  
cms过程: 初始标记、并发标记、重新标记、并发清除              -基于标记-清除算法
G1过程:  初始标记、根部区域扫描、并发标记、最终标记、并发清除   -基于标记-整理回收算法
共同点：都是为了减少停顿时间、并发收集、低停顿
区别:
cms会产生浮动垃圾 从而可能英法FUll GC、G1则不会;
G1把整个Yong区域划分成多个区域、不产生空间碎片  可以准确的控制停顿；

3）java虚拟机运行时数据区
-XX：+HeapDumpOnOutOfMemoryError  配置显示 内存溢出时的快照
8）如何排查死锁
用jps 和jstack  可以分别得到死锁的进程ID和 发生死锁的类位置
 

这些问题可以通过 top(cpu)、free(内存)、df(磁盘)、dstat(网络流量)、pstack、vmstat、strace(底层系统调用)。

jvm问题定位工具
jps查看进程
jstat -class/gc/complier 查看jvm类加载 gc信息
jmap 内存  jmap -heap pid 输出当前进程 JVM 堆新生代、老年代、持久代等请情况，GC 使用的算法等信息
jstack 线程堆栈查看
jinfo参数查看



6）类加载机制 
过程:   加载 链接包括（验证 准备 解析）  初始化  使用 卸载
加载 (classLoader)  

验证：验证原数据 字符码  文件格式 符号引用
准备： 分配内存 为类设置初始值
解析：是一个不确定的顺序过程  类 接口 字段等解析 从 接口 父类 开始查询   继承关系从上往下递归搜索
初始化：执行构造器 先执行父类在子类

那么Java.lang.NoSuchFieldError错误可能在什么阶段抛出呢？

很显然是在链接的验证阶段的符号引用验证时会抛出这个异常，或者NoSuchMethodError等异常。


类加载器：
bootstrapClassLoader   加载java核心类，负责加载lib/rt.jar中的class
extensions class loader 扩展加载器 加载JRE的扩展目录，lib/ext或者由java.ext.dirs系统属性指定的目录中的JAR包的类
application class loader）：被称为系统（也称为应用）类加载器  JVM启动时加载来自Java命令的-classpath选项、java.class.path系统属性

JVM的类加载机制主要有如下3种
全盘负责
双亲委派：所有类加载工作先用父类加载器加载，父类加载不了再子类继续加载。
缓存机制

7） tomcat的原理
1、用户点击网页内容，请求被发送到本机端口8080，被在那里监听的Coyote HTTP/1.1 Connector获得。
 2、Connector把该请求交给它所在的Service的Engine来处理，并等待Engine的回应。 
 3、Engine获得请求localhost/test/index.jsp，匹配所有的虚拟主机Host。
 4、Engine匹配到名为localhost的Host（即使匹配不到也把请求交给该Host处理，因为该Host被定义为该Engine的默认主机），
 名为localhost的Host获得请求/test/index.jsp，匹配它所拥有的所有的Context。Host匹配到路径为/test的Context（如果匹配不到就把该请求交给路径名为“ ”的Context去处理)
 5、path=“/test”的Context获得请求/index.jsp，在它的mapping table中寻找出对应的Servlet。Context匹配到URL PATTERN为*.jsp的Servlet,对应于JspServlet类。 
 6、构造HttpServletRequest对象和HttpServletResponse对象，作为参数调用JspServlet的doGet（）或doPost（）.执行业务逻辑、数据存储等程序。 
7、Context把执行完之后的HttpServletResponse对象返回给Host。 8、Host把HttpServletResponse对象返回给Engine。 
9、Engine把HttpServletResponse对象返回Connector。 
10、Connector把HttpServletResponse对象返回给客户Browser。
Connector 和 Container是两个核心组件
在Tomcat中有4种级别的容器：Engine，Host，Context和Wrapper。


8）  互联网缓存体系
分层 缓存
表示层：http协议缓存   静态文件缓存到本地
web层： Nginx CDN  静态资源缓存服务器     Nginx+keepalived
服务层:  本地缓存  分布式缓存  redis  memcahe 
数据库层: (二级缓存) 一级


6） 视图的作用：
视图是一个虚拟表 ，简化用户操作 数据安全保护

RSA非对称加密算法

7）SOA和RPC的区别？？

SOA 面向服务的架构 是一个独立的组件模型，通过服务接口把各个功能服务联系起来。

RPC远程过程调用协议 是 面向服务SOA的一种具体实现形式。  RPC采用的传输协议 TCP UDP等 

常用的RPC框架 

dubbo   协议和序列化框架都可以插拔是及其鲜明的特色   HSF
Thirft  facebook开源的跨语言的服务框架
gRPC  Google开源的  基于Http协议

9）
过滤器和拦截器的区别：

　　①拦截器是基于java的反射机制的，而过滤器是基于函数回调。
　　②拦截器不依赖与servlet容器，过滤器依赖与servlet容器。
　　③拦截器只能对action请求起作用，而过滤器则可以对几乎所有的请求起作用。

　　④拦截器可以访问action上下文、值栈里的对象，而过滤器不能访问。
　　⑤在action的生命周期中，拦截器可以多次被调用，而过滤器只能在容器初始化时被调用一次。
　　⑥拦截器可以获取IOC容器中的各个bean，而过滤器就不行，这点很重要，
在拦截器里注入一个service，可以调用业务逻辑。

自定义拦截需要实现 HandlerInterceptor接口 重写对应的方法。
mvc中需要在springmvc配置文件配置 <mvc:inteceptor>
 <mvc:interceptor>  
        <mvc:mapping path="/demo/hello.do"/>  
        <!-- 定义在 mvc:interceptor 下面的 Interceptor，表示对特定的请求进行拦截 -->  
        <bean class="com.hit.interceptor.LoginInterceptor"/>  
    </mvc:interceptor>  
	

过滤器好拦截器的区别：
过滤器是 容器tomcat之后 servlet之前的。chain.doFilter(request, response);这个方法的调用作为分水岭
拦截器是 包裹在过滤器中
SpringMVC的机制是由同一个Servlet来分发请求给不同的Controller，其实这一步是在Servlet的service()方法中执行的。



10）AOP用到两种动态代理实现拦截切入功能 -----JDK动态代理和cglib动态代理
JDK动态代理是反射机制实现，cglib代理底层是ASM实现。




java SPI 机制：--SPI 全称为 Service Provider Interface，是一种可扩展的服务提供和注册机制

本质是 将接口实现的类的全名配置在文件中，由服务加载读取配置文件 加载实现类；运行时动态未接口替换实现类。

cookie：保存在浏览器种，有大小限制，有状态；
session：保存在服务器中，服务器有资源开销，分布式、跨系统不好实现；
Token：客户端可以将Token保存到任何地方，无限制，无状态，利于分布式部署。

泛型:
上限  Object<? extends class> object  所有class的子类，不能大于class，都是class的字类
下线  Object<? super class> Object   所有class的父类对象 下线是class





 