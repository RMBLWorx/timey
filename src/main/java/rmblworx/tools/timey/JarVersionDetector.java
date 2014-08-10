/**
 *
 */
package rmblworx.tools.timey;

import static java.nio.file.FileVisitResult.CONTINUE;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rmblworx.tools.timey.exception.EmptyArgumentException;
import rmblworx.tools.timey.exception.NullArgumentException;

/**
 * Diese Implementierung ermöglicht das Auslesen der Attribute Bundle-Version und/oder Implementation-Version aus dem
 * Manifest einer zu findenden Jar-Datei. Gesucht wird hierbei im Verzeichnis in welchem timey ausgefuehrt wird.
 *
 * @author mmatthies
 */
class JarVersionDetector {

	/**
	 * Ein {@code FileVisitor} welcher alle Dateien findet, die zum anzugebenen
	 * glob-Pattern passen.
	 *
	 * @see {@code http://docs.oracle.com/javase/javatutorials/tutorial/essential/io/fileOps.html#glob}
	 * @author mmatthies
	 */
	class Finder extends SimpleFileVisitor<Path> {

		/**
		 * PathMatcher.
		 */
		private final PathMatcher matcher;
		/**
		 * Ergebnis der Suche in Form einer Liste.
		 */
		private final List<Path> result = new LinkedList<>();

		/**
		 * Erweiterter Konstruktor zur Angabe eines glob-Pattern.
		 *
		 * @param pattern
		 *            Ausdruck.
		 */
		public Finder(final String pattern) {
			this.matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
		}

		/**
		 * Prueft ob das Path-Objekt zur Datei dem gegebenen Pattern entspricht.
		 *
		 * @param file
		 *            zu pruefendes Path-Objekt.
		 */
		private void find(final Path file) {
			final Path name = file.getFileName();
			if (name != null && this.matcher.matches(name)) {
				this.result.add(file);
				LOG.debug(file.toString());
			}
		}

		/**
		 * Liefert die gefundenen Dateien.
		 *
		 * @return unveraenderliche Liste mit gefundenen Dateien oder eine leere
		 *         Liste.
		 */
		public List<Path> getResult() {
			return Collections.unmodifiableList(this.result);
		}

		@Override
		public FileVisitResult preVisitDirectory(final Path dir, final BasicFileAttributes attrs) {
			this.find(dir);
			return CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) {
			this.find(file);
			return CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(final Path file, final IOException exc) {
			System.err.println(exc);
			return CONTINUE;
		}
	}

	/**
	 * Bezeichner fuer die Bundle-Version im Manifest.
	 */
	private static final String BUNDLE_VERSION = "Bundle-Version";

	/**
	 * Fehlermeldung fuer den Fall das die jar-Datei nicht gefunden werden konnte.
	 */
	private static final String ERROR_MSG = "Die timey-Jar Datei konnte nicht gefunden und somit die Version nicht ermittelt werden!";
	/**
	 * Bezeichner fuer die Implementation-Version im Manifest.
	 */
	private static final String IMPLEMENTATION_VERSION = "Implementation-Version";
	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(JarVersionDetector.class);

	/**
	 * Versucht die angegebene Jar-Datei im Verzeichnis in welchem diese Anwendung ausgefuehrt wird zu finden und die
	 * Versionsnummer aus dem Manifest zu lesen.
	 *
	 * @param jarFilename
	 *            Name des jar-Archivs inklusive Dateiendung. Die Uebergabe in Form eines glob-Pattern ist ebenso
	 *            moeglich.
	 * @return Die Versionsnummer aus dem Manifest des Jar-Archivs. Wird keine Datei gefunden eine leere Zeichenkette.
	 * @throws EmptyArgumentException
	 *             wenn die Laenge der Zeichenkette kleiner 1.
	 * @throws NullArgumentException
	 *             oder wenn {@code null} adressiert wird.
	 */
	public final String detectJarVersion(final String jarFilename) throws EmptyArgumentException, NullArgumentException {
		if (jarFilename == null) {
			throw new NullArgumentException();
		} else if (jarFilename.length() < 1) {
			throw new EmptyArgumentException();
		}
		final List<String> result = new LinkedList<>();
		File file;
		JarFile jar;
		String versionNumber = "";

		try {
			final List<Path> pathToJar = this.getPathToJar(jarFilename);
			for (Path path : pathToJar) {
				file = path.toFile();
				jar = new java.util.jar.JarFile(file);

				final Manifest manifest = jar.getManifest();
				final Attributes attributes = manifest.getMainAttributes();
				if (attributes != null) {
					final Iterator<Object> it = attributes.keySet().iterator();
					while (it.hasNext()) {
						final Attributes.Name key = (Attributes.Name) it.next();
						final String keyword = key.toString();
						if (IMPLEMENTATION_VERSION.equals(keyword) || BUNDLE_VERSION.equals(keyword)) {
							versionNumber = (String) attributes.get(key);
							break;
						}
					}
				}
				jar.close();
				result.add(versionNumber);
				LOG.debug("Version: " + versionNumber);
			}
		} catch (final IOException e) {
			LOG.error(ERROR_MSG);
		}

		if (result.isEmpty()) {
			throw new IllegalStateException("Kein Archiv gefunden!");
		}

		return result.get(0);
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
	 * @throws EmptyArgumentException
	 *             wenn die Laenge der Zeichenkette kleiner 1.
	 * @throws NullArgumentException
	 *             oder wenn {@code null} adressiert wird.
	 */
	private List<Path> getPathToJar(final String pattern) throws IOException, EmptyArgumentException,
	NullArgumentException {

		if (pattern == null) {
			throw new NullArgumentException();
		} else if (pattern.length() < 1) {
			throw new EmptyArgumentException();
		}

		final String property = System.getProperty("user.dir");
		final Path startDir = Paths.get(property);
		final Finder finder = new Finder(pattern);
		List<Path> result;

		Files.walkFileTree(startDir, finder);
		result = finder.getResult();

		return result;
	}
}
