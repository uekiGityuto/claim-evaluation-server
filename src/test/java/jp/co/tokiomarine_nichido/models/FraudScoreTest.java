package jp.co.tokiomarine_nichido.models;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

public class FraudScoreTest {

	/**
	 * 日付が正常な形式のときにオブジェクトに変換出来ることを確認するテスト。
	 */
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

	/**
	 * 日付が空文字の時にエラーになることを確認するテスト。
	 */
	@Test
	public void gsonTest2() {
		// テスト準備
		String fraudScoreString = "{\"SCORINGDATE\": \"\", \"CLAIMCATEGORY\": \"高\"}";
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();

		// テスト実行
		try {
		gson.fromJson(fraudScoreString, FraudScore.class);
		fail();
		} catch ( JsonSyntaxException | NumberFormatException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 日付の形式が異なるときにエラーになることを確認するテスト。
	 */
	@Test
	public void gsonTest3() {
		// テスト準備
		String fraudScoreString = "{\"SCORINGDATE\": \"2020\", \"CLAIMCATEGORY\": \"高\"}";
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();

		// テスト実行
		try {
		gson.fromJson(fraudScoreString, FraudScore.class);
		fail();
		} catch ( JsonSyntaxException | NumberFormatException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 指定の日付形式とは異なるが、日付と見なせる形式のときの挙動確認。
	 */
	@Test
	public void gsonTest4() {
		// テスト準備
		String fraudScoreString = "{\"SCORINGDATE\": \"2020-08-18\", \"CLAIMCATEGORY\": \"高\"}";
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();

		// テスト実行
		FraudScore fraudScore = gson.fromJson(fraudScoreString, FraudScore.class);
		System.out.println(gson.toJson(fraudScore));
	}

	/**
	 * 指定の日付形式とは異なるが、日付と見なせる形式のときの挙動確認。
	 */
	@Test
	public void gsonTest5() {
		// テスト準備
		String fraudScoreString = "{\"SCORINGDATE\": \"2022-04-18 06:58:21.474Z\", \"CLAIMCATEGORY\": \"高\"}";
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();

		// テスト実行
		try {
			FraudScore fraudScore = gson.fromJson(fraudScoreString, FraudScore.class);
			System.out.println(gson.toJson(fraudScore));
			fail();
		} catch ( JsonSyntaxException | NumberFormatException e) {
			e.printStackTrace();
		}

	}


}
