package rmblworx.tools.timey.gui.config;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Speichern/Laden der Konfiguration auf Basis einer Datei.
 * 
 * @author Christian Raue <christian.raue@gmail.com>
 * @copyright 2014 Christian Raue
 * @license http://opensource.org/licenses/mit-license.php MIT License
 */
public class FileConfigStorage extends ConfigStorage {

	/**
	 * @param config zu speichernde Konfiguration
	 * @param filename Dateiname der Konfigurationsdatei (kann auch Pfad enthalten)
	 */
	public final void saveToFile(final Config config, final String filename) {
		OutputStream out = null;
		try {
			out = new FileOutputStream(filename);
			saveConfig(config, out);
		} catch (final IOException e) {
			System.err.println(e.getLocalizedMessage());
		} finally {
			closeStream(out);
		}
	}

	/**
	 * @param filename Dateiname der Konfigurationsdatei (kann auch Pfad enthalten)
	 * @return geladene Konfiguration
	 */
	public final Config loadFromFile(final String filename) {
		if (!new File(filename).isFile()) {
			return ConfigManager.getDefaultConfig();
		}

		InputStream in = null;
		try {
			in = new FileInputStream(filename);
			return loadConfig(in);
		} catch (final IOException e) {
			System.err.println(e.getLocalizedMessage());
			return ConfigManager.getDefaultConfig();
		} finally {
			closeStream(in);
		}
	}

	/**
	 * @param stream zu schließender Stream
	 */
	protected final void closeStream(final Closeable stream) {
		if (stream != null) {
			try {
				stream.close();
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
	}

}
