package jp.co.tokiomarine_nichido.services.db;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;

import org.hibernate.Session;

import jp.co.tokiomarine_nichido.models.Comment;

/**
 * CommentService
 *
 * @author SKK231099 李
 *
 */
@ApplicationScoped
public class CommentDAO {
	// @PersistenceContext(name = "fd_persistence_unit", type =
	// PersistenceContextType.TRANSACTION)
	private Session session;

	@PostConstruct
	private void init() {
		// TODO:@PersistenceContextでHibernateのEntityManagerを注入できるようにする。
		this.session = (Session) Persistence.createEntityManagerFactory("fraud_detection").createEntityManager();
	}

	/**
	 * claimIdをキーに、関連するCommentのListを取得する。
	 * 
	 * @param claimId
	 * @return
	 */
	public List<Comment> getComments(String claimId) {
		TypedQuery<Comment> query = session.createQuery("from Comment c where c.claimId=:claimId", Comment.class);
		query.setParameter("claimId", claimId);
		return query.getResultList();
	}

	/**
	 * Commentを更新、または存在しない場合は新規に登録する。
	 * 
	 * @param comment
	 * @return 更新された状態のComment（新規の場合はidが採番され、creationDateやupdateDateが更新される）
	 */
	public Comment createOrUpdate(Comment comment) {
		EntityTransaction tx = this.session.beginTransaction();
		try {
			comment = (Comment) session.merge(comment);
			session.saveOrUpdate(comment);
			tx.commit();
		} catch (IllegalStateException | RollbackException e) {
			// TODO: プログラミングエラー？対処はこれで正しいか？RollbackException時もtx.rollback可能？
			if (tx.isActive())
				tx.rollback();

			throw e;
		} catch (EntityExistsException e) {
			if (tx.isActive())
				tx.rollback();

			throw e;
		} catch (Exception e) {
			if (tx.isActive())
				tx.rollback();

			throw e;
		}
		return comment;
	}

	public Boolean updateComment(Comment comment) {

		return true;
//		if (comment.getCreateDate() == null) {
//			Timestamp now = new Timestamp((new Date()).getTime());
//			comment.setCreateDate(now);
//		}
//		if (comment.getIdx() == null && comment.getClaimId() != null) {
//			final String sql = "select i.idx from " + tableName + " i where i.claim_id = '" + comment.getClaimId()
//					+ "' order by i.idx desc limit 1";
//			Integer idx = super.getObjectByNativeQuery(sql, Integer.class);
//			if (idx == null) {
//				idx = 1;
//			} else {
//				idx += 1;
//			}
//			comment.setIdx(idx);
//		}
//		return super.updateObject(comment, Comment.class);
	}
}
