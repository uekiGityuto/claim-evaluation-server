package jp.co.tokiomarine_nichido.models.scores;

import com.google.gson.annotations.SerializedName;

/**
 * IF08スコア照会APIから取得したレスポンスを格納するクラス。
 *
 * @author SKK231527 植木宥登
 *
 */
public class Scores {

	@SerializedName("CLAIM")
    public DetailClaim claim;

}
