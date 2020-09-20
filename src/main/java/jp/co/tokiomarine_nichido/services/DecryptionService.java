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

/**
 * 暗号データを復号するクラス。
 *
 * @author SKK231527 植木宥登
 *
 */
@ApplicationScoped
public class DecryptionService {

//	private static final Logger logger = LogManager.getLogger();
	private static final String KEY = "O89JfeVNMR23KuE4";
	private static final String IV = "1Q43Te234IJJDPWE";

	/**
	 * @param decryptedString 暗号データ
	 * @return
	 */
	public String decrypt(String encryptedString) {

		if (encryptedString.isEmpty()) {
//			logger.log(Level.ERROR, "復号エラー" );
			return null;
		}

		// 変数初期化
		String decryptedString = null;

		try {
			// 暗号データ(Hex)をバイト配列に変換
			// TODO DatatypeConverterの使用について中山さんに確認
			// →Java11から、JAXBは標準ライブラリから削除（Java EE には引き続き含まれている）
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
			decryptedString = new String(decryptedByte, "UTF-8");

			// 暗号データ(Hex)をバイト配列に変換時の例外
		} catch (IllegalArgumentException e) {
//			logger.log(Level.ERROR, "復号エラー" );
			e.printStackTrace();
			// 秘密鍵と初期化ベクトルをバイト配列へ変換時の例外
		} catch (UnsupportedEncodingException e) {
//			logger.log(Level.ERROR, "復号エラー" );
			e.printStackTrace();
			// Cipherオブジェクト生成時の例外
		} catch (NoSuchAlgorithmException e) {
//			logger.log(Level.ERROR, "復号エラー" );
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
//			logger.log(Level.ERROR, "復号エラー" );
			e.printStackTrace();
			// Cipherオブジェクトの初期化時の例外
		} catch (InvalidKeyException e) {
//			logger.log(Level.ERROR, "復号エラー" );
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
//			logger.log(Level.ERROR, "復号エラー" );
			e.printStackTrace();
			// 復号結果の格納時の例外
		} catch (IllegalBlockSizeException e) {
//			logger.log(Level.ERROR, "復号エラー" );
			e.printStackTrace();
		} catch (BadPaddingException e) {
//			logger.log(Level.ERROR, "復号エラー" );
			e.printStackTrace();
		}

		return decryptedString;
	}

}
