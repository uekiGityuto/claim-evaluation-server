package jp.co.tokiomarine_nichido.models.scores;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.SerializedName;

@XmlRootElement
public class ScoreCategory {

	@NotNull
    @SerializedName("TOKUSHUSCORECLASS")
    public String tokushuScoreClass;

	@NotNull
    @SerializedName("NCPDSCORECLASS")
    public String ncpdScoreClass;

	@NotNull
    @SerializedName("CLAIMCATEGORYTYPE")
    public String claimCategoryType;

}
