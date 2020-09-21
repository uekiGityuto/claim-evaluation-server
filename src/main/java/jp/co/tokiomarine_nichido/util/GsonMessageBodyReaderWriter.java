package jp.co.tokiomarine_nichido.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.enterprise.context.Dependent;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import jp.co.tokiomarine_nichido.models.Exclude;

/***
 * Gsonメッセージ変換（シリアライズ、デシリアライズ）クラス
 *
 * @author SKK229873 中山真吾
 * @param
 */
@Provider
@Dependent
// TODO: 必要なければ削除（現時点では使用していない）
public class GsonMessageBodyReaderWriter<T> implements MessageBodyReader<T>, MessageBodyWriter<T> {

	private final Gson gson;

	public GsonMessageBodyReaderWriter() {
		ExclusionStrategy exclusionStrategy = new ExclusionStrategy() {
			@Override
			public boolean shouldSkipClass(Class<?> clazz) {
				return false;
			}

			@Override
			public boolean shouldSkipField(FieldAttributes f) {
				return f.getAnnotation(Exclude.class) != null;
			}
		};

		// ISO-8601でシリアライズ・デシリアライズ
		gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
				.addSerializationExclusionStrategy(exclusionStrategy).create();
	}

	@Override
	public long getSize(T t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		// As of JAX-RS 2.0, the method has been deprecated and the value returned by
		// the method is ignored by a JAX-RS runtime. All MessageBodyWriter
		// implementations are advised to return -1 from the method. Responsibility to
		// compute the actual Content-Length header value has been delegated to JAX-RS
		// runtime.
		return -1;
	}

	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return true;
	}

	@Override
	public void writeTo(T object, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
			throws IOException, WebApplicationException {
		PrintWriter printWriter = new PrintWriter(entityStream);
		try {
			String json = gson.toJson(object);
			printWriter.write(json);
			printWriter.flush();
		} finally {
			printWriter.close();
		}
	}

	@Override
	public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return true;
	}

	@Override
	public T readFrom(Class<T> type, Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
			throws IOException, WebApplicationException {
		try (InputStreamReader reader = new InputStreamReader(entityStream, "UTF-8")) {
			return gson.fromJson(reader, genericType);
		}
	}
}