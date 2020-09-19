package jp.co.tokiomarine_nichido.services;

import org.junit.jupiter.api.Test;

class RestApiClientTest {

	@Test
	void testInquiry1() {
		RestApiClient client = new RestApiClient();
		try {
			client.inquiry();
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

}
