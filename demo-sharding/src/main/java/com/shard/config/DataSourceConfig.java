package com.shard.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.dangdang.ddframe.rdb.sharding.api.ShardingDataSourceFactory;
import com.dangdang.ddframe.rdb.sharding.api.rule.DataSourceRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.ShardingRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.TableRule;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.DatabaseShardingStrategy;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.TableShardingStrategy;
import com.dangdang.ddframe.rdb.sharding.jdbc.ShardingDataSource;
import io.shardingsphere.api.config.rule.ShardingRuleConfiguration;
import io.shardingsphere.api.config.rule.TableRuleConfiguration;
import io.shardingsphere.api.config.strategy.StandardShardingStrategyConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Desc 数据源配置
 * @Date 2020/9/2 9:24
 **/
@Configuration
@MapperScan(basePackages = "com.shard.mapper")
public class DataSourceConfig {


    @Bean
    public DataSource getDataSource() {
        return buildDataSource();
    }


    private DataSource buildDataSource() {
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        //添加两个数据源
        dataSourceMap.put("ds00", createDataSource("ds_0"));
        dataSourceMap.put("ds01", createDataSource("ds_1"));
        //设置默认db为ds_0，也就是为那些没有配置分库分表策略的指定的默认库
        //如果只有一个库，也就是不需要分库的话，map里只放一个映射就行了，只有一个库时不需要指定默认库，但2个及以上时必须指定默认库，否则那些没有配置策略的表将无法操作数据
        DataSourceRule dataSourceRule = new DataSourceRule(dataSourceMap, "ds00");
        //0和1两个表是真实的表，t_user是个虚拟不存在的表，只是供使用。如查询所有数据就是select * from t_user
        TableRule orderTableRule = TableRule.builder("t_user")
                .actualTables(Arrays.asList("t_user_00", "t_user_01", "t_user_02"))
                .dataSourceRule(dataSourceRule)
                .build();
        //具体分库分表策略，按什么规则来分 根据什么属性列来计算分库分表
        ShardingRule shardingRule = ShardingRule.builder()
                .dataSourceRule(dataSourceRule)
                .tableRules(Arrays.asList(orderTableRule))
                .databaseShardingStrategy(new DatabaseShardingStrategy("user_id", new ModuloDatabaseShardingAlgorithm()))
                .tableShardingStrategy(new TableShardingStrategy("user_id", new ModuloTableShardingAlgorithm())).build();

        //多列策略
        //new TableShardingStrategy(Arrays.asList(“order_id”, “order_type”, “order_date”), new MultiKeyShardingAlgorithm()))
        DataSource dataSource = ShardingDataSourceFactory.createDataSource(shardingRule);

        return dataSource;

    }




    @Bean
    public TableRuleConfiguration getUserTableRuleConfiguration() {
        TableRuleConfiguration orderTableRuleConfig = new TableRuleConfiguration();
        orderTableRuleConfig.setLogicTable("t_order");
        orderTableRuleConfig.setActualDataNodes("ds_${0..2}.t_order_{0..1}");
        orderTableRuleConfig.setKeyGeneratorColumnName("user_id");
        return orderTableRuleConfig;
    }


    private Map<String, DataSource> createDataSourceMap() {
        Map<String, DataSource> result = new HashMap<>();
        result.put("ds00", createDataSource("ds_0"));
        result.put("ds01", createDataSource("ds_1"));
        return result;
    }
    private static DataSource createDataSource(final String dataSourceName) {
        //使用druid连接数据库
        DruidDataSource result = new DruidDataSource();
        result.setDriverClassName(Driver.class.getName());
        result.setUrl(String.format("jdbc:mysql://localhost:3306/"+dataSourceName));
        System.out.println("====" + result.getUrl());
        result.setUsername("root");
        result.setPassword("root");
        return result;
    }
}
