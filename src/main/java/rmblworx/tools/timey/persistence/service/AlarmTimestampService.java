package rmblworx.tools.timey.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rmblworx.tools.timey.persistence.dao.IAlarmTimestampDao;
import rmblworx.tools.timey.persistence.model.AlarmTimestamp;
import rmblworx.tools.timey.vo.AlarmDescriptor;


/**
 * Serviceimplementierung zur Persistierung von {@link AlarmTimestamp Alarmzeitpunkt}en.
 * 
 * @author mmatthies
 */
@Service
@Transactional
public class AlarmTimestampService implements IAlarmTimestampService {

	/**
	 * Referenz auf das Datenzugriffsobjekt.
	 */
	@Autowired
	private IAlarmTimestampDao dao;

	@Override
	public Boolean create(final AlarmDescriptor descriptor) {
		return this.getDao().createAlarmTimestamp(descriptor);
	}

	@Override
	public Boolean delete(final AlarmDescriptor descriptor) {
		return this.getDao().deleteAlarmTimestamp(descriptor);
	}

	@Override
	public List<AlarmDescriptor> getAll() {
		return this.getDao().findAll();
	}

	/**
	 * @return Referenz auf das Datenzugriffsobjekt.
	 */
	private IAlarmTimestampDao getDao() {
		return this.dao;
	}

	@Override
	public Boolean isActivated(final AlarmDescriptor descriptor) {
		return this.dao.isActivated(descriptor);
	}

	@Override
	public Boolean setState(final AlarmDescriptor descriptor, final Boolean isActivated) {
		return this.dao.setIsActivated(descriptor, isActivated);
	}
}
