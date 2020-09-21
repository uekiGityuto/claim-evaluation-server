package jp.co.tokiomarine_nichido.models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RequestTargetClaim {
	private String claimNumber;

	public String getClaimNumber() {
		return this.claimNumber;
	}

}
