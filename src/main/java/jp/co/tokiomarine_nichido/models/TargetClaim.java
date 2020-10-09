package jp.co.tokiomarine_nichido.models;

import com.google.gson.annotations.SerializedName;

/**
 * IF08スコア照会APIへのリクエストボディをマッピングするクラス。
 *
 * @author SKK231527 植木宥登
 *
 */
public class TargetClaim {

	@SerializedName("REQ_USER_ID")
	public String userId;
	@SerializedName("CLAIMNUMBER")
	public String claimNumber;

}
