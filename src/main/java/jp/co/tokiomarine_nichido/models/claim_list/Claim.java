package jp.co.tokiomarine_nichido.models.claim_list;

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
    @SerializedName("BUTENKANJI")
    public String butenKanji;
    @SerializedName("KYOTENKANJI")
    public String kyotenKanji;
    @SerializedName("INSURANCEKIND")
    public String insuranceKind;
    @SerializedName("LASTUPDATEDATE")
    public String lastUpdateDate;
    @SerializedName("LOSSDATE")
    public String lossDate;
    @SerializedName("CLAIMCATEGORY")
    public String claimCategory;

}
