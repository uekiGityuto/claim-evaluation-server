package jp.co.tokiomarine_nichido.models;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.SerializedName;

@XmlRootElement
public class FraudScore {

    @SerializedName("SCORINGDATE")
    public Date scoringDate;

    @SerializedName("CLAIMCATEGORY")
    public String claimCategory;

}
