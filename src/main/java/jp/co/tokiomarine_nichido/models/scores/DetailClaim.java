package jp.co.tokiomarine_nichido.models.scores;

import java.util.Date;
import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * IF08スコア照会APIから取得したレスポンスを格納するクラス。
 *
 * @author SKK231527 植木宥登
 *
 */
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
    public Date lossDate;

    @SerializedName("UPDATEDATE")
    public Date updateDate;

    @SerializedName("FRAUDSCOREHISTORY")
    public List<FraudScore> fraudScoreHistory;

}
