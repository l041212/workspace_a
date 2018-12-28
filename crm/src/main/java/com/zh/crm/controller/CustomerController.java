package com.zh.crm.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zh.crm.entity.Customer;
import com.zh.crm.entity.Result;
import com.zh.crm.entity.Tools;
import com.zh.crm.entity.Type;
import com.zh.crm.service.CustomerService;
import com.zh.crm.service.TypeService;
import com.zh.crm.util.EasyPoiUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@CrossOrigin
@Controller
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    CustomerService customerService;
    @Autowired
    TypeService typeService;

    private static String keywords;
    private static String order="desc";
    private static String sort="createTime";


    @GetMapping(value = "/toCust")
    public String toCutsListPage(){
        logger.info("进入客户列表页面");
        return "view/customer/customer_list";
    }

    @ResponseBody
    @GetMapping(value = "/custs")
    public Result getCustList(@RequestParam Map<String,String> allRequestParams){
        logger.info("查询客户列表");
        Result result = new Result();
        int pageNum = 1;
        int pageSize = 10;
        if(allRequestParams.containsKey("pageNumber")){
            pageNum=Integer.parseInt(allRequestParams.get("pageNumber"));
        }
        if(allRequestParams.containsKey("pageSize")){
            pageSize=Integer.parseInt(allRequestParams.get("pageSize"));
        }
        Map<String,String> map = new HashMap<String,String>();
        if(allRequestParams.containsKey("keywords")){
            map.put("keywords",allRequestParams.get("keywords"));
            keywords=allRequestParams.get("keywords");
        }
        if(allRequestParams.containsKey("order")){
            map.put("order",allRequestParams.get("order"));
            order=allRequestParams.get("order");
        }else{
            map.put("order","desc");
        }
        if(allRequestParams.containsKey("sort")){
            map.put("sort",allRequestParams.get("sort"));
            sort=allRequestParams.get("sort");
        }else{
            map.put("sort","createTime");
        }
        PageHelper.startPage(pageNum,pageSize);
        List<Customer> customers = customerService.findAllCust(map);
        PageInfo pageInfo = new PageInfo(customers);
        result.setRows(pageInfo.getList());
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    @GetMapping(value = "/cust")
    public String toCustAdd(Map<String,Object> map){
        logger.info("去客户新增界面");
        //查询单位类型
        List<Type> companyTypes = typeService.findAllTypeByParentId(3);
        map.put("companyTypes",companyTypes);
        //查询信访类型
        List<Type> visitTypes = typeService.findAllTypeByParentId(11);
        map.put("visitTypes",visitTypes);
        return "view/customer/customer_edit";
    }

    @GetMapping(value = "/cust/{id}")
    public String toCustEdit(@PathVariable("id") Integer id,
                             Map<String,Object> map){
        logger.info("去客户修改界面");
        Customer customer = customerService.selectByPrimaryKey(id);
        map.put("customer",customer);
        //查询单位类型
        List<Type> companyTypes = typeService.findAllTypeByParentId(3);
        map.put("companyTypes",companyTypes);
        //查询信访类型
        List<Type> visitTypes = typeService.findAllTypeByParentId(11);
        map.put("visitTypes",visitTypes);
        return "view/customer/customer_edit";
    }

    @PostMapping(value = "/cust")
    public String addCust(Customer customer){
        customerService.insertSelective(customer);
        return "view/common/save_reslut" ;
    }

    @PutMapping(value = "/cust")
    public String editCust(Customer customer){
        customerService.updateByPrimaryKeySelective(customer);
        return "view/common/save_reslut";
    }

    @DeleteMapping(value = "/cust/{id}")
    public String deleteCust(@PathVariable("id") Integer id){
        customerService.deleteByPrimaryKey(id);
        return "redirect:/toCust";
    }

    @GetMapping(value = "/customer/export")
    public void exportCustomer(HttpServletResponse response){
        Map<String,String> map = new HashMap<String,String>();
        if(keywords!=null){
            map.put("keywords",keywords);
        }
        map.put("order",order);
        map.put("sort",sort);
        List<Customer> customers = customerService.findAllCust(map);
        EasyPoiUtils.exportExcel(customers,"客户列表","客户列表",
                Customer.class ,"客户列表.xls",true,response);
    }

    @GetMapping(value = "/customer/import")
    public String toImportCustomer(){
        return "view/customer/customer_upload.html";
    }


    @PostMapping(value = "/customer/uploadExcel")
    public String ImportCustomer(@RequestParam("excel") MultipartFile excel){
        logger.info("开始导入客户数据");
        List<Customer> customers = EasyPoiUtils.importExcel(excel,1,1,Customer.class);
        for (int i = 0; i <customers.size() ; i++) {
            if(customers.get(i).getName()==null){
                continue;
            }
            customers.get(i).setCreateTime(Tools.dateToString(new Date(),"yyyy-MM-dd hh:mm:ss"));
            customerService.insertSelective(customers.get(i));
        }
        return "view/common/save_reslut";
    }

}
