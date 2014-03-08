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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import rmblworx.tools.timey.persistence.model.AlarmTimestamp;

/**
 * @author mmatthies
 */
@Repository
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
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
	 * @param sessionFactory
	 *            Referenz auf die SessionFactory.
	 */
	@Autowired
	public AlarmTimestampDao(final SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Boolean createAlarmTimestamp(final AlarmTimestamp entity) {
		Boolean result = Boolean.FALSE;

		this.currentSession().save(entity);
		result = Boolean.TRUE;

		return result;
	}

	private Session currentSession() {
		return this.sessionFactory.getCurrentSession();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Boolean deleteAlarmTimestamp(Long id) {
		AlarmTimestamp ts = (AlarmTimestamp) this.currentSession().get(AlarmTimestamp.class, id);
		this.currentSession().delete(ts);
		return Boolean.TRUE;
	}

	@Override
	public AlarmTimestamp findById(final Long id) {
		final AlarmTimestamp ts = (AlarmTimestamp) this.currentSession().get(AlarmTimestamp.class, id);
		if (ts != null) {
			Hibernate.initialize(ts.getAlarmTimestamp());
			Hibernate.initialize(ts.getIsActivated());
		}

		return ts;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void setIsActivated(final Long id, final Boolean isActivated) {
		final AlarmTimestamp ts = this.findById(id);

		ts.setIsActivated(isActivated);
		this.currentSession().save(ts);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Boolean updateAlarmTimestamp(AlarmTimestamp entity) {
		final AlarmTimestamp ts = this.findById(entity.getId());
		ts.setAlarmTimestamp(entity.getAlarmTimestamp());
		ts.setIsActivated(entity.getIsActivated());
		this.currentSession().save(ts);

		return Boolean.TRUE;
	}
}
