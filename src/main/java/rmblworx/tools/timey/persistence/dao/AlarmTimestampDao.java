/**
 * 
 */
package rmblworx.tools.timey.persistence.dao;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import rmblworx.tools.timey.persistence.model.AlarmTimestamp;

/**
 * @author mmatthies
 */
@Repository
public class AlarmTimestampDao implements TimeyDao {

	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(AlarmTimestampDao.class);
	/**
	 * SessionFactory.
	 */
	private final SessionFactory sessionFactory;

	/**
	 * @param sessionFactory Referenz auf die SessionFactory.
	 */
	@Autowired
	public AlarmTimestampDao(final SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Boolean addAlarmTimestamp(final AlarmTimestamp entity) {
		Boolean result = Boolean.FALSE;
		Transaktionen verwenden <- bislang gibt es noch ein Problem mit Hibernate - kann keine Transaktion liefern

		//		final Transaction tx = this.currentSession().getTransaction();

		//		try {
		//			tx.begin();
		this.currentSession().save(entity);
		//			tx.commit();
		result = Boolean.TRUE;
		//		} catch (HibernateException hex) {
		//			LOG.error(hex.getLocalizedMessage());
		//			tx.rollback();
		//		}

		return result;
	}

	private Session currentSession() {
		return this.sessionFactory.getCurrentSession();
	}

	@Override
	public AlarmTimestamp findById(final Long id) {
		final AlarmTimestamp ts = (AlarmTimestamp) this.currentSession().load(AlarmTimestamp.class, id);
		Hibernate.initialize(ts.getAlarmTimestamp());
		Hibernate.initialize(ts.getIsActivated());

		return ts;
	}

	@Override
	public void setIsActivated(final Long id, final Boolean isActivated) {
		final AlarmTimestamp ts = this.findById(id);
		//		final Transaction tx = this.currentSession().getTransaction();

		//		try {
		//			tx.begin();
		ts.setIsActivated(isActivated);
		this.currentSession().save(ts);
		//			tx.commit();
		//		} catch (HibernateException hex) {
		//			LOG.error(hex.getLocalizedMessage());
		//			tx.rollback();
		//		}
	}
}
