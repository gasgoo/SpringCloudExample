package com.shard.config;

import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

import java.util.Collection;


public class DemoTableShardingAlgorithm implements PreciseShardingAlgorithm<Long> {
	@Override
	public String doSharding(Collection<String> collection, PreciseShardingValue<Long> preciseShardingValue) {
		for (String each : collection) {
			if (each.endsWith(Long.parseLong(preciseShardingValue.getValue().toString()) % 2 + "")) {
				return each;
			}
		}
		throw new IllegalArgumentException();
	}
}