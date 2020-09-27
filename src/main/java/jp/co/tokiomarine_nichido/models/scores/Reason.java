package jp.co.tokiomarine_nichido.models.scores;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.SerializedName;

@XmlRootElement
public class Reason {

	@NotNull
    @SerializedName("REASON")
    public Double reason;

	@NotNull
    @SerializedName("FEATURENAME")
    public String featureName;

	@NotNull
    @SerializedName("FEATUREDESCRIPTION")
    public String featureDescription;

}
