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
### 创建配置类

关于配置的创建这里有2种方式：

- 纯注解方式 @OpenAPIDefinition+@SecurityScheme（简洁）
- 手动编写固定配置 @Bean注解注入对象（代码多）

**注意**：
_如果需要分别体验两种方式（效果一样），注意修改resources目录META-INF目录下spring.factories文件注入的自动配置类以及注释掉另外一种方式的所有代码，避免启动异常。_

#### 方式一
配置类代码如下：
```java
/**
 * swagger自动配置类 纯注解版本
 * <p>
 * 注解配置说明：
 * <ul>
 * <li>@OpenAPIDefinition全局只能定义一个，主要配置文档信息和安全配置，这里列举了常用的请求头携带token的安全配置模式</li>
 * <li>@OpenAPIDefinition下的info属性配置文档信息</li>
 * <li>@OpenAPIDefinition下的security配置认证方式，name属性引入自定义的认证模式</li>
 * <li>@SecurityScheme注解就是自定义的认证模式，配置请求头携带token</li>
 * </ul>
 *
 * @author olinH, olinone666@gmail.com
 * @version v1.0.0
 * @since 2022-07-04
 */
@OpenAPIDefinition(info = @Info(title = "SpringDoc OpenAPI",
	description = "springdoc openapi sample application",
	version = "v1.0.1",
	contact = @Contact(name = "olinonee",
		url = "https://gitee.com/OlinOnee",
		email = "olinone666@gmail.com")),
	security = {@SecurityRequirement(name = "JWT")})
@SecurityScheme(type = SecuritySchemeType.HTTP, name = "JWT", scheme = "bearer", in = SecuritySchemeIn.HEADER)
public class AnnotationVersionSwaggerAutoConfiguration {

}
```

application.yml配置内容如下：
```yaml
# swagger-ui自定义路径
springdoc:
  api-docs:
    # 是否开启文档功能
    enabled: true
  swagger-ui:
    # 自定义swagger路径，访问路径：http://server:port/context-path/swagger-ui.html
    path: /swagger-ui.html
  # 包扫描路径
  packages-to-scan: org.olinonee.framework.springdoc.openapi.controller,org.olinonee.framework.springdoc.openapi.entity
  # 这里定义了两个分组，可定义多个，也可以不定义
  group-configs:
    # 分组名
    - group: order-api
      # 按路径匹配
      pathsToMatch: /order/**
      # 分组名
    - group: user-api
      # 按路径匹配
      pathsToMatch: /user/**
```

#### 方式二
配置类代码如下：
```java
/**
 * swagger自动配置类 配置版本
 * <pre>
 * 说明：
 * GroupedOpenApi 可以定义多个服务对应的分组api Bean对象
 * OpenAPI 只可以定义一个开放api Bean对象
 * 
 * 使用此版本需要将@Bean注解打开并且两种方式任选其一
 * </pre>
 *
 * @author olinH, olinone666@gmail.com
 * @version v1.0.0
 * @since 2022-07-01
 */
@Configuration
public class SwaggerAutoConfiguration {

	/**
	 * user 分组api
	 *
	 * @return GroupedOpenApi
	 */
	@Bean
	public GroupedOpenApi userGroupApi() {
		return GroupedOpenApi.builder()
			.group("user-api")
			.pathsToMatch("/user/**")
			.packagesToScan("org.olinonee.framework.springdoc.openapi.controller")
			.build();
	}

	/**
	 * order 分组api
	 *
	 * @return GroupedOpenApi
	 */
	@Bean
	public GroupedOpenApi orderGroupApi() {
		return GroupedOpenApi.builder()
			.group("order-api")
			.pathsToMatch("/order/**")
			.packagesToScan("org.olinonee.framework.springdoc.openapi.controller")
			.build();
	}

	/**
	 * 公共开放api摘要
	 *
	 * @return OpenAPI
	 */
	@Bean
	public OpenAPI publicOpenApi() {
		return new OpenAPI()
			.info(new Info()
				.title("SpringDoc OpenAPI")
				.description("springdoc openapi sample application")
				.version("v1.0.1")
				.contact(new Contact()
					.name("olinonee")
					.url("https://gitee.com/OlinOnee")
					.email("olinone666@gmail.com"))
				.license(new License()
					.name("Apache 2.0")
					.url("https://gitee.com/OlinOnee/apidoc-swagger-samples")))
			.externalDocs(new ExternalDocumentation()
				.description("springdoc openapi Wiki Documentation")
				.url("https://gitee.com/OlinOnee/apidoc-swagger-samples"))
			// 全局安全配置项，也可以在对应的Controller上加注解SecurityRequirement
			.security(this.securityRequirements())
			// oauth2.0 password
			.schemaRequirement(HttpHeaders.AUTHORIZATION, this.securityScheme());
	}

	private List<SecurityRequirement> securityRequirements() {
		List<SecurityRequirement> securityRequirements = new ArrayList<>();
		SecurityRequirement securityRequirement = new SecurityRequirement();
		securityRequirement.addList(HttpHeaders.AUTHORIZATION);
		securityRequirements.add(securityRequirement);
		return securityRequirements;
	}

    private SecurityScheme securityScheme() {
        SecurityScheme securityScheme = new SecurityScheme();
        // 类型
        securityScheme.setType(SecurityScheme.Type.HTTP);
        // 请求头的name
        securityScheme.setName("JWT");
        // 设置scheme
        securityScheme.setScheme("bearer");
        // token所在位置
        securityScheme.setIn(SecurityScheme.In.HEADER);
        return securityScheme;
    }
}
```

application.yml配置内容如下：
```yaml
# swagger-ui自定义路径
springdoc:
  api-docs:
    # 是否开启文档功能
    enabled: true
  swagger-ui:
    # 自定义swagger路径，访问路径：http://server:port/context-path/swagger-ui.html
    path: /swagger-ui.html
```