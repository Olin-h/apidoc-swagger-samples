## knife4j swagger整合SpringCloud文档说明 

### 各模块文档详细介绍
[knife4j-aggregation-nacos-sample](./knife4j-aggregation-nacos-sample/README.md)

[knife4j-gateway-sample](./knife4j-gateway-sample/README.md)

---
### 注意事项
#### 网关spring-boot-starter-web依赖和spring-cloud-starter-gateway依赖冲突
当项目中存在标题所述依赖问题时，启动网关项目时会提示如下冲突
> Spring MVC found on classpath, which is incompatible with Spring Cloud Gateway.</br>
Please set spring.main.web-application-type=reactive or remove spring-boot-starter-web dependency.

其实在报错的信息中已经给出了解决方案

- 在application.properties配置文件中添加 `spring.main.web-application-type=reactive` 配置
- 或者在网关中，移除 `spring-boot-starter-web` 依赖

---

#### gateway使用均衡负载报503错误

由于springcloud2020弃用了Ribbon，因此Alibaba在2021版本nacos中删除了Ribbon的jar包，因此无法通过lb路由到指定微服务，出现了503情况。

所以只需要引入springcloud loadbalancer包即可

```xml
<!--客户端负载均衡loadbalancer-->
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-loadbalancer</artifactId>
</dependency>
```

参考地址：[https://blog.csdn.net/qq_41953714/article/details/116239716]()

---