# spring-cloud-bird

## install
    在根目录下执行
    mvn -Drevision=1.0.0 install 
    
## 项目介绍
### spring-cloud-bird-examples
     demo
#### job/spring-cloud-starter-bird-xxl-job-example
    基于springboot xxl-job 例子    

#### mirco
    微服务例子，包括分层，dto 转换，api定义等
    course
    课程，学生需要选择课程
    student
    学生服务，会调用course 服务
#### monitor
    监控
##### monitor/spring-cloud-starter-bird-cat-example    
   cat 监控demo
#### mq
    mq demo
##### mq/spring-cloud-starter-bird-rocketmq-exmple              
    rocketmq 例子，生产 消费，事物等
#### nacos
    nacos 例子
##### nacos\nacos-config-spring-boot-example
    基于spring-boot 的 配置中心调用
##### nacos\nacos-config-spring-cloud-example
    基于spring-cloud 的配置中心调用
#### seata
     seata 的分布式事物例子
     business 业务调用方，会调用 order 和 storage 服务，进行分布式事物处理
     order 订单
     storage 库存   
     db ，需要初始化的sql  
#### sentinel
    sentinel-dashboard 基于nacos 存储进行改造
    sentinel-seivice-provider 服务提供者，基于>spring-cloud-starter-alibaba-sentinel ，并监听nacos 的限流或者降级 配置变化                     
### spring-cloud-bird-common  
    公共工具类和封装类
### spring-cloud-bird-dependencies 
    所有依赖的管理   
### spring-cloud-bird-starters     
    所有的starters
#### spring-cloud-starter-bird-base
    基础功能
#### spring-cloud-starter-bird-cache   
    缓存redis 和  local cache 
#### spring-cloud-starter-bird-cat  
    监控cat 
#### spring-cloud-starter-bird-discover    
    服务注册和发现
#### spring-cloud-starter-bird-mybatis     
    mybatis
#### spring-cloud-starter-bird-rocketmq    
    rocketmq
#### spring-cloud-starter-bird-seata    
    分布式事物seata
#### spring-cloud-starter-bird-xxl-job    
    任务调度 xxl-job