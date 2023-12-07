package com.hzr.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(DemoApplication.class, args);
		// 获取容器中所有bean的名字
		// 默认所有的bean都是单实例，默认懒加载
		// 容器中有什么组件，就具有什么功能
		String[] names = run.getBeanDefinitionNames();
		for (String name : names) {
			System.out.println(name);
		}
	}

}
