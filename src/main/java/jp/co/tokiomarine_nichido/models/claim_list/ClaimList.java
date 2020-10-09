package jp.co.tokiomarine_nichido.models.claim_list;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * IF15事案一覧照会APIから取得したレスポンスを格納するクラス。
 *
 * @author SKK231527 植木宥登
 *
 */
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
