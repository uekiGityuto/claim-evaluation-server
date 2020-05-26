package jp.co.tokiomarine_nichido.models;

import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jboss.resteasy.annotations.StringParameterUnmarshallerBinder;
import org.jboss.resteasy.spi.StringParameterUnmarshaller;

import jp.co.tokiomarine_nichido.util.DateUtil;

/**
 * DateFormat for Rest Param
 * @author SKK231099 李
 */
public class StringParameterUnmarshal {
    @Retention(RUNTIME)
    @StringParameterUnmarshallerBinder(DateFormatter.class)
    public @interface DateFormat {
        public static final String DEFAULT_DATETIME = DateUtil.DATE_FORMAT;
//		String value() default DEFAULT_DATETIME;
    }

    public static class DateFormatter implements StringParameterUnmarshaller<Timestamp> {
        private SimpleDateFormat formatter;

        public void setAnnotations(Annotation[] annotations) {
//	       DateFormat format = FindAnnotation.findAnnotation(annotations, DateFormat.class);
//	       formatter = new SimpleDateFormat(format.value());
        }

        public Timestamp fromString(String str) {
            formatter = new SimpleDateFormat(DateFormat.DEFAULT_DATETIME);
            Date date;
            try {
                date = formatter.parse(str);
                Timestamp datetime = new Timestamp(date.getTime());
                return datetime;
            } catch (ParseException e) {
                e.printStackTrace();
            }
//          Timestamp datetime = new Timestamp(Long.valueOf(str));
//          return datetime;
          return null;
       }
    }
}