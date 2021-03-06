package com.shard.config;

import com.dangdang.ddframe.rdb.sharding.api.ShardingDataSourceFactory;
import com.dangdang.ddframe.rdb.sharding.api.rule.BindingTableRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.DataSourceRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.ShardingRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.TableRule;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.DatabaseShardingStrategy;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.TableShardingStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.DataSourceFactory;

import javax.sql.DataSource;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.*;

/**
 * @Desc 数据源配置
 * @Date 2020/9/2 9:24
 **/
@Configuration
@Slf4j
public class DataSourceConfig {


    @Bean(name = "dataSource0")
    @Qualifier("dataSource0") //默认使用的数据源
    @ConfigurationProperties(prefix = "spring.datasource.ds0")
    public DataSource dataSource1() {
        return DataSourceBuilder.create().build();
        //return DataSourceBuilder.create().type(com.alibaba.druid.pool.DruidDataSource.class).build();
    }

    @Bean(name = "dataSource1")
    @ConfigurationProperties(prefix = "spring.datasource.ds1")
    public DataSource dataSource2() {
        return DataSourceBuilder.create().build();
        //return DataSourceBuilder.create().type(com.alibaba.druid.pool.DruidDataSource.class).build();
    }


    @Bean
    public DataSourceRule dataSourceRule(@Qualifier("dataSource0") DataSource dataSource0,
                                         @Qualifier("dataSource1") DataSource dataSource1) {
        Map<String, DataSource> dataSourceMap = new HashMap<>(); //设置分库映射
        dataSourceMap.put("dataSource0", dataSource0);
        dataSourceMap.put("dataSource1", dataSource1);
        return new DataSourceRule(dataSourceMap, "dataSource0"); //设置默认库，两个库以上时必须设置默认库。默认库的数据源名称必须是dataSourceMap的key之一
    }

    /**
     * 配置数据源策略和表策略，具体策略需要自己实现
     *
     * @param dataSourceRule
     * @return
     */
    @Bean
    public ShardingRule shardingRule(DataSourceRule dataSourceRule) {
        //具体分库分表策略
        TableRule orderTableRule = TableRule.builder("t_order")
                .actualTables(Arrays.asList("t_order_0", "t_order_1", "t_order_2"))
                .tableShardingStrategy(new TableShardingStrategy("user_id", new ModuloTableShardingAlgorithm()))
                .dataSourceRule(dataSourceRule)
                .build();
        //t_user表分库分表策略
        TableRule userTableRule = TableRule.builder("t_user")
                .actualTables(Arrays.asList("t_user_00", "t_user_01", "t_user_02"))
                .tableShardingStrategy(new TableShardingStrategy("user_id", new ModuloTableShardingAlgorithm()))
                .dataSourceRule(dataSourceRule)
                .build();

        //绑定表策略，在查询时会使用主表策略计算路由的数据源，因此需要约定绑定表策略的表的规则需要一致，可以一定程度提高效率
        List<BindingTableRule> bindingTableRules = new ArrayList<BindingTableRule>();
        bindingTableRules.add(new BindingTableRule(Arrays.asList(orderTableRule)));
        return ShardingRule.builder()
                .dataSourceRule(dataSourceRule)
                .tableRules(Arrays.asList(orderTableRule, userTableRule))
                .bindingTableRules(bindingTableRules)
                .databaseShardingStrategy(new DatabaseShardingStrategy("user_id", new ModuloDatabaseShardingAlgorithm()))
                .tableShardingStrategy(new TableShardingStrategy("user_id", new ModuloTableShardingAlgorithm()))
                .build();
    }

    /**
     * 创建sharding-jdbc的数据源DataSource，MybatisAutoConfiguration会使用此数据源
     *
     * @param shardingRule
     * @return
     * @throws SQLException
     */
    @Bean(name = "dataSource")
    public DataSource shardingDataSource(ShardingRule shardingRule) throws SQLException {
        return ShardingDataSourceFactory.createDataSource(shardingRule);
    }

    /**
     * 需要手动配置事务管理器
     *
     * @param dataSource
     * @return
     */
    @Bean
    public DataSourceTransactionManager transactitonManager(@Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "test1SqlSessionFactory")
    @Primary
    public SqlSessionFactory testSqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "test1SqlSessionTemplate")
    @Primary
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("test1SqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }


}
