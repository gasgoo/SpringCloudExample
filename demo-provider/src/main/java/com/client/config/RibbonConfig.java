package com.client.config;

import com.netflix.client.config.DefaultClientConfigImpl;
import org.junit.Test;
import org.springframework.context.annotation.Configuration;

/**
 * @Date 2020/6/1 13:52
 * @name RibbonConfig
 */

@Configuration
public class RibbonConfig extends DefaultClientConfigImpl {

    @Test
    public void test() {
    }
}
