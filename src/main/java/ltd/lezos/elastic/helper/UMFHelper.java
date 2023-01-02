package ltd.lezos.elastic.helper;

import com.google.protobuf.StringValue;
import eu.europa.schengen.cir.umf.FreeTextType;
import eu.europa.schengen.cir.umf.LanguageEnum;
import eu.europa.schengen.cir.umf.MultilingualTextType;
import eu.europa.schengen.cir.umf.SimpleTextType;

public final class UMFHelper {
    public static final MultilingualTextType multilingualText(String primaryValue) {
        return multilingualText(primaryValue, null, null);
    }

    public static final MultilingualTextType multilingualText(String primaryValue, LanguageEnum language) {
        return multilingualText(primaryValue, language, null);
    }
    public static final MultilingualTextType multilingualText(String primaryValue, String metadata) {
        return multilingualText(primaryValue, null, metadata);
    }

    public static final MultilingualTextType multilingualText(String primaryValue, LanguageEnum language, String metadata) {
        MultilingualTextType.Builder builder = MultilingualTextType.newBuilder();
        MultilingualTextType mtt = builder.build();
        if (primaryValue != null) {
            SimpleTextType.Builder primaryValueBuilder = SimpleTextType.newBuilder().setValue(StringValue.newBuilder().setValue(primaryValue).build());
            if(language != null) {
                primaryValueBuilder.setLang(language);
            }
            builder.setPrimaryValue(primaryValueBuilder.build());
            if(metadata != null) {
                builder.setMetadata(StringValue.newBuilder().setValue(metadata).build());
            }
        }
        return builder.build();
    }

    public static final FreeTextType freeText(String value) {
        return freeText(value, null);
    }

    public static final FreeTextType freeText(String value, String metadata) {
        FreeTextType.Builder builder = FreeTextType.newBuilder();
        if (value != null) {
            builder.setValue(StringValue.newBuilder().setValue(value).build());
            if (metadata != null) {
                builder.setMetadata(StringValue.newBuilder().setValue(metadata).build());
            }
        }
        return builder.build();
    }
}
