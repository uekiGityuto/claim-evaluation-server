package jp.co.tokiomarine_nichido.models;

import java.util.Date;
import java.util.List;

/**
 * Claim Model
 *
 * @author SKK231099 李
 */
public class Claim {

	// TODO: 【植木さん】IF15事案一覧照会APIに合わせる
	public String claimId;
	public String userName;
	public String name;
	public String kind;
	public Date birthday;
	public Date occurenceDate;
	public Date createDate;
	public Date updateDate;
	public List<Score> scoreHistory;
}