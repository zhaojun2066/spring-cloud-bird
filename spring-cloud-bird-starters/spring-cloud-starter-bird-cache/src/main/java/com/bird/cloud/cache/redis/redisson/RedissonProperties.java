package com.bird.cloud.cache.redis.redisson;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @program: ce-microservice
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2020-02-14 10:22
 **/
@ConfigurationProperties(prefix = "bird.cloud.redis.redisson")
public class RedissonProperties {

    private boolean enabled;

    private String config;

    private String file;


    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
