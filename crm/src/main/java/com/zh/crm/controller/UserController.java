package com.zh.crm.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zh.crm.entity.*;
import com.zh.crm.service.DepService;
import com.zh.crm.service.RoleService;
import com.zh.crm.service.UserService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.*;

@CrossOrigin
@Controller
public class UserController {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @Autowired
    DepService depService;

    private static final String savePath = "D://upload/user/";

    @PostMapping (value="/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam(value = "extNumber" ,required = false,defaultValue = "") String extNumber,
                        @RequestParam("password") String password,
                        HttpServletRequest request,
                        Map<String,Object> map){
        try {
            SecurityUtils.getSubject().login(new UsernamePasswordToken(username, password,extNumber));
            Session session = SecurityUtils.getSubject().getSession();
            session.setAttribute("ext",extNumber);
           /* User user = userService.findUserByName(username,password);
            HttpSession s =request.getSession();*/
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            String lastLoginDate = Tools.dateToString(new Date(),"yyyy-MM-dd HH:mm:ss");
            user.setLastLogin(lastLoginDate);
            userService.updateByPrimaryKeySelective(user);
            logger.info(username+":登录成功");
        } catch (DisabledAccountException e) {
            map.put("msg","账户已被禁用");
            logger.info(username+":账户已被禁用");
            return "login";
        } catch (UnknownAccountException e){//未知用户异常
            map.put("msg","用户名错误！");
            logger.info(username+":用户名错误");
            return "login";
        }catch (CredentialsException e) {
            map.put("msg","用户名或密码错误");
            logger.info(username+":用户名或密码错误");
            return "login";
        }catch (Exception e){
            map.put("msg","账号异常！");
            logger.info(username+":账号异常");
            return "login";
        }
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String toMain(){
        //获取该用户所有的菜单
        Object key = SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        User user = new User();
        try {
            BeanUtils.copyProperties(user, key);
        } catch (Exception e) {
        }

        return "main";
    }

    @GetMapping({"/login","/"})
    public String toLogin(){
        return "login";
    }
    @GetMapping("/tab")
    public String toTab(){
        return "view/common/tab" ;
    }


    @GetMapping("/default")
    public String toDefault(){
        return "view/common/default" ;
    }

    @GetMapping("/toUser")
    public String toUserListPage(){
        return "view/user/user_list";
    }

    /*@Cacheable(value = {"allusers"},keyGenerator = "keyGenerator" )
    @CacheExpire(expire = 360)*/
    @ResponseBody
    @PostMapping(value = "/users")
    public Result userList(@RequestParam Map<String,String> allRequestParams){
        Result result = new Result();
        int pageNum = 1;
        int pageSize = 10;
        if(allRequestParams.containsKey("pageNumber")){
            pageNum= Integer.parseInt(allRequestParams.get("pageNumber"));
        }
        if(allRequestParams.containsKey("pageSize")){
            pageSize= Integer.parseInt(allRequestParams.get("pageSize"));
        }
        Map<String,Object> map = new HashMap<String,Object>();
        if(allRequestParams.containsKey("keywords")){
            map.put("keywords",allRequestParams.get("keywords"));
        }
        if(allRequestParams.containsKey("order")){
            map.put("order",allRequestParams.get("order"));
        }else{
            map.put("order","desc");
        }
        if(allRequestParams.containsKey("sort")){
            map.put("sort",allRequestParams.get("sort"));
        }else{
            map.put("sort","lastLogin");
        }
        PageHelper.startPage(pageNum,pageSize);
        List<User> users=userService.findAllUsers(map);
        PageInfo pageInfo = new PageInfo(users);
        result.setRows(pageInfo.getList());
        result.setTotal(pageInfo.getTotal());
        return result ;
    }

    @DeleteMapping(value = "/user/{id}")
    public String deleteUserById(@PathVariable("id") Integer id){
        userService.deleteByPrimaryKey(id);
        return "redirect:/toUser";
    }

    @GetMapping(value = "/user")
    public String toAddUserPage(Map<String,Object> map){
        List<Deparment> deps = depService.findAllDep();
        String depss = JSON.toJSONString(deps);
        depss = depss.replaceAll("depId","id").replaceAll("parentId","parentId").replaceAll("depName","name");
        map.put("depss",depss);
        List<Role> roles =roleService.findAllRole();
        String roless = JSON.toJSONString(roles);
        roless = roless.replaceAll("roleId","id").replaceAll("parentId","parentId").replaceAll("roleName","name");
        map.put("roless",roless);
        int max = userService.findMaxNumber()+1;
        map.put("maxNumber",max);
        return "view/user/user_edit";
    }

    @GetMapping(value = "/getUserByName/{username}/{number}")
    @ResponseBody
    public Integer getUserByName(@PathVariable("username") String username,@PathVariable("number") Integer number){
        int a = userService.findUserExist(username) ;
        int b = userService.findUserNumberExist(number);
        if(a!=0){
            return 1;
        }else if(b!=0){
            return 2;
        }else{
            return 0;
        }
    }

    @PostMapping(value = "/user")
    @Transactional
    public String addUser(User user){
        userService.insertSelective(user);
        System.out.println(user.getNumber()+"********************");
        // userService.deleteUserRole(user.getUserId());
        Map<String ,Object> map = new HashMap<String ,Object>();
        if(!"".equals(user.getRoleIds())){
            String [] roles =  user.getRoleIds().split(",");
            int [] userRole = new int[roles.length];
            for (int i = 0; i <roles.length ; i++) {
                userRole[i]=Integer.parseInt(roles[i]);
            }
            map.put("userId",user.getUserId());
            map.put("userRole",userRole);
            userService.insertUserRolesBatch(map);
        }
        if(user.getDepId()!=null){
            userService.insertUserDep(user.getUserId(),user.getDepId());
        }
        return "view/common/save_reslut" ;
    }

    @GetMapping(value = "/user/{userId}")
    public  String toEditUser(@PathVariable("userId") Integer userId,
                              Map<String,Object> map){
        List<Deparment> deps = depService.findAllDep();
        String depss = JSON.toJSONString(deps);
        depss = depss.replaceAll("depId","id").replaceAll("parentId","parentId").replaceAll("depName","name");
        map.put("depss",depss);
        List<Role> roles =roleService.findAllRole();
        User user = userService.findUserInfoById(userId);
        String roless = JSON.toJSONString(roles);
        roless = roless.replaceAll("roleId","id").replaceAll("parentId","parentId").replaceAll("roleName","name");
        map.put("user",user);
        map.put("roless",roless);
        return "view/common/save_reslut";
    }

    @PutMapping("/user")
    @Transactional
    public String editUser(User user){
        userService.updateByPrimaryKeySelective(user);
        userService.deleteUserRole(user.getUserId());
        userService.deleteUserDep(user.getUserId());
        Map<String ,Object> map = new HashMap<String ,Object>();
        if(!"".equals(user.getRoleIds())){
            String [] roles =  user.getRoleIds().split(",");
            int [] userRole = new int[roles.length];
            for (int i = 0; i <roles.length ; i++) {
                userRole[i]=Integer.parseInt(roles[i]);
            }
            map.put("userId",user.getUserId());
            map.put("userRole",userRole);
            userService.insertUserRolesBatch(map);
        }
        if(user.getDepId()!=null){
            userService.insertUserDep(user.getUserId(),user.getDepId());
        }
        return "view/user/user_edit";
    }

    @ResponseBody
    @PutMapping(value = "/userStatus/{userId}/{status}")
    public void editUserStatus(@PathVariable("userId") Integer userId,
                               @PathVariable("status") Integer status){
        User user = new User();
        user.setUserId(userId);
        user.setStatus(status);
        userService.updateByPrimaryKeySelective(user);
    }

    public List<Permission> getPermsByUserId(Integer userId){
        List<Permission> permissionList = new ArrayList<Permission>();
        return permissionList ;
    }


    @GetMapping(value = "/eUser")
    public String toMainEditUser(Map<String,Object>map){
        User user = new User();
        Session session = SecurityUtils.getSubject().getSession();
        try {
            BeanUtils.copyProperties(user, session.getAttribute("user"));
        } catch (Exception e) {
        }
        map.put("user",user);
        return "view/user/u_edit";
    }

    @PutMapping(value = "/iUser")
    public String iUser(User user){
        userService.updateByPrimaryKeySelective(user);
        return "view/common/save_reslut";
    }

    @GetMapping(value = "/eUserImg")
    public String editUserImg(Map<String,Object>map){
        User user = new User();
        Session session = SecurityUtils.getSubject().getSession();
        try {
            BeanUtils.copyProperties(user, session.getAttribute("user"));
        } catch (Exception e) {
        }
        map.put("user",user);
        return "view/user/user_photo";
    }

    @PutMapping("/userImg")
    public String addUserImg(@RequestParam("userImg") MultipartFile fileUpload,User user){
        String realName = System.nanoTime()+"knowFile"+fileUpload.getOriginalFilename();
        String fileName = savePath+realName;
        String url = "/upload/user/"+realName;
        Tools.saveFile(fileName,fileUpload);
        user.setUserImg(url);
        userService.updateByPrimaryKeySelective(user);
        return "view/common/save_reslut";
    }
}
