package com.zh.crm.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zh.crm.entity.Result;
import com.zh.crm.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@Controller
public class ReportController {

    @Autowired
    ReportService reportService;
    //总体话务量日报表
    @GetMapping("/report_total_everydays")
    public String toReport1Page(){
        return "view/report/report_total_everyday";
    }

    @ResponseBody
    @PostMapping(value = "/table_total_everydays")
    public Result getReportEveryday(@RequestParam Map<String,String> allRequestParams){
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

        if(allRequestParams.containsKey("startDate")){
            map.put("startDate",allRequestParams.get("startDate")) ;
        }
        if(allRequestParams.containsKey("endDate")){
            map.put("endDate",allRequestParams.get("endDate")) ;
        }
        if(allRequestParams.containsKey("order")){
            map.put("order",allRequestParams.get("order"));
        }else{
            map.put("order","desc");
        }
        if(allRequestParams.containsKey("sort")){
            map.put("sort",allRequestParams.get("sort"));
        }else{
            map.put("sort","operationTime");
        }
        PageHelper.startPage(pageNum,pageSize);
        List<Map<String,String>> maps = reportService.findReportEveryday(map);
        PageInfo pageInfo = new PageInfo(maps);
        result.setRows(pageInfo.getList());
        result.setTotal(pageInfo.getTotal());
        return result ;
    }

    @ResponseBody
    @GetMapping(value = "/report_everyday/{month}")
    public Result getReportChart(@PathVariable("month") String month){
        Result result = new Result();
        List<Map<String,String>> charts = reportService.findChartEveryday(month);
        result.setRows(charts);
        return result ;
    }


    //总体话务量月报表
    @GetMapping("/report_total_months")
    public String toReportMonthPage(){
        return "view/report/report_total_month";
    }

    @ResponseBody
    @PostMapping(value = "/table_total_months")
    public Result getReportMonth(@RequestParam Map<String,String> allRequestParams){
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

        if(allRequestParams.containsKey("startDate")){
            map.put("startDate",allRequestParams.get("startDate")) ;
        }
        if(allRequestParams.containsKey("endDate")){
            map.put("endDate",allRequestParams.get("endDate")) ;
        }
        if(allRequestParams.containsKey("order")){
            map.put("order",allRequestParams.get("order"));
        }else{
            map.put("order","desc");
        }
        if(allRequestParams.containsKey("sort")){
            map.put("sort",allRequestParams.get("sort"));
        }else{
            map.put("sort","operationTime");
        }
        PageHelper.startPage(pageNum,pageSize);
        List<Map<String,String>> maps = reportService.findReportMonth(map);
        PageInfo pageInfo = new PageInfo(maps);
        result.setRows(pageInfo.getList());
        result.setTotal(pageInfo.getTotal());
        return result ;
    }

    @ResponseBody
    @GetMapping(value = "/report_month/{year}")
    public Result getReportMonthChart(@PathVariable("year") String year){
        Result result = new Result();
        List<Map<String,String>> charts = reportService.findChartMonth(year);
        result.setRows(charts);
        return result ;
    }

    //总体话务量年报表
    @GetMapping("/report_total_years")
    public String toReportYearPage(){
        return "view/report/report_total_year";
    }

    @ResponseBody
    @PostMapping(value = "/table_total_years")
    public Result getReportYear(@RequestParam Map<String,String> allRequestParams){
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

        if(allRequestParams.containsKey("startDate")){
            map.put("startDate",allRequestParams.get("startDate")) ;
        }
        if(allRequestParams.containsKey("endDate")){
            map.put("endDate",allRequestParams.get("endDate")) ;
        }
        if(allRequestParams.containsKey("order")){
            map.put("order",allRequestParams.get("order"));
        }else{
            map.put("order","desc");
        }
        if(allRequestParams.containsKey("sort")){
            map.put("sort",allRequestParams.get("sort"));
        }else{
            map.put("sort","operationTime");
        }
        PageHelper.startPage(pageNum,pageSize);
        List<Map<String,String>> maps = reportService.findReportYear(map);
        PageInfo pageInfo = new PageInfo(maps);
        result.setRows(pageInfo.getList());
        result.setTotal(pageInfo.getTotal());
        return result ;
    }

    @ResponseBody
    @GetMapping(value = "/report_year")
    public Result getReportYearChart(){
        Result result = new Result();
        List<Map<String,String>> charts = reportService.findChartYear();
        result.setRows(charts);
        return result ;
    }


    @GetMapping("/report_total_agent")
    public String toReportAgentPage(){
        return "view/report/report_total_agent";
    }
    //获取坐席话务量报表
    @ResponseBody
    @PostMapping(value = "/table_total_agent_sum")
    public Result getReportAgentSum(@RequestParam Map<String,String> allRequestParams){
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
        if(allRequestParams.containsKey("startDate")){
            map.put("startDate",allRequestParams.get("startDate")) ;
        }
        if(allRequestParams.containsKey("endDate")){
            map.put("endDate",allRequestParams.get("endDate")) ;
        }
        if(allRequestParams.containsKey("order")){
            map.put("order",allRequestParams.get("order"));
        }else{
            map.put("order","desc");
        }
        if(allRequestParams.containsKey("sort")){
            map.put("sort",allRequestParams.get("sort"));
        }else{
            map.put("sort","agentno");
        }
        PageHelper.startPage(pageNum,pageSize);
        List<Map<String,String>> maps = reportService.findTableAgent(map);
        PageInfo pageInfo = new PageInfo(maps);
        result.setRows(pageInfo.getList());
        result.setTotal(pageInfo.getTotal());
        return result ;
    }

    @ResponseBody
    @GetMapping(value = "/report_agent_sum")
    public Result getReportAgentSumChart(@RequestParam Map<String,Object> map){
        Result result = new Result();
        List<Map<String,String>> charts = reportService.findChartAgent(map);
        result.setRows(charts);
        return result ;
    }

    /************************************坐席评价报表************************************/
    @GetMapping("/report_evaluate_agent")
    public String toAgentEvaluatePage(){
        return "view/report/report_total_agent_evaluate";
    }

    @ResponseBody
    @PostMapping(value = "/table_total_agent_evaluate")
    public Result getEvaluateAgentSum(@RequestParam Map<String,String> allRequestParams){
        Result result = new Result();
        int pageNum = 1;
        int pageSize = 10;
        if(allRequestParams.containsKey("pageNumber")){
            pageNum = Integer.parseInt(allRequestParams.get("pageNumber"));
        }
        if(allRequestParams.containsKey("pageSize")){
            pageSize = Integer.parseInt(allRequestParams.get("pageSize"));
        }
        Map<String,Object> map = new HashMap<String,Object>();

        if(allRequestParams.containsKey("startDate")){
            map.put("startDate",allRequestParams.get("startDate")) ;
        }
        if(allRequestParams.containsKey("endDate")){
            map.put("endDate",allRequestParams.get("endDate")) ;
        }
        if(allRequestParams.containsKey("order")){
            map.put("order",allRequestParams.get("order"));
        }else{
            map.put("order","desc");
        }
        if(allRequestParams.containsKey("sort")){
            map.put("sort",allRequestParams.get("sort"));
        }else{
            map.put("sort","agentno");
        }
        PageHelper.startPage(pageNum,pageSize);
        List<Map<String,String>> maps = reportService.findTableAgentEvaluate(map);
        PageInfo pageInfo = new PageInfo(maps);
        result.setRows(pageInfo.getList());
        result.setTotal(pageInfo.getTotal());
        return result ;
    }

    @ResponseBody
    @GetMapping(value = "/report_agent_evaluate")
    public Result getReportAgentEvaluateChart(@RequestParam Map<String,Object> map){
        Result result = new Result();
        List<Map<String,String>> charts = reportService.findChartAgentEvaluate(map);
        result.setRows(charts);
        return result ;
    }

    /**********************************坐席人均话务报表**********************************/
    @GetMapping(value = "/report_average_agent")
    public String toAverageAgentPage(){
        return "view/report/report_agent_average";
    }

    @ResponseBody
    @PostMapping(value = "/table_total_agent_average")
    public Result getAverageAgentSum(@RequestParam Map<String,String> allRequestParams){
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

        if(allRequestParams.containsKey("startDate")){
            map.put("startDate",allRequestParams.get("startDate")) ;
        }
        if(allRequestParams.containsKey("endDate")){
            map.put("endDate",allRequestParams.get("endDate")) ;
        }
        if(allRequestParams.containsKey("order")){
            map.put("order",allRequestParams.get("order"));
        }else{
            map.put("order","desc");
        }
        if(allRequestParams.containsKey("sort")){
            map.put("sort",allRequestParams.get("sort"));
        }else{
            map.put("sort","agentno");
        }
        PageHelper.startPage(pageNum,pageSize);
        List<Map<String,String>> maps = reportService.findTableAgentAverage(map);
        PageInfo pageInfo = new PageInfo(maps);
        result.setRows(pageInfo.getList());
        result.setTotal(pageInfo.getTotal());
        return result ;
    }

    @ResponseBody
    @GetMapping(value = "/report_agent_average")
    public Result getReportAgentAverageChart(@RequestParam Map<String,Object> map){
        Result result = new Result();
        List<Map<String,String>> charts = reportService.findChartAgentAverage(map);
        result.setRows(charts);
        return result ;
    }

    /*坐席话务量日报表*/
    @GetMapping(value = "/report_agent_everyday")
    public String toAgentEverydayPage(){
        return "view/report/report_agent_everyday";
    }


    /**********************************坐席话务明细表*********************************/
    @GetMapping(value = "/agent_call_detail")
    public String toAgentDetailEverydayPage(){
        return "view/report/agent_call_detail";
    }

    @ResponseBody
    @PostMapping(value = "/table_agent_call_detail")
    public Result getAgentDetailSum(@RequestParam Map<String,String> allRequestParams){
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

        if(allRequestParams.containsKey("startDate")){
            map.put("startDate",allRequestParams.get("startDate")) ;
        }
        if(allRequestParams.containsKey("endDate")){
            map.put("endDate",allRequestParams.get("endDate")) ;
        }
        if(allRequestParams.containsKey("agentno")){
            map.put("agentno",allRequestParams.get("agentno")) ;
        }
        if(allRequestParams.containsKey("order")){
            map.put("order",allRequestParams.get("order"));
        }else{
            map.put("order","desc");
        }
        if(allRequestParams.containsKey("sort")){
            map.put("sort",allRequestParams.get("sort"));
        }else{
            map.put("sort","operationTime");
        }
        PageHelper.startPage(pageNum,pageSize);
        List<Map<String,String>> maps = reportService.findCallDetail(map);
        PageInfo pageInfo = new PageInfo(maps);
        result.setRows(pageInfo.getList());
        result.setTotal(pageInfo.getTotal());
        return result ;
    }

    /*坐席话务量日报表*/
    @GetMapping(value = "/agent_status_detail")
    public String toAgentStatusPage(){
        return "view/report/agent_status_detail";
    }

    @ResponseBody
    @PostMapping(value = "/table_agent_status_detail")
    public Result getAgentStatusDetailSum(@RequestParam Map<String,String> allRequestParams){
        Result result = new Result();
        int pageNum = 1;
        int pageSize = 10;
        if(allRequestParams.containsKey("pageNumber")){
            pageNum = Integer.parseInt(allRequestParams.get("pageNumber"));
        }
        if(allRequestParams.containsKey("pageSize")){
            pageSize = Integer.parseInt(allRequestParams.get("pageSize"));
        }
        Map<String,Object> map = new HashMap<String,Object>();

        if(allRequestParams.containsKey("startDate")){
            map.put("startDate",allRequestParams.get("startDate")) ;
        }
        if(allRequestParams.containsKey("endDate")){
            map.put("endDate",allRequestParams.get("endDate")) ;
        }
        if(allRequestParams.containsKey("user_num")){
            map.put("user_num",allRequestParams.get("user_num")) ;
        }
        if(allRequestParams.containsKey("order")){
            map.put("order",allRequestParams.get("order"));
        }else{
            map.put("order","desc");
        }
        if(allRequestParams.containsKey("sort")){
            map.put("sort",allRequestParams.get("sort"));
        }else{
            map.put("sort","deal_time");
        }
        PageHelper.startPage(pageNum,pageSize);
        List<Map<String,String>> maps = reportService.findAgentStatus(map);
        PageInfo pageInfo = new PageInfo(maps);
        result.setRows(pageInfo.getList());
        result.setTotal(pageInfo.getTotal());
        return result ;
    }

    /*坐席话务量日报表*/
    @GetMapping(value = "/bigsreen")
    public String toBigSreenPage(){
        return "view/report/bigscreen";
    }
}
