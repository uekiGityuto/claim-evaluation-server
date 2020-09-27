package jp.co.tokiomarine_nichido.models.scores;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.SerializedName;

// TODO: Date型に変更するか検討（横並びチェック、ISO8601の場合、Gsonをnewするときに工夫が必要？）
@XmlRootElement
public class DetailClaim {

	@NotNull
    @SerializedName("CLAIMNUMBER")
    public String claimNumber;

	@NotNull
    @SerializedName("INSUREDNAMEKANJI")
    public String insuredNameKanji;

	@NotNull
    @SerializedName("INSUREDNAMEKANA")
    public String insuredNameKana;

	@NotNull
    @SerializedName("CONTRACTORNAMEKANJI")
    public String contractorNameKanji;

	@NotNull
    @SerializedName("CONTRACTORNAMEKANA")
    public String contractorNameKana;

	@NotNull
    @SerializedName("INSURANCEKIND_EXT")
    public String insuranceKindExt;

	@NotNull
    @SerializedName("LOSSDATE")
    public String lossDate;

	@NotNull
    @SerializedName("UPDATEDATE")
    public String updateDate;

	@NotNull
    @SerializedName("FRAUDSCOREHISTORY")
    public List<FraudScore> fraudScoreHistory;

}
