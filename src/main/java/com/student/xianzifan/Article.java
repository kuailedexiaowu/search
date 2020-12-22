package com.student.xianzifan;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "article",replicas = 0)
public class Article {

    @Id
    private String id;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String headLine;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String dateLine;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String content;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHeadLine() {
        return headLine;
    }

    public void setHeadLine(String headLine) {
        this.headLine = headLine;
    }

    public String getDateLine() {
        return dateLine;
    }

    public void setDateLine(String dateLine) {
        this.dateLine = dateLine;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
