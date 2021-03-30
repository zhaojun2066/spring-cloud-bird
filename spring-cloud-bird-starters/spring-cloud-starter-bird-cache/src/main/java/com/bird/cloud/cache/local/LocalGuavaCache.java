package com.bird.cloud.cache.local;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @program: ce-microservice
 * @description:
 * @author: JuFeng(ZhaoJun)
 * @create: 2020-02-13 16:26
 **/
@Slf4j
@AllArgsConstructor
public abstract class LocalGuavaCache<K,V> {

    @Setter// 缓存自动刷新周期
    protected int refreshDuration = 10;
    // 缓存刷新周期时间格式
    protected TimeUnit refreshTimeUnit = TimeUnit.MINUTES;
    @Setter// 缓存过期时间（可选择）
    protected int expireDuration = -1;
    // 缓存刷新周期时间格式
    protected TimeUnit expireTimeUnit = TimeUnit.HOURS;

    @Setter
    @Getter
    protected int maxSize = 2000; // 缓存最大容量

    protected boolean reload= false ; // 是否需要reload 数据

    protected int refreshThreads = 20;

    // 数据刷新线程池
    protected static ListeningExecutorService refreshPool = null;

    private LoadingCache<K, V> cache = null;


    private void initCache(){
        CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder()
                .maximumSize(maxSize);
        if (reload){
            if(refreshDuration > 0) {
                cacheBuilder = cacheBuilder.refreshAfterWrite(refreshDuration, refreshTimeUnit);
            }
        }

        if(expireDuration > 0) {
            cacheBuilder = cacheBuilder.expireAfterWrite(expireDuration, expireTimeUnit);
        }
        if (reload){
            reloadData(cacheBuilder);
        }else {
            cache = cacheBuilder.build(new CacheLoader<K, V>() {
                @Override
                public V load(K key) throws Exception {
                    log.info("sync {} ",key);
                    return fetchData(key);
                }
            } );
        }
    }
    public LocalGuavaCache(){
        initAll();
    }
    private void initAll(){
        initCache();
        initRefreshPool();
    }
    public LocalGuavaCache(int expireDuration, TimeUnit expireTimeUnit, int maxSize){
        this.expireDuration = expireDuration;
        this.expireTimeUnit = expireTimeUnit;
        this.maxSize = maxSize;
        initAll();
    }
    public LocalGuavaCache(int expireDuration, TimeUnit expireTimeUnit){
        this.expireDuration = expireDuration;
        this.expireTimeUnit = expireTimeUnit;
        initAll();

    }
    public LocalGuavaCache(int expireDuration, TimeUnit expireTimeUnit, int refreshDuration, TimeUnit refreshTimeUnit){
        this.expireDuration = expireDuration;
        this.expireTimeUnit = expireTimeUnit;
        this.refreshDuration = refreshDuration;
        this.refreshTimeUnit = refreshTimeUnit;
        this.reload = true;
        initAll();
    }
    public LocalGuavaCache(int expireDuration, TimeUnit expireTimeUnit, int refreshDuration, TimeUnit refreshTimeUnit, int maxSize){
        this.expireDuration = expireDuration;
        this.expireTimeUnit = expireTimeUnit;
        this.refreshDuration = refreshDuration;
        this.refreshTimeUnit = refreshTimeUnit;
        this.maxSize = maxSize;
        this.reload = true;
        initAll();
    }
    public LocalGuavaCache(int expireDuration, TimeUnit expireTimeUnit, int refreshDuration, TimeUnit refreshTimeUnit, int maxSize, int refreshThreads){
        this.expireDuration = expireDuration;
        this.expireTimeUnit = expireTimeUnit;
        this.refreshDuration = refreshDuration;
        this.refreshTimeUnit = refreshTimeUnit;
        this.maxSize = maxSize;
        this.reload = true;
        this.refreshThreads = refreshThreads;
        initAll();
    }



    private void initRefreshPool(){
        if (reload){
            refreshPool = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(refreshThreads));
        }
    }


    /**
     * 用于初始化缓存值（某些场景下使用，例如系统启动检测缓存加载是否征程）
     */
    public abstract void loadValueWhenStarted();

    /**
     * @description: 定义缓存值的计算方法
     * @description: 新值计算失败时抛出异常，get操作时将继续返回旧的缓存
     * @param key
     * @throws Exception
     */
    protected abstract V fetchData(K key) throws Exception;

    /**
     * @description: 从cache中拿出数据操作
     * @param key
     * @throws Exception
     */
    public V getValue(K key) throws Exception {
        try {
            return cache.get(key);
        } catch (Exception e) {
            log.error("从内存缓存中获取内容时发生异常，key: " + key, e);
            throw e;
        }
    }

    public V getValueOrDefault(K key, V defaultValue) {
        try {
            return cache.get(key);
        } catch (Exception e) {
            log.error("从内存缓存中获取内容时发生异常，key: " + key, e);
            return defaultValue;
        }
    }

    public void clearAll(){
        this.cache.invalidateAll();
    }

    private void reloadData(CacheBuilder<Object, Object> cacheBuilder ){
        cache = cacheBuilder.build(new CacheLoader<K, V>() {
            @Override
            public V load(K key) throws Exception {
                log.debug("sync {} ",key);
                return fetchData(key);
            }

            @Override
            public ListenableFuture<V> reload(final K key,
                                              V oldValue) throws Exception {
                log.debug("async {} ",key);
                return refreshPool.submit(() -> fetchData(key));
            }
        } );
    }
}
