package jp.co.tokiomarine_nichido.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.PropertyResourceBundle;
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

	private static ResourceBundle.Control UTF8_ENCODING_CONTROL = new ResourceBundle.Control() {

		/**
         * UTF-8エンコーディングのプロパティファイルからResourceBundleオブジェクトを生成。
         * 
         * @throws IllegalAccessException
         * @throws InstantiationException
         * @throws IOException
		 */
		@Override
        public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload)
                throws IllegalAccessException, InstantiationException, IOException {
            String bundleName = toBundleName(baseName, locale);
            String resourceName = toResourceName(bundleName, "properties");

            try (InputStream is = loader.getResourceAsStream(resourceName);
                 InputStreamReader isr = new InputStreamReader(is, "UTF-8");
                 BufferedReader reader = new BufferedReader(isr)) {
                return new PropertyResourceBundle(reader);
            }
        }
    };

	private ResourceBundle bundle = null;
	private ResourceBundle bundleLog = null;

	public PropertyManager() {
		bundle = ResourceBundle.getBundle("config", UTF8_ENCODING_CONTROL);
		bundleLog = ResourceBundle.getBundle("log", UTF8_ENCODING_CONTROL);
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
