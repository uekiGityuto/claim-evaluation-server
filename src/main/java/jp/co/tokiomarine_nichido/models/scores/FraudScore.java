package jp.co.tokiomarine_nichido.models.scores;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.SerializedName;

@XmlRootElement
public class FraudScore {

    @SerializedName("SCORINGDATE")
    public String scoringDate;
    @SerializedName("CLAIMCATEGORY")
    public String claimCategory;
    @SerializedName("SCOREDETAIL")
    public List<ScoreDetail> scoreDetail;
    @SerializedName("SCORECATEGORIES")
    public List<ScoreCategory> scoreCategories;

}
