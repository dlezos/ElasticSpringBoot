package ltd.lezos.elastic;

import com.fasterxml.jackson.databind.ObjectMapper;
import ltd.lezos.elastic.domain.IdentityGroup;
import ltd.lezos.elastic.helper.SpringContext;
import org.apache.flink.api.connector.sink2.SinkWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import java.io.IOException;

public class FlinkElasticWriter<IN> implements SinkWriter<IN> {
    transient ElasticsearchOperations operations;

    public FlinkElasticWriter() {
    }
    @Override
    public void write(IN element, Context context) throws IOException, InterruptedException {
        if (operations == null) {
            operations = SpringContext.getBean(ElasticsearchOperations.class);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        IdentityGroup identityGroup = objectMapper.readValue(element.toString(), IdentityGroup.class);
        operations.save(identityGroup);
        identityGroup.getIdentities().forEach(i -> operations.save(i.getBiographicData()));
        identityGroup.getIdentities().forEach(i -> operations.save(i.getTravelDocumentData()));
    }

    @Override
    public void flush(boolean endOfInput) throws IOException, InterruptedException {

    }

    @Override
    public void close() throws Exception {

    }
}
