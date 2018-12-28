package com.zh.crm.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zh.crm.entity.BusSite;
import com.zh.crm.entity.Result;
import com.zh.crm.entity.Type;
import com.zh.crm.service.BusSiteService;
import com.zh.crm.service.TypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin
public class BusSiteController {
    private static  final Logger logger = LoggerFactory.getLogger(BusSiteController.class);
    @Autowired
    BusSiteService busSiteService;
    @Autowired
    TypeService typeService;
    @GetMapping(value = "/toBusSite")
    public String toBusSiteListPage(){
        return "view/info/busSite/site_list";
    }

    @ResponseBody
    @GetMapping(value = "/busSites")
    public Result getBusSiteList(@RequestParam Map<String,String> allRequestParams){
        logger.info("分页查询站点信息");
        Result result = new Result();
        int pageNum  = allRequestParams.containsKey("pageNumber")?Integer.parseInt(allRequestParams.get("pageNumber")):1;
        int pageSize = allRequestParams.containsKey("pageSize")?Integer.parseInt(allRequestParams.get("pageSize")):10;
        Map<String,Object> map = new HashMap<>();
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
            map.put("sort","createDate");
        }
        PageHelper.startPage(pageNum,pageSize);
        List<BusSite> busSiteList = busSiteService.findAllSitePage(map);
        PageInfo pageInfo = new PageInfo(busSiteList);
        result.setRows(pageInfo.getList());
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    @GetMapping(value = "/busSite")
    public String toBusSiteAdd(Map<String,Object> map){
        List<Type> types = typeService.findAllValidType(40);
        map.put("types",types);
        return "view/info/busSite/site_edit";
    }

    @PostMapping(value = "/busSite")
    public String addBusSite(BusSite busSite){
        busSiteService.insertSelective(busSite);
        return "view/common/save_reslut" ;
    }

    @GetMapping(value = "/busSite/{id}")
    public String toBusSiteEdit(@PathVariable("id") Integer id,Map<String,Object> map){
        List<Type> types = typeService.findAllValidType(40);
        map.put("types",types);
        BusSite busSite = busSiteService.selectByPrimaryKey(id);
        map.put("busSite",busSite);
        return "view/info/busSite/site_edit";
    }

    @PutMapping(value = "/busSite")
    public String editBusSite(BusSite busSite){
        busSiteService.insertSelective(busSite);
        return "view/common/save_reslut" ;
    }

    @DeleteMapping(value = "/busSite/{id}")
    public String deleteBusSite(@PathVariable("id") Integer id){
        busSiteService.deleteByPrimaryKey(id);
        return "redirect:/toBusSite";
    }
}
