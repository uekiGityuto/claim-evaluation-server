package jp.co.tokiomarine_nichido.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DecryptionServiceTest {

//	@Inject
//	private DecryptionService decryptionService;

	// 復号処理（正常系テスト）
	@Test
	void testDecrypt1() {
		DecryptionService decryptionService = new DecryptionService();
		String encryptedString = "14DE3FE9D8A4B354C1D6EF0984491E6558E00D31D55D4B51AB0FD6083E34B33AA71354E7EE182ED7BCA37C2736AA5C1E15FB37131F20EC02C988176E0438EFE2D20B21B268D9D7120A8A32E56C19E64E";
		String decryptedString = decryptionService.decrypt(encryptedString);
//		System.out.println(decryptedString);
		assertTrue(decryptedString.length() > 0);
		assertEquals(decryptedString, "{\"Authflag\":\"1\",\"ClaimNo\":\"1234567890\",\"CreateDate\":\"2020-09-11T13:21:52.052Z\"}");
	}

	// 復号処理（空文字テスト）
	@Test
	void testDecrypt2() {
		DecryptionService decryptionService = new DecryptionService();
		String encryptedString = "";
		String decryptedString = decryptionService.decrypt(encryptedString);
		assertEquals(decryptedString, null);
	}


}