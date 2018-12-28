package com.zh.crm.service.impl;

import com.zh.crm.entity.Type;
import com.zh.crm.entity.TypeExtend;
import com.zh.crm.mapper.KnowMapper;
import com.zh.crm.mapper.TypeMapper;
import com.zh.crm.service.TypeService;
import com.zh.crm.util.DozerUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TypeServiceImpl implements TypeService {

    @Autowired
    TypeMapper typeMapper;
    @Autowired
    KnowMapper knowMapper;
    @Autowired
	private DozerUtil dozerUtil;
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return typeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Type record) {
        return typeMapper.insert(record);
    }

    @Override
    public int insertSelective(Type record) {
        return typeMapper.insertSelective(record);
    }

    @Override
    public Type selectByPrimaryKey(Integer id) {
        return typeMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Type record) {
        return typeMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Type record) {
        return typeMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<Type> findAllType() {
        return typeMapper.findAllType();
    }

    @Override
    public List<Type> findTypeByParentId(Map<String, Object> map) {
        return typeMapper.findTypeByParentId(map);
    }

    @Override
    public Integer findMaxValueByParentId(Integer parentId) {
        return typeMapper.findMaxValueByParentId(parentId);
    }

    @Override
    public List<Type> findAllTypeByParentId(Integer parentId) {
        return typeMapper.findAllTypeByParentId(parentId);
    }

    @Override
    public Integer findTypeExist(Map<String, Object> map) {
        return typeMapper.findTypeExist(map);
    }

    public List<Type> findAllKnowValidType(List<Type>types) {
        for (int i = 0; i <types.size() ; i++) {
            types.get(i).setTypeCount(knowMapper.findKnowCountByTypeId(types.get(i).getId()));
            List<Type> sonTypes = typeMapper.findAllValidType(types.get(i).getId());
            if(sonTypes!=null){
                if(sonTypes.size()>0){
                    types.get(i).setHasMenu(true);
                    types.get(i).setSonTypes(sonTypes);
                    findAllKnowValidType(sonTypes);
                }
            }

        }
        return types;
    }

    public List<Type> findAllValidType(Integer parentId){
        return typeMapper.findAllValidType(parentId);
    }

    @Override
    public List<Type> findValidType() {
        return typeMapper.findValidType();
    }
    
	@Override
	public List<Type> findAllValidTypeLoop(Integer parentId, Boolean flag) {
		List<Type> resultTypes = new ArrayList<Type>();
		List<Type> types = typeMapper.findAllValidType(parentId);
		if (types != null && !types.isEmpty()) {
			resultTypes.addAll(types);
			for (Type type : types) {
				List<Type> sonTypes = findAllValidTypeLoop(type.getId(), flag);
				if (sonTypes != null && !sonTypes.isEmpty()) {
					type.setSonTypes(sonTypes);
					if(flag) {
						resultTypes.addAll(sonTypes);
					}		
				}
			}
		}
		return resultTypes;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> List<TypeExtend<T>> findAllValidTypeLoop(Integer parentId, Boolean flag, Object service, String find, String typeId) {
		List<TypeExtend<T>> resultTypes = new ArrayList<TypeExtend<T>>();
		List<Type> types = findAllValidTypeLoop(parentId, false);
		try {
			Method method = service.getClass().getMethod(find, Map.class);
			for (Type type : types) {
				Map<String, Object> map = new HashMap<String, Object>();
				TypeExtend<T> resultType = dozerUtil.convert(dozerUtil.convert(type, Map.class), TypeExtend.class);
				map.put(typeId, type.getId());
				map.put("status", 0);
				resultType.setId("t_" + type.getId());
				resultType.setParentId("t_" + type.getParentId());
				resultType.setSonTypes(findAllValidTypeLoop(type.getId(), flag, service, find, typeId));
				resultType.setEntityTypes((List<T>) method.invoke(service, map));
				resultTypes.add(resultType);
				if (flag) {
					resultTypes.addAll(resultType.getSonTypes());
					if (resultType.getEntityTypes() != null && !resultType.getEntityTypes().isEmpty()) {
						for (T entity : resultType.getEntityTypes()) {
							TypeExtend<T> entityType = dozerUtil.convert(entity, TypeExtend.class);
							entityType.setId("e_" + entityType.getId());
							entityType.setParentId("t_" + entityType.getParentId());
							resultTypes.add(entityType);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultTypes;
	}
    
}
