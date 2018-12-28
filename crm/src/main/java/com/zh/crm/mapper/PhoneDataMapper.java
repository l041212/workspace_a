package com.zh.crm.mapper;

import com.zh.crm.entity.PhoneData;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PhoneDataMapper {
    List<Map<String,String>> findReportEveryday(Map<String,Object> map);
    Integer findCountReportEveryday(Map<String,Object> map);

    List<Map<String,String>>  findChartEveryday(@Param("month")String month);
    List<Map<String,String>>  findReportMonth(Map<String,Object> map);

    List<Map<String,String>>   findChartMonth(@Param("year")String year);

    List<Map<String,String>> findReportYear(Map<String,Object> map);
    List<Map<String,String>>   findChartYear();
    List<Map<String,String>> findTableAgent(Map<String,Object> map);
    List<Map<String,String>> findChartAgent(Map<String,Object> map);
    List<Map<String,String>> findTableAgentEvaluate (Map<String,Object> map);
    List<Map<String,String>> findChartAgentEvaluate(Map<String,Object> map);

    List<Map<String,String>> findTableAgentAverage(Map<String,Object> map);

    List<Map<String,String>> findChartAgentAverage(Map<String,Object> map);

    List<Map<String,String>> findCallDetail(Map<String,Object> map);
    List<Map<String,String>> findAgentStatus(Map<String,Object> map);
}