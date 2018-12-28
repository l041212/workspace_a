package com.zh.crm.mapper;

import com.zh.crm.entity.CommentCount;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface CommentCountMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CommentCount record);

    int insertSelective(CommentCount record);

    CommentCount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CommentCount record);

    int updateByPrimaryKey(CommentCount record);

    Integer findAgreeCount(@Param("commentId") Integer commentId);

    Integer findOpposeCount(@Param("commentId") Integer commentId);

    Integer findExsitUserAgreeComment(Map<String,Object> map);

    Integer findExsitUserOpposeComment (Map<String,Object> map);
}