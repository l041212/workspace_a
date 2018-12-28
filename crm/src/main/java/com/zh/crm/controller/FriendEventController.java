package com.zh.crm.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zh.crm.annotation.QueryForm;
import com.zh.crm.entity.FriendEvent;
import com.zh.crm.entity.Result;
import com.zh.crm.service.FriendEventService;
import com.zh.crm.util.DozerUtil;

@CrossOrigin
@Controller
public class FriendEventController {

	@Autowired
	private DozerUtil dozerUtil;
	@Autowired
	private Environment environment;
	@Autowired
	private FriendEventService friendEventService;

	@GetMapping(path = "/toFriendEvent")
	public String toFriendEventListPage() {
		return "/view/bus_friend/friend_event_list";
	}

	@GetMapping(path = { "/friendEvent", "/friendEvent/{id}" })
	public String toFriendEventSave(@PathVariable(name = "id", required = false) Integer id, Map<String, Object> map) {
		FriendEvent friendEvent = (id != null) ? friendEventService.find(id) : null;
		map.put("friendEvent", friendEvent);
		return "/view/bus_friend/friend_event_save.html";
	}

	@ResponseBody
	@PostMapping(path = { "/friendEvent" })
	public int addFriendEvent(@RequestParam Map<String, Object> map) {
		String srvPath = environment.getProperty("file.uploadFolder");
		String urlPath = environment.getProperty("file.staticAccessPath").replace("/**", "");
		ShiftController.transAttachment(map, srvPath, urlPath);
		return friendEventService.insert(map);
	}

	@ResponseBody
	@PutMapping(path = { "/friendEvent/{id}" })
	public int editFriendEvent(@PathVariable(name = "id", required = true) Integer id, @RequestParam Map<String, Object> map) {
		String srvPath = environment.getProperty("file.uploadFolder");
		String urlPath = environment.getProperty("file.staticAccessPath").replace("/**", "");
		ShiftController.transAttachment(map, srvPath, urlPath);
		return friendEventService.update(map);
	}

	@ResponseBody
	@DeleteMapping(path = { "/friendEvent/{id}" })
	public int deleteFriendEvent(@PathVariable(name = "id", required = true) Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("status", 1);
		return friendEventService.update(map);
	}

	@SuppressWarnings("unchecked")
	@QueryForm
	@ResponseBody
	@GetMapping(path = { "/friendEvents" })
	public Map<String, Object> getFriendEventList(@RequestParam Map<String, Object> data) {
		Map<String, Object> result = dozerUtil.convert(new Result(), Map.class);
		data.put("status", 0);
		data.put("sort", data.get("sort") != "start_time" ? data.get("sort") : "time_to");
		result.put("list", friendEventService.findAll(data));
		return result;
	}

}
