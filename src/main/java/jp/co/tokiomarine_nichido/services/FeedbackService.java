package jp.co.tokiomarine_nichido.services;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import jp.co.tokiomarine_nichido.models.Feedback;

/**
 * ForeSight Voice Miningから受信した発話を管理。Thread safe。
 * 
 * @author SKK229873 中山真吾
 *
 */
@ApplicationScoped
public class FeedbackService {
	// private Logger logger = LogManager.getLogger();
	
	public Feedback findByScoreId(String scoreId) {
		// TODO: 実装
		return null;
	}
	
	public List<Feedback> findByClaimId(String claimId){
		// TODO: 実装
		return null;
	}
	
	public void register(Feedback feedback) {
		// TODO: 実装
	}
}
