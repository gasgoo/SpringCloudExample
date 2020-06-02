
#dubbo
RMI 远程过程调用  继承Remote接口 即可用rmi调用
rpc调用方式： rmi  http 

全局配置：
<dubbo:application>
<dubbo:registy>
<dubbo:provider>
<dubbo:prototol>

api方式调用dubbo接口
ServiceConfig<Service> privoder=new ServiceCongif();

#容错配置 cluster 取值
failOver  快速切换到其他节点自动重试 -default
failFast  快速失败
failSafe  异常失败 则忽略
failBack   记录失败请求定时重发
forkCluster 并行请求到多个provider
BroadcastCluster 逐个调用provider
#负载均衡策略
随机策略、随机+权重
最近最少活跃策略
一致性hash策略。

异步： Async=true
回调
onreturn
onthrow
回声测试  扫描一遍服务是否都已经就绪。

SPI机制 加载实现类的方式
@Activate(group = "provider", value = "xxx") 自动激活的方法
@Adaptive({"server", "transport"}) 自动适应方式
动态生成一个类 javassist.ClassPool

ExtensionFactory 三个扩展实现获取bean的过程。 
 AdaptiveExtensionFactory、SpiExtensionFactory、SpringExtensionFactory
getExtension(pt,property)

#dubbo启用流程
1. 解析dubbo标签，Spring.handlers引入了dubbo自定义标签。DubboNamesSpaceHandler.init()
2. 解析标签中配置的 serviceid装成beanDefinition
3. @Service  @Reference 服务提供者和消费者上面的注解解析  
3. @EnableDubbo注解引入了 @EnableDubboConfig  、@DubboComponentScan
@EnableDubboConfig
@DubboComponentScan扫描所有 @Service  @Reference注解
ServiceAnnotationBeanPostProcessor.class解析扫描 @Service注解的类注入IOC容器然后对外暴露成服务。
ReferenceANnotationBeanPostProcessor.class @Reference 生成代理对象，把带有该注解的所有属性字段封装成对象。
然后把代理对象注入带有注解的属性对象中。

#serviceBean和referenceBean如何实现联通的?

InitializingBean.afterPropertiesSet()方法中完成服务暴露

 