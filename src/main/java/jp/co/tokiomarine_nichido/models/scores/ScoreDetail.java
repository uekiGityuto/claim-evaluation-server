package jp.co.tokiomarine_nichido.models.scores;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.SerializedName;

@XmlRootElement
public class ScoreDetail {

    @SerializedName("MODELTYPE")
    public String modelType;
    @SerializedName("RANK")
    public String rank;
    @SerializedName("SCORE")
    public Integer score;
    @SerializedName("REASONS")
    public List<Reason> reasons;

}
