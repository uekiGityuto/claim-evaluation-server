package jp.co.tokiomarine_nichido.util;

import java.io.IOException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;

/**
 * リクエストのログを出力するインターセプタークラス
 *
 * @author SKK231527 植木宥登
 */
@Provider
public class RequestClientWriterInterceptor implements WriterInterceptor {

//	@Context UriInfo uriInfo;

	@Override
	public void aroundWriteTo(WriterInterceptorContext context) throws IOException, WebApplicationException {
		// TODO: Loggerで実装
		System.out.println("インターセプター確認1");
		System.out.println(context.getEntity());
		System.out.println(context.getHeaders());

//		AUTHORIZATIONヘッダの付与もインターセプターで実施した方がスマートだが、以下が出来ない？ので断念。
//		・headerの追加→context.getHeaders().add(key, value)で出来そう
//		・リクエストのURI取得

//		System.out.println(uriInfo);
//		System.out.println(uriInfo.getPath());
		context.proceed();
	}
}

