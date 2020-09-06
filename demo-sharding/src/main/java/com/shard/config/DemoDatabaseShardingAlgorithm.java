package com.shard.config;

import java.util.Collection;

import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

public class DemoDatabaseShardingAlgorithm implements PreciseShardingAlgorithm<Long> {

	@Override
	public String doSharding(Collection<String> collection, PreciseShardingValue<Long> preciseShardingValue) {
		for (String each : collection) {
			if (each.endsWith(Long.parseLong(preciseShardingValue.getValue().toString()) % 3 + "")) {
				return each;
			}
		}
		throw new IllegalArgumentException();
	}
}
