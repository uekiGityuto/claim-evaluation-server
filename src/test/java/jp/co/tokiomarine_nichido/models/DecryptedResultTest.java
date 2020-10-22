package jp.co.tokiomarine_nichido.models;

import static org.junit.Assert.*;

import java.time.Instant;

import org.junit.Test;

import jp.co.tokiomarine_nichido.exceptions.AuthorizationFailedException;

public class DecryptedResultTest {

	/**
	 * isAuthorityメソッドの機能確認。
	 * テスト観点：損業権限フラグが1の場合にtrueになること。
	 */
	@Test
	public void testIsAuthority1() {
		DecryptedResult result = new DecryptedResult("1", "1234567890", "2020-08-18T13:11:43.887Z");
		assertTrue(result.isAuthority());
	}

	/**
	 * isAuthorityメソッドの機能確認。
	 * テスト観点：損業権限フラグが0の場合にfalseになること。
	 */
	@Test
	public void testIsAuthority2() {
		DecryptedResult result = new DecryptedResult("0", "1234567890", "2020-08-18T13:11:43.887Z");
		assertTrue(!(result.isAuthority()));
	}

	/**
	 * isCorrectDateメソッドの機能確認。
	 * テスト観点：生成時刻が現在時刻の10秒以内の場合にTrueになること。
	 */
	@Test
	public void testIsCorrectDate1() {
		String createDate = Instant.now().toString();
		DecryptedResult result = new DecryptedResult("0", "1234567890", createDate);
		try {
			assertTrue(result.isCorrectDate());
		} catch (AuthorizationFailedException e) {
			e.printStackTrace();
			fail();
		}
	}


	/**
	 * isCorrectDateメソッドの機能確認。
	 * テスト観点：生成時刻が現在時刻より9秒早い場合にTrueになること。
	 * （境界値は10秒だが、10秒ぴったりのテストは難しいため9秒でのテストを実施）
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
	 * isCorrectDateメソッドの機能確認。
	 * テスト観点：生成時刻が現在時刻より11秒早い場合にfalseになること。
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

	/**
	 * createAuthorizationResultメソッドの機能確認。
	 * テスト観点：AuthorizationResultが生成出来ること。
	 */
	@Test
	public void testCreateAuthResult() {
		DecryptedResult result = new DecryptedResult("0", "1234567890", "2020-08-18T13:11:43.887Z");
		AuthorizationResult authResult = result.createAuthorizationResult("aaa");
		System.out.println(authResult);
	}

}
