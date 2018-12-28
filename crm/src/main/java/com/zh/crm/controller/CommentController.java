package com.zh.crm.controller;

import com.zh.crm.entity.Comment;
import com.zh.crm.entity.CommentCount;
import com.zh.crm.entity.Tools;
import com.zh.crm.entity.User;
import com.zh.crm.service.CommentCountService;
import com.zh.crm.service.CommentService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@Controller
public class CommentController {
    @Autowired
    CommentService commentService;
    @Autowired
    CommentCountService commentCountService;

    @PostMapping(value = "/comment")
    public String addComment(Comment comment){
        comment.setCreateDate(Tools.dateToString(new Date(),"yyyy-MM-hh dd:mm:ss"));
        commentService.insertSelective(comment);
        return "redirect:/know/"+comment.getKnowId();
    }

    @ResponseBody
    @PostMapping(value = "/replyComment")
    public Integer addReplyComment(Comment comment){
        comment.setCreateDate(Tools.dateToString(new Date(),"yyyy-MM-hh dd:mm:ss"));
        commentService.insertSelective(comment);
        return 0;
    }

    @PostMapping(value = "/addCommentAgreeCount")
    @ResponseBody
    public Integer editCommentCount(Comment comment){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("commentId",comment.getId());
        map.put("creater",comment.getCreater());
        Integer a = commentCountService.findExsitUserAgreeComment(map);
        if(a==0){
            CommentCount c = new CommentCount();
            c.setCommentId(comment.getId());
            c.setCreater(comment.getCreater());
            c.setAgreeCount(1);
            commentCountService.insert(c);
            return 0;
        }else{
            return 1;
        }
    }

    @PostMapping(value = "/addOpposeAgreeCount")
    @ResponseBody
    public Integer editCommentOpposeCount(Comment comment){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("commentId",comment.getId());
        map.put("creater",comment.getCreater());
        Integer a = commentCountService.findExsitUserOpposeComment(map);
        if(a==0){
            CommentCount c = new CommentCount();
            c.setCommentId(comment.getId());
            c.setCreater(comment.getCreater());
            c.setOpposeCount(1);
            commentCountService.insert(c);
            return 0;
        }else{
            return 1;
        }
    }
}
