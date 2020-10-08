package jp.co.tokiomarine_nichido.models.claim_list;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.SerializedName;

@XmlRootElement
public class ClaimList {

    @SerializedName("CLAIM")
    public List<Claim> claim;

    @SerializedName("ORDERTYPE")
    public String order;

    @SerializedName("DISPLAYFROM")
    public Integer fromPages;

    @SerializedName("DISPLAYTO")
    public Integer toPages;

    @SerializedName("TOTALNUMBER")
    public Integer totalNumber;

}
