package com.student.xianzifan;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

@Configuration
public class RestClientConfig extends AbstractElasticsearchConfiguration {
    @Value("${es.url:localhost}")
    private String esUrl;

    @Override
    public RestHighLevelClient elasticsearchClient() {
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(esUrl)
                .withSocketTimeout(30 * 1000)
                .build();
        return RestClients.create(clientConfiguration).rest();
    }

    @Primary
    @Bean
    public ElasticsearchRestTemplate elasticsearchOperations() {
        return new ElasticsearchRestTemplate(elasticsearchClient(), elasticsearchConverter(), resultsMapper());
    }

}
