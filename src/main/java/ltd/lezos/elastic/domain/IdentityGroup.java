package ltd.lezos.elastic.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
//import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.util.List;

@Document(indexName = "identitygroup")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@IdClass(ISIdentityGroupReference.class)
public class IdentityGroup implements Serializable {
    // Some options for a composite primary key. Lets simplify for now
    //@EmbeddedId
    //ISIdentityGroupReference isIdentityGroupReference;
    //@Id
    //String informationSystemID;
    //@Id
    //String isIdentityGroupRecordID;
    @Id
    String isID_igID;
    @OneToMany
    List<Identity> identities;
    @OneToMany
    List<Biometric> biometrics;
}
