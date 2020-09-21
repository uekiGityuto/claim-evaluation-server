package jp.co.tokiomarine_nichido.models;

/**
 * IF08スコア照会APIへのリクエストボディ
 *
 * @author SKK231527 植木宥登
 *
 */
public class ScoresReqBody {

	private String REQ_USER_ID;
	private String CLAIMNUMBER;

	public ScoresReqBody(String reqUserId, String claimNumber) {
		this.REQ_USER_ID = reqUserId;
		this.CLAIMNUMBER = claimNumber;
	}
}
