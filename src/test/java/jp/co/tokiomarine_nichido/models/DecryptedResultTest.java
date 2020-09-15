package jp.co.tokiomarine_nichido.models;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;

import org.junit.jupiter.api.Test;

class DecryptedResultTest {

	@Test
	void testIsAuthority1() {
		DecryptedResult result = new DecryptedResult("1", "1234567890", "2020-08-18T13:11:43.887Z");
		assertTrue(result.isAuthority());
	}

	@Test
	void testIsAuthority2() {
		DecryptedResult result = new DecryptedResult("0", "1234567890", "2020-08-18T13:11:43.887Z");
		assertTrue(!(result.isAuthority()));
	}

	@Test
	void testIsCorrectDate1() {
		String createDate = Instant.now().toString();
//		System.out.println(createDate);
		DecryptedResult result = new DecryptedResult("0", "1234567890", createDate);
		assertTrue(result.isCorrectDate());
	}


	// 正常系（境界値）※比較までにラグがあるので10秒ぴったりは出来ない
	@Test
	void testIsCorrectDate2() {
		String createDate = Instant.now().minusSeconds(9).toString();
		DecryptedResult result = new DecryptedResult("0", "1234567890", createDate);
		assertTrue(result.isCorrectDate());
	}

	// 異常系（境界値）
	@Test
	void testIsCorrectDate3() {
		String createDate = Instant.now().minusSeconds(11).toString();
		DecryptedResult result = new DecryptedResult("0", "1234567890", createDate);
		assertTrue(!(result.isCorrectDate()));
	}

	@Test
	void testCreateAuthResult() {
		DecryptedResult result = new DecryptedResult("0", "1234567890", "2020-08-18T13:11:43.887Z");
		AuthResult authResult = result.createAuthResult("aaa");
		System.out.println(authResult);
	}

}
