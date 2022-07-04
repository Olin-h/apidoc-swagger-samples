package org.olinonee.framework.springdoc.openapi.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.List;

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
	// @Bean
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
	// @Bean
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
