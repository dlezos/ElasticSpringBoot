package ltd.lezos.elastic;

import co.elastic.clients.transport.TransportUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchClients;
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
    @Value("${elasticsearch.fingerprint}")
    String fingerprint;
    @Value("${elasticsearch.user}")
    String user;
    @Value("${elasticsearch.password}")
    String password;

    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo("localhost:9200")
                .usingSsl()
                .withBasicAuth(user, password)
                .withClientConfigurer(ElasticsearchClients.ElasticsearchRestClientConfigurationCallback.from(restClientBuilder -> {
                    // configure the Elasticsearch RestClient
                    restClientBuilder.setHttpClientConfigCallback(hc -> hc
                                    .setSSLContext(TransportUtils.sslContextFromCaFingerprint(fingerprint))
                                    //.setSSLContext(TransportUtils.sslContextFromCaFingerprint("204c723e7f119b088e847990f2928015f22d034883b25400566860eb4b69333e"))
                            //.setDefaultCredentialsProvider(credsProv)
                    );
                    return restClientBuilder;
                }))
                .build();
    }
}
