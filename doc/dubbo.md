
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

容错配置 cluster 取值
failOver  快速失败切换到其他节点
failFast 

异步： Async=true
回调
onreturn
onthrow
回声测试  扫描一遍服务是否都已经就绪。

SPI机制 加载实现类的方式
@Activate(group = "provider", value = "xxx") 自动激活的方法
@Adaptive({"server", "transport"}) 自动适应方式
动态生成一个类 javassist.ClassPool
