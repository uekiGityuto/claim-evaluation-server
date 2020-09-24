package jp.co.tokiomarine_nichido.models.scores;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.SerializedName;

@XmlRootElement
public class DetailClaim {

    @SerializedName("CLAIMNUMBER")
    public String claimNumber;
    @SerializedName("INSUREDNAMEKANJI")
    public String insuredNameKanji;
    @SerializedName("INSUREDNAMEKANA")
    public String insuredNameKana;
    @SerializedName("CONTRACTORNAMEKANJI")
    public String contractorNameKanji;
    @SerializedName("CONTRACTORNAMEKANA")
    public String contractorNameKana;
    @SerializedName("INSURANCEKIND_EXT")
    public String insuranceKindExt;
    @SerializedName("LOSSDATE")
    public String lossDate;
    @SerializedName("UPDATEDATE")
    public String updateDate;
    @SerializedName("FRAUDSCOREHISTORY")
    public List<FraudScore> fraudScoreHistory;

}
