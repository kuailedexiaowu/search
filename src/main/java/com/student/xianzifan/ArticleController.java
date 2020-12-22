package com.student.xianzifan;

import org.elasticsearch.index.query.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @GetMapping("/search")
    public List<Article> search(@RequestParam String queryText){
        QueryStringQueryBuilder builder = new QueryStringQueryBuilder("(headLine:"+queryText+") OR (content:"+queryText+")");
        builder.defaultOperator(Operator.OR);
        builder.field("headLine", 100.0f);
        builder.field("content", 25.0f);
        builder.minimumShouldMatch("2");
        SearchQuery searchQuery = new NativeSearchQuery(builder);
        searchQuery.setPageable(PageRequest.of(0, 100));
        searchQuery.addIndices("article");
        searchQuery.addTypes("article");
        return elasticsearchRestTemplate.queryForList(searchQuery, Article.class);


    }

    @PutMapping("/load")
    public String loadArticle(){
        File file = new File("D:\\STNO-UNICODE");
        List<IndexQuery> indexQueries = new ArrayList<>();
        for(File sub : file.listFiles()){
            for (File sub2 : sub.listFiles()){
                Article article = TransferFileUtil.read(sub2);
                IndexQuery indexQuery = new IndexQueryBuilder()
                        .withId(article.getId())
                        .withIndexName("article")
                        .withType("article")
                        .withObject(article)
                        .build();
                indexQueries.add(indexQuery);
            }
        }
        elasticsearchRestTemplate.bulkIndex(indexQueries, BulkOptions.defaultOptions());
        return "finish, load ["+indexQueries.size()+"] files";
    }

    @GetMapping("/all")
    public List<Article> all(){
        QueryStringQueryBuilder builder = new QueryStringQueryBuilder("*:*");
        SearchQuery searchQuery = new NativeSearchQuery(builder);
        searchQuery.addTypes("article");
        searchQuery.setPageable(PageRequest.of(0, 200));
        return elasticsearchRestTemplate.queryForList(searchQuery, Article.class);
    }

    @DeleteMapping("/clear")
    public String clear(){
        DeleteQuery deleteQuery =new DeleteQuery();
        deleteQuery.setIndex("article");
        deleteQuery.setType("article");
        deleteQuery.setQuery(new QueryStringQueryBuilder("*:*"));
        elasticsearchRestTemplate.delete(deleteQuery);
        return "delete all doc";
    }

}
