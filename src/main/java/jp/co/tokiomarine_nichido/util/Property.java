package jp.co.tokiomarine_nichido.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * 設定ファイルから設定値を取得する。
 * 
 * @author SKK229873 中山真吾
 *
 */
@Target(ElementType.LOCAL_VARIABLE)
public @interface Property {
	String value();
}
