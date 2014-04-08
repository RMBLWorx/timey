package rmblworx.tools.timey.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rmblworx.tools.timey.persistence.dao.IAlarmDao;
import rmblworx.tools.timey.persistence.model.AlarmEntity;
import rmblworx.tools.timey.vo.AlarmDescriptor;


/**
 * Serviceimplementierung zur Persistierung von {@link AlarmEntity Alarmzeitpunkt}en.
 * 
 * @author mmatthies
 */
@Service
@Transactional
public class AlarmService implements IAlarmService {

	/**
	 * Referenz auf das Datenzugriffsobjekt.
	 */
	@Autowired
	private IAlarmDao dao;

	@Override
	public Boolean create(final AlarmDescriptor descriptor) {
		return this.getDao().createAlarm(descriptor);
	}

	@Override
	public Boolean delete(final AlarmDescriptor descriptor) {
		return this.getDao().deleteAlarm(descriptor);
	}

	@Override
	public List<AlarmDescriptor> getAll() {
		return this.getDao().findAll();
	}

	/**
	 * @return Referenz auf das Datenzugriffsobjekt.
	 */
	private IAlarmDao getDao() {
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
