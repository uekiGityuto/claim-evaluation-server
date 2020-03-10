package jp.co.tokiomarine_nichido.services;

import javax.enterprise.context.ApplicationScoped;

import jp.co.tokiomarine_nichido.models.User;

/**
 * 認証、及び認証済みユーザーに関するサービス。
 * 
 * @author SKK229873 中山真吾
 *
 */
@ApplicationScoped
public class SAMLService {

	/**
	 * SAML認証によって取得したユーザ情報を返す。
	 * 
	 * @return
	 */
	public User get() {
		// TODO: 実装する
		return null;
	}
	
}