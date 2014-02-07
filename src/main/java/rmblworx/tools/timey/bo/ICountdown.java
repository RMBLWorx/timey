/**
 * 
 */
package rmblworx.tools.timey.bo;

import rmblworx.tools.timey.vo.TimeDescriptor;

/**
 * @author mmatthies
 * 
 */
interface ICountdown {
	/**
	 * Setzt die Zeit die heruntergezaehlt werden soll.
	 * 
	 * @param td
	 *            Werteobjekt das die Zeitangabe kapselt.
	 * @return Werteobjekt mit den gesetzten Zeitwerten.
	 */
	TimeDescriptor setCountdownTime(TimeDescriptor td);

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
