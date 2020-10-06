package jp.co.tokiomarine_nichido.models.scores;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.SerializedName;

@XmlRootElement
public class ScoreDetail {

	@NotNull
    @SerializedName("MODELTYPE")
    public String modelType;

	@NotNull
    @SerializedName("SCORERANK")
    public String rank;

	@NotNull
    @SerializedName("SCORE")
    public Double score;

	@NotNull
    @SerializedName("REASONS")
    public List<Reason> reasons;

}
