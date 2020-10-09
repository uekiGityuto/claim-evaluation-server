package jp.co.tokiomarine_nichido.models.claim_list;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

/**
 * IF15事案一覧照会APIから取得したレスポンスを格納するクラス。
 *
 * @author SKK231527 植木宥登
 *
 */
public class Claim {

    @SerializedName("CLAIMNUMBER")
    public String claimNumber;

    @SerializedName("INSUREDNAMEKANA")
    public String insuredNameKana;

	@SerializedName("INSUREDNAMEKANJI")
    public String insuredNameKanji;

	@SerializedName("CONTRACTORNAMEKANJI")
    public String contractorNameKanji;

	@SerializedName("CONTRACTORNAMEKANA")
    public String contractorNameKana;

	@SerializedName("BASE")
    public String base;

	@SerializedName("INSURANCEKIND")
    public String insuranceKind;

	@SerializedName("LASTUPDATEDATE")
    public Date lastUpdateDate;

	@SerializedName("LOSSDATE")
    public Date lossDate;

	@SerializedName("CLAIMCATEGORY")
    public String claimCategory;

}
