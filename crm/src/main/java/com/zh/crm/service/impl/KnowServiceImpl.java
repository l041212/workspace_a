package com.zh.crm.service.impl;

import com.zh.crm.entity.Know;
import com.zh.crm.entity.KnowWithBLOBs;
import com.zh.crm.mapper.KnowMapper;
import com.zh.crm.service.KnowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
@Transactional
@Service
public class KnowServiceImpl  implements KnowService {
    @Autowired
    KnowMapper knowMapper;
    @Override
    public int deleteByPrimaryKey(Integer knowId) {
        return knowMapper.deleteByPrimaryKey(knowId);
    }

    @Override
    public int insert(KnowWithBLOBs record) {
        return knowMapper.insert(record);
    }

    @Override
    public int insertSelective(KnowWithBLOBs record) {
        return knowMapper.insertSelective(record);
    }

    @Override
    public KnowWithBLOBs selectByPrimaryKey(Integer knowId) {
        return knowMapper.selectByPrimaryKey(knowId);
    }

    @Override
    public int updateByPrimaryKeySelective(KnowWithBLOBs record) {
        return knowMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKeyWithBLOBs(KnowWithBLOBs record) {
        return knowMapper.updateByPrimaryKeyWithBLOBs(record);
    }

    @Override
    public int updateByPrimaryKey(Know record) {
        return knowMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<KnowWithBLOBs> findKnowTopThree(String number) {
        return knowMapper.findKnowTopThree(number);
    }

    @Override
    public void updateVersionByPastId(Integer id) {
        knowMapper.updateVersionByPastId(id);
    }

    @Override
    public List<Know> findHotKnow() {
        return knowMapper.findHotKnow();
    }

    @Override
    public List<KnowWithBLOBs> findNewKnow() {
        return knowMapper.findNewKnow();
    }

    @Override
    public int updateKnowReadPerm(Integer id) {
        return knowMapper.updateKnowReadPerm(id);
    }

    @Override
    public int findKnowCountByTypeId(Integer typeId) {
        return knowMapper.findKnowCountByTypeId(typeId);
    }

    @Override
    public List<KnowWithBLOBs> findKnowSortDate(Map<String, Object> map) {
        return knowMapper.findKnowSortDate(map);
    }

    public List<KnowWithBLOBs> sortKnow(List<KnowWithBLOBs> list,String sort,String order ){
       //按照好评排序
        if("goodCount".equals(order)){
            Collections.sort(list, new Comparator<KnowWithBLOBs>() {
                @Override
                public int compare(KnowWithBLOBs o1, KnowWithBLOBs o2) {

                        if("asc".equals(sort)){//升序
                            return o1.getGoodCount().compareTo(o2.getGoodCount());
                        }else{
                            return o2.getGoodCount().compareTo(o1.getGoodCount());
                        }
                }
            });
        } else if("readCount".equals(order)){
            Collections.sort(list, new Comparator<KnowWithBLOBs>() {
                @Override
                public int compare(KnowWithBLOBs o1, KnowWithBLOBs o2) {

                    if("asc".equals(sort)){//升序
                        return o1.getReadCount().compareTo(o2.getReadCount());
                    }else{
                        return o2.getReadCount().compareTo(o1.getReadCount());
                    }
                }
            });
        }
        return list;
    }
}
