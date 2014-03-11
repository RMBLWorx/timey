package rmblworx.tools.timey.gui.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.junit.Test;

/**
 * Test fürs Speichern/Laden der GUI-Konfiguration.
 * 
 * @author Christian Raue <christian.raue@gmail.com>
 * @copyright 2014 Christian Raue
 * @license http://opensource.org/licenses/mit-license.php MIT License
 */
public class ConfigStorageTest {

	/**
	 * Testet das Speichern der Konfiguration.
	 */
	@Test
	public final void testSaveDefaultConfig() {
		// Standardkonfiguration erzeugen
		final Config config = ConfigManager.getDefaultConfig();

		// Konfiguration speichern
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		new ConfigStorage().saveConfig(config, out);

		// sicherstellen, dass gespeicherte Konfiguration korrekte Einträge enthält
		final String content = out.toString();
		assertTrue(content, content.contains("<entry key=\"locale\">de</entry>"));
		assertTrue(content, content.contains("<entry key=\"minimizeToTray\">false</entry>"));
		assertTrue(content, content.contains("<entry key=\"stopwatchShowMilliseconds\">true</entry>"));
	}

	/**
	 * Testet das Laden einer leeren Konfiguration und Befüllung mit Standardwerten.
	 */
	@Test
	public final void testLoadEmptyConfig() {
		// leere Konfiguration speichern
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			new Properties().storeToXML(out, null);
		} catch (final IOException e) {
			fail(e.getLocalizedMessage());
		}

		// gespeicherte Konfiguration laden, sollte dabei mit Standardwerten gefüllt werden
		final ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
		final Config emptyConfig = new ConfigStorage().loadConfig(in);

		// sicherstellen, dass geladene Konfiguration der Standardkonfiguration entspricht
		assertEquals(getConfigAsString(ConfigManager.getDefaultConfig()), getConfigAsString(emptyConfig));
	}

	/**
	 * @param config Konfiguration
	 * @return String-Repräsentation der Konfiguration
	 */
	private String getConfigAsString(final Config config) {
		return new ConfigStorage().getConfigAsProperties(config).toString();
	}

}
