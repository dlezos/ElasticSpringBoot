package ltd.lezos.elastic;

import eu.europa.schengen.cir.commons.BiographicDataType;
import eu.europa.schengen.cir.commons.IdentityGroupType;
import eu.europa.schengen.cir.commons.IdentityType;
import eu.europa.schengen.cir.commons.TravelDocumentDataType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static ltd.lezos.elastic.helper.UMFHelper.multilingualText;
import static ltd.lezos.elastic.helper.UMFHelper.freeText;


@RestController
public class IngresController {
    @GetMapping("/alive")
    public String alive(){
        return "I am alive";
    }

    @GetMapping("/testProtos")
    public String testProtos() {
        IdentityGroupType identityGroup = IdentityGroupType.newBuilder()
                .addIdentities(IdentityType.newBuilder()
                        .setBiographicData(BiographicDataType.newBuilder()
                                .addFamilyName(multilingualText("Lezos"))
                                .addFirstName(multilingualText("Dimitris"))
                                .build())
                        .addTravelDocumentData(TravelDocumentDataType.newBuilder()
                                .setDocumentNumber(freeText("IYN1730"))
                                .build())
                        .addTravelDocumentData(TravelDocumentDataType.newBuilder()
                                .setDocumentNumber(freeText("YBN6771"))
                                .build())
                        .build())
                .build();
        return "<pre>"+identityGroup.toString()+"</pre>";
    }
}
