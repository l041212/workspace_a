package com.zh.crm.controller;

import com.zh.crm.entity.Collect;
import com.zh.crm.service.CollectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@Controller
public class CollectController {

    @Autowired
    CollectService collectService;
    private static final Logger logger = LoggerFactory.getLogger(CollectController.class);


    @PostMapping(value = "/collect")
    @ResponseBody
    public int addCollect(Collect collect){
        logger.info("收藏知识");
        Integer a = collectService.findCollectExsit(collect);
        if(a==null){
            collectService.insertSelective(collect);
            return collect.getId();
        }else{
            return a;
        }
    }

    @DeleteMapping(value = "/collect/{id}")
    @ResponseBody
    public int deleteCollect(@PathVariable("id") Integer id){
        collectService.deleteByPrimaryKey(id);
        return 0;
    }
}
