package jp.co.tokiomarine_nichido.util;

import java.io.IOException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;

/**
 * リクエストのログを出力するインターセプタークラス
 * AUTHORIZATIONヘッダの付与もインターセプターで実施した方がスマートだが、以下が出来ない？ので断念。
 * ・headerの追加
 * ・リクエストのURI取得
 *
 * @author SKK231527 植木宥登
 */
@Provider
public class RequestClientWriterInterceptor implements WriterInterceptor {

	@Override
	public void aroundWriteTo(WriterInterceptorContext context) throws IOException, WebApplicationException {
		// TODO: Loggerで実装
		System.out.println(context.getEntity());
		System.out.println(context.getHeaders());
		context.proceed();
	}
}

