package com.zh.crm.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zh.crm.entity.Deparment;
import com.zh.crm.entity.Result;
import com.zh.crm.entity.User;
import com.zh.crm.service.DepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@Controller
public class DepController {

    @Autowired
    DepService depService;
    @GetMapping(value = "/deps/{parentId}")
    public String toDepListPage(@PathVariable("parentId")Integer parentId,
                                Map<String,Object> map){
        List<Deparment> deparmentList = depService.findAllDep();
        String deps = JSON.toJSONString(deparmentList);
        deps = deps.replaceAll("depId","id").replaceAll("parentId","pId").replaceAll("depName","name");
        map.put("deps",deps);
        Deparment dep = depService.selectByPrimaryKey(parentId);
        map.put("dep",dep);
        map.put("parentId",parentId);
        return "view/dep/dep_list";
    }

    @ResponseBody
    @GetMapping(value = "/getDep/{parentId}")
    public Result getDepByParentId(@PathVariable("parentId") Integer parentId ,
                                   @RequestParam Map<String,String> allRequestParams){
        Result result = new Result();
        int pageNum = 1;
        int pageSize = 10;
        if(allRequestParams.containsKey("pageNumber")){
            pageNum= Integer.parseInt(allRequestParams.get("pageNumber"));
        }
        if(allRequestParams.containsKey("pageSize")){
            pageSize= Integer.parseInt(allRequestParams.get("pageSize"));
        }
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("parentId",parentId);
        if(allRequestParams.containsKey("keywords")){
            map.put("keywords",allRequestParams.get("keywords"));
        }
        if(allRequestParams.containsKey("order")){
            map.put("order",allRequestParams.get("order"));
        }else{
            map.put("order","desc");
        }
        if(allRequestParams.containsKey("sort")){
            map.put("sort",allRequestParams.get("sort"));
        }else{
            map.put("sort","depId");
        }
        PageHelper.startPage(pageNum,pageSize);
        List<Deparment> deparmentList = depService.findDepByParentId(map);
        PageInfo pageInfo = new PageInfo(deparmentList);
        result.setRows(pageInfo.getList());
        result.setTotal(pageInfo.getTotal());
        return result ;
    }

    @GetMapping(value = "/dep/{parentId}")
    public String toAddDep(@PathVariable("parentId") Integer parentId,
                            Map<String,Object> map){
        map.put("parentId",parentId);
        Deparment dep = depService.selectByPrimaryKey(parentId);
        map.put("dep",dep);
        return "view/dep/dep_edit" ;
    }

    @GetMapping(value = "/edep/{depId}")
    public String toEditDep(@PathVariable("depId") Integer depId,
                             Map<String,Object> map){
        Deparment deps = depService.selectByPrimaryKey(depId);
        Deparment dep = depService.selectByPrimaryKey(deps.getParentId());
        map.put("dep",dep);
        map.put("parentId",deps.getParentId());
        map.put("deps",deps);
        return "view/dep/dep_edit" ;
    }

    @PostMapping(value = "/dep")
    public String addDep(Deparment deparment){
        depService.insertSelective(deparment);
        return "view/common/save_reslut" ;
    }

    @PutMapping(value = "/dep")
    public String editDep(Deparment deparment){
        depService.updateByPrimaryKeySelective(deparment);
        return "view/common/save_reslut" ;
    }

    @ResponseBody
    @PostMapping(value = "/getDepByCode/{depCode}")
    public Integer getDepByCode(@PathVariable("depCode") String depCode){
        int  c = depService.findDepByCode(depCode);
        return c;
    }


    @DeleteMapping(value = "/dep/{id}/{parentId}")
    public String deletePerm(@PathVariable("id") Integer id,@PathVariable("parentId") Integer parentId){
        depService.deleteByPrimaryKey(id);
        return "redirect:/deps/"+parentId;
    }

    @ResponseBody
    @GetMapping(value = "/hasDepSon/{depId}")
    public Integer hasDepSon(@PathVariable("depId") Integer depId){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("parentId",depId);
        List<Deparment> deparmentList = depService.findDepByParentId(map);
        if(deparmentList.size()>0){
            return 1;
        }else{
            return 0;
        }
    }
}
