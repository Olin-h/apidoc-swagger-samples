package org.olinonee.framework.swagger.service.doc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * DOC 服务启动器
 *
 * @author olinH, olinone666@gmail.com
 * @version v1.0.0
 * @since 2022-07-04
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ServiceDocApplication {
	public static void main(String[] args) {
		SpringApplication.run(ServiceDocApplication.class, args);
	}
}
