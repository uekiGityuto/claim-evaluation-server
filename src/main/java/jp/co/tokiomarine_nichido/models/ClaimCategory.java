package jp.co.tokiomarine_nichido.models;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.SerializedName;

/**
 * 事案一覧照会のリクエストボディをマッピングするBean
 *
 * @author SKK231527 植木宥登
 *
 */
@XmlRootElement
public class ClaimCategory {

	// JAX-RSでアンマーシャリングするためにpublicにする必要がある
	@SerializedName("CLAIMCATEGORY")
	public String claimCategory;

}
