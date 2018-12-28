package com.zh.crm.service;


import java.util.List;
import java.util.Map;

public interface ReportService {
    public List<Map<String,String>> findReportEveryday(Map<String,Object> map);
    public Integer findCountReportEveryday(Map<String,Object> map);
    public List<Map<String,String>>  findChartEveryday(String month);
    public List<Map<String,String>>  findReportMonth(Map<String,Object> map);

    public List<Map<String,String>>   findChartMonth(String year);
    public List<Map<String,String>>   findChartYear();

    public List<Map<String,String>> findReportYear(Map<String,Object> map);

    public List<Map<String,String>> findTableAgent(Map<String,Object> map);
    public List<Map<String,String>> findChartAgent(Map<String,Object> map);
    public List<Map<String,String>> findTableAgentEvaluate (Map<String,Object> map);
    public List<Map<String,String>> findChartAgentEvaluate(Map<String,Object> map);

    public List<Map<String,String>> findTableAgentAverage(Map<String,Object> map);
    public List<Map<String,String>> findChartAgentAverage(Map<String,Object> map);
    public List<Map<String,String>> findCallDetail(Map<String,Object> map);
    public List<Map<String,String>> findAgentStatus(Map<String,Object> map);
}
