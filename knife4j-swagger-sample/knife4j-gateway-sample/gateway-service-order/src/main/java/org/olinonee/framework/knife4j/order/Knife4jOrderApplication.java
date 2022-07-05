package org.olinonee.framework.knife4j.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * knife4j 订单启动器
 *
 * @author olinH, olinone666@gmail.com
 * @version v1.0.0
 * @since 2022-07-04
 */
@SpringBootApplication
@EnableDiscoveryClient
public class Knife4jOrderApplication {
	public static void main(String[] args) {
		SpringApplication.run(Knife4jOrderApplication.class, args);
	}
}
