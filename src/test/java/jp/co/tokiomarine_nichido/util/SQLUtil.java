package jp.co.tokiomarine_nichido.util;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.MessageFormat;

import javax.sql.DataSource;

import com.google.gson.Gson;

/**
 * 
 * @author SKK229873 中山真吾
 *
 */
public class SQLUtil {
	private static Gson gson = new Gson();

	/**
	 * 指定のテーブルをTruncateする。
	 * 
	 * @param ds
	 * @param tableName
	 * @throws Exception
	 */
	public static void truncateTable(DataSource ds, String tableName) throws Exception {
		Connection con = ds.getConnection();

		try (PreparedStatement truncateUserTable = con.prepareStatement(MessageFormat.format("TRUNCATE TABLE public.\"{0}\"", tableName))) {
			truncateUserTable.execute();

			System.out.println(MessageFormat.format("{0}テーブルをTruncate", tableName));
		}
	}
	
	/**
	 * <p>指定したjsonファイルからオブジェクトを作成。
	 * jsonファイルはsrc/test/resourcesからのパスをルートに指定する。
	 * </p>
	 * 
	 * 例<br>
	 * src/test/resource/xxx.jsonの場合、/xxx.jsonと指定する。
	 * 
	 * @param <T>
	 * @param jsonPath src/test/resourcesからのパス。
	 * @param clazz jsonからオブジェクトにシリアライズするときに、オブジェクトの型。
	 * @return jsonからシリアライズしたオブジェクト
	 * @throws FileNotFoundException jsonファイルがない場合に投げられる例外。
	 */
	public static <T> T createObject(String jsonPath, Class<T> clazz) throws FileNotFoundException{
		InputStream is = Thread.currentThread().getClass().getResourceAsStream(jsonPath);
		
		InputStreamReader reader = new InputStreamReader(is);
		return gson.fromJson(reader, clazz);
	}
}
