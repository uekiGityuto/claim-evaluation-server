package jp.co.tokiomarine_nichido.models.claim_list;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.SerializedName;

@XmlRootElement
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
