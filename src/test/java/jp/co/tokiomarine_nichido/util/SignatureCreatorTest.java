package jp.co.tokiomarine_nichido.util;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.junit.jupiter.api.Test;

import com.google.gson.Gson;

import jp.co.tokiomarine_nichido.models.ScoresReqBody;

class SignatureCreatorTest {

	SignatureCreator creator = new SignatureCreator();

	@Test
	public void uriEncodeTest1() {
		try {
			String encodedPath = creator.uriEncode("https", "/prd/inqiry");
			assertEquals("https:/prd/inqiry", encodedPath);
		} catch (URISyntaxException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	@Test
	public void uriEncodeTest2() {
		try {
			String encodedPath = creator.uriEncode("https", "/documents and settings/").substring(6);
			assertEquals(encodedPath, "/documents%20and%20settings/", "encodedPath:" + encodedPath);
			encodedPath = creator.uriEncode("https", encodedPath).substring(6);
			assertEquals("/documents%2520and%2520settings/", encodedPath);
		} catch (URISyntaxException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	@Test
	public void getXAmzDateTest() {
		String xAmzDate = creator.getXAmzDate();
		System.out.println("xAmzDate:" + xAmzDate);
	}

	@Test
	public void trimSpaceTest1() {
		// テスト準備
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();
		String host = "cmnnnxfwxi.execute-api.ap-northeast-1.amazonaws.com";
		headers.putSingle("host", host);
		String xAmzDate = "20150830T123600Z";
		headers.putSingle("X-Amz-Date", xAmzDate);

		// テスト実行
		List<String> valueList1 = creator.trimSpace("host", headers);
		assertEquals(host, valueList1.get(0));
		List<String> valueList2 = creator.trimSpace("X-Amz-Date", headers);
		assertEquals(xAmzDate, valueList2.get(0));
	}

	@Test
	public void trimSpaceTest2() {
		// テスト準備
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();
		headers.putSingle("Content-Type", "application/json");
		headers.add("Content-Type", "charset=utf-8");

		// テスト実行
		List<String> valueList1 = creator.trimSpace("Content-Type", headers);
		assertEquals("application/json", valueList1.get(0));
		assertEquals("charset=utf-8", valueList1.get(1));
	}

	@Test
	public void trimSpaceTest3() {
		// テスト準備
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();
		headers.putSingle("host", " abc ");

		// テスト実行
		List<String> valueList1 = creator.trimSpace("host", headers);
		assertEquals("abc", valueList1.get(0));
	}

	@Test
	public void trimSpaceTest4() {
		// テスト準備
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();
		headers.putSingle("host", " a  b      c ");

		// テスト実行
		List<String> valueList1 = creator.trimSpace("host", headers);
		assertEquals("a b c", valueList1.get(0));
	}

	@Test
	public void normalizeKeyValueTest1() {
		// テスト準備
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();
		headers.putSingle("host", " a  b      c ");

		// テスト実行
		Map<String, String> normalizedHeaders = creator.normalizeKeyValue(headers);
		assertEquals("a b c", normalizedHeaders.get("host"));
	}

	@Test
	public void normalizeKeyValueTest2() {
		// テスト準備
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();
		headers.putSingle("Host", " a  b      c ");

		// テスト実行
		Map<String, String> normalizedHeaders = creator.normalizeKeyValue(headers);
		assertEquals(null, normalizedHeaders.get("Host"));
		assertEquals("a b c", normalizedHeaders.get("host"));
	}

	@Test
	public void sortHeaderTest1() {
		// テスト準備
		Map<String, String> normalizedHeaders = new HashMap<String, String>();
		String host = "cmnnnxfwxi.execute-api.ap-northeast-1.amazonaws.com";
		normalizedHeaders.put("host", host);
		String xAmzDate = "20150830T123600Z";
		normalizedHeaders.put("x-amz-date", xAmzDate);

		// テスト実行
		List<Map.Entry<String, String>> sortedHeadersList = creator.sortHeader(normalizedHeaders);
		assertEquals("host", sortedHeadersList.get(0).getKey());
		assertEquals("x-amz-date", sortedHeadersList.get(1).getKey());
	}

	@Test
	public void sortHeaderTest2() {
		// テスト準備
		Map<String, String> normalizedHeaders = new HashMap<String, String>();
		String xAmzDate = "20150830T123600Z";
		normalizedHeaders.put("x-amz-date", xAmzDate);
		String host = "cmnnnxfwxi.execute-api.ap-northeast-1.amazonaws.com";
		normalizedHeaders.put("host", host);

		// テスト実行
		List<Map.Entry<String, String>> sortedHeadersList = creator.sortHeader(normalizedHeaders);
		assertEquals("host", sortedHeadersList.get(0).getKey());
		assertEquals("x-amz-date", sortedHeadersList.get(1).getKey());
	}

	@Test
	public void sortHeaderTest3() {
		// テスト準備
		Map<String, String> normalizedHeaders = new HashMap<String, String>();
		normalizedHeaders.put("b", "abc");
		normalizedHeaders.put("a", "abc");
		normalizedHeaders.put("Z", "abc");
		normalizedHeaders.put("A", "abc");

		// テスト実行
		List<Map.Entry<String, String>> sortedHeadersList = creator.sortHeader(normalizedHeaders);
		assertEquals("A", sortedHeadersList.get(0).getKey());
		assertEquals("Z", sortedHeadersList.get(1).getKey());
		assertEquals("a", sortedHeadersList.get(2).getKey());
		assertEquals("b", sortedHeadersList.get(3).getKey());
	}

	@Test
	public void normalizeHeadersTest1() {
		// テスト準備
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();
		String xAmzDate = "20150830T123600Z";
		headers.putSingle("X-Amz-Date", xAmzDate);
		String host = "cmnnnxfwxi.execute-api.ap-northeast-1.amazonaws.com";
		headers.putSingle("host", host);

		// テスト実行
		Map<String, String> canonicalHeadersMap = creator.normalizeHeaders(headers);
		String expectedHeaders[] = { "host", "x-amz-date" };
		int i = 0;
		for (String key : canonicalHeadersMap.keySet()) {
			assertEquals(expectedHeaders[i], key);
			i++;
		}
	}

	@Test
	public void getCanonicalHeadersTest1() {
		// テスト準備
		Map<String, String> canonicalHeadersMap = new LinkedHashMap<>();
		String host = "cmnnnxfwxi.execute-api.ap-northeast-1.amazonaws.com";
		canonicalHeadersMap.put("host", host);
		String xAmzDate = "20150830T123600Z";
		canonicalHeadersMap.put("x-amz-date", xAmzDate);

		// テスト実行
		String canonicalHeaders = creator.getCanonicalHeaders(canonicalHeadersMap);
		String expectedHeaders = "host:" + host + "\n" + "x-amz-date:" + xAmzDate + "\n";
		assertEquals(expectedHeaders, canonicalHeaders);
	}

	@Test
	public void getSignedHeadersTest1() {
		// テスト準備
		Map<String, String> canonicalHeadersMap = new LinkedHashMap<>();
		String host = "cmnnnxfwxi.execute-api.ap-northeast-1.amazonaws.com";
		canonicalHeadersMap.put("host", host);
		String xAmzDate = "20150830T123600Z";
		canonicalHeadersMap.put("x-amz-date", xAmzDate);

		// テスト実行
		String signedHeaders = creator.getSignedHeaders(canonicalHeadersMap);
		assertEquals("host;x-amz-date", signedHeaders);
	}

	@Test
	public void getBodyHashTest() {
		// テスト準備
		// 空文字のハッシュ（AWS公式ドキュメントより引用）
		String expectedSha = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";

		// テスト実行
		String payloadShaHex = creator.getBodyHash("");
		assertEquals(expectedSha, payloadShaHex);
	}

	@Test
	public void getSignatureTest() {
		// テスト準備（AWS公式ドキュメントより引用）
		String expectedSignature = "5d672d79c15b13162d9279b0855cfba6789a8edb4c82c400e06b5924a6f2b5d7";
		String stringToSign = "AWS4-HMAC-SHA256\n" +
				"20150830T123600Z\n" +
				"20150830/us-east-1/iam/aws4_request\n" +
				"f536975d06c0309214f805bb90ccff089219ecd68b2577efef23edd43b7e1a59";
		String secretAccessKey = "wJalrXUtnFEMI/K7MDENG+bPxRfiCYEXAMPLEKEY";
		byte[] signingKey;
		try {
			// テスト実行
			signingKey = creator.getSignatureKey(secretAccessKey, "20150830", "us-east-1", "iam");
			String signature = creator.getSignature(signingKey, stringToSign);
			assertEquals(expectedSignature, signature);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	@Test
	public void getAuthorizationTest() {
		// テスト準備（AWS公式ドキュメントより引用）
		// ヘッダ作成
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();
		String host = "cmnnnxfwxi.execute-api.ap-northeast-1.amazonaws.com";
		headers.putSingle("Host", host);
		String path = "/prd/inqiry";
		// ボディ作成
		ScoresReqBody bodyObj = new ScoresReqBody("AAA123456", "1234567890");
		Gson gson = new Gson();
		String bodyStr = gson.toJson(bodyObj);

		// テスト実行
		SignatureCreator creator = new SignatureCreator();
		try {
			headers = creator.getAuthorization(headers, bodyStr, host, path);
			assertTrue(headers.containsKey("Authorization"));
			assertTrue(headers.containsKey("Host"));
			assertTrue(headers.containsKey("X-Amz-Date"));
			System.out.println("headers:" + headers);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

}
