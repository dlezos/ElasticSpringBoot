package ltd.lezos.elastic;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;


@Configuration
public class ElasticConfiguration extends ElasticsearchConfiguration {
//    @Autowired
//    ReactiveElasticsearchOperations operations;
//
//    @Autowired
//    ReactiveElasticsearchClient elasticsearchClient;
//
//    @Autowired
//    RestClient restClient;

    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo("localhost:9200")
                .build();
    }

}
