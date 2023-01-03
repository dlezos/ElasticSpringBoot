package ltd.lezos.elastic.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BiometricData implements Serializable {
    @Id
    String attachmentID;
    String nistFormat;
    String index;
    Integer qualityValue;
    String sampleWarning;
    Boolean includeInComparisonFlag;
}
