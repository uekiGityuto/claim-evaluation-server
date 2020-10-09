package jp.co.tokiomarine_nichido.models.scores;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * IF08スコア照会APIから取得したレスポンスを格納するクラス。
 *
 * @author SKK231527 植木宥登
 *
 */
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
