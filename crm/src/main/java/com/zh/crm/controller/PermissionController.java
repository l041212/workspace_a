package com.zh.crm.controller;

import com.alibaba.fastjson.JSON;
import com.zh.crm.entity.Permission;
import com.zh.crm.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@Controller
public class PermissionController {
    @Autowired
    PermissionService permissionService;

    @GetMapping(value="/perms/{parentId}")
    public String toPerm(@PathVariable("parentId")Integer parentId,
                         Map<String,Object> map){
        List<Permission> permissionList = permissionService.findAllPerms();
        String perms = JSON.toJSONString(permissionList);
        perms = perms.replaceAll("permissionId","id").replaceAll("parentId","pId").replaceAll("permissionName","name");
        map.put("perms",perms);
        map.put("parentId",parentId);
        Permission perm = new Permission();
        perm = permissionService.selectByPrimaryKey(parentId);
        map.put("perm",perm);
        return "view/permission/permission_list";
    }

    @ResponseBody
    @GetMapping(value = "/getPerm/{parentid}")
    public List getPermByParentId(@PathVariable("parentid") Integer parentid ){
        List<Permission> permissionList = permissionService.findPermsByParentId(parentid);
        return permissionList;
    }



    @GetMapping(value = "/perm/{parentid}")
    public String toAddPerm(@PathVariable("parentid") Integer parentid,
                            Map<String,Object> map){
        map.put("parentid",parentid);
        Permission perm = permissionService.selectByPrimaryKey(parentid);
        map.put("perm",perm);
        return "view/permission/permission_edit" ;
    }

    @PostMapping(value = "/perm")
    public String addPerm(Permission permission){
        permissionService.insertSelective(permission);
        return "view/common/save_reslut" ;
    }

    @GetMapping(value = "/toEdit/{permissionId}")
    public String toEditPerm(@PathVariable("permissionId") Integer permissionId,
                             Map<String,Object> map){
        Permission perms = permissionService.selectByPrimaryKey(permissionId);
        map.put("parentid",perms.getParentId());
        map.put("perms",perms);
        return "view/permission/permission_edit";
    }

    @PutMapping(value = "/perm")
    public String editPerm(Permission permission){
        permissionService.updateByPrimaryKeySelective(permission);
        return "view/common/save_reslut" ;
    }

    @DeleteMapping(value = "/perm/{id}/{parentId}")
    public String deletePerm(@PathVariable("id") Integer id,@PathVariable("parentId") Integer parentId){
        permissionService.deleteByPrimaryKey(id);
        return "redirect:/perms/"+parentId;
    }

    @ResponseBody
    @GetMapping(value = "/hasPermSon/{permissionId}")
    public Integer hasPermSon(@PathVariable("permissionId") Integer permissionId){
        List<Permission> permissionList = permissionService.findPermsByParentId(permissionId);
        if(permissionList.size()>0){
            return 1;
        }else
            return 0;
    }

    @GetMapping(value = "/toEditPermIcon/{permissionId}")
    public String toEditPermIcon(@PathVariable("permissionId") Integer permissionId,
                                 Map<String,Object> map){
        Permission perm = permissionService.selectByPrimaryKey(permissionId);
        map.put("perm",perm);
        return "view/permission/permission_icon";
    }

    @PutMapping(value = "/perm/icon")
    public String editIcon(Permission permission){
        permissionService.updateByPrimaryKeySelective(permission);
        return "view/common/save_reslut" ;
    }
}
