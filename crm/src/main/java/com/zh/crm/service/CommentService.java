package com.zh.crm.service;

import com.zh.crm.entity.Comment;

import java.util.List;

public interface CommentService {
    int deleteByPrimaryKey(Integer id);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);

    List<Comment> findSonComment( Integer knowId);

    List<Comment> findTopFiveCommentByKnowId( Integer knowId);

    Integer findCountCommentByKnowId (Integer knowId);
}
