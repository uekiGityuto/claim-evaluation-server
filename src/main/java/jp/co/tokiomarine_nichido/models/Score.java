package jp.co.tokiomarine_nichido.models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * 推論結果Score
 *
 * @modify SKK231099 李
 */
public class Score {
	public String fraudScoreId;
	public Integer score;
	public Timestamp createDate;
	public String claimId;

	public Claim claim;
	public List<Reason> reasons = new ArrayList<>();
	public Feedback feedback;
}
