package jp.co.tokiomarine_nichido.models.target_claim_list;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * IF15事案一覧照会APIへのリクエストボディをマッピングするクラス。
 *
 * @author SKK231527 植木宥登
 *
 */
public class TargetClaimList {

	// JAX-RSでアンマーシャリングするためにpublicにする必要がある
	@SerializedName("REQ_USER_ID")
	public String userId;
	@SerializedName("CLAIMNUMBER")
	public String claimNumber;
	@SerializedName("CLAIMCATEGORYINFO")
	public List<ClaimCategory> claimCategoryInfo;
	@SerializedName("INSURANCEKINDINFO")
	public List<InsuranceKind> insuranceKindInfo;
	@SerializedName("FROMLOSSDATE")
	public String  fromLossDate;
	@SerializedName("TOLOSSDATE")
	public String  toLossDate;
	@SerializedName("INSUREDNAMEKANA")
	public String  insuredNameKana;
	@SerializedName("INSUREDNAMEKANJI")
	public String  insuredNameKanji;
	@SerializedName("CONTRACTORNAMEKANA")
	public String  contractorNameKana;
	@SerializedName("CONTRACTORNAMEKANJI")
	public String  contractorNameKanji;
	@SerializedName("BUTENKANJI")
	public String  butenKanji;
	@SerializedName("KYOTENKANJI")
	public String  kyotenKanji;
	@SerializedName("LABELTYPE")
	public String  labelType;
	@SerializedName("ORDER")
	public String  order;
	@SerializedName("DISPLAYFROM")
	public String  displayFrom;

}
