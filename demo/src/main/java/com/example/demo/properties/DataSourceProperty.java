package com.example.demo.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.zaxxer.hikari.HikariDataSource;

@Component
@PropertySource(value = { "classpath:/properties/datasources.properties" })
@ConfigurationProperties(prefix = "datasource")
public class DataSourceProperty {

	private HikariDataSource library0;
	private HikariDataSource library1;

	public HikariDataSource getLibrary0() {
		return library0;
	}

	public void setLibrary0(HikariDataSource library0) {
		this.library0 = library0;
	}

	public HikariDataSource getLibrary1() {
		return library1;
	}

	public void setLibrary1(HikariDataSource library1) {
		this.library1 = library1;
	}

}
