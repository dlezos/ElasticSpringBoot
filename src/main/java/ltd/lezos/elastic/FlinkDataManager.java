package ltd.lezos.elastic;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.connector.sink2.Sink;
import org.apache.flink.connector.base.DeliveryGuarantee;
import org.apache.flink.connector.elasticsearch.sink.Elasticsearch7SinkBuilder;
import org.apache.flink.connector.elasticsearch.sink.ElasticsearchSink;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Requests;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import java.util.Map;
import java.util.HashMap;

public class FlinkDataManager {

    static StreamExecutionEnvironment environment;

    public static KafkaSource<String> createKafkaSource(String topic, String kafkaAddress, String kafkaGroup) {
        return KafkaSource.<String>builder()
                .setBootstrapServers(kafkaAddress)
                .setTopics(topic)
                .setGroupId(kafkaGroup)
                //.setStartingOffsets(OffsetsInitializer.earliest())
                .setValueOnlyDeserializer(new SimpleStringSchema())
                .build();
    }

    public static KafkaSink<String> createKafkaSink(String topic, String kafkaAddress) {
        return KafkaSink.<String>builder()
                .setBootstrapServers(kafkaAddress)
                .setRecordSerializer(KafkaRecordSerializationSchema.builder()
                        .setTopic(topic)
                        .setValueSerializationSchema(new SimpleStringSchema())
                        .build()
                )
                .setDeliverGuarantee(DeliveryGuarantee.AT_LEAST_ONCE)
                .build();
    }
    public static ElasticsearchSink<String> createElasticsearchSink(HttpHost httpHost) {
        return new Elasticsearch7SinkBuilder<String>()
                .setBulkFlushMaxActions(1) // Instructs the sink to emit after every element, otherwise they would be buffered
                .setHosts(httpHost)
                .setConnectionPassword("204c723e7f119b088e847990f2928015f22d034883b25400566860eb4b69333e")
                .setEmitter((element, context, indexer) -> indexer.add(createIndexRequest(element)))
                .build();
    }

    public static FlinkElasticSink<String> createFlinkElasticSink() {
//        return new Elasticsearch7SinkBuilder<String>()
//                .setBulkFlushMaxActions(1) // Instructs the sink to emit after every element, otherwise they would be buffered
//                .setHosts(httpHost)
//                .setConnectionPassword("204c723e7f119b088e847990f2928015f22d034883b25400566860eb4b69333e")
//                .setEmitter((element, context, indexer) -> indexer.add(createIndexRequest(element)))
//                .build();
        return new FlinkElasticSink<String>();
    }

    private static IndexRequest createIndexRequest(String element) {
        Map<String, Object> json = new HashMap<>();
        json.put("data", element);

        return Requests.indexRequest()
                .index("identitygroup")
                //.id(element)
                .source(json);

    }

    public static void execute() {
        String inputTopic = "IdentityGroup_in";
        String outputTopic = "IdentityGroup_out";
        String group = "group_id";
        String address = "localhost:9092";
        //StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        if (environment == null) {
            environment = StreamExecutionEnvironment.createLocalEnvironment();
            DataStreamSource<String> kafkaSource = environment.fromSource(createKafkaSource(inputTopic, address, group), WatermarkStrategy.noWatermarks(), "Kafka Source");
//            kafkaSource.sinkTo(createKafkaSink(outputTopic, address));
//            kafkaSource.sinkTo(createElasticsearchSink(new HttpHost("localhost", 9200, "https")));
            kafkaSource.sinkTo((Sink<String>)createFlinkElasticSink());
        }
        try {
            environment.execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
