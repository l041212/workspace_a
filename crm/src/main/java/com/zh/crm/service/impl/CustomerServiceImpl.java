package com.zh.crm.service.impl;

import com.zh.crm.entity.Customer;
import com.zh.crm.mapper.CustomerMapper;
import com.zh.crm.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
@Transactional
@Service
public class CustomerServiceImpl  implements CustomerService {

    @Autowired
    CustomerMapper customerMapper;
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return customerMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Customer record) {
        return customerMapper.insert(record);
    }

    @Override
    public int insertSelective(Customer record) {
        return customerMapper.insertSelective(record);
    }

    @Override
    public Customer selectByPrimaryKey(Integer id) {
        return customerMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Customer record) {
        return customerMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Customer record) {
        return customerMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<Customer> findAllCust(Map<String, String> map) {
        return customerMapper.findAllCust(map);
    }
    
    @Override
	public int save(Customer customer) {
		if (customer != null) {
			if (customer.getId() != null) {
				return updateByPrimaryKeySelective(customer);
			}
			return insertSelective(customer);
		}
		return 0;
	}
   
}
