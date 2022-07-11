package org.olinonee.framework.knife4j.v3.web.config;

import cn.hutool.core.util.StrUtil;
import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.olinonee.framework.knife4j.v3.web.factory.YamlPropertySourceFactory;
import org.olinonee.framework.knife4j.v3.web.prop.CustomizeSwaggerProperties;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.PathProvider;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.paths.DefaultPathProvider;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.plugins.WebMvcRequestHandlerProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Field;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * swagger自动配置类
 *
 * @author olinH, olinone666@gmail.com
 * @version v1.0.0
 * @since 2022-07-07
 */
@Configuration
@EnableSwagger2
@AllArgsConstructor
@Import(BeanValidatorPluginsConfiguration.class)
@EnableConfigurationProperties(CustomizeSwaggerProperties.class)
@PropertySource(factory = YamlPropertySourceFactory.class, value = "classpath:swagger.yml")
public class SwaggerAutoConfiguration implements WebMvcConfigurer {
	private final CustomizeSwaggerProperties swaggerProperties;

	private final OpenApiExtensionResolver openApiExtensionResolver;

	@Bean
	public PathProvider pathProvider() {
		return new DefaultPathProvider() {
			@Override
			public String getOperationPath(String operationPath) {
				return super.getOperationPath(operationPath);
			}
		};
	}

	/**
	 * Swagger忽略的参数类型
	 */
	@SuppressWarnings("all")
	private final Class[] ignoredParameterTypes = new Class[]{
		ServletRequest.class,
		ServletResponse.class,
		HttpServletRequest.class,
		HttpServletResponse.class,
		HttpSession.class,
		ApiIgnore.class,
		Principal.class,
		Map.class
	};

	@Bean(value = "restApi")
	@ConditionalOnMissingBean
	public Docket createRestApi() {
		// ApiSelectorBuilder apiSelectorBuilder = new Docket(DocumentationType.SWAGGER_2)
		ApiSelectorBuilder apiSelectorBuilder = new Docket(DocumentationType.OAS_30)
			// 用来创建该API的基本信息，展示在文档的页面中（自定义展示的信息）
			.apiInfo(groupApiInfo())
			// 设置哪些接口暴露给Swagger展示
			.select();
		if (StrUtil.isBlank(swaggerProperties.getBasePackage())) {
			// 扫描所有有注解的api，用这种方式更灵活
			apiSelectorBuilder.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class));
		} else {
			// 扫描指定的包
			apiSelectorBuilder.apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage()));
		}
		return apiSelectorBuilder.paths(PathSelectors.any())
			.build()
			.enable(swaggerProperties.isEnable())
			.securitySchemes(securitySchemes())
			.securityContexts(securityContexts())
			.pathProvider(pathProvider())
			.ignoredParameterTypes(ignoredParameterTypes)
			.pathMapping("/")
			.extensions(openApiExtensionResolver.buildExtensions("restApi"));
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/js/**").addResourceLocations("classpath:/js/");
		registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
		registry.addResourceHandler("/favicon.ico").addResourceLocations("classpath:/static/");
	}

	private ApiInfo groupApiInfo() {
		return new ApiInfoBuilder()
			.title(swaggerProperties.getTitle())
			.description(swaggerProperties.getDescription())
			.license(swaggerProperties.getLicense())
			.licenseUrl(swaggerProperties.getLicenseUrl())
			.termsOfServiceUrl(swaggerProperties.getTermsOfServiceUrl())
			.contact(new Contact(swaggerProperties.getContactName(),
				swaggerProperties.getContactUrl(),
				swaggerProperties.getContactEmail()))
			.version(swaggerProperties.getVersion())
			.build();
	}


	private List<SecurityScheme> securitySchemes() {
		List<SecurityScheme> securitySchemeList = new ArrayList<>();
		securitySchemeList.add(new ApiKey("Authorization", "Authorization", "header"));
		securitySchemeList.add(new ApiKey("Knife4j-Auth", "Knife4j-Auth", "header"));
		return securitySchemeList;
	}

	/**
	 * swagger2 认证的安全上下文
	 */
	private List<SecurityContext> securityContexts() {
		List<SecurityContext> securityContexts = new ArrayList<>();
		securityContexts.add(
			SecurityContext.builder()
				.securityReferences(defaultAuth())
				.forPaths(PathSelectors.regex("^(?!auth).*$")) // 2.0.9版本使用这种方式，3.0.3版本使用下行的这种方式
				// .operationSelector(operationContext -> operationContext.requestMappingPattern().matches("^(?!auth).*$"))
				.build());
		return securityContexts;
	}

	List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		List<SecurityReference> securityReferences = new ArrayList<>();
		securityReferences.add(new SecurityReference("Authorization", authorizationScopes));
		return securityReferences;
	}

	/**
	 * 解决与knife4j有兼容问题
	 *
	 * @see <a href="https://github.com/xiaoymin/swagger-bootstrap-ui/issues/396">swagger-bootstrap-ui（issues-397）</a>
	 * @see <a href="https://github.com/springfox/springfox/issues/3462">springfox（issues-3462）</a>
	 */
	@Bean
	@SuppressWarnings("all")
	public static BeanPostProcessor springfoxHandlerProviderBeanPostProcessor() {
		return new BeanPostProcessor() {

			@Override
			public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
				if (bean instanceof WebMvcRequestHandlerProvider) {
					customizeSpringfoxHandlerMappings(getHandlerMappings(bean));
				}
				return bean;
			}

			private <T extends RequestMappingInfoHandlerMapping> void customizeSpringfoxHandlerMappings(List<T> mappings) {
				List<T> copy = mappings.stream()
					.filter(mapping -> mapping.getPatternParser() == null)
					.collect(Collectors.toList());
				mappings.clear();
				mappings.addAll(copy);
			}

			@SuppressWarnings("unchecked")
			private List<RequestMappingInfoHandlerMapping> getHandlerMappings(Object bean) {
				try {
					Field field = ReflectionUtils.findField(bean.getClass(), "handlerMappings");
					field.setAccessible(true);
					return (List<RequestMappingInfoHandlerMapping>) field.get(bean);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					throw new IllegalStateException(e);
				}
			}
		};
	}
}
