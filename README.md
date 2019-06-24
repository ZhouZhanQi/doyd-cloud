# 多易得微服务doyd-cloud
基于Spring Cloud（Greenwich SR1 GA）开发，目录说明：
```
DoydCloud
    |-- doyd-eureka-server: 服务注册和发现中心
    |-- config: 
    |   |-- doyd-configserver-server: 配置中心服务,同时提供yaml配置API
    |   |-- config-repo: 配置文件仓库
    |   |-- config-backup: 配置历史文件仓库
    |-- auth: 登录认证服务
    |-- gateway: 服务网关
    |-- monitor: 服务监控
    |   |-- doyd-hystrix-dashboard: 熔断器的监控面板
    |   |-- doyd-zipkin-server: 调用链跟踪
    |   |-- doyd-boot-admin: 微服务健康监控(升级到2.1.4之后删除hystrix ui turbine ui)
    |-- tcc: 待开发 （柔性事务）https://github.com/prontera/spring-cloud-rest-tcc
    |-- scripts: 项目所用到的各种脚本(数据库sql脚本等)

## 构建和运行

执行`maven`任务`clean compile install`可以将项目编译并打包为包含全部依赖的jar文件(fat jar)
命令运行示例:

```bash
Windows: java -jar eureka-20180103.jar
Linux: nohup java -jar eureka-20180103.jar > console_out.log 2>&1 &
```

若项目配置包含多个profiles,可以使用参数` --spring.profiles.active=prod`来指定要使用的profile

也可以使用`--server.port=9901`来指定运行端口

Example:

```bash
nohup java -XX:+UseParallelOldGC -Xss256k -Xms1g -Xmx1g -XX:NewRatio=3 -jar eureka-20180228.jar --server.port=23333 --spring.profiles.active=test > console_out.log 2>&1 &
```

**启动顺序**:

1. doyd-eureka-server
2. doyd-configserver-server
3. microservices(xxx-ms)
4. gateway
5. doyd-hystrix-dashboard
6. doyd-boot-admin
7. doyd-zipkin-server