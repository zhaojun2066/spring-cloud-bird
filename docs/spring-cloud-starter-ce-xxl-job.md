## 《分布式任务调度平台XXL-JOB》

[![GitHub release](https://img.shields.io/github/release/xuxueli/xxl-job.svg)](https://github.com/xuxueli/xxl-job/releases)

[![GitHub stars](https://img.shields.io/github/stars/xuxueli/xxl-job)](https://github.com/xuxueli/xxl-job/) 

[官方文档](https://www.xuxueli.com/xxl-job/)


## 环境准备
- Maven3+
- Jdk1.8+
- Mysql5.7+


## 下载源码
```
git clone https://github.com/xuxueli/xxl-job.git
git checkout 2.2.0 
```

## 安装admin
 导入刚下载的工程到你的IDE
 启动 xxl-job-admin 项目，默认端口为8080
 访问 http://localhost:8080/xxl-job-admin
 具体使用详见 [官方文档](https://www.xuxueli.com/xxl-job/)
 
## 执行器
创建项目，详见demo spring-cloud-starter-bird-xxl-job-example  

### 执行器配置项说明
        ### 调度中心部署跟地址 [选填]：如调度中心集群部署存在多个地址则用逗号分隔。执行器将会使用该地址进行"执行器心跳注册"和"任务结果回调"；为空则关闭自动注册；
        bird.cloud.job.xxl.admin-addresses=http://127.0.0.1:8080/xxl-job-admin
        
        ### 执行器通讯TOKEN [选填]：非空时启用；
        bird.cloud.job.xxl.accessToken=
        
        ### 执行器AppName [选填]：执行器心跳注册分组依据；为空则关闭自动注册
        bird.cloud.job.xxl.executor.appname=xxl-job-executor-sample
        ### 执行器注册 [选填]：优先使用该配置作为注册地址，为空时使用内嵌服务 ”IP:PORT“ 作为注册地址。从而更灵活的支持容器类型执行器动态IP和动态映射端口问题。
        bird.cloud.job.xxl.executor.address=
        ### 执行器IP [选填]：默认为空表示自动获取IP，多网卡时可手动设置指定IP，该IP不会绑定Host仅作为通讯实用；地址信息用于 "执行器注册" 和 "调度中心请求并触发任务"；
        bird.cloud.job.xxl.executor.ip=
        ### 执行器端口号 [选填]：小于等于0则自动获取；默认端口为9999，单机部署多个执行器时，注意要配置不同执行器端口；
        bird.cloud.job.xxl.executor.port=9999
        ### 执行器运行日志文件存储磁盘路径 [选填] ：需要对该路径拥有读写权限；为空则使用默认路径；
        bird.cloud.job.xxl.executor.logpath=/data/applogs/xxl-job/jobhandler
        ### 执行器日志文件保存天数 [选填] ： 过期日志自动清理, 限制值大于等于3时生效; 否则, 如-1, 关闭自动清理功能；
        bird.cloud.job.xxl.executor.logretentiondays=30


