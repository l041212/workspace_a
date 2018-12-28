package com.zh.crm.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zh.crm.entity.Result;
import com.zh.crm.entity.Type;
import com.zh.crm.service.TypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建人：詹行
 * 修改时间：2018年10月29日
 * @version
 * 数据选项配置
 */
@CrossOrigin
@Controller
public class TypeController {

    private static final Logger logger = LoggerFactory.getLogger(TypeController.class);

    @Autowired
    TypeService typeService;

    @GetMapping(value = "/toType/{parentId}")
    public String toTypeListPage(@PathVariable Integer parentId,
                                 Map<String,Object> map){
        logger.info("进入数据选项页面");
        List<Type> typeList = typeService.findAllType();
        String types = JSON.toJSONString(typeList);
        types = types.replaceAll("parentId","pId").replaceAll("text","name");
        Type type = typeService.selectByPrimaryKey(parentId);
        map.put("types",types);
        map.put("type",type);
        map.put("parentId",parentId);
        return "view/type/type_list";
    }

    @ResponseBody
    @GetMapping(value = "/types/{parentId}")
    public Result getTypeList(@PathVariable Integer parentId,
                              @RequestParam Map<String,String> allRequestParams){
        logger.info("分页查询类型列表");
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
            map.put("sort","parentId");
        }
        PageHelper.startPage(pageNum,pageSize);
        List<Type> typeList = typeService.findTypeByParentId(map);
        PageInfo pageInfo = new PageInfo(typeList);
        Result result = new Result();
        result.setRows(pageInfo.getList());
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    @GetMapping(value = "/type/{parentId}")
    public String toTypeAdd(@PathVariable Integer parentId,
                            Map<String,Object>map){
        logger.info("去新增选项界面");
        /* Map<String,Object>map1 = new HashMap<String,Object>();
        map1.put("parentId",'0');
        List<Type> types = typeService.findTypeByParentId(map1);
        map.put("types",types);*/
        Type parentType = typeService.selectByPrimaryKey(parentId);
        map.put("parentType",parentType);
        map.put("parentId",parentId);
        return "view/type/type_edit";
    }

    @GetMapping(value = "/type/{id}/{parentId}")
    public String toTypeEdit(@PathVariable Integer id,
                             @PathVariable Integer parentId,
                             Map<String,Object>map){
        logger.info("去修改选项界面");
        Type type = typeService.selectByPrimaryKey(id);
        Type parentType = typeService.selectByPrimaryKey(parentId);
        map.put("parentType",parentType);
        map.put("parentId",parentId);
        map.put("type",type);
        return "view/type/type_edit";
    }

    @PostMapping(value = "/type")
    public String addType(Type type){
        logger.info("新增选项"+type.toString());
        typeService.insertSelective(type);
        return "view/common/save_reslut" ;
    }

    @PutMapping(value = "/type")
    public String editType(Type type){
        logger.info("修改选项"+type.toString());
        typeService.updateByPrimaryKeySelective(type);
        return "view/common/save_reslut" ;
    }

    @DeleteMapping(value = "/type/{id}/{parentId}")
    public String deleteType(@PathVariable Integer id,
                             @PathVariable Integer parentId){
        typeService.deleteByPrimaryKey(id);
        return "redirect:/toType/"+parentId;
    }

    @ResponseBody
    @GetMapping(value = "/findTypeExist")
    public int findTypeExist(@RequestParam Map<String,Object> map){
        Integer c = typeService.findTypeExist(map);
        if(c==null){
            return 0;
        }else{
            return c;
        }

    }

    @ResponseBody
    @GetMapping(value = "/findTypeSon/{id}")
    public int hasTypeSon(@PathVariable("id") Integer id){
        List<Type> typeList = typeService.findAllTypeByParentId(id);
        if(typeList.size()>0){
            return 1;
        }else{
            return 0;
        }
    }

}
