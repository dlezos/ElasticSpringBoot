package ltd.lezos.elastic;

import ltd.lezos.elastic.domain.IdentityGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ElasticDataManager {
    @Autowired
    ElasticsearchOperations operations;

    public void indexData(IdentityGroup identityGroup) {
        //Index in identitygroup AND biographic - traveldocument
        operations.save(identityGroup);
        identityGroup.getIdentities().forEach(i -> operations.save(i.getBiographicData()));
        identityGroup.getIdentities().forEach(i -> operations.save(i.getTravelDocumentData()));
    }

    public void indexData(List<IdentityGroup> identityGroupList) {
        identityGroupList.forEach(this::indexData);
    }
}
