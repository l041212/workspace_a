package com.zh.crm.controller;

import com.alibaba.fastjson.JSON;
import com.zh.crm.entity.Permission;
import com.zh.crm.entity.Role;
import com.zh.crm.service.PermissionService;
import com.zh.crm.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@Controller
public class RoleController {

    @Autowired
    RoleService roleService;
    @Autowired
    PermissionService permissionService;
    @GetMapping(value = "/roles/{parentId}")
    public String toListPage(@PathVariable("parentId") Integer parentId,
                             Map<String,Object> map){
        List<Role> roleGroup = roleService.findRoleByParentId(0);
        map.put("roleGroup",roleGroup);
        map.put("parentId",parentId);
        Role role = roleService.selectByPrimaryKey(parentId);
        map.put("roleName",role.getRoleName());
        return "view/role/role_list.html";
    }

    @ResponseBody
    @GetMapping(value = "/getRoles/{parentId}")
    public List getRoleListByParentId(@PathVariable("parentId") Integer parentId,
                                      Map<String,Object> map ){
        List<Role> roleList = roleService.findRoleByParentId(parentId);
        return roleList ;
    }

    @GetMapping(value = "/role/{parentId}")
    public String  toAddPage(@PathVariable("parentId") Integer parentId,
                                Map<String,Object> map ){
        map.put("parentId",parentId);
        return "view/role/role_edit" ;
    }

    @PostMapping(value = "/role")
    public String addRole(Role roles){
        roleService.insertSelective(roles) ;
        return "view/common/save_reslut" ;
    }

    @GetMapping(value = "/erole/{id}")
    public String toEditPage(@PathVariable("id") Integer id,
                             Map<String,Object> map){
        Role role = roleService.selectByPrimaryKey(id);
        map.put("parentId",role.getParentId());
        map.put("role",role);
        return "view/role/role_edit";
    }

    @PutMapping(value = "/role")
    public String editRole(Role role){
        roleService.updateByPrimaryKeySelective(role);
        return "view/common/save_reslut" ;
    }

    @GetMapping(value = "/editRolePerms/{roleId}")
    public String getPermsByRoleId(@PathVariable("roleId") Integer roleId,
                                   Map<String,Object> map){
        List<Permission> permissionList = permissionService.findAllPerms();
        List<Permission> permissions = permissionService.findPermsByRoleId(roleId);
        for (int i = 0; i <permissionList.size() ; i++) {
            for (int j = 0; j <permissions.size() ; j++) {
                if(permissionList.get(i).getPermissionId().equals(permissions.get(j).getPermissionId())){
                    permissionList.get(i).setChecked("true");
                }
            }
        }
        String perms = JSON.toJSONString(permissionList);
        perms = perms.replaceAll("permissionId","id").replaceAll("parentId","pId").replaceAll("permissionName","name");
        map.put("perms",perms);
        map.put("roleId",roleId);
        return "view/role/role_perms_ztree" ;
    }

    @DeleteMapping(value = "/role/{roleId}/{pareneId}")
    public String  deleteRole(@PathVariable("roleId")Integer roleId,
                              @PathVariable("pareneId")Integer pareneId){
        roleService.deleteByPrimaryKey(roleId);
        return  "redirect:/roles/"+pareneId;
    }

    @ResponseBody
    @PutMapping(value = "/role/perms/{roleId}")
    @Transactional
    public String  updatePermsByRoleId(@PathVariable("roleId") Integer roleId,
                                      @RequestParam(value = "permIds[]" )Integer[] permIds){
        roleService.deletePermsByRoleId(roleId);
        Map<String ,Object> map = new HashMap<String ,Object>();
        if(permIds.length!=0){
            map.put("permIds",permIds);
            map.put("roleId",roleId);
            roleService.insertPermsBatch(map);
        }
        return "success" ;
    }
}
