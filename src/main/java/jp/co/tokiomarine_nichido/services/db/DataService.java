package jp.co.tokiomarine_nichido.services.db;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.transaction.RollbackException;

import org.hibernate.exception.SQLGrammarException;

import com.fasterxml.jackson.databind.ObjectMapper;

import jp.co.tokiomarine_nichido.models.BaseEntity;
import jp.co.tokiomarine_nichido.models.StatusCode;
import jp.co.tokiomarine_nichido.util.DefaultExceptionMapper;
import jp.co.tokiomarine_nichido.util.PropertyManager;

/**
 * DataService with JPA
 * @author SKK231099 李
 *
 */
public class DataService {
//    private final Logger log = LogManager.getLogger();

    private EntityManager em;
    private EntityTransaction tx;

    @Inject
    protected PropertyManager pm;

    protected DataService() {
    }

    @PostConstruct
    protected void init() {
        if (this.em != null) {
        } else {
            em = Persistence.createEntityManagerFactory(pm.get("unitName")).createEntityManager();
        }
        this.tx = this.em.getTransaction();
    }

    protected Integer getSequenceId(String name) throws Exception {
        Integer sequence = null;
        String sql = "select nextval('" + name + "')";
        try {
            Query q = this.em.createNativeQuery(sql);
            sequence = Integer.parseUnsignedInt(String.valueOf(q.getSingleResult()));
        } catch (IllegalStateException e) {
            DefaultExceptionMapper.status = StatusCode.ILLEGAL_STATE_EXCEPTION;
            throw new Exception("ILLEGAL_STATE_EXCEPTION");
        } catch (EntityExistsException e) {
            DefaultExceptionMapper.status = StatusCode.ENTITY_EXISTS_EXCEPTION;
            throw new Exception("ENTITY_EXISTS_EXCEPTION");
        } catch (SQLGrammarException e) {
            DefaultExceptionMapper.status = StatusCode.SQL_GRAMMAR_EXCEPTION;
            throw new Exception("SQL_GRAMMAR_EXCEPTION");
        } catch (Exception e) {
            DefaultExceptionMapper.status = StatusCode.EXECUTE_EXCEPTION;
            throw new Exception("EXECUTE_EXCEPTION");
        }
        return sequence;
    }

    @SuppressWarnings("unchecked")
    protected <T> List<T> getListByQuery(Class<T> type, String sql, Map<String, String> params) throws Exception {
        List<T> list = null;
        try {
            Query q = this.em.createQuery(sql);
            params.forEach((key, value) -> {
                q.setParameter(key, value);
            });
            list = q.getResultList();
        } catch (IllegalStateException e) {
            DefaultExceptionMapper.status = StatusCode.ILLEGAL_STATE_EXCEPTION;
            throw new Exception("ILLEGAL_STATE_EXCEPTION");
        } catch (EntityExistsException e) {
            DefaultExceptionMapper.status = StatusCode.ENTITY_EXISTS_EXCEPTION;
            throw new Exception("ENTITY_EXISTS_EXCEPTION");
        } catch (SQLGrammarException e) {
            DefaultExceptionMapper.status = StatusCode.SQL_GRAMMAR_EXCEPTION;
            throw new Exception("SQL_GRAMMAR_EXCEPTION");
        } catch (Exception e) {
            DefaultExceptionMapper.status = StatusCode.EXECUTE_EXCEPTION;
            throw new Exception("EXECUTE_EXCEPTION");
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    protected <T> T getObjectByNativeQuery(Class<T> type, String sql) throws Exception {
        try {
            Query q = this.em.createNativeQuery(sql);
            Object obj = q.getSingleResult();
            if (obj != null) {
                return (T) obj;
            }
        } catch (IllegalStateException e) {
            DefaultExceptionMapper.status = StatusCode.ILLEGAL_STATE_EXCEPTION;
            throw new Exception("ILLEGAL_STATE_EXCEPTION");
        } catch (EntityExistsException e) {
            DefaultExceptionMapper.status = StatusCode.ENTITY_EXISTS_EXCEPTION;
            throw new Exception("ENTITY_EXISTS_EXCEPTION");
        } catch (SQLGrammarException e) {
            DefaultExceptionMapper.status = StatusCode.SQL_GRAMMAR_EXCEPTION;
            throw new Exception("SQL_GRAMMAR_EXCEPTION");
        } catch (Exception e) {
            DefaultExceptionMapper.status = StatusCode.EXECUTE_EXCEPTION;
            throw new Exception("EXECUTE_EXCEPTION");
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    protected <T> T getObject(Class<T> type, Object primaryKey) throws Exception {
        try {
            String typeName = primaryKey.getClass().getTypeName();
            if ("java.lang.Integer".equals(typeName) || "java.lang.String".equals(typeName)) {
                // for single primarykey
                Object obj = this.em.find(type, primaryKey);
                if (obj != null) {
                    return (T) obj;
                }
            } else {
                // for multi primarykey
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
                    Object obj = q.getSingleResult();
                    return (T) obj;
                }
            }
        } catch (IllegalStateException e) {
            DefaultExceptionMapper.status = StatusCode.ILLEGAL_STATE_EXCEPTION;
            throw new Exception("ILLEGAL_STATE_EXCEPTION");
        } catch (EntityExistsException e) {
            DefaultExceptionMapper.status = StatusCode.ENTITY_EXISTS_EXCEPTION;
            throw new Exception("ENTITY_EXISTS_EXCEPTION");
        } catch (SQLGrammarException e) {
            DefaultExceptionMapper.status = StatusCode.SQL_GRAMMAR_EXCEPTION;
            throw new Exception("SQL_GRAMMAR_EXCEPTION");
        } catch (Exception e) {
            DefaultExceptionMapper.status = StatusCode.EXECUTE_EXCEPTION;
            throw new Exception("EXECUTE_EXCEPTION");
        }
        return null;
    }

    protected BaseEntity insertObject(BaseEntity be) throws Exception {
        try {
            this.tx.begin();
            this.em.persist(be);
            this.tx.commit();
            BaseEntity obj = be.copy();
            return obj;
        } catch (IllegalStateException e) {
            try {
                rollback(tx);
                DefaultExceptionMapper.status = StatusCode.ILLEGAL_STATE_EXCEPTION;
                throw new Exception("ILLEGAL_STATE_EXCEPTION");
            } catch (RollbackException re) {
                DefaultExceptionMapper.status = StatusCode.ROLLBACK_EXCEPTION;
                throw new Exception("ROLLBACK_EXCEPTION");
            }
        } catch (EntityExistsException e) {
            try {
                rollback(tx);
                DefaultExceptionMapper.status = StatusCode.ENTITY_EXISTS_EXCEPTION;
                throw new Exception("ENTITY_EXISTS_EXCEPTION");
            } catch (RollbackException re) {
                DefaultExceptionMapper.status = StatusCode.ROLLBACK_EXCEPTION;
                throw new Exception("ROLLBACK_EXCEPTION");
            }
        } catch (SQLGrammarException e) {
            try {
                rollback(tx);
                DefaultExceptionMapper.status = StatusCode.SQL_GRAMMAR_EXCEPTION;
                throw new Exception("SQL_GRAMMAR_EXCEPTION");
            } catch (RollbackException re) {
                DefaultExceptionMapper.status = StatusCode.ROLLBACK_EXCEPTION;
                throw new Exception("ROLLBACK_EXCEPTION");
            }
        } catch (Exception e) {
            try {
                rollback(tx);
                DefaultExceptionMapper.status = StatusCode.TRANSACTION_EXCEPTION;
                throw new Exception("TRANSACTION_EXCEPTION");
            } catch (RollbackException re) {
                DefaultExceptionMapper.status = StatusCode.ROLLBACK_EXCEPTION;
                throw new Exception("ROLLBACK_EXCEPTION");
            }
        }
    }

    protected BaseEntity updateObject(BaseEntity be, String sql, Map<String, Object> param) throws Exception {
        if (MismatchedDbUpdateDate(be)) {
            DefaultExceptionMapper.status = StatusCode.EXCLUSIVE_EXCEPTION;
            throw new Exception("EXCLUSIVE_EXCEPTION");
        }

        if (String.valueOf(sql).length() > 0) {
            try {
                this.tx.begin();
                Query q = this.em.createQuery(sql);
                be.onUpdate();
                param.put("updateDate", be.getUpdateDate());
                param.forEach((key, value) -> {
                    q.setParameter(key, value);
                });
                q.executeUpdate();
                this.tx.commit();
                BaseEntity obj = be.copy();
                return obj;
            } catch (IllegalStateException e) {
                try {
                    rollback(tx);
                    DefaultExceptionMapper.status = StatusCode.ILLEGAL_STATE_EXCEPTION;
                    throw new Exception("ILLEGAL_STATE_EXCEPTION");
                } catch (RollbackException re) {
                    DefaultExceptionMapper.status = StatusCode.ROLLBACK_EXCEPTION;
                    throw new Exception("ROLLBACK_EXCEPTION");
                }
            } catch (EntityExistsException e) {
                try {
                    rollback(tx);
                    DefaultExceptionMapper.status = StatusCode.ENTITY_EXISTS_EXCEPTION;
                    throw new Exception("ENTITY_EXISTS_EXCEPTION");
                } catch (RollbackException re) {
                    DefaultExceptionMapper.status = StatusCode.ROLLBACK_EXCEPTION;
                    throw new Exception("ROLLBACK_EXCEPTION");
                }
            } catch (SQLGrammarException e) {
                try {
                    rollback(tx);
                    DefaultExceptionMapper.status = StatusCode.SQL_GRAMMAR_EXCEPTION;
                    throw new Exception("SQL_GRAMMAR_EXCEPTION");
                } catch (RollbackException re) {
                    DefaultExceptionMapper.status = StatusCode.ROLLBACK_EXCEPTION;
                    throw new Exception("ROLLBACK_EXCEPTION");
                }
            } catch (Exception e) {
                try {
                    rollback(tx);
                    DefaultExceptionMapper.status = StatusCode.TRANSACTION_EXCEPTION;
                    throw new Exception("TRANSACTION_EXCEPTION");
                } catch (RollbackException re) {
                    DefaultExceptionMapper.status = StatusCode.ROLLBACK_EXCEPTION;
                    throw new Exception("ROLLBACK_EXCEPTION");
                }
            }
        } else {
            DefaultExceptionMapper.status = StatusCode.SQL_GRAMMAR_EXCEPTION;
            throw new Exception("SQL_GRAMMAR_EXCEPTION");
        }
    }

    /**
     * updateDate基準排他チェック
     * @param <T>
     * @param primaryKey
     * @param be
     * @return
     */
    public <T> Boolean MismatchedDbUpdateDate(BaseEntity be) throws Exception {
        BaseEntity obj = getObject(be.getClass(), be.getPrimaryKey());
        if (obj.getUpdateDate().compareTo(be.getUpdateDate()) != 0) {
            return true;
        }
        return false;
    }

    /**
     * rollback
     * @param tx
     * @throws RollbackException
     */
    private void rollback(EntityTransaction tx) throws RollbackException {
        if (tx.isActive())
            tx.rollback();
    }

}
