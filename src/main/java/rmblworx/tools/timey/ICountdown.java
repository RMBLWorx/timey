/**
 * 
 */
package rmblworx.tools.timey;

import rmblworx.tools.timey.vo.TimeDescriptor;

/**
 * @author mmatthies
 */
interface ICountdown {
	/**
	 * Setzt die Zeit die heruntergezaehlt werden soll.
	 * 
	 * @param descriptor
	 *            Werteobjekt das die Zeitangabe kapselt.
	 * @return Werteobjekt mit den gesetzten Zeitwerten.
	 */
	TimeDescriptor setCountdownTime(TimeDescriptor descriptor);

	/**
	 * Startet den Countdown.
	 * 
	 * @return true wenn erfolgreich.
	 */
	Boolean startCountdown();

	/**
	 * Stoppt bzw. unterbricht den Countdown.
	 * 
	 * @return true wenn erfolgreich.
	 */
	Boolean stopCountdown();

}
