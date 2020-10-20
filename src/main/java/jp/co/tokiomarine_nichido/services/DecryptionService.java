package jp.co.tokiomarine_nichido.services;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

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

	@Inject
	private PropertyManager pm;

	/**
	 * @param decryptedString 暗号データ
	 * @return 復号結果
	 * @throws Exception
	 */
	public String decrypt(String encryptedString, String userId) throws Exception {
//		PropertyManager pm = new PropertyManager();
		final String KEY = pm.get("decryption.key");
		final String IV = pm.get("decryption.iv");

		try {
			if (encryptedString.isEmpty()) {
				throw new AuthorizationFailedException();
			}

			// 暗号データ(Hex)をバイト配列に変換
			byte[] encryptedByte = Hex.decodeHex(encryptedString.toCharArray());

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
			// TODO: 全部catchして403エラーにするかは要検討（入力値以外の要因で例外を出す場合もある）
		} catch (AuthorizationFailedException | DecoderException | UnsupportedEncodingException
				| IllegalArgumentException | NullPointerException | NoSuchAlgorithmException
				| NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException
				| IllegalBlockSizeException | BadPaddingException e) {
			throw new AuthorizationFailedException(
					MessageFormat.format(pm.getLogMessage("E002"), userId), e);
		}
	}

}
