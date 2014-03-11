package rmblworx.tools.timey.gui.config;

/**
 * Verwaltung der Konfiguration.
 * 
 * @author Christian Raue <christian.raue@gmail.com>
 * @copyright 2014 Christian Raue
 * @license http://opensource.org/licenses/mit-license.php MIT License
 */
public final class ConfigManager {

	/**
	 * Aktive Konfiguration.
	 */
	private static Config currentConfig;

	/**
	 * @param config aktive Konfiguration
	 */
	public static void setCurrentConfig(final Config config) {
		currentConfig = config;
	}

	/**
	 * @return aktive Konfiguration
	 */
	public static Config getCurrentConfig() {
		if (currentConfig == null) {
			currentConfig = getDefaultConfig();
		}

		return currentConfig;
	}

	/**
	 * @return Standardkonfiguration
	 */
	public static Config getDefaultConfig() {
		return new Config();
	}

	/**
	 * Privater Konstruktor.
	 */
	private ConfigManager() {
	}

}
