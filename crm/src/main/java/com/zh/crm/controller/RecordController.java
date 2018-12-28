package com.zh.crm.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zh.crm.entity.Record;
import com.zh.crm.entity.Result;
import com.zh.crm.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@Controller
public class RecordController {

    @Autowired
    RecordService recordService;

    @ResponseBody
    @PostMapping(value = "/records")
    public Result getAllRecord(@RequestParam Map<String,String> allRequestParams){
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
            map.put("sort","start_time");
        }
        PageHelper.startPage(pageNum,pageSize);
        List<Record> records = recordService.findAllRecord(map);
        PageInfo pageInfo = new PageInfo(records);
        result.setRows(pageInfo.getList());
        result.setTotal(pageInfo.getTotal());
        return result ;
    }
}
