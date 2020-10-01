package jp.co.tokiomarine_nichido.util;

import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

/**
 * プロパティファイルからプロパティを取得。
 *
 * @author SKK229873 中山真吾
 * @author SKK231527 植木宥登
 */
@ApplicationScoped
public class PropertyManager {
	private ResourceBundle bundle = null;
	private ResourceBundle bundleLog = null;

	public PropertyManager() {
		bundle = ResourceBundle.getBundle("config");
		bundleLog = ResourceBundle.getBundle("log");
	}

	@PostConstruct
	public void init() {
		// この時点で宣言するとDAOから取得する時にnullになる現象が発生するため処理をconstructorに移動
		// ここに処理しても2回まわる現象は存在
	}

	/**
	 * プロパティファイルからプロパティを取得。
	 *
	 * @param key プロパティのキー
	 * @return プロパティ値
	 */
	public String get(String key) {
		return bundle.getString(key);
	}

	/**
	 * ログプロパティファイルからログメッセージを取得
	 *
	 * @param key プロパティのキー
	 * @return プロパティの値
	 */
	public String getLogMessage(String key) {
		return bundleLog.getString(key);
	}
}
