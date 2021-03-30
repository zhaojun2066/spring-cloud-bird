package com.bird.cloud.cache.redis.redisson;

import org.redisson.config.Config;

/**
 * @program: spring-cloud-bird
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2021-01-06 15:16
 **/
public interface RedissonAutoConfigurationCustomizer {
    /**
     * Customize the RedissonClient configuration.
     * @param configuration the {@link Config} to customize
     */
    void customize(final Config configuration);
}
