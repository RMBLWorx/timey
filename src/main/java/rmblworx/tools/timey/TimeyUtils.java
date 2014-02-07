/**
 * 
 */
package rmblworx.tools.timey;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author mmatthies
 * 
 */
final class TimeyUtils {
	private final static Logger log = LogManager.getLogger(TimeyUtils.class);

	private TimeyUtils() {
	}

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
	 * @throws IllegalArgumentException
	 *             wenn die Zeichenkette {@code null} referenziert oder eine
	 *             {@code Laenge == 0} aufweist.
	 */
	public static List<Path> getPathToJar(String pattern) throws IOException,
			IllegalArgumentException {
		log.entry();

		if (isNullOrEmpty(pattern)) {
			throw new IllegalArgumentException(
					"Null referenzierende oder leere Zeichenketten sind nicht zulaessig!");
		}

		String property = System.getProperty("user.dir");
		Path startDir = Paths.get(property);
		Finder finder = new Finder(pattern);
		List<Path> result = new LinkedList<Path>();

		Files.walkFileTree(startDir, finder);
		result = finder.getResult();

		log.exit();

		return result;
	}

	/**
	 * Prueft die Zeichenkette(n) ob sie {@code null} referenzieren oder die
	 * {@code Laenge == 0} aufweisen.
	 * 
	 * @param string
	 *            zu pruefende Zeichenkette.
	 * @return true wenn auch nur eine Zeichenkette {@code null} oder die
	 *         {@code Laenge == 0} aufweist.
	 */
	public static boolean isNullOrEmpty(String... string) {
		log.entry();
		boolean result = false;

		for (String str : string) {
			if (isNull(str) || isEmpty(str)) {
				result = true;
				break;
			}
		}

		log.exit();

		return result;
	}

	/**
	 * Prueft die Zeichenkette ob sie {@code Laenge == 0} aufweist.
	 * 
	 * @param string
	 *            zu pruefende Zeichenkette.
	 * @return true wenn {@code Laenge == 0}.
	 */
	public static boolean isEmpty(String string) {
		return string.length() < 1;
	}

	/**
	 * Prueft die Zeichenkette ob sie {@code null} referenziert.
	 * 
	 * @param string
	 *            zu pruefende Zeichenkette.
	 * @return true wenn {@code null} referenziert wird.
	 */
	public static boolean isNull(String string) {
		return string == null;
	}

}
