package rmblworx.tools.timey.gui.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
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
		final Config config = ConfigManager.getNewDefaultConfig();

		// Konfiguration speichern
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		new ConfigStorage().saveConfig(config, out);

		final Map<String, String> testCases = new HashMap<>(4);
		testCases.put(ConfigStorage.PROP_LOCALE, String.format("%s", config.getLocale()));
		testCases.put(ConfigStorage.PROP_MINIMIZE_TO_TRAY, String.format("%s", config.isMinimizeToTray()));
		testCases.put(ConfigStorage.PROP_STOPWATCH_SHOW_MILLIS, String.format("%s", config.isStopwatchShowMilliseconds()));
		testCases.put(ConfigStorage.PROP_ACTIVE_TAB, String.format("%d", config.getActiveTab()));

		// sicherstellen, dass gespeicherte Konfiguration korrekte Einträge enthält
		final String content = out.toString();
		for (final Entry<String, String> testCase : testCases.entrySet()) {
			assertTrue(content, content.contains(String.format("<entry key=\"%s\">%s</entry>", testCase.getKey(), testCase.getValue())));
		}
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
		final Config emptyConfig = new ConfigStorage().loadConfig(in, true);

		// sicherstellen, dass geladene Konfiguration der Standardkonfiguration entspricht
		assertEquals(getConfigAsString(ConfigManager.getNewDefaultConfig()), getConfigAsString(emptyConfig));
	}

	/**
	 * Testet das Laden einer Konfiguration mit ungültige Werten.
	 */
	@Test
	public final void testConfigWithInvalidValues() {
		// ungültige Werte festlegen
		final Properties props = new Properties();
		props.put(ConfigStorage.PROP_LOCALE, "blabla");
		props.put(ConfigStorage.PROP_MINIMIZE_TO_TRAY, "blabla");
		props.put(ConfigStorage.PROP_STOPWATCH_SHOW_MILLIS, "blabla");
		props.put(ConfigStorage.PROP_ACTIVE_TAB, "blabla");

		// Konfiguration speichern
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			props.storeToXML(out, null);
		} catch (final IOException e) {
			fail(e.getLocalizedMessage());
		}

		// gespeicherte Konfiguration laden, sollte dabei mit Standardwerten gefüllt werden
		final ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
		final Config emptyConfig = new ConfigStorage().loadConfig(in, true);

		// erwartete Konfiguration
		final Config expectedConfig = ConfigManager.getNewDefaultConfig();
		expectedConfig.setMinimizeToTray(false);
		expectedConfig.setStopwatchShowMilliseconds(false);

		// sicherstellen, dass geladene Konfiguration der erwarteten Konfiguration entspricht
		assertEquals(getConfigAsString(expectedConfig), getConfigAsString(emptyConfig));
	}

	/**
	 * Testet das Laden einer ungültigen Konfiguration.
	 */
	@Test
	public final void testInvalidConfig() {
		// Konfiguration speichern
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			out.write("kein gültiges XML-Dokument".getBytes("UTF-8"));
		} catch (final IOException e) {
			fail(e.getLocalizedMessage());
		}

		// gespeicherte Konfiguration laden, sollte dabei mit Standardwerten gefüllt werden
		final ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
		final Config emptyConfig = new ConfigStorage().loadConfig(in, true);

		// sicherstellen, dass geladene Konfiguration der Standardkonfiguration entspricht
		assertEquals(getConfigAsString(ConfigManager.getNewDefaultConfig()), getConfigAsString(emptyConfig));
	}

	/**
	 * Testet, ob eine Konfiguration, nachdem sie gespeichert wurde, als nicht-geändert gilt.
	 */
	@Test
	public final void testSavingConfigWillMarkItAsUnchanged() {
		// Standardkonfiguration erzeugen
		final Config config = ConfigManager.getNewDefaultConfig();
		config.setChanged(true);

		// Konfiguration speichern
		new ConfigStorage().saveConfig(config, new ByteArrayOutputStream());

		// sicherstellen, dass gespeicherte Konfiguration als nicht-geändert gilt 
		assertFalse(config.isChanged());
	}

	/**
	 * Testet, ob eine Konfiguration, nachdem sie geladen wurde, als nicht-geändert gilt.
	 */
	@Test
	public final void testLoadingConfigWillMarkItAsUnchanged() {
		// leere Konfiguration speichern
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			new Properties().storeToXML(out, null);
		} catch (final IOException e) {
			fail(e.getLocalizedMessage());
		}

		// gespeicherte Konfiguration laden, sollte dabei mit Standardwerten gefüllt werden
		final ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
		final Config emptyConfig = new ConfigStorage().loadConfig(in, true);

		// sicherstellen, dass geladene Konfiguration als nicht-geändert gilt 
		assertFalse(emptyConfig.isChanged());
	}

	/**
	 * @param config Konfiguration
	 * @return String-Repräsentation der Konfiguration
	 */
	private String getConfigAsString(final Config config) {
		return new ConfigStorage().getConfigAsProperties(config).toString();
	}

}
