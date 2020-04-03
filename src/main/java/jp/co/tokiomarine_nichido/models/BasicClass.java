package jp.co.tokiomarine_nichido.models;

import java.util.Map;

public abstract class BasicClass {

	public abstract String getIdNames();
	public abstract Object getPrimaryKey();
	public abstract Map<String, Object> getProperties();
	public abstract void setParams(Object obj);
	public abstract Object getValue(String fieldName);
}
