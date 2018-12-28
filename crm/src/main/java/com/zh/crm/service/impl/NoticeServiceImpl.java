package com.zh.crm.service.impl;

import com.zh.crm.entity.Notice;
import com.zh.crm.mapper.NoticeMapper;
import com.zh.crm.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
@Transactional
@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    NoticeMapper noticeMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return noticeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Notice record) {
        return noticeMapper.insert(record);
    }

    @Override
    public int insertSelective(Notice record) {
        return noticeMapper.insertSelective(record);
    }

    @Override
    public Notice selectByPrimaryKey(Integer id) {
        return noticeMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Notice record) {
        return noticeMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKeyWithBLOBs(Notice record) {
        return noticeMapper.updateByPrimaryKeyWithBLOBs(record);
    }

    @Override
    public int updateByPrimaryKey(Notice record) {
        return noticeMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<Notice> findAllNoticePaging(Map<String, Object> map) {
        return noticeMapper.findAllNoticePaging(map);
    }

    @Override
    public void setExpireNotice(String deadline) {
        noticeMapper.setExpireNotice(deadline);
    }
}
