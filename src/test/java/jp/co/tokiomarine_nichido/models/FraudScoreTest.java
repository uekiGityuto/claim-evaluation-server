package jp.co.tokiomarine_nichido.models;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

public class FraudScoreTest {

	@Test
	public void gsonTest1() {
		// テスト準備
		String fraudScoreString = "{\"SCORINGDATE\": \"2020-08-18T13:11:43.887Z\", \"CLAIMCATEGORY\": \"高\"}";
		Gson gson1 = new Gson();
		Gson gson2 = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();

		// テスト実行
		FraudScore fraudScore1 = gson1.fromJson(fraudScoreString, FraudScore.class);
		FraudScore fraudScore2 = gson2.fromJson(fraudScoreString, FraudScore.class);
		System.out.println(gson1.toJson(fraudScore1));
		System.out.println(gson2.toJson(fraudScore2));
	}

	@Test
	public void gsonTest2() {
		// テスト準備
		String fraudScoreString = "{\"SCORINGDATE\": \"\", \"CLAIMCATEGORY\": \"高\"}";
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();

		// テスト実行
		try {
		FraudScore fraudScore = gson.fromJson(fraudScoreString, FraudScore.class);
		fail();
		} catch ( JsonSyntaxException | NumberFormatException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void gsonTest3() {
		// テスト準備
		String fraudScoreString = "{\"SCORINGDATE\": \"2020\", \"CLAIMCATEGORY\": \"高\"}";
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();

		// テスト実行
		try {
		FraudScore fraudScore = gson.fromJson(fraudScoreString, FraudScore.class);
		fail();
		} catch ( JsonSyntaxException | NumberFormatException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void gsonTest4() {
		// テスト準備
		String fraudScoreString = "{\"SCORINGDATE\": \"2020-08-18\", \"CLAIMCATEGORY\": \"高\"}";
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();

		// テスト実行
		FraudScore fraudScore = gson.fromJson(fraudScoreString, FraudScore.class);
		System.out.println(gson.toJson(fraudScore));
	}



}
