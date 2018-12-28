package com.zh.crm.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.zh.crm.annotation.QueryForm;
import com.zh.crm.entity.FriendMember;
import com.zh.crm.entity.Type;
import com.zh.crm.entity.TypeExtend;
import com.zh.crm.entity.Result;
import com.zh.crm.service.FriendMemberService;
import com.zh.crm.service.TypeService;
import com.zh.crm.util.DozerUtil;
import com.zh.crm.util.EasyPoiUtils;
import com.zh.crm.util.ExcelMiniUtil;

@Controller
@CrossOrigin
public class FriendMemberController {

	@Autowired
	private DozerUtil dozerUtil;
	@Autowired
	private TypeService typeService;
	@Autowired
	private FriendMemberService friendMemberService;

	@GetMapping(path = "/toFriendMember")
	public String toFriendMemberListPage() {
		return "/view/bus_friend/friend_member_list";
	}

	@GetMapping(path = { "/friendMember", "/friendMember/{id}" })
	public String toFriendMemberSave(@PathVariable(name = "id", required = false) Integer id, Map<String, Object> map) {
		FriendMember friendMember = (id != null) ? friendMemberService.find(id) : null;
		map.put("friendMember", friendMember);
		return "/view/bus_friend/friend_member_save.html";
	}

	@ResponseBody
	@PostMapping(path = { "/friendMember" })
	public int addFriendMember(@RequestParam Map<String, Object> map) {
		return friendMemberService.insert(map);
	}

	@ResponseBody
	@PutMapping(path = { "/friendMember/{id}" })
	public int editFriendMember(@PathVariable(name = "id", required = true) Integer id, @RequestParam Map<String, Object> map) {
		return friendMemberService.update(map);
	}

	@ResponseBody
	@DeleteMapping(path = { "/friendMember/{id}" })
	public int deleteFriendMember(@PathVariable(name = "id", required = true) Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("status", 1);
		return friendMemberService.update(map);
	}

	@SuppressWarnings("unchecked")
	@QueryForm(FriendMember.class)
	@ResponseBody
	@GetMapping(path = { "/friendMembers" })
	public Map<String, Object> getFriendMemberList(@RequestParam Map<String, Object> data) {
		Map<String, Object> result = dozerUtil.convert(new Result(), Map.class);
		data.put("status", 0);
		data.put("sort", data.get("sort") != "start_time" ? data.get("sort") : "apply_time");
		result.put("list", friendMemberService.findAll(data));
		return result;
	}

	@ResponseBody
	@GetMapping("/friendMember/excel/model")
	public void exportFriendMemberExcelModel(HttpServletResponse response) {
		ExcelMiniUtil.reWriteModelWorkbook(response, "friendMemberListModel", FriendMember.class);
	}

	@ResponseBody
	@SuppressWarnings("unchecked")
	@PostMapping("/friendMember/excel/import")
	public void importFriendMemberExcel(MultipartHttpServletRequest request) {
		MultipartFile excelImportFile = request.getFile("excelImportFile");
		List<FriendMember> friendMembers = EasyPoiUtils.importExcel(excelImportFile, 1, 1, FriendMember.class);
		if (friendMembers != null && !friendMembers.isEmpty()) {
			for (FriendMember friendMember : friendMembers) {
				Map<String, Object> map = dozerUtil.convert(friendMember, Map.class);
				if (map.get("id") != null && Integer.valueOf(map.get("id").toString()) > 0) {
					friendMemberService.update(map);
				} else {
					friendMemberService.insert(map);
				}
			}
		}
	}

	@ResponseBody
	@GetMapping("/friendMember/excel/export")
	public void exportFriendMemberExcel(@RequestParam Map<String, String> map, HttpServletResponse response) {
		Map<String, Object> data = ShiftController.getQueryFormData(map, FriendMember.class);
		data.put("name", data.get("keywords"));
		data.put("status", "0");
		data.put("sort", "apply_time");
		EasyPoiUtils.exportExcel(friendMemberService.findAll(data), "friendMemberList", "friendMemberList", FriendMember.class, "friendMemberList.xls", true,
				response);
	}

	@ResponseBody
	@GetMapping(path = { "/friendMemberTypes/department/{flag}/{parentId}" })
	public List<Type> getFriendMemberDepartmentTypeList(@PathVariable(name = "parentId", required = true) Integer parentId,
			@PathVariable(name = "flag", required = true) Boolean flag) {
		return typeService.findAllValidTypeLoop(parentId, flag);
	}
	
	@ResponseBody
	@GetMapping(path = { "/friendMemberTypes/member/{flag}/{parentId}" })
	public List<TypeExtend<FriendMember>> getFriendMemberMemberTypeList(@PathVariable(name = "parentId", required = true) Integer parentId,
			@PathVariable(name = "flag", required = true) Boolean flag) {
		return friendMemberService.findAll(parentId, flag);
	}

}
