package jp.co.tokiomarine_nichido.models;

import java.util.HashMap;
import java.util.Map;

/**
 * return
 *
 * @author SKK231099 李
 */
public class Result {
	private Object data;
	private boolean isSuccess;
	private Map<String, String> errMsgList;

	public Result() {
		this.data = null;
		this.isSuccess = false;
		this.errMsgList = new HashMap<String, String>();
	}

	public Result(Object data, boolean isSuccess, Map<String, String> errMsgList) {
		this.data = data;
		this.isSuccess = isSuccess;
		this.errMsgList = errMsgList;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public Map<String, String> getErrMsgList() {
		return errMsgList;
	}

	public void setErrMsgList(Map<String, String> errMsgList) {
		this.errMsgList = errMsgList;
	}
}
