package rmblworx.tools.timey;

import java.util.concurrent.TimeUnit;

import rmblworx.tools.timey.vo.TimeDescriptor;

interface ITimer {

	/**
	 * Setzt die Stoppuhr auf Null.
	 */
	void resetStopwatch();

	/**
	 * Startet den Stopvorgang.
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
	 *         kapselt. Es handelt sich hierbei um das im Konstruktor
	 *         uebergebene Objekt.
	 */
	TimeDescriptor startStopwatch(int amountOfThreads, int delayPerThread, TimeUnit timeUnit);

	/**
	 * Stoppt die Zeitmessung und beendet den/die gestarteten Threads.
	 */
	void stopStopwatch();

}
