package jp.co.tokiomarine_nichido.models;

import javax.xml.bind.annotation.XmlRootElement;


/**
 * リクエストボディをマッピングするBean
 * （publicなフィールド、または、privateなフィールド+getterのみにする必要がある）
 *
 * @author SKK231527 植木宥登
 *
 */
@XmlRootElement
public class TargetClaim {
	private String claimNumber;

	public String getClaimNumber() {
		return this.claimNumber;
	}

}
