/**
 * 
 */
package rmblworx.tools.timey.persistence.service;

import rmblworx.tools.timey.persistence.model.AlarmTimestamp;

/**
 * @author mmatthies
 */
public interface IAlarmTimestampService {
	void activate(Long id, Boolean isActivated);

	Boolean create(AlarmTimestamp entity);

	AlarmTimestamp findById(Long id);
}
