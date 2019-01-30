package com.example.demo.configurations.shiro;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.utils.AESCrypt;

@Configuration
public class RememberMeConfigurer {

	private static final String AES_SECRET_KEY = "abcddcbaabcddcba";
	private static final String AES_PLAIN_TEXT = "beijingtianya";
	private static final String COOKIE_TEXT = "rememberMe";
	private static final int MAX_AGE = 3600 * 24 * 7;

	@Bean
	public SimpleCookie rememberMeCookie() {
		SimpleCookie simpleCookie = new SimpleCookie(COOKIE_TEXT);
		simpleCookie.setHttpOnly(true);
		simpleCookie.setMaxAge(MAX_AGE);
		return simpleCookie;
	}

	@Bean
	public CookieRememberMeManager rememberMeManager() {
		CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
		cookieRememberMeManager.setCookie(rememberMeCookie());
		cookieRememberMeManager.setCipherKey(Base64.decode(AESCrypt.encrypt(AES_SECRET_KEY, AES_PLAIN_TEXT)));
		return cookieRememberMeManager;
	}

	@Bean
	public FormAuthenticationFilter formAuthenticationFilter() {
		FormAuthenticationFilter formAuthenticationFilter = new FormAuthenticationFilter();
		formAuthenticationFilter.setRememberMeParam(COOKIE_TEXT);
		return formAuthenticationFilter;
	}

}
