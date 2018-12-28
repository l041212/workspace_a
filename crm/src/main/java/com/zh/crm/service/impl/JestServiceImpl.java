package com.zh.crm.service.impl;

import com.zh.crm.entity.KnowEs;
import com.zh.crm.service.JestService;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.*;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.DeleteIndex;
import io.searchbox.indices.mapping.GetMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
@Transactional
@Service
public class JestServiceImpl implements JestService {
    @Autowired
    JestClient jestClient;
    private static String indexName = "knowindex";
    private static String typeName = "know";
    private static final Logger logger = LoggerFactory.getLogger(JestServiceImpl.class);

    //创建索引
    public void createIndex() throws Exception {
        JestResult jr = jestClient.execute(new CreateIndex.Builder(indexName).build());
        logger.info("索引创建"+jr.isSucceeded());
    }

    //新增数据
    public void insert(KnowEs know) throws Exception {
        Index index = new Index.Builder(know).index(indexName).type(typeName).build();
        JestResult jr = jestClient.execute(index);
        logger.info("新增知识"+jr.isSucceeded());
    }

    //获取mapping

    public void getIndexMapping() throws Exception {
        GetMapping getMapping = new GetMapping.Builder().addIndex(indexName).addType(typeName).build();
        JestResult jr =jestClient.execute(getMapping);
        logger.info(jr.getJsonString());
    }

    //批量新增数据
    public void insertBatch(List<KnowEs> objs ) throws Exception{
        Bulk.Builder bulk = new Bulk.Builder().defaultIndex(indexName).defaultType(typeName);
        boolean result = false;
        for (Object obj : objs) {
            Index index = new Index.Builder(obj).build();
            bulk.addAction(index);
        }
        BulkResult br = jestClient.execute(bulk.build());
        result = br.isSucceeded();
        logger.info("批量新增"+result);
    }

    //全文检索并高亮显示
    public   SearchResult   search(String query) throws Exception {
        Search search = new Search.Builder(query).addIndex(indexName).addType(typeName).build();
        SearchResult  jr = jestClient.execute(search);
        System.out.println("--++"+jr.getJsonString());
        System.out.println("本次查询共查到："+jr.getTotal()+"篇文章！");
        List<SearchResult.Hit<KnowEs,Void>> hits = jr.getHits(KnowEs.class);

        return jr;
    }
    //删除索引
    public void deleteIndex() throws Exception{
        JestResult jr = jestClient.execute(new DeleteIndex.Builder(indexName).build());
        System.out.println(jr.isSucceeded());
    }

    //删除单条数据
    public void deleteData(String id)throws Exception{
        DocumentResult dr = jestClient.execute(new Delete.Builder(id).index(indexName).type(typeName).build());
        logger.info("删除数据："+dr.isSucceeded());
    }


}
