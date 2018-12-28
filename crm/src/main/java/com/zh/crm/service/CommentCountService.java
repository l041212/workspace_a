package com.zh.crm.service;

import com.zh.crm.entity.CommentCount;

import java.util.Map;

public interface CommentCountService {

    int deleteByPrimaryKey(Integer id);

    int insert(CommentCount record);

    int insertSelective(CommentCount record);

    CommentCount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CommentCount record);

    int updateByPrimaryKey(CommentCount record);

    Integer findAgreeCount(Integer commentId);

    Integer findOpposeCount( Integer commentId);

    Integer findExsitUserAgreeComment(Map<String,Object> map);

    Integer findExsitUserOpposeComment (Map<String,Object> map);
}
