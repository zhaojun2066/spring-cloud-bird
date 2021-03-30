package com.bird.cloud.discover.feign;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.concurrent.TimeUnit;

/**
 * @program: spring-cloud-bird
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-01-06 11:52
 **/

@Data
@ConfigurationProperties(prefix="bird.cloud.feign.okhttp3")
public class OkHttpClientProperties {
    private Long connectTimeout = 6000L;
    private Long readTimeout = 6000L;
    private Long writeTimeout = 6000L;
    private Boolean retryOnConnectionFailure = false;

    private Integer maxIdleConnections;
    private Long keepAliveDuration;
    private TimeUnit keepAliveDurationTimeUnit = TimeUnit.SECONDS;

    public Long getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Long connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public Long getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(Long readTimeout) {
        this.readTimeout = readTimeout;
    }

    public Long getWriteTimeout() {
        return writeTimeout;
    }

    public void setWriteTimeout(Long writeTimeout) {
        this.writeTimeout = writeTimeout;
    }

    public Boolean getRetryOnConnectionFailure() {
        return retryOnConnectionFailure;
    }

    public void setRetryOnConnectionFailure(Boolean retryOnConnectionFailure) {
        this.retryOnConnectionFailure = retryOnConnectionFailure;
    }

    public Integer getMaxIdleConnections() {
        return maxIdleConnections;
    }

    public void setMaxIdleConnections(Integer maxIdleConnections) {
        this.maxIdleConnections = maxIdleConnections;
    }

    public Long getKeepAliveDuration() {
        return keepAliveDuration;
    }

    public void setKeepAliveDuration(Long keepAliveDuration) {
        this.keepAliveDuration = keepAliveDuration;
    }

    public TimeUnit getKeepAliveDurationTimeUnit() {
        return keepAliveDurationTimeUnit;
    }

    public void setKeepAliveDurationTimeUnit(TimeUnit keepAliveDurationTimeUnit) {
        this.keepAliveDurationTimeUnit = keepAliveDurationTimeUnit;
    }
}
