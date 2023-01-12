package ltd.lezos.elastic;

import ltd.lezos.elastic.domain.IdentityGroup;

import java.util.List;

public interface ElasticConnection {
    default void indexData(List<IdentityGroup> identityGroupList) {
        identityGroupList.forEach(this::indexData);
    }
    void indexData(IdentityGroup identityGroup);
}
