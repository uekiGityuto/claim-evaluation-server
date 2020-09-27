package jp.co.tokiomarine_nichido.models;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.Test;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import jp.co.tokiomarine_nichido.models.scores.Reason;

class ReasonTest {

	@Test
	void gsonTest1() {
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

	@Test
	void validateTest1() {
		// テスト準備
        // Validator取得
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

		// Validate対象Bean作成
		String reasonString = "{\"REASON\": \"50\", \"FEATURENAME\": \"特徴量1\", \"FEATUREDESCRIPTION\": \"説明1\"}";
		Gson gson = new Gson();
		Reason reason = gson.fromJson(reasonString, Reason.class);

		// テスト実行
		// バリデーション実行
		Set<ConstraintViolation<Reason>> validationResult = validator.validate(reason);
		assertEquals(validationResult.size(), 0);

	}

	@Test
	void validateTest2() {
		// テスト準備
        // Validator取得
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

		// Validate対象Bean作成
		String reasonString = "{\"REASON\":null , \"FEATURENAME\": \"特徴量1\", \"FEATUREDESCRIPTION\": \"説明1\"}";
		Gson gson = new Gson();
		Reason reason = gson.fromJson(reasonString, Reason.class);

		// テスト実行
		// バリデーション実行
		Set<ConstraintViolation<Reason>> validationResult = validator.validate(reason);
		System.out.println("スコア:" + reason.reason);
		assertEquals(validationResult.size(), 1);

	}

}
