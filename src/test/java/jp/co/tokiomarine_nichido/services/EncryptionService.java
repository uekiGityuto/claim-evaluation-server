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

import org.apache.commons.codec.binary.Hex;

import jp.co.tokiomarine_nichido.util.PropertyManager;

/**
 * データを暗号化するクラス。
 *
 * @author SKK231527 植木宥登
 *
 */
public class EncryptionService {

	public String encrypt(String targetString) throws Exception {

		try {
			PropertyManager pm = new PropertyManager();
			final String KEY = pm.get("decryption.key");
			final String IV = pm.get("decryption.iv");

			// 暗号化対象文字列をバイト配列へ変換
			byte[] targetByte = targetString.getBytes("UTF-8");

			// 秘密鍵キーと初期化ベクトルをバイト配列へ変換
			byte[] byteKey = KEY.getBytes("UTF-8");
			byte[] byteIv = IV.getBytes("UTF-8");

			// 秘密鍵と初期化ベクトルのオブジェクト生成
			SecretKeySpec secretKey = new SecretKeySpec(byteKey, "AES");
			IvParameterSpec params = new IvParameterSpec(byteIv);

			// Cipherオブジェクト生成
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

			// Cipherオブジェクトの初期化
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, params);

			// 暗号化の結果格納
			byte[] encryptedByte = cipher.doFinal(targetByte);

			// 暗号化の結果を16進数文字列へ変換
			String encryptedString = new String(Hex.encodeHex(encryptedByte)).toUpperCase();

			return encryptedString;

		} catch (IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException | NoSuchPaddingException | UnsupportedEncodingException | InvalidKeyException | InvalidAlgorithmParameterException e) {
			e.printStackTrace();
			throw e;
		}

	}
}
