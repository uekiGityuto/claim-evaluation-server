package jp.co.tokiomarine_nichido.models;

import javax.xml.bind.annotation.XmlRootElement;


/**
 * スコア照会のリクエストボディをマッピングするBean
 *
 * @author SKK231527 植木宥登
 *
 */
@XmlRootElement
public class TargetClaim {

	// JAX-RSでアンマーシャリングするためにpublicにする必要がある
	public String REQ_USER_ID;
	public String CLAIMNUMBER;
}
