package com.example.demo.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

public class TestInfoInterceptor implements HandlerInterceptor {

	private final static Logger logger = LoggerFactory.getLogger(TestInfoInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		boolean flag = true;
		logger.info("session id: " + request.getSession().getId());
		logger.info("session last accessed time: " + request.getSession().getLastAccessedTime());
		logger.info("request uri path:" + request.getRequestURI());
		return flag;
	}

}
