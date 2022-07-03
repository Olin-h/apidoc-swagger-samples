package org.olinonee.framework.springdoc.openapi.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * swagger自动配置类
 * <pre>
 * 说明：
 * GroupedOpenApi 可以定义多个服务对应的分组api Bean对象
 * OpenAPI 只可以定义一个开放api Bean对象
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
				.license(new License()
					.name("Apache 2.0")
					.url("https://gitee.com/OlinOnee/apidoc-swagger-samples")))
			.externalDocs(new ExternalDocumentation()
				.description("springdoc openapi Wiki Documentation")
				.url("https://gitee.com/OlinOnee/apidoc-swagger-samples"));
	}
}
