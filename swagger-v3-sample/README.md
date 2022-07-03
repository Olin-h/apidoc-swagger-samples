## swagger v3 文档说明

### quickstart

启动`swagger-v3-sample`模块的应用程序

浏览器访问如下地址：

```http
http://localhost:700/swagger-v3/swagger-ui/
```
---
### 引入的依赖
```xml
<properties>
    <maven.compiler.source>8</maven.compiler.source>
    <maven.compiler.target>8</maven.compiler.target>

    <springfox-boot.version>3.0.0</springfox-boot.version>
</properties>

<dependencies>
    <dependency>
        <groupId>io.springfox</groupId>
        <artifactId>springfox-boot-starter</artifactId>
        <version>${springfox-boot.version}</version>
    </dependency>
</dependencies>
```
---
### 添加配置
```yaml
server:
  port: 700
  servlet:
    context-path: /swagger-v3

spring:
  application:
    name: swagger-v3-sample
  mvc:
    # 解决Spring Boot 2.6.X swagger接口列表不显示的问题 这是因为Springfox使用的路径匹配是基于AntPathMatcher的，而Spring Boot 2.6.X使用的是PathPatternMatcher。
    pathmatch:
      matching-strategy: ant_path_matcher
```
---
### swagger V2和V3 注解对比
swagger 3 注解的包路径为`io.swagger.v3.oas.annotations`。

| swagger2           |                       openApi3(swagger3)                        | 注解位置                         |
|:------------------:|:---------------------------------------------------------------:|:----------------------------:|
| @Api               |                      @Tag(name = “接口类描述”)                       | Controller 类上                |
| @ApiOperation      |                  @Operation(summary =“接口方法描述”)                  | Controller 方法上               |
| @ApiImplicitParams |                           @Parameters                           | Controller 方法上               |
| @ApiImplicitParam  |                 @Parameter(description=“参数描述”)                  | Controller 方法上 @Parameters 里 |
| @ApiParam          |                 @Parameter(description=“参数描述”)                  | Controller 方法的参数上            |
| @ApiIgnore         | @Parameter(hidden = true) 或 @Operation(hidden = true) 或 @Hidden | -                            |
| @ApiModel          |                             @Schema                             | DTO类上                        |
| @ApiModelProperty  |                             @Schema                             | DTO属性上                       |

---

### 注意事项

#### 1. Spring Boot 2.6.X swagger接口列表不显示

> 这是因为Springfox使用的路径匹配是基于AntPathMatcher的，而Spring Boot 2.6.X使用的是PathPatternMatcher

需要在`application.yml`文件中追加如下配置：

```yaml
spring:
  mvc:
    pathmatch:
      matching-strategy: ant_path_matcher
```

#### 2. 解决springboot2.6 和 springfox不兼容问题

> 异常信息：Failed to start bean 'documentationPluginsBootstrapper'; nested exception is java.lang.NullPointerException

##### 解决办法：

**两种方法**：<font color="red">避免代码冗余，推荐使用第二种。</font>

###### 方法一

在swagger配置类中增加如下配置：

```java
package org.olinonee.framework.swagger.v3.config;

import cn.hutool.core.collection.CollUtil;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.plugins.WebMvcRequestHandlerProvider;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * swagger自动配置类
 *
 * @author olinH, olinone666@gmail.com
 * @version v1.0.0
 * @since 2022-07-01
 */
@Configuration
@EnableOpenApi
public class SwaggerAutoConfiguration {
    // ...

    /**
     * 解决springboot2.6 和springfox不兼容问题
     * <pre>
     * Failed to start bean 'documentationPluginsBootstrapper'; nested exception is java.lang.NullPointerException
     * </pre>
     *
     * @see <a href="https://github.com/xiaoymin/swagger-bootstrap-ui/issues/396">https://github.com/xiaoymin/swagger-bootstrap-ui/issues/396</a>
     * @see <a href="https://github.com/springfox/springfox/issues/3462">https://github.com/springfox/springfox/issues/3462</a>
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
```

###### 方法二

跟注意事项【1. Spring Boot 2.6.X swagger接口列表不显示】解决方案一样

需要在`application.yml`文件中追加如下配置：

```yaml
spring:
  mvc:
    pathmatch:
      matching-strategy: ant_path_matcher
```