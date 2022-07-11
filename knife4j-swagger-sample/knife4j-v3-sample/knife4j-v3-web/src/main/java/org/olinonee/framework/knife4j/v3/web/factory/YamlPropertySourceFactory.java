package org.olinonee.framework.knife4j.v3.web.factory;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * 加载yml格式的自定义配置文件
 *
 * 参考链接：<a href="https://blog.csdn.net/zxl8899/article/details/106382719/">YamlPropertySourceFactory</a>
 *
 * @author olinH, olinone666@gmail.com
 * @version v1.0.0
 * @since 2022-07-07
 */
@AllArgsConstructor
@SuppressWarnings("all")
public class YamlPropertySourceFactory implements PropertySourceFactory {

	public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
		Properties propertiesFromYaml = loadYamlIntoProperties(resource);
		String sourceName = name != null ? name : resource.getResource().getFilename();
		assert sourceName != null;
		return new PropertiesPropertySource(sourceName, propertiesFromYaml);
	}

	private Properties loadYamlIntoProperties(EncodedResource resource) throws FileNotFoundException {
		try {
			YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
			factory.setResources(resource.getResource());
			factory.afterPropertiesSet();
			return factory.getObject();
		} catch (IllegalStateException e) {
			Throwable cause = e.getCause();
			if (cause instanceof FileNotFoundException) {
				throw (FileNotFoundException) e.getCause();
			}
			throw e;
		}
	}
}
