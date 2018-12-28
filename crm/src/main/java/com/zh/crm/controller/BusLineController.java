package com.zh.crm.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zh.crm.entity.BusLine;
import com.zh.crm.entity.Result;
import com.zh.crm.service.BusLineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@Controller
public class BusLineController {
    private final static Logger logger = LoggerFactory.getLogger(BusSiteController.class);
    @Autowired
    BusLineService busLineService;

    @GetMapping(value = "/toBusLine")
    public String toBusLineListPage(){
        return "view/info/busLine/line_list";
    }

    @GetMapping(value = "/busLines")
    @ResponseBody
    public Result getBusLineList(@RequestParam Map<String,String> allRequestParams){
        logger.info("分页查询线路信息");
        Result result = new Result();
        int pageNum = allRequestParams.containsKey("pageNumber")?Integer.parseInt(allRequestParams.get("pageNumber")):1;
        int pageSize = allRequestParams.containsKey("pageSize")?Integer.parseInt(allRequestParams.get("pageSize")):10;
        Map<String,Object> map = new HashMap<>();
        if(allRequestParams.containsKey("keywords")){
            map.put("keywords",allRequestParams.get("keywords"));
        }
        if(allRequestParams.containsKey("sort")){
            map.put("sort",allRequestParams.get("sort"));
        }else{
            map.put("sort","openDate");
        }
        if(allRequestParams.containsKey("order")){
            map.put("order",allRequestParams.get("order"));
        }else{
            map.put("order","desc");
        }
        PageHelper.startPage(pageNum,pageSize);
        List<BusLine> busLines = busLineService.findBusLinePaging(map);
        PageInfo pageInfo = new PageInfo(busLines);
        result.setRows(pageInfo.getList());
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    @DeleteMapping(value = "/busLine/{id}")
    public String deleteBusLine(@PathVariable("id") Integer id){
        busLineService.deleteByPrimaryKey(id);
        return "redirect:/toBusLine";
    }
}
