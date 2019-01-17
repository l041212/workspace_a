package com.example.demo.aspects;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.example.demo.annotations.TargetDataSource;
import com.example.demo.utils.DynamicDataSourceContextHolder;

@Aspect
@Order(0)
@Component
public class DataSourceAspect {

	private final static Logger logger = LoggerFactory.getLogger(DataSourceAspect.class);

	@Pointcut("@annotation(com.example.demo.annotations.TargetDataSource)")
	private void targetPointCut() {
	}

	@Before(value = "targetPointCut() && @annotation(TargetDataSource)", argNames = "JoinPoint, TargetDataSource")
	public void targetTransfer(JoinPoint point, TargetDataSource annotation) {
		if (annotation != null && StringUtils.isNotBlank(annotation.label())) {
			if (DynamicDataSourceContextHolder.isContainsDataSource(annotation.label())) {
				DynamicDataSourceContextHolder.setDataSource(annotation.label());
				logger.info("DataSource is changed...");
			}
		}
		logger.info("DataSource: " + DynamicDataSourceContextHolder.getDataSource());
	}

	@After(value = "targetPointCut() && @annotation(TargetDataSource)", argNames = "JoinPoint, TargetDataSource")
	public void targetCleaner(JoinPoint point, TargetDataSource annotation) {
		DynamicDataSourceContextHolder.removeDataSoure();
		logger.info("DataSource is cleaned...");
		logger.info("DataSource: " + DynamicDataSourceContextHolder.getDataSource());
	}

}
