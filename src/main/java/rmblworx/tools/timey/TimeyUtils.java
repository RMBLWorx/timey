package rmblworx.tools.timey;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author mmatthies
 */
public final class TimeyUtils {

	/**
	 * Vom aktuellen Verzeichnis in welchem die Anwendung gerade ausgefuehrt
	 * wird, liefert diese Hilfsmethode den Pfad zu jenen Dateien, die zum
	 * uebergebenen Muster passen.
	 * 
	 * @param pattern
	 *            das glob-Pattern - siehe hierzu {@link Finder}.
	 * @return unveraenderliche Liste von {@code Path}-Objekten oder leere
	 *         Liste.
	 * @throws IOException
	 *             wenn beim durchsuchen der Verzeichnisse ein Fehler auftrat.
	 */
	public static List<Path> getPathToJar(final String pattern) throws IOException {
		if (isNullOrEmpty(pattern)) {
			throw new IllegalArgumentException("Null referenzierende oder leere Zeichenketten sind nicht zulaessig!");
		}

		final String property = System.getProperty("user.dir");
		final Path startDir = Paths.get(property);
		final Finder finder = new Finder(pattern);
		List<Path> result = new LinkedList<Path>();

		Files.walkFileTree(startDir, finder);
		result = finder.getResult();

		return result;
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
	 * Veranlasst die fuer die Zeitnahme verwendete Implementierung sich zu beenden. Es wird so lange gewartet bis sie
	 * beendet wurde.
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
