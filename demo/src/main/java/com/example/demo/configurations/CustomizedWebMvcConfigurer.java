package com.example.demo.configurations;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
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
		registry.addRedirectViewController("/", "/login");
		registry.addViewController("/login").setViewName("/theatre/login.html");
		registry.addViewController("/index").setViewName("/theatre/index.html");
		registry.addViewController("/unauthorized").setViewName("/theatre/exception/401.html");
		registry.addViewController("/forbidden").setViewName("/theatre/exception/403.html");
		registry.addViewController("/notFound").setViewName("/theatre/exception/404.html");
		registry.addViewController("/internalServerError").setViewName("/theatre/exception/500.html");
	}

	@Bean
	public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer() {
		return (ConfigurableWebServerFactory factory) -> {
			List<ErrorPage> errorPages = new ArrayList<ErrorPage>();
			errorPages.add(new ErrorPage(HttpStatus.UNAUTHORIZED, "/unauthorized"));
			errorPages.add(new ErrorPage(HttpStatus.FORBIDDEN, "/forbidden"));
			errorPages.add(new ErrorPage(HttpStatus.NOT_FOUND, "/notFound"));
			errorPages.add(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/internalServerError"));
			factory.addErrorPages(errorPages.toArray(new ErrorPage[errorPages.size()]));
		};
	}

}
