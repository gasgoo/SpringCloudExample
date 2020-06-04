package com.server.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @Author 读取配置文件项映射到对象属性中
 * @Date 2019/3/26 11:05
 */

@Component
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
@RefreshScope
@Setter
@Getter
public class ApplicationProperties {

    private final Aliyun aliyun=new Aliyun();

    public Aliyun getAliyun() {
        return aliyun;
    }

    public static class Aliyun{
        private String appKey="00";
        private String appSecret="00";
        private String bucket="proxy";
        private String endpoint="url";

        public String getAppKey() {
            return appKey;
        }

        public void setAppKey(String appKey) {
            this.appKey = appKey;
        }

        public String getEndpoint() {
            return endpoint;
        }

        public void setEndpoint(String endpoint) {
            this.endpoint = endpoint;
        }

        public String getAppSecret() {
            return appSecret;
        }

        public void setAppSecret(String appSecret) {
            this.appSecret = appSecret;
        }

        public String getBucket() {
            return bucket;
        }

        public void setBucket(String bucket) {
            this.bucket = bucket;
        }
    }


    //线程池大小
    private Integer corePoolSize=5;

    public Integer getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(Integer corePoolSize) {
        this.corePoolSize = corePoolSize;
    }
}
