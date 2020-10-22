package jp.co.tokiomarine_nichido.util;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.junit.Ignore;
import org.junit.Test;

public class SignatureCreatorTest {

	SignatureCreator creator = new SignatureCreator();

	/**
	 * uriEncodeメソッドの機能確認。
	 * テスト観点：正常な引数が与えられた場合に正常に処理出来ること。
	 *
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@Test
	public void uriEncodeTest1() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
			// テスト準備
			Method method = SignatureCreator.class.getDeclaredMethod("uriEncode", String.class, String.class);
			method.setAccessible(true);

			// テスト実行
			String encodedPath = (String) method.invoke(creator, "https", "/prd/inqiry");
			assertEquals("https:/prd/inqiry", encodedPath);
	}

	/**
	 * uriEncodeメソッドの機能確認。
	 * テスト観点：uriEncodeメソッドでエンコードした結果をさらにuriEncodeメソッドでエンコード出来ること。
	 *
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws URISyntaxException
	 */
	@Test
	public void uriEncodeTest2() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, URISyntaxException {
		// テスト準備
		Method method = SignatureCreator.class.getDeclaredMethod("uriEncode", String.class, String.class);
		method.setAccessible(true);

		// テスト実行
		String encodedPath = (String) method.invoke(creator, "https", "/documents and settings/");
		encodedPath = encodedPath.substring(6);
		assertEquals("/documents%20and%20settings/", encodedPath);
		encodedPath = (String) method.invoke(creator, "https", encodedPath);
		encodedPath = encodedPath.substring(6);
		assertEquals("/documents%2520and%2520settings/", encodedPath);
	}

	/**
	 * getXAmzDateメソッドの機能確認。
	 * テスト観点：正常な引数が与えられた場合に正常に処理出来ること。
	 *
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	@Test
	public void getXAmzDateTest1() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		// テスト準備
		Method method = SignatureCreator.class.getDeclaredMethod("getXAmzDate");
		method.setAccessible(true);

		// テスト実行
		String xAmzDate = (String) method.invoke(creator);
		System.out.println("xAmzDate:" + xAmzDate);
	}

	/**
	 * trimSpaceメソッドの機能確認。
	 * テスト観点：トリミング対象がない場合に正常に処理出来ること。
	 *
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void trimSpaceTest1() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		// テスト準備
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();
		String host = "cmnnnxfwxi.execute-api.ap-northeast-1.amazonaws.com";
		headers.putSingle("host", host);
		String xAmzDate = "20150830T123600Z";
		headers.putSingle("X-Amz-Date", xAmzDate);

		Method method = SignatureCreator.class.getDeclaredMethod("trimSpace", String.class, MultivaluedMap.class);
		method.setAccessible(true);

		// テスト実行
		List<String> valueList1 = (List<String>) method.invoke(creator, "host", headers);
		assertEquals(host, valueList1.get(0));
		List<String> valueList2 = (List<String>) method.invoke(creator, "X-Amz-Date", headers);
		assertEquals(xAmzDate, valueList2.get(0));
	}

	/**
	 * trimSpaceメソッドの機能確認。
	 * テスト観点：1つのkeyに対して、2つのvalueをもつ場合に正常に処理出来ること。
	 *
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void trimSpaceTest2() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		// テスト準備
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();
		headers.putSingle("Content-Type", "application/json");
		headers.add("Content-Type", "charset=utf-8");

		Method method = SignatureCreator.class.getDeclaredMethod("trimSpace", String.class, MultivaluedMap.class);
		method.setAccessible(true);

		// テスト実行
		List<String> valueList = (List<String>) method.invoke(creator, "Content-Type", headers);
		assertEquals("application/json", valueList.get(0));
		assertEquals("charset=utf-8", valueList.get(1));
	}

	/**
	 * trimSpaceメソッドの機能確認。
	 * テスト観点：前後の半角スペースがトリミングされること。
	 *
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void trimSpaceTest3() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		// テスト準備
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();
		headers.putSingle("host", " abc ");

		Method method = SignatureCreator.class.getDeclaredMethod("trimSpace", String.class, MultivaluedMap.class);
		method.setAccessible(true);

		// テスト実行
		List<String> valueList = (List<String>) method.invoke(creator, "host", headers);
		assertEquals("abc", valueList.get(0));
	}

	/**
	 * trimSpaceメソッドの機能確認。
	 * テスト観点：文字列と文字列の間の2つ以上の半角スペースが1つにトリミングされること。
	 *
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void trimSpaceTest4() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		// テスト準備
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();
		headers.putSingle("host", " a  b      c ");

		Method method = SignatureCreator.class.getDeclaredMethod("trimSpace", String.class, MultivaluedMap.class);
		method.setAccessible(true);

		// テスト実行
		List<String> valueList = (List<String>) method.invoke(creator, "host", headers);
		assertEquals("a b c", valueList.get(0));
	}

	/**
	 * normalizeKeyValueメソッドの機能確認。
	 * テスト観点：keyが小文字の場合は、keyの変換はされないこと。
	 *
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void normalizeKeyValueTest1() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		// テスト準備
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();
		headers.putSingle("host", " a  b      c ");

		Method method = SignatureCreator.class.getDeclaredMethod("normalizeKeyValue", MultivaluedMap.class);
		method.setAccessible(true);

		// テスト実行
		Map<String, String> normalizedHeaders = (Map<String, String>) method.invoke(creator, headers);
		assertEquals("a b c", normalizedHeaders.get("host"));
	}

	/**
	 * normalizeKeyValueメソッドの機能確認。
	 * テスト観点：keyが大文字の場合は小文字に変換されること。
	 *
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void normalizeKeyValueTest2() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		// テスト準備
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();
		headers.putSingle("Host", " a  b      c ");

		Method method = SignatureCreator.class.getDeclaredMethod("normalizeKeyValue", MultivaluedMap.class);
		method.setAccessible(true);

		// テスト実行
		Map<String, String> normalizedHeaders = (Map<String, String>) method.invoke(creator, headers);
		assertEquals(null, normalizedHeaders.get("Host"));
		assertEquals("a b c", normalizedHeaders.get("host"));
	}

	/**
	 * sortHeaderメソッドの機能確認。
	 * テスト観点：文字コード順にソートされること。（既に正しくソートされている場合）
	 *
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void sortHeaderTest1() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		// テスト準備
		Map<String, String> normalizedHeaders = new HashMap<String, String>();
		String host = "cmnnnxfwxi.execute-api.ap-northeast-1.amazonaws.com";
		normalizedHeaders.put("host", host);
		String xAmzDate = "20150830T123600Z";
		normalizedHeaders.put("x-amz-date", xAmzDate);

		Method method = SignatureCreator.class.getDeclaredMethod("sortHeader", Map.class);
		method.setAccessible(true);

		// テスト実行
		List<Map.Entry<String, String>> sortedHeadersList =
				(List<Map.Entry<String, String>>) method.invoke(creator, normalizedHeaders);
		assertEquals("host", sortedHeadersList.get(0).getKey());
		assertEquals("x-amz-date", sortedHeadersList.get(1).getKey());
	}

	/**
	 * sortHeaderメソッドの機能確認。
	 * テスト観点：文字コード順にソートされること。
	 *
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void sortHeaderTest2() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		// テスト準備
		Map<String, String> normalizedHeaders = new HashMap<String, String>();
		String xAmzDate = "20150830T123600Z";
		normalizedHeaders.put("x-amz-date", xAmzDate);
		String host = "cmnnnxfwxi.execute-api.ap-northeast-1.amazonaws.com";
		normalizedHeaders.put("host", host);

		Method method = SignatureCreator.class.getDeclaredMethod("sortHeader", Map.class);
		method.setAccessible(true);

		// テスト実行
		List<Map.Entry<String, String>> sortedHeadersList =
				(List<Map.Entry<String, String>>) method.invoke(creator, normalizedHeaders);
		assertEquals("host", sortedHeadersList.get(0).getKey());
		assertEquals("x-amz-date", sortedHeadersList.get(1).getKey());
	}

	/**
	 * sortHeaderメソッドの機能確認。
	 * テスト観点：文字コード順にソートされること。（小文字大文字が混ざっている場合）
	 *
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void sortHeaderTest3() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		// テスト準備
		Map<String, String> normalizedHeaders = new HashMap<String, String>();
		normalizedHeaders.put("b", "abc");
		normalizedHeaders.put("a", "abc");
		normalizedHeaders.put("Z", "abc");
		normalizedHeaders.put("A", "abc");

		Method method = SignatureCreator.class.getDeclaredMethod("sortHeader", Map.class);
		method.setAccessible(true);

		// テスト実行
		List<Map.Entry<String, String>> sortedHeadersList =
				(List<Map.Entry<String, String>>) method.invoke(creator, normalizedHeaders);
		assertEquals("A", sortedHeadersList.get(0).getKey());
		assertEquals("Z", sortedHeadersList.get(1).getKey());
		assertEquals("a", sortedHeadersList.get(2).getKey());
		assertEquals("b", sortedHeadersList.get(3).getKey());
	}

	/**
	 * normalizeHeadersメソッドの機能確認。
	 * テスト観点：正常な引数が与えられた場合に正常に処理出来ること。
	 *
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void normalizeHeadersTest1() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		// テスト準備
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();
		String xAmzDate = "20150830T123600Z";
		headers.putSingle("X-Amz-Date", xAmzDate);
		String host = "cmnnnxfwxi.execute-api.ap-northeast-1.amazonaws.com";
		headers.putSingle("host", host);

		Method method = SignatureCreator.class.getDeclaredMethod("normalizeHeaders", MultivaluedMap.class);
		method.setAccessible(true);

		// テスト実行
		Map<String, String> canonicalHeadersMap = (Map<String, String>) method.invoke(creator, headers);
		String expectedHeaders[] = { "host", "x-amz-date" };
		int i = 0;
		for (String key : canonicalHeadersMap.keySet()) {
			assertEquals(expectedHeaders[i], key);
			i++;
		}
	}

	/**
	 * getCanonicalHeadersメソッドの機能確認。
	 * テスト観点：正常な引数が与えられた場合に正常に処理出来ること。
	 *
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	@Test
	public void getCanonicalHeadersTest1() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		// テスト準備
		Map<String, String> canonicalHeadersMap = new LinkedHashMap<>();
		String host = "cmnnnxfwxi.execute-api.ap-northeast-1.amazonaws.com";
		canonicalHeadersMap.put("host", host);
		String xAmzDate = "20150830T123600Z";
		canonicalHeadersMap.put("x-amz-date", xAmzDate);

		Method method = SignatureCreator.class.getDeclaredMethod("getCanonicalHeaders", Map.class);
		method.setAccessible(true);

		// テスト実行
		String canonicalHeaders = (String) method.invoke(creator, canonicalHeadersMap);
		String expectedHeaders = "host:" + host + "\n" + "x-amz-date:" + xAmzDate + "\n";
		assertEquals(expectedHeaders, canonicalHeaders);
	}

	/**
	 * getSignedHeadersメソッドの機能確認。
	 * テスト観点：正常な引数が与えられた場合に正常に処理出来ること。
	 *
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	@Test
	public void getSignedHeadersTest1() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		// テスト準備
		Map<String, String> canonicalHeadersMap = new LinkedHashMap<>();
		String host = "cmnnnxfwxi.execute-api.ap-northeast-1.amazonaws.com";
		canonicalHeadersMap.put("host", host);
		String xAmzDate = "20150830T123600Z";
		canonicalHeadersMap.put("x-amz-date", xAmzDate);

		Method method = SignatureCreator.class.getDeclaredMethod("getSignedHeaders", Map.class);
		method.setAccessible(true);

		// テスト実行
		String signedHeaders = (String) method.invoke(creator, canonicalHeadersMap);
		assertEquals("host;x-amz-date", signedHeaders);
	}

	/**
	 * getBodyHashメソッドの機能確認。
	 * テスト観点：正常な引数が与えられた場合に正常に処理出来ること。
	 *
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	@Test
	public void getBodyHashTest() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		// テスト準備
		// 空文字のハッシュ（AWS公式ドキュメントより引用）
		String expectedSha = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";

		Method method = SignatureCreator.class.getDeclaredMethod("getBodyHash", String.class);
		method.setAccessible(true);

		// テスト実行
		String payloadShaHex = (String) method.invoke(creator, "");
		assertEquals(expectedSha, payloadShaHex);
	}

	@Ignore
	@Test
	public void getSignatureTest() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		// テスト準備（AWS公式ドキュメントより引用）
		String expectedSignature = "5d672d79c15b13162d9279b0855cfba6789a8edb4c82c400e06b5924a6f2b5d7";
		String stringToSign = "AWS4-HMAC-SHA256\n" +
				"20150830T123600Z\n" +
				"20150830/us-east-1/iam/aws4_request\n" +
				"f536975d06c0309214f805bb90ccff089219ecd68b2577efef23edd43b7e1a59";
		String secretAccessKey = "wJalrXUtnFEMI/K7MDENG+bPxRfiCYEXAMPLEKEY";
		byte[] signingKey;

		Method getKeyMethod = SignatureCreator.class
				.getDeclaredMethod("getSignatureKey", String.class, String.class, String.class, String.class);
		getKeyMethod.setAccessible(true);
		Method getSigMethod = SignatureCreator.class
				.getDeclaredMethod("getSignature", byte[].class, String.class);
		getSigMethod.setAccessible(true);

		// テスト実行
		signingKey = (byte[]) getKeyMethod.invoke(creator, secretAccessKey, "20150830", "us-east-1", "iam");
		String signature = (String) getSigMethod.invoke(creator, signingKey, stringToSign);
		assertEquals(expectedSignature, signature);
	}

	//	@Test
	//	public void getAuthorizationTest() {
	//		// テスト準備（AWS公式ドキュメントより引用）
	//		// ヘッダ作成
	//		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();
	//		String host = "cmnnnxfwxi.execute-api.ap-northeast-1.amazonaws.com";
	//		headers.putSingle("Host", host);
	//		String path = "/prd/inqiry";
	//		// ボディ作成
	//		ScoresReqBody bodyObj = new ScoresReqBody("AAA123456", "1234567890");
	//		Gson gson = new Gson();
	//		String bodyStr = gson.toJson(bodyObj);
	//
	//		// テスト実行
	//		SignatureCreator creator = new SignatureCreator();
	//		try {
	//			headers = creator.getAuthorization(headers, bodyStr, host, path);
	//			assertTrue(headers.containsKey("Authorization"));
	//			assertTrue(headers.containsKey("Host"));
	//			assertTrue(headers.containsKey("X-Amz-Date"));
	//			System.out.println("headers:" + headers);
	//		} catch (Exception e) {
	//			e.printStackTrace();
	//		}
	//	}

}
