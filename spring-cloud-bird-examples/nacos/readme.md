
### 地址
    https://nacos.io/zh-cn/index.html
    
### github
    https://github.com/alibaba/nacos
    
### 源码编译
    mvn -Prelease-nacos clean install -U

### 集群部署
    部署手册 https://nacos.io/zh-cn/docs/deployment.html
    集群
    https://nacos.io/zh-cn/docs/cluster-mode-quick-start.html
    
    
### 概念    

#### 配置集 ID
    Nacos 中的某个配置集的 ID。配置集 ID 是组织划分配置的维度之一。
    Data ID 通常用于组织划分系统的配置集。一个系统或者应用可以包含多个配置集，
    每个配置集都可以被一个有意义的名称标识。Data ID 通常采用类 Java 包（如 com.taobao.tc.refund.log.level）
    的命名规则保证全局唯一性。此命名规则非强制。
    
    
    

#### 配置分组
     Nacos 中的一组配置集，是组织配置的维度之一。通过一个有意义的字符串（如 Buy 或 Trade ）对配置集进行分组，
     从而区分 Data ID 相同的配置集。当您在 Nacos 上创建一个配置时，如果未填写配置分组的名称，则配置分组的名称默认
     采用 DEFAULT_GROUP 。配置分组的常见场景：不同的应用或组件使用了相同的配置类型，如 database_url 配置和 MQ_topic 配置。   
  
### spring cloud 集成
    在 Nacos Spring Cloud 中，dataId 的完整格式如下：
    ${prefix}-${spring.profiles.active}.${file-extension}
    
    prefix 默认为 spring.application.name 的值，也可以通过配置
    spring.profiles.active 即为当前环境对应的 profile，详情可以参考 Spring Boot文档。 
    注意：当 spring.profiles.active 为空时，对应的连接符 - 也将不存在，dataId 的拼接格式变成 ${prefix}.${file-extension}
    file-exetension 为配置内容的数据格式，可以通过配置项 spring.cloud.nacos.config.file-extension 来配置。目前只支持 properties 和 yaml 类型。
    
    bootstrap.yml
```yaml
spring:
  profiles:
    active: local
  application:
    name: nacos-config-spring-cloud-example
  cloud:
    nacos:
      config:
        server-addr: 127.0.0.1:8848
        file-extension: yaml
```    
    
    
    接着就可以用过
    通过 Spring Cloud 原生注解 @RefreshScope 实现配置自动更新：  
      
```java
@RestController
@RequestMapping("/config")
@RefreshScope
public class ConfigController {

    @Value("${useLocalCache:false}")
    private boolean useLocalCache;

    @RequestMapping("/get")
    public boolean get() {
        return useLocalCache;
    }
}

```
在配置中心添加配置

     data-id :  nacos-config-spring-cloud-example-local.yaml   
     增加一个yaml类型的配置就可以了。
     
    
启动程序
      详见nacos-config-spring-cloud-example
      
      可以getValue 查看值，
      接着在配置中心修改，在调用getValue ，值直接变了。             
    
    还可以调用api 直接修改
    useLocalCache 为要改的参数
    curl -X POST "http://127.0.0.1:8848/nacos/v1/cs/configs?dataId=example.properties&group=DEFAULT_GROUP&content=useLocalCache=true"
  
### spring boot 接入
    接入文档
    https://github.com/nacos-group/nacos-spring-boot-project/wiki/spring-boot-0.2.2-%E4%BB%A5%E5%8F%8A-0.1.2%E7%89%88%E6%9C%AC%E6%96%B0%E5%8A%9F%E8%83%BD%E4%BD%BF%E7%94%A8%E6%89%8B%E5%86%8C    



### 变化之后 重新初始化一些动作，如数据库连接
```java
@Configuration
public class DataSourceConfigure {
    @Bean
    @RefreshScope
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource(DataSourceProperties properties){
        System.out.println("执行了重新获取数据源");
        return DataSourceBuilder.create(properties.getClassLoader())
                .type(HikariDataSource.class)
                .driverClassName(properties.determineDriverClassName())
                .url(properties.determineUrl())
                .username(properties.determineUsername())
                .password(properties.determinePassword())
                .build();
    }
}
```    
   