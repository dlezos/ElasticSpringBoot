package ltd.lezos.elastic;

import com.fasterxml.jackson.databind.ObjectMapper;
import ltd.lezos.elastic.domain.IdentityGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class KafkaDataManager implements ElasticConnection {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    static final String OUT_TOPIC = "IdentityGroup_in";
    static final String IN_TOPIC = "IdentityGroup_out";

    public void send(String topic, String payload) {
        CompletableFuture<SendResult<String, String>> result = kafkaTemplate.send(topic, payload);
    }

//    @Override
//    public void indexData(List<IdentityGroup> identityGroupList) {
//
//    }

    @Override
    public void indexData(IdentityGroup identityGroup) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            kafkaTemplate.send(OUT_TOPIC, objectMapper.writeValueAsString(identityGroup));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @KafkaListener(topics = IN_TOPIC, groupId = "group_id")
    public void listenOutTopic(String message) {
        System.out.println("Received Message written in " + IN_TOPIC + ": " + message);
    }
}
