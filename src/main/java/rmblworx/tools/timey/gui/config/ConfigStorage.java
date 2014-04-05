package rmblworx.tools.timey.gui.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Locale;
import java.util.Properties;

/**
 * Speichern/Laden der Konfiguration.
 * 
 * @author Christian Raue <christian.raue@gmail.com>
 * @copyright 2014 Christian Raue
 * @license http://opensource.org/licenses/mit-license.php MIT License
 */
public class ConfigStorage {

	public static final String PROP_LOCALE = "locale";
	public static final String PROP_MINIMIZE_TO_TRAY = "minimizeToTray";
	public static final String PROP_STOPWATCH_SHOW_MILLIS = "stopwatchShowMilliseconds";
	public static final String PROP_ACTIVE_TAB = "activeTab";

	/**
	 * @param config zu speichernde Konfiguration
	 * @param outputStream
	 */
	public final void saveConfig(final Config config, final OutputStream outputStream) {
		final Properties props = getConfigAsProperties(config);

		try {
			props.storeToXML(outputStream, null);
			config.setChanged(false);
		} catch (final IOException e) {
			System.err.println(e.getLocalizedMessage());
		}
	}

	/**
	 * @param inputStream
	 * @return geladene Konfiguration
	 */
	public final Config loadConfig(final InputStream inputStream) {
		return loadConfig(inputStream, false);
	}

	/**
	 * @param inputStream
	 * @param suppressErrors ob Fehlermeldungen unterdrückt werden sollen
	 * @return geladene Konfiguration
	 */
	public final Config loadConfig(final InputStream inputStream, final boolean suppressErrors) {
		try {
			final Properties props = new Properties();
			props.loadFromXML(inputStream);
			final Config config = getPropertiesAsConfig(props);
			config.setChanged(false);
			return config;
		} catch (final IOException e) {
			if (!suppressErrors) {
				System.err.println("Error while trying to load the config file: " + e.getLocalizedMessage());
			}
			return ConfigManager.getNewDefaultConfig();
		}
	}

	/**
	 * @param config Konfiguration
	 * @return Konfiguration als {@code Properties}-Objekt
	 */
	public final Properties getConfigAsProperties(final Config config) {
		final Properties props = new Properties();
		props.setProperty(PROP_LOCALE, config.getLocale().toString());
		props.setProperty(PROP_MINIMIZE_TO_TRAY, Boolean.toString(config.isMinimizeToTray()));
		props.setProperty(PROP_STOPWATCH_SHOW_MILLIS, Boolean.toString(config.isStopwatchShowMilliseconds()));
		props.setProperty(PROP_ACTIVE_TAB, Integer.toString(config.getActiveTab()));

		return props;
	}

	/**
	 * @param props Konfiguration als {@code Properties}-Objekt
	 * @return Konfiguration
	 */
	public final Config getPropertiesAsConfig(final Properties props) {
		final Config config = ConfigManager.getNewDefaultConfig();

		final String propLocale = props.getProperty(PROP_LOCALE);
		if (propLocale != null) {
			final Locale locale = new Locale(propLocale);
			if (Arrays.asList(Config.AVAILABLE_LOCALES).contains(locale)) {
				config.setLocale(locale);
			}
		}

		final String propMinimizeToTray = props.getProperty(PROP_MINIMIZE_TO_TRAY);
		if (propMinimizeToTray != null) {
			config.setMinimizeToTray(Boolean.parseBoolean(propMinimizeToTray));
		}

		final String propStopwatchShowMilliseconds = props.getProperty(PROP_STOPWATCH_SHOW_MILLIS);
		if (propStopwatchShowMilliseconds != null) {
			config.setStopwatchShowMilliseconds(Boolean.parseBoolean(propStopwatchShowMilliseconds));
		}

		try {
			config.setActiveTab(Integer.parseInt(props.getProperty(PROP_ACTIVE_TAB)));
		} catch (final NumberFormatException e) {
			// ignorieren
		}

		return config;
	}

}
