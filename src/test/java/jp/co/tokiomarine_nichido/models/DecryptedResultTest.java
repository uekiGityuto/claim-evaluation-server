package jp.co.tokiomarine_nichido.models;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Test;

import com.google.gson.Gson;

import jp.co.tokiomarine_nichido.exceptions.AuthorizationFailedException;

public class DecryptedResultTest {

	@Test
	public void testIsAuthority1() {
		DecryptedResult result = new DecryptedResult("1", "1234567890", "2020-08-18T13:11:43.887Z");
		assertTrue(result.isAuthority());
	}

	@Test
	public void testIsAuthority2() {
		DecryptedResult result = new DecryptedResult("0", "1234567890", "2020-08-18T13:11:43.887Z");
		assertTrue(!(result.isAuthority()));
	}

	/**
	 * 生成時刻チェック
	 * 正常系
	 */
	@Test
	public void testIsCorrectDate1() {
		String createDate = Instant.now().toString();
//		System.out.println(createDate);
		DecryptedResult result = new DecryptedResult("0", "1234567890", createDate);
		try {
			assertTrue(result.isCorrectDate());
		} catch (AuthorizationFailedException e) {
			e.printStackTrace();
			fail();
		}
	}


	/**
	 * 生成時刻チェック
	 * 正常系（境界値）※比較までにラグがあるので10秒ぴったりは出来ない
	 */
	@Test
	public void testIsCorrectDate2() {
		String createDate = Instant.now().minusSeconds(9).toString();
		DecryptedResult result = new DecryptedResult("0", "1234567890", createDate);
		try {
			assertTrue(result.isCorrectDate());
		} catch (AuthorizationFailedException e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * 生成時刻チェック
	 * 異常系（境界値）
	 */
	@Test
	public void testIsCorrectDate3() {
		String createDate = Instant.now().minusSeconds(11).toString();
		DecryptedResult result = new DecryptedResult("0", "1234567890", createDate);
		try {
			System.out.println(result.isCorrectDate());
			fail();
		} catch (AuthorizationFailedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCreateAuthResult() {
		DecryptedResult result = new DecryptedResult("0", "1234567890", "2020-08-18T13:11:43.887Z");
		AuthorizationResult authResult = result.createAuthorizationResult("aaa");
		System.out.println(authResult);
	}

	/**
	 * Validationテスト
	 * 正常系
	 */
	@Test
	public void validationTest1() {
		// テスト準備
        // Validatorを取得
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

		// Validate対象Bean作成
		String decryptedString = "{\"Authflag\": \"1\", \"ClaimNo\": \"1234567890\", \"CreateDate\": \"2020-08-18T13:11:43.887Z\"}";
		Gson gson = new Gson();
		DecryptedResult decryptedResult = gson.fromJson(decryptedString, DecryptedResult.class);

		// テスト実行
		// バリデーションを実行
		Set<ConstraintViolation<DecryptedResult>> validationResult = validator.validate(decryptedResult);
		assertEquals(validationResult.size(), 0);

	}

	/**
	 * Validationテスト
	 * 異常系
	 */
	@Test
	public void validationTest2() {
		// テスト準備
        // Validatorを取得
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

		// Validate対象Bean作成
		String decryptedString = "{\"Authflag\": \"1\", \"CreateDate\": \"2020-08-18T13:11:43.887Z\"}";
		Gson gson = new Gson();
		DecryptedResult decryptedResult = gson.fromJson(decryptedString, DecryptedResult.class);

		// テスト実行
		// バリデーションを実行
		Set<ConstraintViolation<DecryptedResult>> validationResult = validator.validate(decryptedResult);
		assertEquals(validationResult.size(), 1);
	}

}
