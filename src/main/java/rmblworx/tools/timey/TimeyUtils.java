package rmblworx.tools.timey;

import java.util.concurrent.ScheduledExecutorService;

/**
 * Helferklasse mit haeufig benoetigten Funktionen.
 * 
 * @author mmatthies
 */
public final class TimeyUtils {

	private static final String LINUX = "linux";
	private static final String MAC_OS_X = "mac os x";
	private static final String SYSTEM_PROPERTY_OS_NAME = "os.name";
	private static final String WINDOWS = "windows";

	/**
	 * @return
	 */
	public static String getOsName() {
		return System.getProperty(SYSTEM_PROPERTY_OS_NAME).toLowerCase();
	}

	/**
	 * Prueft die Zeichenkette ob sie {@code Laenge == 0} aufweist.
	 * 
	 * @param string
	 *            zu pruefende Zeichenkette.
	 * @return true wenn {@code Laenge == 0}.
	 */
	public static boolean isEmpty(final String string) {
		return string.length() < 1;
	}

	/**
	 * Liefert die Antwort darauf, ob timey gerade auf einem Linux Betriebssystem ausgefuehrt wird.
	 * 
	 * @return true wenn Linux sonst false
	 */
	public static boolean isLinuxSystem() {
		String osName = getOsName();
		return osName.indexOf(LINUX) != -1;
	}

	/**
	 * Prueft die Zeichenkette ob sie {@code null} referenziert.
	 * 
	 * @param string
	 *            zu pruefende Zeichenkette.
	 * @return true wenn {@code null} referenziert wird.
	 */
	public static boolean isNull(final String string) {
		return string == null;
	}

	/**
	 * Prueft die Zeichenkette(n) ob sie {@code null} referenzieren oder die {@code Laenge == 0} aufweisen.
	 * 
	 * @param string
	 *            zu pruefende Zeichenkette.
	 * @return true wenn auch nur eine Zeichenkette {@code null} oder die {@code Laenge == 0} aufweist.
	 */
	public static boolean isNullOrEmpty(final String... string) {
		boolean result = false;

		for (String str : string) {
			if (isNull(str) || isEmpty(str)) {
				result = true;
				break;
			}
		}

		return result;
	}

	/**
	 * Liefert die Antwort darauf, ob timey gerade auf einem OS X-Betriebssystem ausgefuehrt wird.
	 * 
	 * @return true wenn OS X sonst false
	 */
	public static boolean isOSXSystem() {
		String osName = getOsName();
		return osName.indexOf(MAC_OS_X) != -1;
	}

	/**
	 * Liefert die Antwort darauf, ob timey gerade auf einem Windows-Betriebssystem ausgefuehrt wird.
	 * 
	 * @return true wenn Windows sonst false
	 */
	public static boolean isWindowsSystem() {
		String osName = getOsName();
		return osName.indexOf(WINDOWS) != -1;
	}

	/**
	 * Veranlasst die fuer die Zeitnahme verwendete Implementierung sich zu beenden. Es wird so lange gewartet bis sie
	 * beendet wurde.
	 * 
	 * @param scheduler
	 *            Referenz auf den Scheduler.
	 */
	public static void shutdownScheduler(final ScheduledExecutorService scheduler) {
		if (null != scheduler) {
			scheduler.shutdownNow();
			while (!scheduler.isTerminated()) {
				// wir warten solange bis alle Threads beendet wurden
			}
		}
	}

	/**
	 * Konstruktor.
	 */
	private TimeyUtils() {
	}
}
