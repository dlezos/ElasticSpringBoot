package ltd.lezos.elastic.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BiographicData implements Serializable {
    @Id
    String isBiographicDataID;
    String biographicIdenityType;
    String countryOfBirth;
    Date dateOfBirth;
    String familyName;
    String firstName;
    String fullName;
    String firstNameOfFather;
    String familyNameOfFather;
    String firstNameOfMother;
    String familyNameOfMother;
    String previousFamilyName;
    String previousFirstName;
    String gender;
    String nationality;
    String nationalityAtBirth;
    String otherName;
    String placeOfBirth;
    String familyNameAtBirth;
    String otherNationality;
}
