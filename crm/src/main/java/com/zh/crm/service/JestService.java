package com.zh.crm.service;

import com.zh.crm.entity.KnowEs;
import io.searchbox.client.JestResult;
import io.searchbox.core.SearchResult;

import java.util.List;

public interface JestService {

    public void createIndex() throws Exception;

    public void insert(KnowEs know) throws Exception;

    public void getIndexMapping() throws Exception;

    public void insertBatch(List<KnowEs> objs ) throws Exception;

    public SearchResult search(String query) throws Exception;

    public void deleteIndex() throws Exception;

    public void deleteData(String id)throws Exception;
}
