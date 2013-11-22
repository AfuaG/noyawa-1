package org.motechproject.noyawa.domain;

import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.TypeDiscriminator;
import org.motechproject.model.MotechBaseDataObject;

import java.util.List;

import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

@TypeDiscriminator("doc.type === 'ShortCode'")
public class ShortCode extends MotechBaseDataObject {
    @JsonProperty("type")
    private String type = "ShortCode";
    public static final String RELATIVE = "relative";
    public static final String STOP = "stop";
    public static final String REGISTER_WITH_AGE_GENDER_EDUCATION_LEVEL = "register_with_age_gender_education_level";
    public static final String REGISTER_WITH_AGE_GENDER = "register_with_age_gender";

    private String codeKey;
    private List<String> codes;

    public String getCodeKey() {
        return codeKey;
    }

    public ShortCode setCodeKey(String codeKey) {
        this.codeKey = codeKey;
        return this;
    }

    public List<String> getCodes() {
        return codes;
    }

    public ShortCode setCodes(List<String> codes) {
        this.codes = codes;
        return this;
    }

    public String defaultCode()  {
        return isNotEmpty(this.codes) ? this.codes.get(0) : ""; 
    }
}