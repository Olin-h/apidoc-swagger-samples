package org.olinonee.framework.swagger.v3.config;

import cn.hutool.core.collection.CollUtil;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * swagger自动配置类
 *
 * @author olinH, olinone666@gmail.com
 * @version v1.0.0
 * @since 2022-07-01
 */
@Configuration
@EnableSwagger2
@EnableOpenApi
public class SwaggerAutoConfiguration {

	@Bean(value = "userApi")
	public Docket userApi() {
		return new Docket(DocumentationType.OAS_30)
			.apiInfo(userApiInfo())
			.select()
			.apis(RequestHandlerSelectors.withMethodAnnotation(Operation.class))
			.paths(s -> s.contains("/user/"))
			.build()
			.groupName("user-api")
			.securitySchemes(securitySchemes())
			.securityContexts(securityContexts());
	}

	/**
	 * 创建Swagger页面 信息
	 */
	private ApiInfo userApiInfo() {
		return new ApiInfoBuilder()
			.title("用户服务 - 接口文档")
			.description("<div style='font-size:14px;color:red;'>用户服务聚合接口文档</div>")
			.license("Powered By Olinonee")
			.licenseUrl("https://gitee.com/OlinOnee/apidoc-swagger-samples")
			.termsOfServiceUrl("https://gitee.com/OlinOnee/apidoc-swagger-samples")
			.contact(new Contact("olinonee", "https://gitee.com/OlinOnee", "olinone666@gmail.com"))
			.version("1.0.1")
			.build();
	}

	@Bean(value = "orderApi")
	public Docket orderApi() {
		return new Docket(DocumentationType.OAS_30)
			.apiInfo(orderApiInfo())
			.select()
			.apis(RequestHandlerSelectors.withMethodAnnotation(Operation.class))
			.paths(s -> s.contains("/order/"))
			.build()
			.groupName("order-api")
			.securitySchemes(securitySchemes())
			.securityContexts(securityContexts());
	}

	/**
	 * 创建Swagger页面 信息
	 */
	private ApiInfo orderApiInfo() {
		return new ApiInfoBuilder()
			.title("订单服务 - 接口文档")
			.description("<div style='font-size:14px;color:red;'>订单服务聚合接口文档</div>")
			.license("Powered By Olinonee")
			.licenseUrl("https://gitee.com/OlinOnee/apidoc-swagger-samples")
			.termsOfServiceUrl("https://gitee.com/OlinOnee/apidoc-swagger-samples")
			.contact(new Contact("olinonee", "https://gitee.com/OlinOnee", "olinone666@gmail.com"))
			.version("1.0.1")
			.build();
	}

	private List<SecurityScheme> securitySchemes() {
		List<SecurityScheme> apiKeyList = new ArrayList<>();
		apiKeyList.add(new ApiKey("Authorization", "Authorization", "header"));
		apiKeyList.add(new ApiKey("BearerToken", "BearerToken", "header"));
		return apiKeyList;
	}

	/**
	 * swagger2 认证的安全上下文
	 */
	private List<SecurityContext> securityContexts() {
		List<SecurityContext> securityContexts = new ArrayList<>();
		securityContexts.add(authorizationSecurityContext());
		securityContexts.add(bearerTokenSecurityContext());
		return securityContexts;
	}

	private SecurityContext authorizationSecurityContext() {
		return SecurityContext
			.builder()
			.securityReferences(authorization())
			.operationSelector(operationContext -> operationContext.requestMappingPattern().matches("^(?!auth).*$"))
			.build();
	}

	private SecurityContext bearerTokenSecurityContext() {
		return SecurityContext
			.builder()
			.securityReferences(bearerToken())
			.operationSelector(operationContext -> operationContext.requestMappingPattern().matches("^(?!auth).*$"))
			.build();
	}

	List<SecurityReference> authorization() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		List<SecurityReference> securityReferences = new ArrayList<>();
		securityReferences.add(new SecurityReference("Authorization", authorizationScopes));
		return securityReferences;
	}

	List<SecurityReference> bearerToken() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return CollUtil.newArrayList(new SecurityReference("BearerToken", authorizationScopes));
	}
}
