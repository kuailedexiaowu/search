package com.student.xianzifan;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Component;

@Component
public class IndexInitialization implements InitializingBean {
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public void afterPropertiesSet(){
        elasticsearchRestTemplate.createIndex(Article.class);
    }
}
