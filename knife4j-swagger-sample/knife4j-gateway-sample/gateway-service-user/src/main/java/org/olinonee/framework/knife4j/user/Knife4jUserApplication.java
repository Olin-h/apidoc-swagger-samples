package org.olinonee.framework.knife4j.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * knife4j 用户启动器
 *
 * @author olinH, olinone666@gmail.com
 * @version v1.0.0
 * @since 2022-07-04
 */
@SpringBootApplication
@EnableDiscoveryClient
public class Knife4jUserApplication {
	public static void main(String[] args) {
		SpringApplication.run(Knife4jUserApplication.class, args);
	}
}
