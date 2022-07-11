package org.olinonee.framework.knife4j.v3.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * swagger资源配置
 * <p>
 * 使用Spring Boot单体架构集成swagger时，是通过包路径进行业务分组，然后在前端进行不同模块的展示，而在微服务架构下，单个服务类似于原来业务组；
 * <p>
 * springfox-swagger提供的分组接口是swagger-resource，返回的是分组接口名称、地址等信息；
 * <p>
 * 在Spring Cloud微服务架构下，需要swagger-resource重写接口，由网关的注册中心动态发现所有的微服务文档
 *
 * @author olinH, olinone666@gmail.com
 * @version v1.0.0
 * @since 2022-06-29
 */
@Slf4j
@Component
@Primary
public class SwaggerResourceConfig implements SwaggerResourcesProvider {

	private final RouteLocator routeLocator;

	/**
	 * swagger2默认的url后缀
	 */
	private static final String SWAGGER2URL = "/v2/api-docs";

	/**
	 * 网关应用名称
	 */
	@Value("${spring.application.name}")
	private String self;

	@Autowired
	public SwaggerResourceConfig(RouteLocator routeLocator) {
		this.routeLocator = routeLocator;
	}

	@Override
	public List<SwaggerResource> get() {
		List<SwaggerResource> resources = new ArrayList<>();
		List<String> routeHosts = new ArrayList<>();
		// 由于我的网关采用的是负载均衡的方式，因此我需要拿到所有应用的serviceId
		// 获取所有可用的host：serviceId
		routeLocator.getRoutes()
			.filter(route -> route.getUri().getHost() != null)
			.filter(route -> !self.equals(route.getUri().getHost()))
			.subscribe(route -> routeHosts.add(route.getUri().getHost()));

		// 记录已经添加过的server，存在同一个应用注册了多个服务在nacos上
		Set<String> repeated = new HashSet<>();
		routeHosts.forEach(instance -> {
			// 拼接url，样式为/serviceId/v3/api-info，当网关调用这个接口时，会自动通过负载均衡寻找对应的主机
			String url = "/" + instance + SWAGGER2URL;
			if (!repeated.contains(url)) {
				repeated.add(url);
				SwaggerResource swaggerResource = new SwaggerResource();
				swaggerResource.setUrl(url);
				swaggerResource.setName(instance);
				swaggerResource.setSwaggerVersion("1.0.1");
				resources.add(swaggerResource);
			}
		});
		return resources;
	}
}
