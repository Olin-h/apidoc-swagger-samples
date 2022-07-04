package org.olinonee.framework.springdoc.openapi.config;

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
// @OpenAPIDefinition(info = @Info(title = "SpringDoc OpenAPI",
// 	description = "springdoc openapi sample application",
// 	version = "v1.0.1",
// 	contact = @Contact(name = "olinonee",
// 		url = "https://gitee.com/OlinOnee",
// 		email = "olinone666@gmail.com")),
// 	security = {@SecurityRequirement(name = "JWT")})
// @SecurityScheme(type = SecuritySchemeType.HTTP, name = "JWT", scheme = "bearer", in = SecuritySchemeIn.HEADER)
public class AnnotationVersionSwaggerAutoConfiguration {

}
