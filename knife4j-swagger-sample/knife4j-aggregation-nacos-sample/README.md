## knife4j聚合文档 nacos模式文档说明
### quickstart
启动`knife4j-aggregation-nacos-sample`模块下的微服务模块应用程序，如下：

- ServiceDocApplication
- ServiceOrderApplication
- ServiceUserApplication

浏览器访问如下地址：
```http request
http://127.0.0.1:403/doc.html
```

### 引入的依赖
聚合微服务（service-doc）引入的依赖
```xml
<properties>
    <knife4j-aggregation.version>2.0.9</knife4j-aggregation.version>
</properties>

<dependencies>
    <!-- knife4j 微服务聚合依赖 -->
    <dependency>
        <groupId>com.github.xiaoymin</groupId>
        <artifactId>knife4j-aggregation-spring-boot-starter</artifactId>
        <version>${knife4j-aggregation.version}</version>
    </dependency>
    
    <!-- nacos 服务发现与注册 -->
    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
    </dependency>
</dependencies>
```

application.yml配置文件内容：
```yaml
## 具体配置参考：https://doc.xiaominfo.com/knife4j/resources/aggregation-nacos.html
knife4j:
  # 是否启用聚合模式
  enableAggregation: true
  basicAuth:
    # 是否启用Basic验证，默认是false
    enable: true
    # Basic用户名
    username: root
    # Basic密码
    password: root
  nacos:
    # 将该属性设置为true，则代表启用nacos模式
    enable: true
    # nacos注册中心的地址
    serviceUrl: http://localhost:28999/nacos
    # 该属性是一个公共路由Basic验证属性(可选)，如果开发者提供的OpenAPI规范的服务需要以路由Basic验证进行鉴权访问，
    # 那么可以配置该属性，如果配置该属性，则该模式下所有配置的Routes节点接口都会以Basic验证信息访问接口
#    routeAuth:
#      # 是否启用路由Basic验证，默认为false
#      enable: true
#      username: nacos
#      password: nacos
    # 需要聚合的服务集合(必选)，可以配置多个
    routes:
      # 服务名称(显示名称，最终在Ui的左上角下拉框进行显示)，如果该属性不配置，最终Ui会显示serviceName
      - name: 订单服务
        # nacos注册中心的服务名称
        service-name: service-order
        # 命名空间id,非必须,开发者根据自己的实际情况进行配置
        namespace-id: apidoc-swagger-samples
        # Nacos分组名称,非必须,开发者根据自己的实际情况进行配置
        group-name: DEV_GROUP
        # 具体资源接口地址，最终Knife4j是通过注册服务uri+location的组合路径进行访问
        location: /v3/api-docs
        # 该属性是最终在Ui中展示的接口前缀属性，提供该属性的目的也是因为通常开发者在以Gateway等方式聚合时，需要一个前缀路径来进行转发，而最终这个前缀路径会在每个接口中进行追加
        service-path: /api
      - name: 用户服务
        service-name: service-user
        namespace-id: apidoc-swagger-samples
        group-name: DEV_GROUP
        location: /v3/api-docs
        service-path: /api
```

被聚合的微服务（service-order|user）引入的依赖
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