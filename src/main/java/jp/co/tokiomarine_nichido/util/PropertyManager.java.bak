package jp.co.tokiomarine_nichido.util;

import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * プロパティファイルからプロパティを取得。
 * 
 * @author SKK229873 中山真吾
 *
 */
@ApplicationScoped
public class PropertyManager {
	private ResourceBundle bundle = null;
	private static final Logger logger = LogManager.getLogger();

	public PropertyManager() {
	}
	
	@PostConstruct
	public void init() {
		bundle = ResourceBundle.getBundle("config");
		
		logger.info("config.propertiesから設定値を読み込み。");
		for (String key : bundle.keySet()) {
			logger.info("          {}:{}", key, bundle.getObject(key));
		}
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
}
