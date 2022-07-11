package org.olinonee.framework.knife4j.v3.web.prop;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * swagger 常用配置属性
 *
 * @author olinH, olinone666@gmail.com
 * @version v1.0.0
 * @since 2022-07-07
 */
@Getter
@Setter
@RefreshScope
@ConfigurationProperties(prefix = "customize.swagger")
public class CustomizeSwaggerProperties {

	/**
	 * 是否启用Swagger，默认不启用
	 */
	private boolean enable = false;

	/**
	 * 是否是生产环境，生产环境建议屏蔽
	 */
	private boolean isProduction = false;

	/**
	 * 扫描的基本包
	 */
	private String basePackage = "org.olinonee.framework.knife4j";

	/**
	 * ApiInfo标题
	 */
	private String title = "接口文档管理中心";

	/**
	 * ApiInfo描述
	 */
	private String description = "接口文档管理中心";

	/**
	 * ApiInfo许可证
	 */
	private String license = "Powered by olinonee";

	/**
	 * ApiInfo许可证url
	 */
	private String licenseUrl = "https://gitee.com/olinonee";

	/**
	 * ApiInfo服务条款url
	 */
	private String termsOfServiceUrl = "https://gitee.com/olinonee";

	/**
	 * 联系人姓名
	 */
	private String contactName = "olinonee";

	/**
	 * 联系人url
	 */
	private String contactUrl = "https://gitee.com/olinonee";

	/**
	 * 联系人邮箱
	 */
	private String contactEmail = "olinone666@gmail.com";

	/**
	 * 定义的版本号，和项目版本保持一致
	 */
	private String version = "1.0.1";
}
