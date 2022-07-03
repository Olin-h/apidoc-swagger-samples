## springdoc-openapi 文档说明
### quickstart
启动`springdoc-openapi-sample`模块的应用程序

浏览器访问如下地址：
```http request
http://127.0.0.1:500/springdoc-openapi/swagger-ui.html
```
---
### 引入的依赖
```xml
<properties>
    <maven.compiler.source>8</maven.compiler.source>
    <maven.compiler.target>8</maven.compiler.target>

    <springdoc-openapi.version>1.6.9</springdoc-openapi.version>
</properties>

<dependencies>
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-ui</artifactId>
    <version>${springdoc-openapi.version}</version>
</dependency>
</dependencies>
```
---
### 添加配置
```yaml
server:
  port: 500
  servlet:
    context-path: /springdoc-openapi

spring:
  application:
    name: springdoc-openapi-sample

# swagger-ui自定义路径
springdoc:
  api-docs:
    enabled: true
  swagger-ui:
    # 自定义swagger路径，访问路径：http://server:port/context-path/swagger-ui.html
    path: /swagger-ui.html
```
---
