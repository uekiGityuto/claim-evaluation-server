package jp.co.tokiomarine_nichido.models;

import java.util.Date;

/**
 * 推論結果Score
 *
 * @modify SKK231099 李
 */
public class Score {

	// TODO: 【植木さん】IF08スコア照会APIに合わせる
	public String fraudScoreId;
	public Integer score;
	public String claimId;
	public Date createDate;
	public Date updateDate;
	public Claim claim;
}
