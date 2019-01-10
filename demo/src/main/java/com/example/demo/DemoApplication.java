package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableCaching
@EnableTransactionManagement
@ComponentScan(basePackages = { "com.example.*" })
@MapperScan(basePackages = { "com.example.*.mapper" })
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class DemoApplication {

	private static ConfigurableApplicationContext applicationContext;

	public static ConfigurableApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static void main(String[] args) {
		applicationContext = SpringApplication.run(DemoApplication.class, args);
	}

}
