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
1. 新生代内存不足时 发生 minor GC
2. 老年代内存不足时 发生Full GC
> 回收算法
1. 复制算法
    优点：简单高效、不会出现内存碎片   缺点：内存利用率低 只用一半、存活对象多时效率低
    使用在新生代 eden-s1
2. 标记清除算法
   有碎片 效率相对复制更低。 标记可达 清除不可达
3. 标记整理算法
   没有碎片 内存利用率高、内存块的移动
>收集器
 1. Serial 单线程的新生代 复制算法的收集器、  Serial Old 单线程的老年代 标记整理算法收集器
 2. Paralled Scavenge 并行的新生地复制算法收集器、  Paralled OLD  并行的老年代标记整理算法收集器
 3. cms 老年代的标记清楚算法收集器 并行并发
    初始标记、并发标记、重新标记、并发清除   标记stop theWorld  追求最短的暂停时间为目标
    耗费CPU资源、产生浮动垃圾、内存碎片
 4. G1 跨新生代和老年代的复制+标记整理+ 划分多个区域的并发收集。 
     内存空间整合，不会产生内存碎片、可预测的停顿
    初始标记、并发标记、最终标记、并发回收
     
 5. 内存泄漏的原因：
    内部类持有外部类、连接未关闭、变量作用域不合理、
 6. 内存溢出的情况
    申请内存的时候内存不足抛出内存溢出异常、
    栈溢出 StackOverFlowError
    OOM: java heap over 
    OOM: GC OverHead Limit exceeded 频繁的FullGC但是没有回收
    

 >性能调优
 1. 前端优化： 减少请求请求合并、  keepAlive 、客户端缓存(Cache-control Expires)、启用压缩、 文件加载顺序
            减少cookie传输、
 2. CDN加速  反向代理缓存、Web组件分离
 3. 应用服务器优化
    缓存、集群、异步、代码优化

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


 
    











