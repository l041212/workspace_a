package com.example.demo.configurations;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.demo.interceptors.TestInfoInterceptor;
import com.example.demo.properties.FileProperty;

@Configuration
public class CustomizedWebMvcConfigurer implements WebMvcConfigurer {

	@Autowired
	private FileProperty fileProperty;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new TestInfoInterceptor()).addPathPatterns("/**");
		WebMvcConfigurer.super.addInterceptors(registry);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(fileProperty.getAccessPath())
				.addResourceLocations("file:" + fileProperty.getLocalPath() + File.separator);
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {

	}

}
