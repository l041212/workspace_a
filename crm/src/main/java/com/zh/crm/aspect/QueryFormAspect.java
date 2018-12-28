package com.zh.crm.aspect;

import java.util.List;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zh.crm.annotation.QueryForm;

@Aspect
@Component
public class QueryFormAspect {

	@Pointcut(value = "@annotation(com.zh.crm.annotation.QueryForm)")
	public void handlerMethodPointcut() {
	}

	@SuppressWarnings("unchecked")
	@Before(value = "handlerMethodPointcut() && @annotation(QueryForm)", argNames = "JoinPoint, QueryForm")
	public void dataTransfer(JoinPoint point, QueryForm annotation) {
		if (point.getArgs() != null && point.getArgs().length > 0) {
			for (Object arguments : point.getArgs()) {
				if (arguments instanceof Map) {
					Map<String, Object> parameters = (Map<String, Object>) arguments;
					int pageNumber = parameters.containsKey("pageNumber") ? Integer.valueOf(parameters.get("pageNumber").toString()) : 1;
					int pageSize = parameters.containsKey("pageSize") ? Integer.valueOf(parameters.get("pageSize").toString()) : 10;
					parameters.put("order", parameters.containsKey("order") ? parameters.get("order") : "desc");
					parameters.put("sort", parameters.containsKey("sort") ? parameters.get("sort") : "start_time");
					parameters.put("keywords", parameters.containsKey("keywords") ? parameters.get("keywords") : null);
					PageHelper.startPage(pageNumber, pageSize);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@AfterReturning(value = "handlerMethodPointcut() && @annotation(QueryForm)", argNames = "JoinPoint, QueryForm, Object", returning = "Object")
	public void pageInfoTransfer(JoinPoint point, QueryForm annotation, Object object) {
		if (object != null && object instanceof Map) {
			Map<String, Object> map = (Map<String, Object>) object;
			PageInfo<Object> pageInfo = new PageInfo<Object>(map.containsKey("list") ? (List<Object>) map.get("list") : null);
			map.put("rows", map.containsKey("rows") ? pageInfo.getList() : null);
			map.put("total", map.containsKey("total") ? pageInfo.getTotal() : 0);
			map.remove("list");
		}
	}

}
