package jp.co.tokiomarine_nichido.models;

/**
 * 推論結果に対するフィードバック。
 * 
 * @author SKK229873 中山真吾
 *
 */
public class Feedback {
	// 未評価の場合、null
	public Boolean isCorrect;
	
	public String comment;
	
	public String fraudScoreId;
}
