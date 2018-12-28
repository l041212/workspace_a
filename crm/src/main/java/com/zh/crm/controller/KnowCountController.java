package com.zh.crm.controller;

import com.zh.crm.entity.KnowCount;
import com.zh.crm.service.KnowCountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@Controller
public class KnowCountController {

    private static final Logger logger = LoggerFactory.getLogger(KnowCountController.class);
    @Autowired
    KnowCountService knowCountService;

    @ResponseBody
    @PostMapping(value = "/knowGoodCount")
    public Integer addKnowGoodCount(KnowCount kc){
        Map<String,Object> goodMap = new HashMap<String,Object>();
        goodMap.put("knowId",kc.getKnowId());
        goodMap.put("creater",kc.getCreater());
        Integer a = knowCountService.findGoodCountByUser(goodMap);
        if(a==0||a==null){
            kc.setGoodCount(1);
            knowCountService.insertSelective(kc);
            return 0;
        }else{
            return 1;
        }
    }

    @ResponseBody
    @PostMapping(value = "/knowBadCount")
    public Integer addKnowBadCount(KnowCount kc){
        Map<String,Object> goodMap = new HashMap<String,Object>();
        goodMap.put("knowId",kc.getKnowId());
        goodMap.put("creater",kc.getCreater());
        Integer a = knowCountService.findBadCountByUser(goodMap);
        if(a==0||a==null){
            kc.setOpposeCount(1);
            knowCountService.insertSelective(kc);
            return 0;
        }else{
            return 1;
        }
    }
}
