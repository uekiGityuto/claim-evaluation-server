package jp.co.tokiomarine_nichido.models;

import java.util.Map;

/**
 * Scoreに影響を与えた要因情報
 *
 * @author SKK231099 李
 */
public class Reason {
	// TODO:【李】fraudScoreIdとidxは不要
	private String fraudScoreId;
	private String idx;
	private String factor;
	// TODO:【李】なぜShort?
	private Short effect;

	// TODO:【李】JAX-RSでシリアライズするため不要
	public Reason(Map<String, Object> obj) {
		this.fraudScoreId = String.valueOf(obj.get("fraudScoreId"));
		this.idx = String.valueOf(obj.get("idx"));
		this.factor = String.valueOf(obj.get("factor"));
		this.effect = Short.valueOf(String.valueOf(Math.round(Double.valueOf(String.valueOf(obj.get("effect"))))));
	}

	public String getFraudScoreId() {
		return fraudScoreId;
	}

	public void setFraudScoreId(String fraudScoreId) {
		this.fraudScoreId = fraudScoreId;
	}

	public String getIdx() {
		return idx;
	}

	public void setIdx(String idx) {
		this.idx = idx;
	}

	public String getFactor() {
		return factor;
	}

	public void setFactor(String factor) {
		this.factor = factor;
	}

	public Short getEffect() {
		return effect;
	}

	public void setEffect(Short effect) {
		this.effect = effect;
	}

}
