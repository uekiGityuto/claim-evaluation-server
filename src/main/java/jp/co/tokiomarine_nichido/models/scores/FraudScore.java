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
public class FraudScore {

    @SerializedName("SCORINGDATE")
    public Date scoringDate;

    @SerializedName("CLAIMCATEGORY")
    public String claimCategory;

    @SerializedName("SCOREDETAIL")
    public List<ScoreDetail> scoreDetail;

    @SerializedName("SCORECATEGORIES")
    public List<ScoreCategory> scoreCategories;

}
