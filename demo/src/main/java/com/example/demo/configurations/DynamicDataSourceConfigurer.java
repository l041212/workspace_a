package com.example.demo.configurations;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import com.example.demo.properties.DataSourceProperty;
import com.example.demo.utils.DynamicDataSource;
import com.example.demo.utils.DynamicDataSourceContextHolder;

@Configuration
public class DynamicDataSourceConfigurer implements TransactionManagementConfigurer {

	@Autowired
	private DataSourceProperty dynamicDataSourceProperty;
	private final static Logger logger = LoggerFactory.getLogger(DynamicDataSourceConfigurer.class);

	@Bean(name = "dataSource")
	public DataSource dataSource() {
		try {
			Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
			DynamicDataSource dataSource = new DynamicDataSource();
			for (Field library : DataSourceProperty.class.getDeclaredFields()) {
				String get = "get" + String.valueOf(library.getName().charAt(0)).toUpperCase() + library.getName().substring(1);
				targetDataSources.put(library.getName(), DataSourceProperty.class.getDeclaredMethod(get).invoke(dynamicDataSourceProperty));
				DynamicDataSourceContextHolder.getLabels().add(library.getName());
			}
			dataSource.setTargetDataSources(targetDataSources);
			dataSource.setDefaultTargetDataSource(targetDataSources.get(DynamicDataSourceContextHolder.getLabels().get(0)));
			logger.info("targetDataSource: " + targetDataSources);
			return dataSource;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}

	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return transactionManager();
	}

}
