package jp.co.tokiomarine_nichido.util;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.MultivaluedMap;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * API GateWayでIAM認証するためのヘッダーを付与するためのクラス
 *
 * @author SKK231527 植木宥登
 *
 */
@ApplicationScoped
public class SignatureCreator {
	@Inject
	private PropertyManager pm;

	/**
	 * @param headers
	 * @param body
	 * @param host
	 * @param path
	 * @return headerにX-Amz-DateとAuthorizationを付与して返す
	 * @throws URISyntaxException
	 * @throws IllegalStateException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 * @throws InvalidKeyException
	 */
	public MultivaluedMap<String, Object> getAuthorization(MultivaluedMap<String, Object> headers, String body,
			String host, String path) throws URISyntaxException, InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, IllegalStateException {

		/* 1. 正規リクエスト(CanonicalRequest)の生成 */

		// 正規URI（パスをRFC3986 に従って2回URIエンコードした結果）
		String encodedPath = uriEncode("https", path).substring(6);
		encodedPath = uriEncode("https", encodedPath).substring(6);

		// X-Amz-Date（UTCで「YYYYMMDD'T'HHMMSS'Z'」のISO8601形式）
		String xAmzDate = getXAmzDate();

		// ヘッダーにX-Amz-Dateを追加
		headers.putSingle("X-AMZ-DATE", xAmzDate);

		// 正規ヘッダー
		Map<String, String> canonicalHeadersMap = normalizeHeaders(headers);
		String canonicalHeaders = getCanonicalHeaders(canonicalHeadersMap);

		// 署名付きヘッダー
		String signedHeaders = getSignedHeaders(canonicalHeadersMap);

		// リクエストペイロード（Body部）のハッシュ値
		String payloadShaHex = getBodyHash(body);

		// 正規リクエスト
		String canonicalRequest =
				// HTTPリクエストメソッド
				"POST\n"
						// 正規URI
						+ encodedPath + "\n"
						// 正規クエリ文字列（ない場合は改行のみ）
						+ "\n"
						// 正規ヘッダー（改行含む）
						+ canonicalHeaders
						// 正規ヘッダーの後に改行
						+ "\n"
						// 署名付きヘッダー
						+ signedHeaders + "\n"
						// ハッシュされたペイロード
						+ payloadShaHex;

//		logger.info("正規リクエスト(canonicalRequest):{}", canonicalRequest);

		/* 2. 署名文字列(StringToSign)の生成 */

		// AWS APIリクエスト先のリージョン
		String algorithm = "AWS4-HMAC-SHA256";
		String regionName = "ap-northeast-1";
		// リクエスト先のサービス名
		String serviceName = "execute-api";
		// 認証情報スコープ（aws4_requestは固定文字）
		String credentialScope = xAmzDate.substring(0, 8) + "/" + regionName + "/" + serviceName + "/aws4_request";
		String stringToSign =
				// アルゴリズム
				algorithm + "\n"
				// x-amz-date（ISO8601 基本形式）
						+ xAmzDate + "\n"
						// 認証情報スコープ（aws4_requestは固定文字）
						+ credentialScope + "\n"
						// 正規リクエストのハッシュ
						+ DigestUtils.sha256Hex(canonicalRequest);

//		logger.info("署名文字列(StringToSign):{}", stringToSign);

		/* 3. 署名(signature)の生成 */

		// シークレットアクセスキー
		String secretAccessKey = pm.get("api.secretAccessKey");
		// 固定文字（AWS4）→X-Amz-Date→リージョン→サービス名→固定文字(aws4_request)の順でハッシュ化
		byte[] signingKey = getSignatureKey(secretAccessKey, xAmzDate.substring(0, 8), regionName, serviceName);
		// 署名
		String signature = getSignature(signingKey, stringToSign);

//		logger.info("署名(signature):{}", signature);

		/* 4. Authorizationヘッダー文字列の生成 */
		String accessKeyId = pm.get("api.accessKey");
		String authorization = algorithm + " "
				+ "Credential=" + accessKeyId + "/" + credentialScope + ", "
				+ "SignedHeaders=" + signedHeaders + ", "
				+ "Signature=" + signature;

//		logger.info("Authorizationヘッダー:{}", authorization);

		// ヘッダーにAuthorizationを追加
		headers.putSingle("AUTHORIZATION", authorization);

		return headers;
	}

	/**
	 * @param method
	 * @param path
	 * @return RFC3986でURIエンコードした文字列
	 * @throws URISyntaxException
	 */
	public String uriEncode(String scheme, String path) throws URISyntaxException {
		URI uri = new URI(scheme, path, null);
		return uri.toASCIIString();
	}

	/**
	 * @return 「YYYYMMDD'T'HHMMSS'Z'」形式の日付文字列
	 */
	public String getXAmzDate() {
		OffsetDateTime dateUtc = OffsetDateTime.now(ZoneId.of("UTC"));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'");
		return dateUtc.format(formatter);
	}

	/**
	 * @param headers
	 * @return ヘッダを正規化出来るように変換したmap
	 */
	public LinkedHashMap<String, String> normalizeHeaders(MultivaluedMap<String, Object> headers) {
		// keyを小文字に変換し、valueの前後のスペースと連続スペースを取り除く
		Map<String, String> normalizedHeaders = normalizeKeyValue(headers);

		// keyの文字コード順でsort
		List<Map.Entry<String, String>> sortedHeadersList = sortHeader(normalizedHeaders);

		// 変換したヘッダーをmapで返す
		LinkedHashMap<String, String> canonicalHeadersMap = new LinkedHashMap<>();
		for (Map.Entry<String, String> sortedHeaders : sortedHeadersList) {
			canonicalHeadersMap.put(sortedHeaders.getKey(), sortedHeaders.getValue());
		}

		return canonicalHeadersMap;
	}

	/**
	 * @param headers
	 * @return 小文字に変換したkeyと前後のスペースと連続スペースを取り除いたvalueのmap
	 */
	public Map<String, String> normalizeKeyValue(MultivaluedMap<String, Object> headers) {
		Map<String, String> normalizedHeaders = new HashMap<String, String>();
		for (String key : headers.keySet()) {
			// valueの前後のスペースと連続スペースを取り除く
			List<String> valueList = trimSpace(key, headers);
			// keyを小文字に変換して新しいmapにプット
			normalizedHeaders.put(key.toLowerCase(), String.join(" ;", valueList));
		}
		return normalizedHeaders;
	}

	/**
	 * @param key
	 * @param headers
	 * @return 前後のスペースと連続スペースを取り除いたヘッダー値
	 */
	public List<String> trimSpace(String key, MultivaluedMap<String, Object> headers) {
		List<String> valueList = new ArrayList<>();
		for (Object value : headers.get(key)) {
			// 前後の半角スペースを削除
			String trimedValue = value.toString().trim();
			// 連続した半角スペースを単一の半角スペースに変換
			trimedValue = trimedValue.toString().replaceAll(" {2,}", " ");
			valueList.add(trimedValue);
		}
		return valueList;
	}

	/**
	 * @param normalizedHeaders
	 * @return keyの文字コード順でsortしたリスト
	 */
	public List<Map.Entry<String, String>> sortHeader(Map<String, String> normalizedHeaders) {
		List<Map.Entry<String, String>> sortedHeadersList = new LinkedList<>(normalizedHeaders.entrySet());
		Collections.sort(sortedHeadersList, (a, b) -> a.getKey().compareTo(b.getKey()));
		return sortedHeadersList;
	}

	/**
	 * @param canonicalHeadersMap
	 * @return 正規ヘッダー（「key:value\n」をkeyの数だけ連結した文字列）
	 */
	public String getCanonicalHeaders(Map<String, String> canonicalHeadersMap) {
		String canonicalHeaders = "";
		for(String key : canonicalHeadersMap.keySet()) {
			canonicalHeaders += key + ":" + canonicalHeadersMap.get(key) + "\n";
		}
		return canonicalHeaders;
	}

	/**
	 * @param canonicalHeadersMap
	 * @return 署名付きヘッダー（keyを;で連結した文字列）
	 */
	public String getSignedHeaders(Map<String, String> canonicalHeadersMap) {
		List<String> keyList = new ArrayList<>();
		for (String key : canonicalHeadersMap.keySet()) {
			keyList.add(key);
		}
		String signedHeaders = String.join(";", keyList);
		return signedHeaders;
	}

	/**
	 * @param param
	 * @return SHA256でハッシュし、小文字の16進文字列にエンコードした文字列
	 */
	public String getBodyHash(String param) {
		return DigestUtils.sha256Hex(param).toLowerCase();
	}

	/**
	 * @param key
	 * @param xAmzDate
	 * @param regionName
	 * @param serviceName
	 * @return 署名キー
	 * @throws UnsupportedEncodingException
	 * @throws IllegalStateException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 */
	public byte[] getSignatureKey(String key, String xAmzDate, String regionName, String serviceName) throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, IllegalStateException {
		byte[] kSecret = ("AWS4" + key).getBytes("UTF-8");
		byte[] kDate = hmacSHA256(xAmzDate, kSecret);
		byte[] kRegion = hmacSHA256(regionName, kDate);
		byte[] kService = hmacSHA256(serviceName, kRegion);
		return hmacSHA256("aws4_request", kService);
	}

	/**
	 * @param data
	 * @param key
	 * @return dataをkeyでHMAC-SHA-256にハッシュ化した値
	 * @throws InvalidKeyException
	 * @throws UnsupportedEncodingException
	 * @throws IllegalStateException
	 * @throws NoSuchAlgorithmException
	 */
	public byte[] hmacSHA256(String data, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException, IllegalStateException, UnsupportedEncodingException {
		String algorithm = "HmacSHA256";
		Mac mac = Mac.getInstance(algorithm);
		mac.init(new SecretKeySpec(key, algorithm));
		return mac.doFinal(data.getBytes("UTF-8"));
	}

	/**
	 * @param signatureKey
	 * @param stringToSign
	 * @return 署名（HMAC-SHA-256でハッシュ化した結果を小文字の16進文字列にエンコードした文字列）
	 * @throws UnsupportedEncodingException
	 * @throws IllegalStateException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws Exception
	 */
	public String getSignature(byte[] signatureKey, String stringToSign) throws InvalidKeyException, NoSuchAlgorithmException, IllegalStateException, UnsupportedEncodingException {
		byte[] signature = hmacSHA256(stringToSign, signatureKey);
		return DatatypeConverter.printHexBinary(signature).toLowerCase();
	}

}
