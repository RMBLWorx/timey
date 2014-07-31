package rmblworx.tools.timey;

import rmblworx.tools.timey.vo.TimeDescriptor;

/**
 * Schnittstellenbeschreibung des Countdown-Systems.
 * 
 * @author mmatthies
 */
interface ICountdown {

	/**
	 * Setzt die Zeit die heruntergezaehlt werden soll.
	 * 
	 * @param descriptor
	 *            Werteobjekt das die Zeitangabe kapselt.
	 * @return true wenn erfolgreich.
	 */
	Boolean setCountdownTime(TimeDescriptor descriptor);

	/**
	 * Startet den Countdown.
	 * 
	 * @return Werteobjekt mit den gesetzten Zeitwerten.Es handelt sich hierbei um das ueber
	 *         {@link #setCountdownTime(rmblworx.tools.timey.vo.TimeDescriptor)} uebergebene Objekt.
	 */
	TimeDescriptor startCountdown();

	/**
	 * Stoppt bzw. unterbricht den Countdown.
	 * 
	 * @return true wenn erfolgreich.
	 */
	Boolean stopCountdown();
}
