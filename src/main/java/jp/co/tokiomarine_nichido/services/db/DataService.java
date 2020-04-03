package jp.co.tokiomarine_nichido.services.db;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.exception.DataException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jp.co.tokiomarine_nichido.models.BasicClass;
import jp.co.tokiomarine_nichido.util.PropertyManager;

/**
 * DataService with JPA
 * @author SKK231099 李
 *
 */
public class DataService {
	private final Logger log = LogManager.getLogger();

	private EntityManager em;
	private EntityTransaction tx;

	protected DataService() {
		PropertyManager pm = new PropertyManager();
		String unit_name = pm.get("db_unit_name");
		String url = pm.get("db_url");
		String user = pm.get("db_user");
		String password = pm.get("db_password");
		String driver = pm.get("db_driver");
		try {
			Map<String, String> props = new HashMap<String, String>();
			props.put("javax.persistence.jdbc.url", url);
			props.put("javax.persistence.jdbc.user", user);
			props.put("javax.persistence.jdbc.password", password);
			props.put("javax.persistence.jdbc.driver", driver);
			EntityManagerFactory emf = Persistence.createEntityManagerFactory(unit_name, props);
			em = emf.createEntityManager();
			tx = em.getTransaction();

		} catch(Exception e) {
			log.info(e);
		}
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
			// new object class
		}
		return list;
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
					className = className.substring(className.lastIndexOf(".") + 1,className.length());
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

	protected <T> Boolean updateObject(BasicClass bc, Class<T> type) {
		Boolean result = null;
		if (bc != null) {
			try {
				final Object primaryKey = bc.getPrimaryKey();
				Map<String, Object> properties = bc.getProperties();
				this.tx.begin();
				T ettCls = getObject(primaryKey, properties, type);
				if (ettCls != null) {
					final String className = bc.getClass().getName();
					final Field[] fields = ettCls.getClass().getDeclaredFields();
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
						sql = update.substring(0, update.length() - 1) + where.substring(0, where.length() - and.length());
					}
					Query q = this.em.createQuery(sql);
					map.forEach ((key, value) -> {
						q.setParameter(key, value);
					});
					idMap.forEach((key, value) -> {
						q.setParameter(key, value);
					});
					q.executeUpdate();

					// 下記のcommandが効かないため上記の様に共通Update処理を実装
	//				entity = (BasicClass) ettCls;
	//				entity.setParams(bc);
	//				em.merge(ettCls);
				} else {
					this.em.persist(bc);
				}
				this.tx.commit();
				result = true;
			} catch(DataException e) {
				if (this.tx != null && tx.isActive()) {
					this.tx.rollback();
				}
				result = false;
			} finally {
//				this.em.close();
			}
		}
		return result;
	}

}
