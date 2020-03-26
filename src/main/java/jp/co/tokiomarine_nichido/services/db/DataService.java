package jp.co.tokiomarine_nichido.services.db;

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

import com.fasterxml.classmate.AnnotationConfiguration;

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

//			AnnotationConfiguration config = new AnnotationConfiguration();

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

	protected <T> T getObject(String id, Class<T> type) {
		T objClass = null;
		try {
			objClass = (T) em.find(type, id);
		} catch (Exception e) {
			// new object class
		}
		return objClass;

	}

//	public <T> T updateObject() {
//		this.tx.begin();
//		// do update;
//		this.tx.commit();
//	}

}
