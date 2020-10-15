package jp.co.tokiomarine_nichido.models;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class FraudScore {

    @SerializedName("SCORINGDATE")
    public Date scoringDate;

    @SerializedName("CLAIMCATEGORY")
    public String claimCategory;

}
