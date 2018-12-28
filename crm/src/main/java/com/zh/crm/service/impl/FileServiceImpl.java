package com.zh.crm.service.impl;

import com.zh.crm.entity.KnowFile;
import com.zh.crm.mapper.FileMapper;
import com.zh.crm.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class FileServiceImpl  implements FileService {
    @Autowired
    FileMapper fileMapper;
    @Override
    public int deleteByPrimaryKey(Integer fileId) {
        return fileMapper.deleteByPrimaryKey(fileId);
    }

    @Override
    public int insert(KnowFile record) {
        return fileMapper.insert(record);
    }

    @Override
    public int insertSelective(KnowFile record) {
        return fileMapper.insertSelective(record);
    }

    @Override
    public KnowFile selectByPrimaryKey(Integer fileId) {
        return fileMapper.selectByPrimaryKey(fileId);
    }

    @Override
    public int updateByPrimaryKeySelective(KnowFile record) {
        return fileMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(KnowFile record) {
        return fileMapper.updateByPrimaryKey(record);
    }
}
