package com.zh.crm.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zh.crm.config.WebSocketServer;
import com.zh.crm.entity.Deparment;
import com.zh.crm.entity.Notice;
import com.zh.crm.entity.Result;
import com.zh.crm.service.DepService;
import com.zh.crm.service.NoticeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@Controller
public class NoticeController {
    private final static Logger logger = LoggerFactory.getLogger(NoticeController.class);

    @Autowired
    NoticeService noticeService;

    @Autowired
    DepService depService;

    @GetMapping(value = "/toNotice")
    public String toNoticeListPage(){
        logger.info("进入公告列表页面");
        return "view/notice/notice_list";
    }

    @ResponseBody
    @GetMapping(value = "/notices")
    public Result getNoticeList(@RequestParam Map<String,String> allRequestParams){
        logger.info("查询公告列表");
        Result result = new Result();
        int pageNum = 1;
        int pageSize = 10 ;
        if(allRequestParams.containsKey("pageNumber")){
            pageNum = Integer.valueOf(allRequestParams.get("pageNumber"));
        }
        if(allRequestParams.containsKey("pageSize")){
            pageSize = Integer.valueOf(allRequestParams.get("pageSize"));
        }
        Map<String,Object> map = new HashMap<String,Object>();
        if(allRequestParams.containsKey("keywords")){
            map.put("keywords",allRequestParams.get("keywords"));
        }
        if(allRequestParams.containsKey("sort")){
            map.put("sort",allRequestParams.get("sort"));
        }else{
            map.put("sort","createDate");
        }
        if(allRequestParams.containsKey("order")){
            map.put("order",allRequestParams.get("order"));
        }else{
            map.put("order","desc");
        }
        PageHelper.startPage(pageNum,pageSize);
        List<Notice> noticeList = noticeService.findAllNoticePaging(map);
        PageInfo pageInfo = new PageInfo(noticeList);
        result.setRows(pageInfo.getList());
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    @GetMapping(value = "/notice")
    public String toNoticeAdd(Map<String,Object> map){
        logger.info("去新增公告界面");
        List<Deparment> depList = depService.findAllDep();
        String deps = JSON.toJSONString(depList);
        deps = deps.replaceAll("depId","id").replaceAll("parentId","parentId").replaceAll("depName","name");
        map.put("deps",deps);
        return "view/notice/notice_edit";
    }

    @PostMapping(value = "/notice")
    public String addNotice(Notice notice,@RequestParam("index") Integer index) throws IOException {
        logger.info("新增公告");
        noticeService.insertSelective(notice);
        if(index==1){//发布跑马灯消息
            String message = "新增公告:&nbsp;&nbsp;&nbsp;&nbsp;"+notice.getTitle();
            WebSocketServer.sendInfo(message,null);
        }
        return "view/common/save_reslut";
    }

    @GetMapping(value = "/notice/{id}")
    public String toNoticeEdit(@PathVariable("id") Integer id,
                               Map<String,Object> map){
        logger.info("去修改公告界面");
        List<Deparment> depList = depService.findAllDep();
        String deps = JSON.toJSONString(depList);
        deps = deps.replaceAll("depId","id").replaceAll("parentId","parentId").replaceAll("depName","name");
        map.put("deps",deps);
        Notice notice = noticeService.selectByPrimaryKey(id);
        map.put("notice",notice);
        return "view/notice/notice_edit";
    }

    @PutMapping(value = "/notice")
    public String editNotice(Notice notice){
        logger.info("修改公告");
        noticeService.updateByPrimaryKeySelective(notice);
        return "view/common/save_reslut";
    }

    @DeleteMapping(value = "/notice/{id}")
    public String DeleteNotice(@PathVariable("id") Integer id){
        noticeService.deleteByPrimaryKey(id);
        return "redirect:/toNotice";
    }


}
