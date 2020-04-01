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

	protected <T> T getObject(String primaryKey, Map<String, Object> properties, Class<T> type) {
		T objClass = null;
		try {
			if (properties != null) {
				objClass = (T) this.em.find(type, primaryKey, properties);
			} else {
				objClass = (T) this.em.find(type, primaryKey);
			}
		} catch (Exception e) {
			// new object class
		}
		return objClass;

	}

	protected <T> Boolean updateObject(BasicClass bc, Class<T> type) {
	Boolean result = null;
		BasicClass entity = null;
		try {
			String primaryKey = bc.getPrimaryKey();
			Map<String, Object> properties = bc.getProperties();
			this.tx.begin();
			T ettCls = getObject(primaryKey, properties, type);
			if (ettCls != null) {
				String className = ettCls.getClass().getName();
				Field[] fields = ettCls.getClass().getDeclaredFields();
				String fieldName = null;
				String idNames = bc.getIdNames();
				String and = " and ";
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
			} else if (bc != null){
				entity = bc;
				this.em.persist(entity);
			}
			this.tx.commit();
			result = true;
		} catch(DataException e) {
			this.tx.rollback();
			result = false;
		} finally {
			em.close();
		}
		return result;
	}

}
