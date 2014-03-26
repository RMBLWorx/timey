package rmblworx.tools.timey;

import java.util.concurrent.TimeUnit;

import rmblworx.tools.timey.vo.TimeDescriptor;

/**
 * Einfach ausgelegte Schnittstelle fuer die Implementierung eines Countdown-Objekts das die konkrete Zeitmessung
 * implementiert.
 * 
 * @author mmatthies
 */
interface ICountdownTimer {

	/**
	 * Setzt den Startwert fuer den Countdown.
	 * 
	 * @param descriptor
	 *            Referenz auf das Werteobjekt das die darzustellende Zeit kapselt.
	 * @return true wenn die Ausgangszeit erfolgreich gesetzt werden konnte.
	 */
	Boolean setCountdownTime(TimeDescriptor descriptor);

	/**
	 * Startet den Countdown.
	 * 
	 * @param amountOfThreads
	 *            Anzahl fuer die Zeitmessung zu verwendender Threads.
	 * @param delayPerThread
	 *            Setzt die Verzoegerung pro Thread. Die Maszeinheit
	 *            wird mittels des TimeUnit-Enum gesetzt.
	 * @param timeUnit
	 *            Setzt die zu verwendende Zeiteinheit fuer die
	 *            wiederholte Aktualisierung des Werteobjekts.
	 * @return Referenz auf das Wertobjekt das die darzustellende Zeit
	 *         kapselt. Es handelt sich hierbei um das ueber {@link #setTime(TimeDescriptor)} uebergebene Objekt.
	 */
	TimeDescriptor startCountdown(int amountOfThreads, int delayPerThread, TimeUnit timeUnit);

	/**
	 * Stoppt den Countdown und beendet den/die gestarteten Threads.
	 * 
	 * @return true wenn erfolgreich die Uhr angehalten werden konnte sonst false.
	 */
	Boolean stopCountdown();
}
