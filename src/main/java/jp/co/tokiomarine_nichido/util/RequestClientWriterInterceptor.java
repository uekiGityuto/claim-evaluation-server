package jp.co.tokiomarine_nichido.util;

import java.io.IOException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * リクエストのログを出力するインターセプタークラス
 *
 * @author SKK231527 植木宥登
 */
public class RequestClientWriterInterceptor implements WriterInterceptor {

	private static final Logger logger = LogManager.getLogger(RequestClientWriterInterceptor.class);
//	@Context UriInfo uriInfo;

	@Override
	public void aroundWriteTo(WriterInterceptorContext context) throws IOException, WebApplicationException {

		// TODO: Loggerで実装
		System.out.println("インターセプター確認1");
		logger.debug("リクエストヘッダ:{}", context.getHeaders());
		logger.debug("リクエストボディ:{}", context.getEntity());

//		AUTHORIZATIONヘッダの付与もインターセプターで実施した方がスマートだが、以下が出来ない？ので断念。
//		・headerの追加→context.getHeaders().add(key, value)で出来そう
//		・リクエストのURI取得

//		System.out.println(uriInfo);
//		System.out.println(uriInfo.getPath());
		context.proceed();
	}
}

