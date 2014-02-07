/**
 * 
 */
package rmblworx.tools.timey.bo;

import rmblworx.tools.timey.vo.TimeDescriptor;

/**
 * @author mmatthies
 * 
 */
public interface IAlarm {

	/**
	 * Setzt die Alarmzeit.
	 * 
	 * @param td
	 *            Werteobjekt zur Beschreibung des Zeitpunktes.
	 * @return Werteobjekt das die gesetzten Werte kapselt.
	 */
	TimeDescriptor setAlarmTime(TimeDescriptor td);

	/**
	 * Stellt die Alarmzeit scharf.
	 * 
	 * @return true wenn erfolgreich.
	 */
	Boolean turnOn();

	/**
	 * Stellt die Alarmzeit unscharf.
	 * 
	 * @return true wenn erfolgreich.
	 */
	Boolean turnOff();

}
