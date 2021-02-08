#jvm
线程私有： 线程结束则回收
>程序计数器
1. 为什么需要程序计数器?
    java是多线程的，意味着多线程切换，确保多线程的情况下线程正常运行。
2. 唯一没有oom的区域。
>虚拟机栈  设置大小的参数 -Xss(1M)
1. Stack -数据结构  先进后出的特点
2. 为什么需要栈？
    兼容方法调用方法的特点 
    栈帧细分如下：
3. 操作数栈帧  方法的调用栈
4. 动态链接
5. 局部变量表
6. 返回地址
7. 反编译命令： javap -v xxx.class > a.txt
>本地方法栈

线程共享：
>堆   -Xms；最小的size  -Xmx； 最大的  -Xmn；新生代的大小  -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemeryError
1. 实例变量 int count=0;
2. 运行时常量池   字面量 、符号引用
3. 新生代  eden、s1、 s2 区
4. 老年代 

>方法区---元空间
1. 类信息
2. 常量  final String ss="the value"
3. 静态变量 static String st=";
4. 1.8去除永久代 使用元数据区 则避免元数据导致的fullGC和OOM，元数据区的空间只受制于内存
5. 常量池、符号引用-直接引用

Object obj=new Object(); 
#jvm遇见new指令时：
1. 检查类加载
2. 分配内存   指针碰撞、空闲列表 两种分配方式 ，内存分配线程安全有CAS处理，失败重试保证分配成功。
    TLAB-线程本地分配缓冲 eden区1%的空间、 
3. 对象空间初始化   int =0  boolean=false;  
4. 对象访问方式 句柄访问-系统管理的引用标识  直接指针访问-包含引用对象的内存地址
5. 对象内布局： 对象头(MarkWorld、类型指针、数组长度)、  实例数据    、对齐填充(为了满足对象大小是2的N次方)
    markWord: 哈希码、GC分带年龄 4位、锁状态标识 2位 持有锁的线程ID 线程持有的锁
    
#对象内存分配原则：
1. 优先分配eden区
2. 大对象直接分配老年代  如 超过配置的值则认为是大对象。
3. 长期存活的对象直接分配老年代  年龄15进入老年代
4. 动态判断对象年龄进入老年代
5. 空间分配担保 
6. 满足逃逸分析或标量替换还可以再栈上分配

#垃圾回收和回收算法
GC Roots 可达性分析  判断对象是否存活。哪些对象可以当作根对象:  
1. 虚拟机栈中引用的对象；2.方法区中的静态变量引用的对象  常量的引用对象。  3.本地方法栈中引用的对象。

1. 引用计数法
2. 可达性分析 GCRoots
3. 引用类型
强引用 ==
软引用 SoftReference<Object> soft=new SoftReference(obj);  即将OOM时回收
弱引用 WeakReference   GC时则回收。
虚引用 PhantomReference
>什么时候发生回收？
1. 新生代内存不足时 发生 minor GC  YoungGC
2. 老年代内存不足时 发生 MajorGC   Old GC  
3. Full GC 则包括 堆区 方法区 
> 回收机制
1. 复制算法
    优点：简单高效、不会出现内存碎片   缺点：内存利用率低 只用一半、存活对象多时效率低
    使用在新生代 eden-s1
2. 标记清除算法
   有碎片 效率相对复制更低。 标记可达 清除不可达
3. 标记整理算法
   没有碎片 内存利用率高、内存块的移动
>收集器  内存占用、吞吐量、延迟
 1. **Serial** 单线程的新生代 复制算法的收集器、  Serial Old 单线程的老年代 标记整理算法收集器
 2. **Paralled** Scavenge 并行的新生代复制算法收集器、  Paralled OLD  并行的老年代标记整理算法收集器
 3. **cms** 老年代的标记清楚算法收集器 并行并发
    初始标记、并发标记、重新标记、并发清除   标记stop theWorld  追求最短的暂停时间为目标
    耗费CPU资源、产生浮动垃圾、内存碎片
 4. **G1** 跨新生代和老年代的复制+标记整理+ 划分多个区域的并发收集。 
    初始标记、并发标记、最终标记、并发回收
    内存空间整合，不会产生内存碎片、可预测的停顿 
    
    并发标记中采用三色标记 黑灰白
    黑色：已经标记过； 灰：正在被标记；白色：还没有被标记
    对于漏标 G1采用的快照对比的方式
    
     
 5. 内存泄漏的原因：
    内部类持有外部类、连接未关闭、变量作用域不合理、
 6. 内存溢出的情况
    申请内存的时候内存不足抛出内存溢出异常、
    栈溢出 StackOverFlowError
    OOM: java heap over 
    OOM: GC OverHead Limit exceeded 频繁的FullGC但是没有回收
 7. 安全点： 方法调用、循环跳转、异常跳转、这些指令才会产生主动中断。
 8.  低延迟的垃圾回收
    Eplison 不干活的垃圾回收器
    ZGC  染色指针  类似于G1的Region
    Shenandoah
   

 >性能调优
 1. 前端优化： 减少请求请求合并、  keepAlive 、客户端缓存(Cache-control Expires)、启用压缩、 文件加载顺序
            减少cookie传输、
 2. CDN加速  反向代理缓存、Web组件分离
 3. 应用服务器优化
    缓存、集群、异步、代码优化
    
>问题定位的jvm命令

-XX：+HeapDumpOnOutOfMemoryError  配置显示 内存溢出时的存储堆内存快照
8）如何排查死锁
用jps 和jstack  可以分别得到死锁的进程ID和 发生死锁的类位置   
 
这些问题可以通过 top(cpu)、free(内存)、df(磁盘)、dstat(网络流量)、pstack、vmstat、strace(底层系统调用)。

jvm问题定位工具
jps查看进程
jstat -class/gc/complier 查看jvm类加载 gc信息
jmap 内存  jmap -heap pid 输出当前进程 JVM 堆新生代、老年代、持久代等请情况，GC 使用的算法等信息
jmap -dump:live,format=b,file=king.bin pid  生成pid进程的内存堆快照

jstack 线程堆栈查看
jinfo参数查看

双亲委派模型的破坏
tomcat webAppClassLoader    
线程上下文加载器 appClassLoader 
spi
#Tomcat中类加载顺序
1. 使用bootstrap引导类加载器加载
2. 使用system系统类加载器加载
3. 使用应用类加载器在WEB-INF/classes中
4. WEB-INF/lib
5. common classLoader 加载CATALINA_HOME/lib


#类加载机制 
过程:   加载 链接包括（验证 准备 解析）  初始化  使用 卸载
加载 (classLoader)  

验证：验证原数据 字符码  文件格式 符号引用
准备： 分配内存 为类设置初始值
解析：是一个不确定的顺序过程  类 接口 字段等解析 从 接口 父类 开始查询   继承关系从上往下递归搜索

初始化：执行构造器 先执行父类在子类 static 执行 

那么Java.lang.NoSuchFieldError错误可能在什么阶段抛出呢？
很显然是在链接的验证阶段的符号引用验证时会抛出这个异常，或者NoSuchMethodError等异常。

类加载器：
bootstrapClassLoader   加载java核心类，负责加载lib/rt.jar中的class
extensions class loader 扩展加载器 加载JRE的扩展目录，lib/ext或者由java.ext.dirs系统属性指定的目录中的JAR包的类
application class loader）：被称为系统（也称为应用）类加载器  JVM启动时加载来自Java命令的-classpath选项、java.class.path系统属性

JVM的类加载机制主要有如下3种
全盘负责
双亲委派：所有类加载工作先用父类加载器加载，父类加载不了再子类继续加载。
类加载有优先级的层次关系，保证java运行的稳定
缓存机制
 
##Arthas 使用命令
java -jar arthas-boot.jar --target-ip 0.0.0.0

sysprop 打印所有的System properties的信息

jad com.xxx.xx.classname  反编译代码
ognl '@java.lang.System@out.println("hello ognl")'  调用static函数
sc 查找类的 classloader hash
watch com.xxx.class * '{params,throwExp}'   查看异常函数
>>在线热更新代码的步骤>>
jad --source-only com.xxx.UserController >/tmp/userController.java 反编译类保存到某个路径
然后 vi编辑 反编译后的 java文件.
sc -d *userController |grep classLoaderHash 查找类的ClassLoader
mc(memory Compiler)命令来编译 并指定classLoaderClass
mc --classLoaderClass org.springframework.boot.loader.launchedURlClassLoader /tmp/UserController.java -d /tmp
redefine /tmp/com/example/demo/arthas/user/UserController.class 重新加载新编译的class
>
>
##k8s  部署应用
准备镜像-deployment.yaml文件编写
Kubectl apply -f ~/deploy.yaml 部署应用
kubectl -n ${namespaveValue} get pod get pod 查看部署的应用 status =running

部署服务
service.yaml
kubectl apply -f ~/service.yaml  部署服务
kubectl get service -n  ${namespaveValue}  查看部署的服务

暴露服务
ingress.yaml
kubectl apply -f ~/ingress.yaml
卸载资源

kubectl delete ingress example-ingress -n ${namespaveValue}  卸载 ingress
kubectl delete service myapp-service -n ${namespaveValue}  卸载 service
kubectl delete deployment myapp-deployment -n  ${namespaveValue} 卸载 deployment













