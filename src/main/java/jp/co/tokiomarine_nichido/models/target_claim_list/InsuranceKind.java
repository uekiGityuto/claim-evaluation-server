package jp.co.tokiomarine_nichido.models.target_claim_list;

import com.google.gson.annotations.SerializedName;

/**
 * IF15事案一覧照会APIへのリクエストボディをマッピングするクラス。
 *
 * @author SKK231527 植木宥登
 *
 */
public class InsuranceKind {

	// JAX-RSでアンマーシャリングするためにpublicにする必要がある
	@SerializedName("INSURANCEKIND")
	public String insuranceKind;

}
