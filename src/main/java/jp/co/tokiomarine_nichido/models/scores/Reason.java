package jp.co.tokiomarine_nichido.models.scores;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.SerializedName;

@XmlRootElement
public class Reason {

    @SerializedName("REASON")
    public Integer reason;
    @SerializedName("FEATURENAME")
    public String featureName;
    @SerializedName("FEATUREDESCRIPTION")
    public String featureDescription;

}
