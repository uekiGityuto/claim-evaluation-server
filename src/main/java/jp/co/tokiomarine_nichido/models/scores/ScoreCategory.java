package jp.co.tokiomarine_nichido.models.scores;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.SerializedName;

@XmlRootElement
public class ScoreCategory {

    @SerializedName("TOKUSHUSCORECLASS")
    public String tokushuScoreClass;

    @SerializedName("NCPDSCORECLASS")
    public String ncpdScoreClass;

    @SerializedName("CLAIMCATEGORYTYPE")
    public String claimCategoryType;

}
