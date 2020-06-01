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
 * @add SKK231099 李 : sql.properties
 */
@ApplicationScoped
public class PropertyManager {
	private ResourceBundle bundle = null;
	private ResourceBundle bundleSql = null;
	private static final Logger logger = LogManager.getLogger();

	public PropertyManager() {
        bundle = ResourceBundle.getBundle("config");
        bundleSql = ResourceBundle.getBundle("sql");
        logger.info("config.propertiesから設定値を読み込み。");
        for (String key : bundle.keySet()) {
            logger.info("          {}:{}", key, bundle.getObject(key));
        }
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
	 * get SQL from properties
	 * @param key
	 * @return
	 */
	public String getSql(String key) {
	    return bundleSql.getString(key);
	}
}
