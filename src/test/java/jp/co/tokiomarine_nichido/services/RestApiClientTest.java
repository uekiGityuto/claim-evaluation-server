package jp.co.tokiomarine_nichido.services;

import org.junit.jupiter.api.Test;

import com.google.gson.Gson;

import jp.co.tokiomarine_nichido.models.ScoresReqBody;

class RestApiClientTest {

	@Test
	void testInquiry1() {
		// テスト準備
		String path = "/prd/inqiry";
		ScoresReqBody bodyObj = new ScoresReqBody("AAA123456", "1234567890");
		Gson gson = new Gson();
		String bodyStr = gson.toJson(bodyObj);

		// テスト実施
		RestApiClient client = new RestApiClient();
		try {
			String response = client.inquire(path, bodyStr);
			System.out.println("response:" + response);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

}
