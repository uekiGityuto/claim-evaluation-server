package jp.co.tokiomarine_nichido.models;

public class ScoreReqBody {

	private String REQ_USER_ID;
	private String CLAIMNUMBER;

	public ScoreReqBody(String reqUserId, String claimNumber) {
		this.REQ_USER_ID = reqUserId;
		this.CLAIMNUMBER = claimNumber;
	}
}
