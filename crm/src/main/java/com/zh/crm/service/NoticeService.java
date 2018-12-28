package com.zh.crm.service;

import com.zh.crm.entity.Notice;

import java.util.List;
import java.util.Map;

public interface NoticeService {
    int deleteByPrimaryKey(Integer id);

    int insert(Notice record);

    int insertSelective(Notice record);

    Notice selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Notice record);

    int updateByPrimaryKeyWithBLOBs(Notice record);

    int updateByPrimaryKey(Notice record);

    List<Notice> findAllNoticePaging(Map<String,Object> map);

    void setExpireNotice( String deadline);
}
