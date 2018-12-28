package com.zh.crm.service.impl;

import com.zh.crm.mapper.PhoneDataMapper;
import com.zh.crm.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
@Transactional
@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    PhoneDataMapper phoneDataMapper;

    @Override
    public List<Map<String, String>> findReportEveryday(Map<String, Object> map) {
        return phoneDataMapper.findReportEveryday(map);
    }

    @Override
    public Integer findCountReportEveryday(Map<String, Object> map) {
        return phoneDataMapper.findCountReportEveryday(map);
    }

    @Override
    public List<Map<String, String>> findChartEveryday(String month) {
        return phoneDataMapper.findChartEveryday(month);
    }

    @Override
    public List<Map<String, String>> findReportMonth(Map<String, Object> map) {
        return phoneDataMapper.findReportMonth(map);
    }

    @Override
    public List<Map<String, String>> findChartMonth(String year) {
        return phoneDataMapper.findChartMonth(year);
    }

    @Override
    public List<Map<String, String>> findChartYear() {
        return phoneDataMapper.findChartYear();
    }

    @Override
    public List<Map<String, String>> findReportYear(Map<String, Object> map) {
        return phoneDataMapper.findReportYear(map);
    }

    @Override
    public List<Map<String, String>> findTableAgent(Map<String, Object> map) {
        return phoneDataMapper.findTableAgent(map);
    }

    @Override
    public List<Map<String, String>> findChartAgent(Map<String, Object> map) {
        return phoneDataMapper.findChartAgent(map);
    }

    @Override
    public List<Map<String, String>> findTableAgentEvaluate(Map<String, Object> map) {
        return phoneDataMapper.findTableAgentEvaluate(map);
    }

    @Override
    public List<Map<String, String>> findChartAgentEvaluate(Map<String, Object> map) {
        return phoneDataMapper.findChartAgentEvaluate(map);
    }

    @Override
    public List<Map<String, String>> findTableAgentAverage(Map<String, Object> map) {
        return phoneDataMapper.findTableAgentAverage(map);
    }

    @Override
    public List<Map<String, String>> findChartAgentAverage(Map<String, Object> map) {
        return phoneDataMapper.findChartAgentAverage(map);
    }

    @Override
    public List<Map<String, String>> findCallDetail(Map<String, Object> map) {
        return phoneDataMapper.findCallDetail(map);
    }

    @Override
    public List<Map<String, String>> findAgentStatus(Map<String, Object> map) {
        return phoneDataMapper.findAgentStatus(map);
    }
}
