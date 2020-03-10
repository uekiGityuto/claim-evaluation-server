package jp.co.tokiomarine_nichido.models;

import java.util.Date;

import org.apache.logging.log4j.Level;

/**
 * クライアントのログメッセージを表すクラス。
 * 
 * @author SKK229873 中山真吾
 *
 */
public class Log {
	public Date timeStamp;
	public String logMessage;
	public String logLevel;

	public Level getLogLevel() {
		switch (logLevel) {
		case "TRACE":
			return Level.TRACE;
		case "DEBUG":
			return Level.DEBUG;
		case "INFO":
			return Level.INFO;
		case "ERROR":
			return Level.ERROR;
		case "WARN":
			return Level.WARN;
		default:
			// 指定がない場合は、重要でないと解釈しtraceレベルとする
			return Level.TRACE;
		}
	}

	/**
	 * ブラウザ側で指定する、ログレベル。 log4jに対応させている。
	 * 
	 * @author SKK229873 中山真吾
	 *
	 */
	public enum LogLevel {
		TRACE, DEBUG, INFO, WARN, ERROR;
	}
}
