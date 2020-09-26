package jp.co.tokiomarine_nichido.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Exceptionに関連するユーティリティクラス。
 *
 * @author SKK229873 中山真吾
 *
 */
public class ExceptionUtil {

	/**
	 * Exceptionのメッセージを文字列として返す。
	 *
	 * @param error
	 * @return
	 */
	public static String getMessage(Throwable error) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw, true);

		error.printStackTrace(pw);

		return sw.toString();
	}
}
