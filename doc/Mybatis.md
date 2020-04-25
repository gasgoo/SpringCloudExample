#mybatis缓存; 
1一级缓存： 同一个sqlSession会话中同一个statement执行两次，
第二次默认使用第一次创建的缓存对象。 设置一级缓存类型 SESSION或者STATEMENT，
默认是SESSION级别， 即在一个MyBatis会话中执行的所有语句，都会共享这一个缓存。
 一种是STATEMENT级别，可以理解为缓存只对当前执行的这一个Statement有效。 
 多个相同的sql中间有修改的sql，则缓存失效； 验证一级缓存只在数据库会话内部共享。
  每次update语句都会情况缓存； 没有容量限制的hashMap再性能上有所欠缺；
   cachekey(Statement Id + Offset + Limmit + Sql + Params) 缓存范围:SqlSession内部，
   多个会话或分布式环境会有脏数据，建议一级缓存设置为 Statement

二级缓存(全局)： 当开启缓存后，数据的查询执行的流程就是  二级缓存 -> 一级缓存 -> 数据库。 
二级缓存被多个SqlSession共享，是一个全局的变量。 
开启二级缓存 cache标签用于声明这个namespace使用二级缓存，
并且可以自定义配置。 事物提交了缓存才会生效。 
分布式环境下，由于默认的MyBatis Cache实现都是基于本地的，分布式环境下必然会出现读取到脏数据，
需要使用集中式缓存将MyBatis的Cache接口实现， 
有一定的开发成本，直接使用Redis、Memcached等分布式缓存可能成本更低，安全性也更高。 
内存中维护一个hashMap对象来存储，Executor对象上来做文章 cacheEnabled=true时，在创建SqlSession时会创建 Executor对象，同时会有一个CacheExecutor，会判断application级别是否有缓存结果，如果有则直接返回，没有这执行Executor执行查询。

SqlSession对象完成和数据库的交互： SqlSession通过调用api的Statement ID找到对应的MappedStatement对象 通过Executor（负责动态SQL的生成和查询缓存的维护）将MappedStatement对象进行解析，sql参数转化、动态sql拼接，生成jdbc Statement对象 d、JDBC执行sql。

sqlSession的四大对象 executor statementHandler(声明处理器) parameterHandler ResultSetHandler SqlSession是对外暴露的统一接口 如 selectOne selectList selectmap 实物提交 回滚 等 executor 执行所有的sql 执行器 调度核心 Configuration sqlSessionFactory sqlSession
MappedStatement 动态SQL的封装

#Mybatis原理
InputStream inputStream = Resources.getResourceAsStream("mybatis.xml"); SqlSessionFactory sqlSessionFactory =new SqlSessionFactoryBuilder().build(inputStream); SqlSession sqlSession = sqlSessionFactory.openSession();

// 以下使我们需要关注的重点 UserMapper mapper = sqlSession.getMapper(UserMapper.class); Integer id = 1; User user = mapper.selectById(id);

加载mybatis全局配置文件（数据源、mapper映射文件等），解析配置文件，MyBatis基于XML配置文件生成Configuration， 和一个个MappedStatement SqlSessionFactoryBuilder通过Configuration对象生成SqlSessionFactory， SqlSessionFactory调用Configuration的方法用来开启SqlSession、并生成执行器Executor对象。 org.apache.ibatis.session.defaults.DefaultSqlSession.getMapper org.apache.ibatis.binding.MapperRegistry.getMapper 通过MapperProxyFactory工厂对象 通过Proxy.newInstances()实例化Dao接口生成代理对象MapperProxy

生成MapperProxy代理对象后需要把bean放到ioc容器中。

如何把Mybatis的代理对象作为一个bean放入Spring容器中？ 通过 FactoryBean把Mybatis生成的代理对象放入容器中

MapperFactoryBean 用来把代理对象生成bean对象。 MapperScannerRegistrar 生成不同对象的 MapperFactoryBean @Mapper注解 扫描@mapper注解获取注解的value包路径;生成BeanDefinition放入容器中。

===SpringBoot-mybatis整合过程
SpringBoot自动配置 EnableAutoConfiguration 通过AutoConfigurationImportSelect导入mybatis自动配置类 通过反射加载Spring.factories中指定的 MybatisAutoConfiguration配置类 生成sqlSessionFactory和SqlSessionTemplate SqlSessionTemplate中的getConfiguration().getMapper() getMapper实际上是从MapperRegistry对象中通过 mapperProxyFactory工厂newInstance来创建一个mapperProxy 这个mapperProxy就是在使用userdao的时候的代理类

===Dao接口如何变成对象-- jdk动态代理 产生代理对象 ===执行sql InvocationHandler invoke得到注解 或解析xml文件

过程： 读取mybatis配置文件创建 SqlSessoinFactory 获取sqlSession 获取对应mapper executes 执行sql返回结果
Spring加载MyBatis这个过程，其实就是把MyBatis的Mapper转换成Bean，注入到Spring容器的过程。 也是 FactoryBean的使用 其中使用到 MapperFactoryBean SqlSessionFactoryBean

数据存储在文件系统 （柱面 磁道 扇区） 2）mysql的语句优化，使用什么工具； 优化的方向：减少联合多表查询 建立正确的索引 减少统计类查询 重点是拆分表 减少 联合查询 拆分成短sql 3）mysql的索引分类：B+，hash；什么情况用什么索引；