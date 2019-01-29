package com.example.demo.configurations;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.utils.CustomizedRealm;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

@Configuration
public class ShiroConfigurer implements ApplicationContextAware {

	private static ConfigurableApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		applicationContext = (ConfigurableApplicationContext) context;
	}

	private void loadShiroCustomizedFilter(ShiroFilterFactoryBean shiroFilterFactoryBean) {
		Map<String, Filter> filters = new LinkedHashMap<String, Filter>();
		shiroFilterFactoryBean.setFilters(filters);
	}

	private void loadShiroFilterChain(ShiroFilterFactoryBean shiroFilterFactoryBean) {
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		filterChainDefinitionMap.put("/plugin/**", "anon");
		filterChainDefinitionMap.put("/script/**", "anon");
		filterChainDefinitionMap.put("/style/**", "anon");
		filterChainDefinitionMap.put("/", "anon");
		filterChainDefinitionMap.put("/login", "anon");
		filterChainDefinitionMap.put("/theatre/login/signIn", "anon");
		filterChainDefinitionMap.put("/theatre/actor/helloworld", "roles[admin]");
		filterChainDefinitionMap.put("/**", "user");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
	}

	@Bean
	public ShiroFilterFactoryBean shiroFilter() {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager());
		loadShiroCustomizedFilter(shiroFilterFactoryBean);
		loadShiroFilterChain(shiroFilterFactoryBean);
		shiroFilterFactoryBean.setLoginUrl("/login");
		return shiroFilterFactoryBean;
	}

	@Bean
	public DefaultWebSecurityManager securityManager() {
		DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
		defaultWebSecurityManager.setRealm(shiroRealm());
		defaultWebSecurityManager.setRememberMeManager(applicationContext.getBean(CookieRememberMeManager.class));
		return defaultWebSecurityManager;
	}

	@Bean
	public Realm shiroRealm() {
		return new CustomizedRealm();
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
			DefaultWebSecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	@Bean
	public ShiroDialect shiroDialect() {
		return new ShiroDialect();
	}

}
