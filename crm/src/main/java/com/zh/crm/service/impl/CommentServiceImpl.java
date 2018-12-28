package com.zh.crm.service.impl;

import com.zh.crm.entity.Comment;
import com.zh.crm.mapper.CommentMapper;
import com.zh.crm.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentMapper commentMapper;
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return commentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Comment record) {
        return commentMapper.insert(record);
    }

    @Override
    public int insertSelective(Comment record) {
        return commentMapper.insertSelective(record);
    }

    @Override
    public Comment selectByPrimaryKey(Integer id) {
        return commentMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Comment record) {
        return commentMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Comment record) {
        return commentMapper.updateByPrimaryKey(record);
    }

    @Override
    public  List<Comment> findSonComment(Integer knowId) {
        return commentMapper.findSonComment(knowId);
    }

    @Override
    public List<Comment> findTopFiveCommentByKnowId(Integer knowId) {
        return commentMapper.findTopFiveCommentByKnowId(knowId);
    }

    @Override
    public Integer findCountCommentByKnowId(Integer knowId) {
        return commentMapper.findCountCommentByKnowId(knowId);
    }
}
