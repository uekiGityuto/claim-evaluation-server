package jp.co.tokiomarine_nichido.models;

/***
 * ユーザ情報を格納するクラス。
 *
 * @author SKK229783 中山真吾
 *
 */
public class User {
	// IF09から連携される、抜本認証からのユーザID
	public String userId;

	// GNetから連携される権限（0:担当者権限、1:損業権限）
	public int role;
}
