package jp.co.tokiomarine_nichido.models;

import javax.xml.bind.annotation.XmlRootElement;


/**
 * スコア照会のリクエストボディをマッピングするBean
 * （publicなフィールドにする必要がある）
 *
 * @author SKK231527 植木宥登
 *
 */
@XmlRootElement
public class TargetClaim {

	public String REQ_USER_ID;
	public String CLAIMNUMBER;
}
