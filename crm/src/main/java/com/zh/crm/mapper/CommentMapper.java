package com.zh.crm.mapper;

import com.zh.crm.entity.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);

    List<Comment> findSonComment(@Param("id") Integer id);

    List<Comment> findTopFiveCommentByKnowId(@Param("knowId") Integer knowId);

    Integer findCountCommentByKnowId (@Param("knowId") Integer knowId);
}