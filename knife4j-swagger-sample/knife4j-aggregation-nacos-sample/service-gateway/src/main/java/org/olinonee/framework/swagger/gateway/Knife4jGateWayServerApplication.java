package org.olinonee.framework.swagger.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 网关启动器
 *
 * @author olinH, olinone666@gmail.com
 * @version v1.0.0
 * @since 2022-07-04
 */
@EnableDiscoveryClient
@SpringBootApplication
public class Knife4jGateWayServerApplication implements WebMvcConfigurer {
	public static void main(String[] args) {
		SpringApplication.run(Knife4jGateWayServerApplication.class, args);
	}
}
