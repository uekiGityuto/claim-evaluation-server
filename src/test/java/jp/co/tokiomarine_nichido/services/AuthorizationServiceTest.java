package jp.co.tokiomarine_nichido.services;

import static org.junit.Assert.*;

import java.time.Instant;

import org.junit.Test;

// TODO: CDIを使用したテストを出来るようにする（Arquillian）
//@RunWith(Arquillian.class)
public class AuthorizationServiceTest {

	@Test
	public void encryptTest() {
		// テスト準備
		EncryptionService encryptionService = new EncryptionService();
		String targetString = "{\"Authflag\":\"1\",\"ClaimNo\":\"1234567890\",\"CreateDate\":\"2020-09-11T13:21:52.052Z\"}";
		String expectedParam = "14DE3FE9D8A4B354C1D6EF0984491E6558E00D31D55D4B51AB0FD6083E34B33AA71354E7EE182ED7BCA37C2736AA5C1E15FB37131F20EC02C988176E0438EFE2D20B21B268D9D7120A8A32E56C19E64E";

		// テスト実施
		try {
			String encryptedParam = encryptionService.encrypt(targetString);
			assertEquals(expectedParam, encryptedParam);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void authorizeTest() {
		// テスト準備
		AuthorizationService authorizeService = new AuthorizationService();
		String userId = "AAA123456";
		String authFlag = "1";
		String claimNumber = "1234567890";
		String createDate = Instant.now().toString();
		String targetString = "{\"Authflag\":\"" + authFlag + "\",\"ClaimNo\":\"" + claimNumber + "\",\"CreateDate\":\""
				+ createDate + "\"}";
		System.out.println(targetString);

		EncryptionService encryptionService = new EncryptionService();
		try {
			String encryptedParam = encryptionService.encrypt(targetString);
			// authorizeService.authorize(encryptedParam, userId, request);
		} catch (Exception e) {
			fail();
		}

	}

}
