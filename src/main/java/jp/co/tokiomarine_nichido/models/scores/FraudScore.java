package jp.co.tokiomarine_nichido.models.scores;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.SerializedName;

@XmlRootElement
public class FraudScore {

	@NotNull
    @SerializedName("SCORINGDATE")
    public Date scoringDate;

	@NotNull
    @SerializedName("CLAIMCATEGORY")
    public String claimCategory;

	@NotNull
    @SerializedName("SCOREDETAIL")
    public List<ScoreDetail> scoreDetail;

	@NotNull
    @SerializedName("SCORECATEGORIES")
    public List<ScoreCategory> scoreCategories;

}
