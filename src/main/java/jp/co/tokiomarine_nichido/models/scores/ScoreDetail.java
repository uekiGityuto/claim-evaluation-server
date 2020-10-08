package jp.co.tokiomarine_nichido.models.scores;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.SerializedName;

@XmlRootElement
public class ScoreDetail {

    @SerializedName("MODELTYPE")
    public String modelType;

    @SerializedName("SCORERANK")
    public String rank;

    @SerializedName("SCORE")
    public Double score;

    @SerializedName("REASONS")
    public List<Reason> reasons;

}
