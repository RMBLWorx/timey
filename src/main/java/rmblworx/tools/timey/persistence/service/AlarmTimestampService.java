/**
 * 
 */
package rmblworx.tools.timey.persistence.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rmblworx.tools.timey.persistence.dao.TimeyDao;
import rmblworx.tools.timey.persistence.model.AlarmTimestamp;

/**
 * @author mmatthies
 *
 */
@Service
@Transactional
public class AlarmTimestampService implements IAlarmTimestampService {

	/**
	 * Referenz auf das Dao.
	 */
	@Autowired
	private TimeyDao dao;

	@Override
	public void activate(Long id, Boolean isActivated) {
		this.dao.setIsActivated(id, isActivated);
	}


	@Override
	public Boolean create(final AlarmTimestamp entity) {
		return this.getDao().createAlarmTimestamp(entity);
	}


	@Override
	public Boolean delete(final Long id) {
		return this.getDao().deleteAlarmTimestamp(id);
	}


	@Override
	public AlarmTimestamp findById(final Long id) {
		return this.getDao().findById(id);
	}


	/**
	 * @return Referenz auf das Dao.
	 */
	private TimeyDao getDao() {
		return this.dao;
	}


	@Override
	public Boolean update(AlarmTimestamp entity) {
		return this.getDao().updateAlarmTimestamp(entity);
	}

}
