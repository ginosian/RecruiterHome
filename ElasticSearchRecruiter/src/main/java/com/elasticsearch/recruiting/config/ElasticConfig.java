package com.elasticsearch.recruiting.config;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Martha on 5/25/2017.
 */
@Configuration
@EnableElasticsearchRepositories(basePackages = "com.elasticsearch.recruiting.repository")
public class ElasticConfig {

    @Bean
    public ElasticsearchTemplate elasticsearchTemplate() throws UnknownHostException {
        Settings settings = Settings.settingsBuilder()
                .put("cluster.name", "elasticsearch")
                .put("cluster-nodes", "localhost:9300")
                .put("client.transport.ignore_cluster_name", false)
                .put("node.client", true)
                .build();

        TransportClient client =
                TransportClient.builder().settings(settings).build().addTransportAddress(
                        new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));

        return new ElasticsearchTemplate(client);
    }

}
