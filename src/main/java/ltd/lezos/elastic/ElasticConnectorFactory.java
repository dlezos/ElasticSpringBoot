package ltd.lezos.elastic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ElasticConnectorFactory {
    @Autowired
    ElasticDataManager elasticDataManager;

    @Autowired
    KafkaDataManager kafkaDataManager;

    public static enum Transport {
        DIRECT,
        KAFKA
    }

    public ElasticConnection connection(Transport transport) {
        switch (transport) {
            case DIRECT:
                return elasticDataManager;
            case KAFKA:
                return kafkaDataManager;
            default:
                return null;
        }
    }
}
