package org.motechproject.noyawa.domain;

import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.TypeDiscriminator;
import org.motechproject.model.MotechBaseDataObject;

@TypeDiscriminator("doc.type === 'Subscriber'")
public class Subscriber extends MotechBaseDataObject {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("type")
    private String type = "Subscriber";
    private String number;

    public Subscriber() {
    }

    public Subscriber(String number) {
        this.number = number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }
}
