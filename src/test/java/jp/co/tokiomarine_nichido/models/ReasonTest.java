package jp.co.tokiomarine_nichido.models;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import jp.co.tokiomarine_nichido.models.scores.Reason;

public class ReasonTest {

	@Test
	public void gsonTest1() {
		// テスト準備
		String reasonString = "{\"REASON\": \"\", \"FEATURENAME\": \"特徴量1\", \"FEATUREDESCRIPTION\": \"説明1\"}";
		Gson gson = new Gson();

		// テスト実行
		try {
		Reason reason = gson.fromJson(reasonString, Reason.class);
		fail();
		} catch (JsonSyntaxException | NumberFormatException e) {
			e.printStackTrace();
		}

	}

}
