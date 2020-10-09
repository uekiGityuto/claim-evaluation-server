package jp.co.tokiomarine_nichido.models.scores;

import com.google.gson.annotations.SerializedName;

/**
 * IF08スコア照会APIから取得したレスポンスを格納するクラス。
 *
 * @author SKK231527 植木宥登
 *
 */
public class Reason {

    @SerializedName("REASON")
    public Double reason;

    @SerializedName("FEATURENAME")
    public String featureName;

    @SerializedName("FEATUREDESCRIPTION")
    public String featureDescription;

}
