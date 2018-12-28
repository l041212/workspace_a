package com.zh.crm.service.impl;

import com.zh.crm.entity.CommentCount;
import com.zh.crm.mapper.CommentCountMapper;
import com.zh.crm.service.CommentCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
@Transactional
@Service
public class CommentCountServiceImpl  implements CommentCountService {
    @Autowired
    CommentCountMapper commentCountMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return commentCountMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(CommentCount record) {
        return commentCountMapper.insert(record);
    }

    @Override
    public int insertSelective(CommentCount record) {
        return commentCountMapper.insertSelective(record);
    }

    @Override
    public CommentCount selectByPrimaryKey(Integer id) {
        return commentCountMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(CommentCount record) {
        return commentCountMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(CommentCount record) {
        return commentCountMapper.updateByPrimaryKey(record);
    }

    @Override
    public Integer findAgreeCount(Integer commentId) {
        return commentCountMapper.findAgreeCount(commentId);
    }

    @Override
    public Integer findOpposeCount(Integer commentId) {
        return commentCountMapper.findOpposeCount(commentId);
    }

    @Override
    public Integer findExsitUserAgreeComment(Map<String, Object> map) {
        return commentCountMapper.findExsitUserAgreeComment(map);
    }

    @Override
    public Integer findExsitUserOpposeComment(Map<String, Object> map) {
        return commentCountMapper.findExsitUserOpposeComment(map);
    }
}
