package ltd.lezos.elastic;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.elasticsearch.client.RestClient;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.erhlc.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.client.erhlc.RestClients;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

@SpringBootApplication
public class ElasticApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElasticApplication.class, args);
	}

}
