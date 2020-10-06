package jp.co.tokiomarine_nichido.models.claim_list;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.SerializedName;

@XmlRootElement
public class ClaimList {

	@NotNull
    @SerializedName("CLAIM")
    public List<Claim> claim;

	@NotNull
    @SerializedName("ORDERTYPE")
    public String order;

	@NotNull
    @SerializedName("DISPLAYFROM")
    public Integer fromPages;

	@NotNull
    @SerializedName("DISPLAYTO")
    public Integer toPages;

	@NotNull
    @SerializedName("TOTALNUMBER")
    public Integer totalNumber;

}
