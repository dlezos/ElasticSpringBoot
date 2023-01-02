package ltd.lezos.elastic.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
//import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.util.List;

@Document(indexName = "IdentityGroup")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class IdentityGroup implements Serializable {
    @Id
    ISIdentityGroupReference isIdentityGroupReference;

    @OneToMany
    List<Identity> identities;
    @OneToMany
    List<Biometric> biometrics;
//    List<NFPSpecificIdentityGroupData> nfpSpecificIdentityGroupData;
//    CBSSpecificIdentityGroupData cbsSpecificIdentityGroupData;
}
