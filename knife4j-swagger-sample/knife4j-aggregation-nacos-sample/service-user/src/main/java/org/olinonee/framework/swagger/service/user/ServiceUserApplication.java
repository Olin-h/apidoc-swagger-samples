package org.olinonee.framework.swagger.service.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 用户 服务启动器
 *
 * @author olinH, olinone666@gmail.com
 * @version v1.0.0
 * @since 2022-07-04
 */
@EnableDiscoveryClient
@SpringBootApplication
public class ServiceUserApplication {
	public static void main(String[] args) {
		SpringApplication.run(ServiceUserApplication.class, args);
	}
}
