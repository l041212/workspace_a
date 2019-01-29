package com.example.theatre.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/theatre/actorTemp")
public class ActorTempController {

	private final static Logger logger = LoggerFactory.getLogger(ActorTempController.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@GetMapping("/info")
	@RequiresPermissions("read:p1")
	public String info(HttpServletRequest request, Map<String, Object> map) {

		String sql = "select * from actor";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);

		logger.info("------------------------");
		logger.info(request.toString());
		map.put("test", "test...");
		map.put("list", list);
		logger.info(map.toString());
		logger.info("------------------------");

		return "/theatre/actor/info.html";

	}

	@GetMapping("/test")
	public String test(HttpServletRequest request, Map<String, Object> map) {
		return "/theatre/actor/test.html";
	}

}
