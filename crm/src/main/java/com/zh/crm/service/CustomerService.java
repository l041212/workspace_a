package com.zh.crm.service;

import com.zh.crm.entity.Customer;

import java.util.List;
import java.util.Map;

public interface CustomerService {
    int deleteByPrimaryKey(Integer id);

    int insert(Customer record);

    int insertSelective(Customer record);

    Customer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Customer record);

    int updateByPrimaryKey(Customer record);

    List<Customer> findAllCust(Map<String,String> map);
    
    public int save(Customer customer);
    
}
