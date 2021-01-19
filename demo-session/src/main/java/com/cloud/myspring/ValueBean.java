package com.cloud.myspring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @Desc
 * @Date 2021/1/6 21:09
 **/
@Component
public class ValueBean implements EnvironmentAware {

    @Value("${app.name}")
    private String name;

    @Override
    public void setEnvironment(Environment environment) {
        System.out.println("@Value>>>>" + name);

        System.out.println("env>>>" + environment.getProperty("app.name"));
    }
}
