## gateway整合knife4j文档说明
### quickstart
启动`knife4j-gateway-sample`模块下的微服务模块应用程序，如下：

- Knife4jGateWayApplication
- Knife4jOrderApplication
- Knife4jUserApplication

浏览器访问如下地址：
```http request
http://127.0.0.1:1400/doc.html
```

### 引入的依赖
聚合微服务网关（gateway-service-gateway）引入的依赖
```xml
<properties>
    <knife4j.version>3.0.3</knife4j.version>
</properties>

<dependencies>
    <!-- gateway -->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-gateway</artifactId>
    </dependency>
    
    <!--客户端负载均衡loadbalancer-->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-loadbalancer</artifactId>
    </dependency>
    
    <!-- Swagger 增强knife4j UI资源与微服务starter -->
    <dependency>
        <groupId>com.github.xiaoymin</groupId>
        <artifactId>knife4j-spring-boot-starter</artifactId>
        <version>${knife4j.version}</version>
    </dependency>
    
    <!-- nacos 服务发现与注册 -->
    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
    </dependency>
    <!-- nacos 配置中心 -->
    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
    </dependency>
</dependencies>
```

application.yml配置文件内容：
```yaml
spring:
  cloud:
    nacos:
      discovery:
        server-addr: 127.0.0.1:28999
        namespace: apidoc-swagger-samples
        group: DEV_GROUP
      config:
        server-addr: ${spring.cloud.nacos.discovery.server-addr}
        file-extension: yaml
        namespace: apidoc-swagger-samples
        group: DEV_GROUP

    # gateway的配置
    gateway:
      discovery:
        locator:
          # enabled：默认为false，设置为true表明spring cloud gateway开启服务发现和路由的功能，网关自动根据注册中心的服务名为每个服务创建一个router，将以服务名开头的请求路径转发到对应的服务
          enabled: true
          # lower-case-service-id：启动 locator.enabled=true 自动路由时，路由的路径默认会使用大写服务ID，若想要使用小写服务ID，可将该属性设置为true
          lower-case-service-id: true
```

被聚合的微服务（gateway-service-order|user）引入的依赖
```xml
<properties>
    <knife4j-micro.version>3.0.3</knife4j-micro.version>
</properties>

<dependencies>
    <dependency>
        <groupId>com.github.xiaoymin</groupId>
        <artifactId>knife4j-micro-spring-boot-starter</artifactId>
        <version>${knife4j-micro.version}</version>
    </dependency>
    
    <!-- nacos 服务发现与注册 -->
    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
    </dependency>
    <!-- nacos 配置中心 -->
    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
    </dependency>
</dependencies>
```

对应的application.yml配置文件内容：
```yaml
spring:
  mvc:
    # 解决Spring Boot 2.6.X swagger接口列表不显示的问题 这是因为Springfox使用的路径匹配是基于AntPathMatcher的，而Spring Boot 2.6.X使用的是PathPatternMatcher。
    pathmatch:
      matching-strategy: ant_path_matcher
```
---