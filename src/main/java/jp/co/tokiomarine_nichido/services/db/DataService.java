package jp.co.tokiomarine_nichido.services.db;

import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.exception.DataException;

import com.fasterxml.jackson.databind.ObjectMapper;

import jp.co.tokiomarine_nichido.models.BasicClass;
import jp.co.tokiomarine_nichido.util.DateUtil;
import jp.co.tokiomarine_nichido.util.PropertyManager;

/**
 * DataService with JPA
 * 
 * @author SKK231099 李
 *
 */
@ApplicationScoped
public class DataService {
	private final Logger log = LogManager.getLogger();

	@Inject
	private PropertyManager pm;

	private EntityManager em;

	@PostConstruct
	private void init() {
		this.em = Persistence.createEntityManagerFactory(pm.get("db_unit_name")).createEntityManager();
	}

	@SuppressWarnings("unchecked")
	protected <T> List<T> getList(String sql, Class<T> type) {
		List<T> list = null;
		try {
			Query q = this.em.createQuery(sql);
			list = (List<T>) q.getResultList();
		} catch (Exception e) {
			log.info(e);
			list = new ArrayList<T>();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	protected <T> List<T> getList(Object primaryKey, Map<String, Object> properties, Class<T> type) {
		List<T> list = null;
		try {
			if (properties != null) {
				list = (List<T>) this.em.find(type, primaryKey, properties);
			} else {
				list = (List<T>) this.em.find(type, primaryKey);
			}
		} catch (Exception e) {
			// fail
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	protected <T> T getObjectByNativeQuery(String sql, Class<T> type) {
		T objClass = null;
		try {
			Query q = this.em.createNativeQuery(sql);
			objClass = (T) q.getSingleResult();
		} catch (Exception e) {
			// fail
		}
		return objClass;
	}

	@SuppressWarnings("unchecked")
	protected <T> T getObject(Object primaryKey, Map<String, Object> properties, Class<T> type) {
		T objClass = null;
		try {
			if ("java.lang.String".equals(primaryKey.getClass().getTypeName())) {
				if (properties != null) {
					objClass = (T) this.em.find(type, primaryKey, properties);
				} else {
					objClass = (T) this.em.find(type, primaryKey);
				}
			} else {
				final ObjectMapper om = new ObjectMapper();
				Map<String, Object> map = om.convertValue(primaryKey, Map.class);
				if (map.size() > 0) {
					String className = type.getName();
					className = className.substring(className.lastIndexOf(".") + 1, className.length());
					final String and = " and ";
					StringBuilder select = new StringBuilder("select i from " + className + " i where ");
					map.forEach((key, value) -> {
						select.append("i." + key + " = '" + value + "'" + and);
					});
					String sql = String.valueOf(select.substring(0, select.length() - and.length()));
					Query q = this.em.createQuery(sql);
					objClass = (T) q.getSingleResult();
				}
			}
		} catch (Exception e) {
			log.info(e);
		}
		return objClass;
	}

	protected Boolean updateObject(String sql, Map<String, Object> param) {
		Boolean result = null;
		EntityTransaction tx = em.getTransaction();
		if (String.valueOf(sql).length() > 0) {
			try {
				tx.begin();
				Query q = this.em.createQuery(sql);
				param.forEach((key, value) -> {
					q.setParameter(key, value);
				});
				q.executeUpdate();
				tx.commit();
				result = true;
			} catch (Exception e) {
				if (tx != null && tx.isActive()) {
					tx.rollback();
				}
			}
		}

		return result;
	}

	protected <T> Boolean updateObject(BasicClass bc, Class<T> type) {
		Boolean result = null;
		if (bc != null) {
			EntityTransaction tx = this.em.getTransaction();
			try {
				final Object primaryKey = bc.getPrimaryKey();
				Map<String, Object> properties = bc.getProperties();
				tx.begin();
				T ettCls = getObject(primaryKey, properties, type);
				if (ettCls != null) {
					final Field[] fields = ettCls.getClass().getDeclaredFields();
					if (isNotSameDateTimeForUpdate(fields, ettCls, bc)) {
						throw new DataException("古いDataから更新しようとしています。画面を更新しやり直してください。", null);
					}
					final String className = bc.getClass().getName();
					String fieldName = null;
					final String idNames = bc.getIdNames();
					final String and = " and ";
					Map<String, Object> map = new HashMap<String, Object>();
					Map<String, Object> idMap = new HashMap<String, Object>();
					StringBuilder update = new StringBuilder("update " + className + " set ");
					StringBuilder where = new StringBuilder(" where ");
					String sql = null;
					for (Field field : fields) {
						fieldName = field.getName();
						if (idNames.contains(fieldName)) {
							where.append(fieldName + " = :" + fieldName + and);
							idMap.put(fieldName, bc.getValue(fieldName));
						} else {
							update.append(fieldName + " = :" + fieldName + ",");
							map.put(fieldName, bc.getValue(fieldName));
						}
					}
					if (map.size() > 0) {
						sql = update.substring(0, update.length() - 1)
								+ where.substring(0, where.length() - and.length());
					}
					Query q = this.em.createQuery(sql);
					map.forEach((key, value) -> {
						q.setParameter(key, value);
					});
					idMap.forEach((key, value) -> {
						q.setParameter(key, value);
					});
					q.executeUpdate();

					// 下記のcommandが効かないため上記の様に共通Update処理を実装
					// entity = (BasicClass) ettCls;
					// entity.setParams(bc);
					// em.merge(ettCls);
				} else {
					this.em.persist(bc);
				}
				tx.commit();
				result = true;
			} catch (DataException e) {
				if (tx != null && tx.isActive()) {
					tx.rollback();
				}
				result = false;
			} finally {
//				em.close();
			}
		}
		return result;
	}

	private <T> Boolean isNotSameDateTimeForUpdate(Field[] fields, T ettCls, BasicClass bc) {
		Boolean isErr = null;
		StringBuilder sb = new StringBuilder();
		ObjectMapper om = new ObjectMapper();
		final String[] updateNames = { "update", "edit", "modify" };
		final BasicClass origin = (BasicClass) ettCls;
		Map<String, String> updateTimestampMap = new HashMap<String, String>();
		for (int i = 0; i < fields.length; i++) {
			Field f = fields[i];
			final String fullTypeName = f.getType().getName();
			final String typeName = fullTypeName.substring(fullTypeName.lastIndexOf(".") + 1, fullTypeName.length())
					.toLowerCase();
			switch (typeName) {
			case "timestamp":
			case "date":
				for (int j = 0; j < updateNames.length; j++) {
					if (f.getName().toLowerCase().contains(updateNames[j])) {
						updateTimestampMap.put(f.getName(), typeName);
					}
				}
				break;
			}

		}

		updateTimestampMap.forEach((fieldName, typeName) -> {
			switch (typeName) {
			case "timestamp":
				Timestamp ts1 = om.convertValue(origin.getValue(fieldName), Timestamp.class);
				Timestamp ts2 = om.convertValue(bc.getValue(fieldName), Timestamp.class);
				if (ts1 != null && ts2 != null) {
					if (!ts1.equals(ts2)) {
						sb.append("1");
					}
				}
				break;
			case "date":
				Date dt1 = om.convertValue(origin.getValue(fieldName), Date.class);
				Date dt2 = om.convertValue(bc.getValue(fieldName), Date.class);
				if (dt1 != null && dt2 != null) {
					if (!dt1.equals(dt2)) {
						sb.append("2");
					}
				}
				break;
			}
		});

		isErr = sb.length() == 0 ? false : true;

		if (!isErr) {
			DateUtil du = new DateUtil();
			Timestamp now = du.getNewTimestamp();
			updateTimestampMap.forEach((fieldName, typeName) -> {
				bc.setValue(fieldName, now);
			});
		}

		return isErr;
	}

}
