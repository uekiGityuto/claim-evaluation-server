package jp.co.tokiomarine_nichido.models;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.SerializedName;


/**
 * スコア照会のリクエストボディをマッピングするBean
 *
 * @author SKK231527 植木宥登
 *
 */
@XmlRootElement
public class TargetClaim {

	// JAX-RSでアンマーシャリングするためにpublicにする必要がある
	@SerializedName("REQ_USER_ID")
	public String userId;
	@SerializedName("CLAIMNUMBER")
	public String claimNumber;

}
