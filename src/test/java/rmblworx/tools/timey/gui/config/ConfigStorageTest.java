package rmblworx.tools.timey.gui.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
	 * @throws IOException 
	 */
	@Test
	public final void testSaveDefaultConfig() throws IOException {
		// Standardkonfiguration erzeugen
		final Config config = ConfigManager.getNewDefaultConfig();

		// Konfiguration speichern
		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		new ConfigStorage().saveConfig(config, outputStream);

		final Map<String, String> testCases = new HashMap<>(4);
		testCases.put(ConfigStorage.PROP_LOCALE, String.format("%s", config.getLocale()));
		testCases.put(ConfigStorage.PROP_MINIMIZE_TO_TRAY, String.format("%s", config.isMinimizeToTray()));
		testCases.put(ConfigStorage.PROP_STOPWATCH_SHOW_MILLIS, String.format("%s", config.isStopwatchShowMilliseconds()));
		testCases.put(ConfigStorage.PROP_ACTIVE_TAB, String.format("%d", config.getActiveTab()));

		// sicherstellen, dass gespeicherte Konfiguration korrekte Einträge enthält
		final String content = outputStream.toString();
		for (final Entry<String, String> testCase : testCases.entrySet()) {
			assertTrue(content, content.contains(String.format("<entry key=\"%s\">%s</entry>", testCase.getKey(), testCase.getValue())));
		}
	}

	/**
	 * Testet das Laden einer leeren Konfiguration und Befüllung mit Standardwerten.
	 * @throws IOException 
	 */
	@Test
	public final void testLoadEmptyConfig() throws IOException {
		// leere Konfiguration speichern
		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			new Properties().storeToXML(outputStream, null);
		} catch (final IOException e) {
			fail(e.getLocalizedMessage());
		}

		// gespeicherte Konfiguration laden, sollte dabei mit Standardwerten gefüllt werden
		final Config emptyConfig = new ConfigStorage().loadConfig(redirectOutputToInput(outputStream));

		// sicherstellen, dass geladene Konfiguration der Standardkonfiguration entspricht
		assertEquals(getConfigAsString(ConfigManager.getNewDefaultConfig()), getConfigAsString(emptyConfig));
	}

	/**
	 * Testet das Laden einer Konfiguration mit ungültige Werten.
	 * @throws IOException 
	 */
	@Test
	public final void testConfigWithInvalidValues() throws IOException {
		// ungültige Werte festlegen
		final Properties props = new Properties();
		final String invalidValue = "blabla";
		props.put(ConfigStorage.PROP_LOCALE, invalidValue);
		props.put(ConfigStorage.PROP_MINIMIZE_TO_TRAY, invalidValue);
		props.put(ConfigStorage.PROP_STOPWATCH_SHOW_MILLIS, invalidValue);
		props.put(ConfigStorage.PROP_ACTIVE_TAB, invalidValue);

		// Konfiguration speichern
		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			props.storeToXML(outputStream, null);
		} catch (final IOException e) {
			fail(e.getLocalizedMessage());
		}

		// gespeicherte Konfiguration laden, sollte dabei mit Standardwerten gefüllt werden
		final Config emptyConfig = new ConfigStorage().loadConfig(redirectOutputToInput(outputStream));

		// erwartete Konfiguration
		final Config expectedConfig = ConfigManager.getNewDefaultConfig();
		expectedConfig.setMinimizeToTray(false);
		expectedConfig.setStopwatchShowMilliseconds(false);

		// sicherstellen, dass geladene Konfiguration der erwarteten Konfiguration entspricht
		assertEquals(getConfigAsString(expectedConfig), getConfigAsString(emptyConfig));
	}

	/**
	 * Testet das Laden einer ungültigen Konfiguration.
	 * @throws IOException 
	 */
	@Test
	public final void testInvalidConfig() throws IOException {
		// Konfiguration speichern
		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			outputStream.write("kein gültiges XML-Dokument".getBytes("UTF-8"));
		} catch (final IOException e) {
			fail(e.getLocalizedMessage());
		}

		// gespeicherte Konfiguration laden, sollte dabei mit Standardwerten gefüllt werden
		final Config emptyConfig = new ConfigStorage().loadConfig(redirectOutputToInput(outputStream), true);

		// sicherstellen, dass geladene Konfiguration der Standardkonfiguration entspricht
		assertEquals(getConfigAsString(ConfigManager.getNewDefaultConfig()), getConfigAsString(emptyConfig));
	}

	/**
	 * Testet, ob eine Konfiguration, nachdem sie gespeichert wurde, als nicht-geändert gilt.
	 * @throws IOException 
	 */
	@Test
	public final void testSavingConfigWillMarkItAsUnchanged() throws IOException {
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
	 * @throws IOException 
	 */
	@Test
	public final void testLoadingConfigWillMarkItAsUnchanged() throws IOException {
		// leere Konfiguration speichern
		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			new Properties().storeToXML(outputStream, null);
		} catch (final IOException e) {
			fail(e.getLocalizedMessage());
		}

		// gespeicherte Konfiguration laden, sollte dabei mit Standardwerten gefüllt werden
		final Config emptyConfig = new ConfigStorage().loadConfig(redirectOutputToInput(outputStream));

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

	/**
	 * Lenkt den Ausgabestream auf einen Eingabestream um.
	 * @param outputStream Ausgabestream
	 * @return Eingabestream
	 */
	private InputStream redirectOutputToInput(final ByteArrayOutputStream outputStream) {
		return new ByteArrayInputStream(outputStream.toByteArray());
	}

}
