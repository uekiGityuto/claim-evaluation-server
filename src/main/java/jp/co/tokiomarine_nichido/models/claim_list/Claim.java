package jp.co.tokiomarine_nichido.models.claim_list;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.SerializedName;

@XmlRootElement
public class Claim {

	@NotNull
    @SerializedName("CLAIMNUMBER")
    public String claimNumber;

	@NotNull
    @SerializedName("INSUREDNAMEKANA")
    public String insuredNameKana;

	@NotNull
	@SerializedName("INSUREDNAMEKANJI")
    public String insuredNameKanji;

	@NotNull
	@SerializedName("CONTRACTORNAMEKANJI")
    public String contractorNameKanji;

	@NotNull
	@SerializedName("CONTRACTORNAMEKANA")
    public String contractorNameKana;

	@NotNull
	@SerializedName("BUTENKANJI")
    public String butenKanji;

	@NotNull
	@SerializedName("KYOTENKANJI")
    public String kyotenKanji;

	@NotNull
	@SerializedName("INSURANCEKIND")
    public String insuranceKind;

	@NotNull
	@SerializedName("LASTUPDATEDATE")
    public Date lastUpdateDate;

	@NotNull
	@SerializedName("LOSSDATE")
    public Date lossDate;

	@NotNull
	@SerializedName("CLAIMCATEGORY")
    public String claimCategory;

}
