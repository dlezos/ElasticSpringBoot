package ltd.lezos.elastic;

import eu.europa.schengen.cir.commons.BiographicDataType;
import eu.europa.schengen.cir.commons.IdentityGroupType;
import eu.europa.schengen.cir.commons.IdentityType;
import eu.europa.schengen.cir.commons.TravelDocumentDataType;
import jakarta.persistence.OneToMany;
import ltd.lezos.elastic.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.elc.QueryBuilders;
import org.springframework.data.elasticsearch.client.erhlc.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.web.bind.annotation.*;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.*;

import static ltd.lezos.elastic.helper.UMFHelper.multilingualText;
import static ltd.lezos.elastic.helper.UMFHelper.freeText;
import static org.springframework.data.elasticsearch.client.elc.QueryBuilders.matchQuery;


@RestController
public class IngresController {
    @Autowired
    ElasticsearchOperations operations;

    @GetMapping("/alive")
    public String alive(){
        return "I am alive now "+System.currentTimeMillis();
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

    @GetMapping("/elastic")
    public String getElastic() {
        Criteria criteria = new Criteria("identities.biographicData.familyName").is("Lezos");
        Query query = new CriteriaQuery(criteria);
        SearchHits<IdentityGroup> results = operations.search(query, IdentityGroup.class);
        StringBuffer result = new StringBuffer();
        results.forEach(r -> result.append(new JSONObject(r.getContent()).toString(4)).append(",\n"));
        return result.toString();
        //return new JSONObject(results.getSearchHit(0)).toString(4);
        //return "OK";
    }

    @PostMapping("/elastic")
    public String postElasticAdd(@RequestBody IdentityGroup identityGroup) {
        operations.save(identityGroup);
        return "OK\n"+new JSONObject(identityGroup).toString(4);
    }

    @PostMapping("/elastic/{instances}")
    public String postElastic(@PathVariable Integer instances) {
        List<IdentityGroup> identityGroupList = new ArrayList<>(instances);
        for(int i=0; i<instances; i++) {
            Long minute = System.currentTimeMillis()/1000/60/60;
            identityGroupList.add(new IdentityGroup(
                    "0001-"+minute.toString()+"-"+i,
                    Arrays.asList(new Identity(
                            minute.toString()+"-"+i,
                            new BiographicData(
                                    minute.toString()+"-"+i,
                                    "0001",
                                    "000"+i,
                                    new Calendar.Builder().setDate(1971,5, 1).build().getTime(),
                                    "Lezos",
                                    "Dimitris",
                                    "Dimitris Lezos",
                                    "Vasilios",
                                    "Lezos",
                                    "Eirini",
                                    "Lezou",
                                    "Douros",
                                    "Mits Jim Freddy",
                                    "MALE",
                                    "GRE",
                                    "GRE",
                                    "Jim Lezos",
                                    "Athens",
                                    "Lezos",
                                    ""
                            ),
                            Arrays.asList(new TravelDocumentData(
                                    "GRE542588",
                                    "0001",
                                    "GRE542588",
                                    new Calendar.Builder().setDate(2020,1, 1).build().getTime(),
                                    "0031",
                                    new Calendar.Builder().setDate(2023,12, 31).build().getTime(),
                                    "Ministry of foreign affairs GRE"
                            ))
                    )),
                    Arrays.asList(new Biometric(
                            "isIdentityRecordID",
                            Arrays.asList(new BiometricData(
                                    "001-"+minute.toString()+"-"+i+"-1",
                                    "0002",
                                    "1",
                                    90,
                                    null,
                                    Boolean.TRUE
                                    ),
                                    new BiometricData(
                                            "001-"+minute.toString()+"-"+i+"-2",
                                            "0002",
                                            "2",
                                            78,
                                            null,
                                            Boolean.TRUE
                                    ))
                    ))
            ));
        }
        operations.save(identityGroupList);
        JSONObject json = new JSONObject(identityGroupList.get(0));
        return "OK-"+instances+"\n"+json.toString(4);
    }
}
