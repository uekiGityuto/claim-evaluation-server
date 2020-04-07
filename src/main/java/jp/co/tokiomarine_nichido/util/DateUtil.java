package jp.co.tokiomarine_nichido.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
Letter  Date or Time Component  Presentation        Examples
------  ----------------------  ------------------  -------------------------------------
G       Era designator          Text                AD
y       Year                    Year                1996; 96
Y       Week year               Year                2009; 09
M/L     Month in year           Month               July; Jul; 07
w       Week in year            Number              27
W       Week in month           Number              2
D       Day in year             Number              189
d       Day in month            Number              10
F       Day of week in month    Number              2
E       Day in week             Text                Tuesday; Tue
u       Day number of week      Number              1
a       Am/pm marker            Text                PM
H       Hour in day (0-23)      Number              0
k       Hour in day (1-24)      Number              24
K       Hour in am/pm (0-11)    Number              0
h       Hour in am/pm (1-12)    Number              12
m       Minute in hour          Number              30
s       Second in minute        Number              55
S       Millisecond             Number              978
z       Time zone               General time zone   Pacific Standard Time; PST; GMT-08:00
Z       Time zone               RFC 822 time zone   -0800
X       Time zone               ISO 8601 time zone  -08; -0800; -08:00
*/

/**
 * DateUtil
 *
 * @author SKK231099 李
 */
public class DateUtil {
	String formatDate = "yyyy/MM/dd";
	String formatTimestamp = "yyyy/MM/dd HH:mm:ss";
	String formatFullTimestamp = "yyyy/MM/dd HH:mm:ss.SSS";

	private SimpleDateFormat getSimpleDateFormat(String strDate) {
		String format = strDate.contains(":") ? (strDate.contains(".") ? formatFullTimestamp : formatTimestamp)
				: formatDate;
		if (strDate.contains("-")) {
			strDate = strDate.replaceAll("-", "/");
		}
		if (format.contains(".")) {
			int len = strDate.substring(strDate.indexOf(".") + 1, strDate.length()).length();
			if (len == 1) {
				strDate += "00";
			} else if (len == 2) {
				strDate += "0";
			}
		}
		return new SimpleDateFormat(format);
	}

	public java.sql.Date toDate(String strDate) {
		if (strDate != null && strDate.length() == 0) {
			return null;
		}
		java.sql.Date date = new java.sql.Date(0);
		try {
			SimpleDateFormat df = getSimpleDateFormat(strDate);
			date.setTime(df.parse(strDate).getTime());
		} catch (Exception e) {
			date = null;
		}

		return date;
	}

	public Date toDateTime(String strDate) {
		if (strDate != null && strDate.length() == 0) {
			return null;
		}
		Date date = new Date(0);
		try {
			SimpleDateFormat df = getSimpleDateFormat(strDate);
			date.setTime(df.parse(strDate).getTime());
		} catch (Exception e) {
			date = null;
		}

		return date;
	}

	public Timestamp toTimestamp(String strDate) {
		if (strDate != null && strDate.length() == 0) {
			return null;
		}
		Timestamp ts = null;
		try {
			ts = new Timestamp(toDateTime(strDate).getTime());
		} catch (Exception e) {
		}

		return ts;
	}

	public Timestamp getNewTimestamp() {
		java.util.Date date = new java.util.Date();
		Timestamp now = new Timestamp(date.getTime());
		return now;
	}
}
