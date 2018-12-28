package com.zh.crm.controller;

import com.alibaba.fastjson.JSON;
import com.zh.crm.config.WebSocketServer;
import com.zh.crm.entity.*;
import com.zh.crm.service.*;
import com.zh.crm.service.impl.JestServiceImpl;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Index;
import io.searchbox.core.SearchResult;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@CrossOrigin
public class KnowledgeController {

    private static final Logger logger = LoggerFactory.getLogger(KnowledgeController.class);
    private static final String savePath = "D://upload/knowledge/";


    @Autowired
    FileService fileService;
    @Autowired
    TypeService typeService;
    @Autowired
    DepService depService;
    @Autowired
    RoleService roleService;
    @Autowired
    KnowService knowService;
    @Autowired
    CollectService collectService;
    @Autowired
    CommentService commentService;
    @Autowired
    CommentCountService commentCountService;
    @Autowired
    KnowCountService knowCountService;
    @Autowired
    JestService jestService;


    @GetMapping(value = "/addKnow")
    public String toKnowAdd(Map<String,Object> map){
        logger.info("进入新增知识界面");
        List<Type> typeList = typeService.findAllValidType(13);
        List<Type> types = typeService.findAllKnowValidType(typeList);
        map.put("types",types);
        List<Deparment> deparmentList = depService.findAllDep();
        String deps = JSON.toJSONString(deparmentList);
        deps = deps.replaceAll("depId","id").replaceAll("parentId","pId").replaceAll("depName","name");
        map.put("deps",deps);
        List<Role> roleList = roleService.findAllRole();
        String roles = JSON.toJSONString(roleList);
        roles = roles.replaceAll("roleId","id").replaceAll("parentId","pId").replaceAll("roleName","name");
        map.put("roles",roles);
        return "view/knowledge/knowledge_create";
    }

    @ResponseBody
    @PostMapping(value = "/knowUpload")
    public Object knowFileUpload(@RequestParam("konwFile") MultipartFile fileUpload){
        logger.info("文件上传");
        Map<String,Object> map = new HashMap<String,Object>();
        String realName = System.nanoTime()+"knowFile"+fileUpload.getOriginalFilename();
        String fileName = savePath+realName;
        String url = "http://localhost:8081/upload/knowledge/"+realName;
        try {
            Tools.saveFile(fileName,fileUpload);
            KnowFile knowFile = new KnowFile();
            knowFile.setFileCategory("knowledge");
            knowFile.setFileName(fileName);
            knowFile.setCreateDate(Tools.dateToString(new Date(),"yyyy-MM-dd HH:mm:ss"));
            fileService.insertSelective(knowFile);
            map.put("url",url);
            map.put("id",knowFile.getFileId());
            map.put("fileName",fileUpload.getOriginalFilename());
            map.put("realName",realName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map ;
    }

    @DeleteMapping(value = "/file/{id}")
    @ResponseBody
    public void deleteFile(@PathVariable("id") Integer id){
        KnowFile file = fileService.selectByPrimaryKey(id);
        System.out.println(file.getFileName());
        File file1 = new java.io.File(file.getFileName());
        if(!file1.exists()){
            fileService.deleteByPrimaryKey(id);
        }else{
            if (file1.isFile()){
                file1.delete();
                fileService.deleteByPrimaryKey(id);
            }
        }
    }

    @PostMapping(value = "/know")
    public String addKnowledge(KnowWithBLOBs know) throws Exception {
        /*if(know.getPastId()!=0){//更改知识版本
            knowService.updateVersionByPastId(know.getPastId());
        }*/
        String createDate = Tools.dateToString(new Date(),"yyyy-MM-dd hh:mm:ss");
        User user = new User();
        Session session = SecurityUtils.getSubject().getSession();
        try {
            BeanUtils.copyProperties(user, session.getAttribute("user"));
        } catch (Exception e) {
        }
        String creater = user.getNumber();
        know.setCreater(creater);
        know.setCreateDate(createDate);
        know.setStatus("待审核");
        knowService.insertSelective(know);

        //将知识存入检索服务器
        KnowEs kes = new KnowEs();
        kes.setId(know.getId());
        kes.setKnowTag(know.getKnowTag());
        kes.setCreateDate(createDate);
        kes.setResultType(know.getResultType());
        kes.setCreater(creater);
        kes.setContent(know.getKnowContentTxt());
        kes.setKnowTitle(know.getKnowTitle());
        kes.setKnowType(know.getKnowType());
        kes.setReadPerm(know.getReadPerm());
        kes.setReadRoleIds(know.getReadRoleIds());
        jestService.insert(kes);
        String message = "新增知识:&nbsp;&nbsp;&nbsp;&nbsp;<a href='/know/'"+know.getId()+">"+know.getKnowTitle()+"</a>";
        WebSocketServer.sendInfo(message,null);
        return "redirect:/know/"+know.getId();
    }

    @PutMapping(value = "/know")
    public String editKnowledge(KnowWithBLOBs know)throws Exception {
        String createDate = Tools.dateToString(new Date(),"yyyy-MM-dd hh:mm:ss");
        User user = new User();
        Session session = SecurityUtils.getSubject().getSession();
        try {
            BeanUtils.copyProperties(user, session.getAttribute("user"));
        } catch (Exception e) {
        }
        String creater = user.getNumber();
        know.setCreater(creater);
        know.setCreateDate(createDate);
        know.setStatus("待审核");
        knowService.updateByPrimaryKeyWithBLOBs(know);
        //删除知识检索服务器中的文档
        String id = know.getId().toString();
        jestService.deleteData(id);
        //将知识存入检索服务器
        KnowEs kes = new KnowEs();
        kes.setId(know.getId());
        kes.setKnowTag(know.getKnowTag());
        kes.setCreateDate(createDate);
        kes.setResultType(know.getResultType());
        kes.setCreater(creater);
        kes.setContent(know.getKnowContentTxt());
        kes.setKnowTitle(know.getKnowTitle());
        kes.setKnowType(know.getKnowType());
        kes.setReadPerm(know.getReadPerm());
        kes.setReadRoleIds(know.getReadRoleIds());
        jestService.insert(kes);
        return "redirect:/know/"+know.getId();
    }
    @GetMapping(value = "/knowMain")
    public String toKnowMain(Map<String,Object> map){
        logger.info("进入知识库首页");
        //查询最新三条知识
        User user = new User();
        Session session = SecurityUtils.getSubject().getSession();
        try {
            BeanUtils.copyProperties(user, session.getAttribute("user"));
        } catch (Exception e) {
        }
        List<KnowWithBLOBs> topThreelist = knowService.findKnowTopThree(user.getNumber());
        for (int i = 0; i <topThreelist.size() ; i++) {
            topThreelist.get(i).setReadCount(knowCountService.findKnowReadCount(topThreelist.get(i).getId()));
            topThreelist.get(i).setGoodCount(knowCountService.findKnowGoodCount(topThreelist.get(i).getId()));
        }
        map.put("topThreelist",topThreelist);
        //查询前10条热门知识
        List<Know> hotKnows = knowService.findHotKnow();
        map.put("hotKnows",hotKnows);
        //查询最新的10条知识10
        List<KnowWithBLOBs> newKnows = knowService.findNewKnow();
        for (int i = 0; i <newKnows.size() ; i++) {
            //设置好评量
            newKnows.get(i).setGoodCount(knowCountService.findKnowGoodCount(newKnows.get(i).getId()));
            //阅读量
            newKnows.get(i).setReadCount(knowCountService.findKnowReadCount(newKnows.get(i).getId()));
            //评价量
            newKnows.get(i).setBadCount(commentService.findCountCommentByKnowId(newKnows.get(i).getId()));
        }
        map.put("newKnows",newKnows);
        List<Type> typeList = typeService.findValidType();
        List<Type> types = typeService.findAllKnowValidType(typeList);
        map.put("types",types);
        return "view/knowledge/knowledge_main";
    }

    @GetMapping(value = "/know/{id}")
    public String toKnowDetail(@PathVariable("id") Integer id,Map<String,Object> map){
        logger.info("获取知识");
        KnowWithBLOBs know = knowService.selectByPrimaryKey(id);
        map.put("know",know);
        String [] tags = know.getKnowTag().split("/");
        map.put("tags",tags);
        Collect collect = new Collect();
        collect.setKnowId(id);
        User user = new User();
        Session session = SecurityUtils.getSubject().getSession();
        try {
            BeanUtils.copyProperties(user, session.getAttribute("user"));
        } catch (Exception e) {
        }
        collect.setNumber(user.getNumber());
        Integer a = collectService.findCollectExsit(collect);
        int c = 0;
        if(a!=null && a!=0){
            c=a;
        }
        map.put("c",c);
        //查询知识关联的前5条评论
        List<Comment> comments = commentService.findTopFiveCommentByKnowId(id);
        Integer commentToal = commentService.findCountCommentByKnowId(id);
        map.put("commentToal",commentToal);

        if(comments.size()!=0){
            for (int i = 0; i <comments.size() ; i++) {
                List<Comment> sonComments = commentService.findSonComment(comments.get(i).getId());
                if(sonComments.size()!=0){
                    comments.get(i).setHasSon(true);
                    comments.get(i).setSonComments(sonComments);
                }
                Integer agreeCount = commentCountService.findAgreeCount(comments.get(i).getId());
                Integer opposeCount = commentCountService.findOpposeCount(comments.get(i).getId());
                comments.get(i).setAgreeCount(agreeCount);
                comments.get(i).setOpposeCount(opposeCount);
            }
        }
        map.put("comments",comments);
        Map<String,Object> map1 = new HashMap<String,Object>();
        map1.put("knowId",id);
        map1.put("creater",user.getNumber());
        KnowCount kc = new KnowCount();
        kc.setKnowId(id);
        kc.setCreater(user.getNumber());
        kc.setCreateDate(Tools.dateToString(new Date(),"yyyy-MM-dd HH:mm:ss"));
        kc.setReadCount(1);
        String lastReadDate = knowCountService.findTopOneReadDate(map1);
        if(lastReadDate=="" || lastReadDate==null){
            knowCountService.insertSelective(kc);
        }else{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date d = sdf.parse(lastReadDate);
                long b = new Date().getTime()-d.getTime();
                if(b>30*60*1000){
                    knowCountService.insertSelective(kc);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        Integer readCount = knowCountService.findKnowReadCount(id);
        map.put("readCount",readCount);
        Integer goodCount = knowCountService.findKnowGoodCount(id);
        map.put("goodCount",goodCount);
        Integer badCount = knowCountService.findKnowBadCount(id);
        map.put("badCount",badCount);

        //设置编辑权限
        boolean editPerm = true ;
        if(know.getEditPerm()==1){
            if(!user.getNumber().equals(know.getCreater())){
                editPerm = false;
            }
        }else if(know.getEditPerm()==2){
            String roleIds = user.getRoleIds();
            String [] knowEditRoles = know.getEditRoleIds().split(",");
            for (int i = 0; i <knowEditRoles.length ; i++) {
                if(roleIds.indexOf(knowEditRoles[i])!=-1){//没有
                    editPerm = false;
                }
            }
        }
        boolean readPerm = true;
        if(know.getReadPerm()==1){
            if(!user.getNumber().equals(know.getCreater())){
                readPerm = false;
            }
        }else if(know.getReadPerm()==2){
            String roleIds = user.getRoleIds();
            String [] knowReadRoles = know.getReadRoleIds().split(",");
            for (int i = 0; i <knowReadRoles.length ; i++) {
                if(roleIds.indexOf(knowReadRoles[i])!=-1){//没有
                    readPerm = false;
                }
            }
        }
        map.put("editPerm",editPerm);
        map.put("readPerm",readPerm);
        String knowFiles [] = null;
        if(know.getKnowFileNames()!=null &&!"".equals(know.getKnowFileNames())){
            knowFiles  = (know.getKnowFileNames()).split("/");
            List<String> knowFile = new ArrayList<String>();
            for (int i = 0; i <knowFiles.length ; i++) {
                knowFile.add(knowFiles[i]);
            }
            map.put("knowFile",knowFile);
        }

        return "view/knowledge/knowledge_detail";
    }

    //全文检索知识
    @PostMapping(value = "/searchAllKnow")
    public String searchAll(@RequestParam(value = "word" ,required = false)String word,
                            Map<String,Object> map){
        //查询表达式
        String query ="{\n" +
                "  \"query\": {\n" +
                "    \"bool\": {\n" +
                "      \"should\": [\n" +
                "                  { \"match\": { \"knowTitle\": \""+word+"\" }},\n" +
                "                  { \"match\": { \"knowType\": \""+word+"\" }},\n" +
                "                  { \"match\": { \"knowTag\": \""+word+"\" }},\n" +
                "                  { \"match\": { \"content\": \""+word+"\" }}\n" +
                "      ]\n" +
                "    }\n" +
                "  }\n," +
                 "  \"highlight\": {\n" +
                 "\"pre_tags\" : [ \"<hih>\" ],"+
                 "\"post_tags\" : [ \"</hih>\" ],"+
                 "      \"fields\":      { \"knowTitle\": {} ,\"knowType\": {},\"knowTag\": {},\"content\": {}}\n" +
                 "  }\n" +
                "}";
        try {
            SearchResult jr = jestService.search(query);
            List<SearchResult.Hit<KnowEs,Void>> hits = jr.getHits(KnowEs.class);
            List<KnowEs>knowEsList = new ArrayList<KnowEs>();
            for (SearchResult.Hit<KnowEs, Void> hit : hits) {
                KnowEs source = hit.source;
                //获取高亮后的内容
                if(hit.highlight==null){
                    break;
                }
                Map<String, List<String>> highlight = hit.highlight;
                List<String> views= highlight.get("knowTitle");//高亮后的title
                if(views!=null){
                    source.setKnowTitle(views.get(0));
                }
                List<String> tpye= highlight.get("knowType");//高亮后的title
                if(tpye!=null){
                    source.setKnowType(tpye.get(0));
                }
                List<String> knowTag= highlight.get("knowTag");//高亮后的title
                if(knowTag!=null){
                    source.setKnowTag(knowTag.get(0));
                }
                List<String> content= highlight.get("content");//高亮后的title
                if(content!=null){
                    source.setContent(content.get(0));
                }
                knowEsList.add(source);
            }

            map.put("knowEsList",knowEsList);
            List<Type> typeList = typeService.findAllValidType(13);
            List<Type> types = typeService.findAllKnowValidType(typeList);
            map.put("types",types);
            String times = jr.getJsonObject().get("took").toString();
            map.put("times",times);
            System.out.println(times);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "view/knowledge/knowledge_search";
    }

    //去知识编辑界面
    @GetMapping(value = "/eKnow/{id}")
    public String toEditKnow(@PathVariable("id") Integer id,
                             Map<String,Object>map){
        KnowWithBLOBs know = knowService.selectByPrimaryKey(id);
        map.put("know",know);
        List<Deparment> deps1 = new ArrayList<Deparment>();
        if(know.getPublish()==1){
            if(know.getPublishDepIds()!=null){
                deps1=depService.findDepNamesByIds(know.getPublishDepIds());
            }
        }
        map.put("deps1",deps1);
        List<Role> roles1 = new ArrayList<Role>();
        if(know.getEditPerm()==2){
            if(know.getEditRoleIds()!=null){
                roles1 = roleService.findRolesByIds(know.getEditRoleIds());
            }

        }
        map.put("roles1",roles1);
        List<Role> roles2 = new ArrayList<Role>();
        if(know.getReadPerm()==2){
            if(know.getReadRoleIds()!=null){
                roles2 = roleService.findRolesByIds(know.getReadRoleIds());
            }
        }
        map.put("roles2",roles2);
        List<Type> typeList = typeService.findAllValidType(13);
        List<Type> types = typeService.findAllKnowValidType(typeList);
        map.put("types",types);
        List<Deparment> deparmentList = depService.findAllDep();
        String deps = JSON.toJSONString(deparmentList);
        deps = deps.replaceAll("depId","id").replaceAll("parentId","pId").replaceAll("depName","name");
        map.put("deps",deps);
        List<Role> roleList = roleService.findAllRole();
        String roles = JSON.toJSONString(roleList);
        roles = roles.replaceAll("roleId","id").replaceAll("parentId","pId").replaceAll("roleName","name");
        map.put("roles",roles);
        return "view/knowledge/knowledge_create";
    }

    @GetMapping(value = "/addQuest")
    public String toAddKnowQuestion(Map<String,Object> map){
        return "view/knowledge/knowledge_ques_create";
    }

    //公开知识阅读权限
    @ResponseBody
    @PutMapping(value = "/updateKnowReadPerm/{id}")
    public int publishKnow(@PathVariable("id") Integer id){
        knowService.updateKnowReadPerm(id);
        return 0;
    }

    //删除知识
    @DeleteMapping(value = "/know/{id}")
    public String deleteKnow(@PathVariable("id") Integer id){
        return "redirect:/knowMain";
    }

    //去知识分类界面
    @GetMapping(value = "/knowType/{typeId}")
    public String toKnowType(@PathVariable("typeId") Integer  typeId,
                             @RequestParam Map<String,String> allRequestParams,
                             Map<String,Object>map){
        List<Type> typeList = typeService.findAllValidType(13);
        List<Type> types = typeService.findAllKnowValidType(typeList);
        map.put("types",types);
        Map<String,Object>paraMap=new HashMap<String,Object>();
        if(allRequestParams.containsKey("sort")){
            paraMap.put("sort",allRequestParams.get("sort"));
            map.put("sort",allRequestParams.get("sort"));
        }else {
            paraMap.put("sort","desc");
            map.put("sort","desc");
        }
        if(typeId!=13){
            paraMap.put("typeId",typeId);
        }
        Type type = typeService.selectByPrimaryKey(typeId);
        map.put("type",type);
        List<KnowWithBLOBs> knows = knowService.findKnowSortDate(paraMap);
        for (int i = 0; i <knows.size() ; i++) {
            //设置好评量
            knows.get(i).setGoodCount(knowCountService.findKnowGoodCount(knows.get(i).getId()));
            //阅读量
            knows.get(i).setReadCount(knowCountService.findKnowReadCount(knows.get(i).getId()));
            //评价量
            knows.get(i).setBadCount(commentService.findCountCommentByKnowId(knows.get(i).getId()));
        }
        if(allRequestParams.containsKey("order")){
            map.put("order",allRequestParams.get("order"));
        }
       if(!"createDate".equals(allRequestParams.get("order"))){
           knows =  knowService.sortKnow(knows,map.get("sort").toString(),allRequestParams.get("order"));
       }
        map.put("knows",knows);
        map.put("showType",allRequestParams.get("showType"));
        if("grid".equals(allRequestParams.get("showType"))){
            return "view/knowledge/knowledge_type_grid";
        }else{
            return "view/knowledge/knowledge_type";
        }
    }
}
