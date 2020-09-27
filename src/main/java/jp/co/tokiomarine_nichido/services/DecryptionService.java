package jp.co.tokiomarine_nichido.services;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.enterprise.context.ApplicationScoped;
import javax.xml.bind.DatatypeConverter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jp.co.tokiomarine_nichido.exceptions.AuthorizationFailedException;
import jp.co.tokiomarine_nichido.util.PropertyManager;

/**
 * 暗号データを復号するクラス。
 *
 * @author SKK231527 植木宥登
 *
 */
@ApplicationScoped
public class DecryptionService {

	//	@Inject
	//	private PropertyManager pm;

	private static final Logger logger = LogManager.getLogger(DecryptionService.class);

	//	private static final String KEY = "O89JfeVNMR23KuE4";
	//	private static final String IV = "1Q43Te234IJJDPWE";

	/**
	 * @param decryptedString 暗号データ
	 * @return 復号結果
	 * @throws Exception
	 */
	public String decrypt(String encryptedString) throws Exception {
		// TODO: Arquillianを利用したテストが出来るようになればInjectするように変更
		PropertyManager pm = new PropertyManager();
		final String KEY = pm.get("decryption.key");
		final String IV = pm.get("decryption.iv");

		try {
			if (encryptedString.isEmpty()) {
				throw new AuthorizationFailedException();
			}

			// 暗号データ(Hex)をバイト配列に変換
			byte[] encryptedByte = DatatypeConverter.parseHexBinary(encryptedString);

			// 秘密鍵と初期化ベクトルをバイト配列へ変換
			byte[] byteKey = KEY.getBytes("UTF-8");
			byte[] byteIv = IV.getBytes("UTF-8");

			// 秘密鍵と初期化ベクトルのオブジェクト生成
			SecretKeySpec secretKey = new SecretKeySpec(byteKey, "AES");
			IvParameterSpec params = new IvParameterSpec(byteIv);

			// Cipherオブジェクト生成
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

			// Cipherオブジェクトの初期化
			cipher.init(Cipher.DECRYPT_MODE, secretKey, params);

			// 復号結果の格納
			byte[] decryptedByte = cipher.doFinal(encryptedByte);

			// バイト配列を文字列へ変換
			String decryptedString = new String(decryptedByte, "UTF-8");

			return decryptedString;
		} catch (AuthorizationFailedException | IllegalArgumentException | UnsupportedEncodingException
				| NullPointerException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// logger.error(pm.get("E002"), e);
			// TODO: プレースホルダーをセットすると無駄に複雑になる。要相談。
			throw new AuthorizationFailedException(pm.get("E002"));
		}
	}

}
