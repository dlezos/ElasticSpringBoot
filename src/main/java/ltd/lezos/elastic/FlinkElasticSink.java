package ltd.lezos.elastic;

import org.apache.flink.api.connector.sink2.Sink;
import org.apache.flink.api.connector.sink2.SinkWriter;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import java.io.IOException;

//public class FlinkElasticSink<IN> extends RichSinkFunction<IN> {
public class FlinkElasticSink<IN> implements Sink<IN> {

//    ElasticsearchOperations operations;

    @Override
    public SinkWriter<IN> createWriter(InitContext context) throws IOException {
        FlinkElasticWriter<IN> writer = new FlinkElasticWriter<IN>();
        return writer;
    }

//    public void invoke(IN value, Context context) throws Exception {
//        System.out.println(value);
//    }

}
