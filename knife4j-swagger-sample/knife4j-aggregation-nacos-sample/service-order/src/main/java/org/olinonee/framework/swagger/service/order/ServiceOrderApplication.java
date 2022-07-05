package org.olinonee.framework.swagger.service.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 订单 服务启动器
 *
 * @author olinH, olinone666@gmail.com
 * @version v1.0.0
 * @since 2022-07-04
 */
@EnableDiscoveryClient
@SpringBootApplication
public class ServiceOrderApplication {
	public static void main(String[] args) {
		SpringApplication.run(ServiceOrderApplication.class, args);
	}
}
