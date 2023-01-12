package ltd.lezos.elastic.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.util.Date;

@Document(indexName = "traveldocumentdata")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TravelDocumentData implements Serializable {
    @Id
    String isTravelDocumentRecordID;
    String documentType;
    String documentNumber;
    Date issueDate;
    String issuingCountry;
    Date validUntil;
    String documentIssuingAuthority;
}
