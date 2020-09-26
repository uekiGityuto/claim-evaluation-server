package jp.co.tokiomarine_nichido.models;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 認可処理の結果をまとめるクラス
 *
 * @author SKK231527 植木宥登
 *
 */
@XmlRootElement
public class AuthorizationResult {

	// JAX-RSでマーシャリングするためにpublicにする必要がある
	public String userId;
	public boolean authFlag;
	public String claimNumber;

	public AuthorizationResult(String userId, boolean authFlag, String claimNumber) {
		this.userId = userId;
		this.authFlag = authFlag;
		this.claimNumber = claimNumber;
	}
}
