package rmblworx.tools.timey.gui.config;

/**
 * Verwaltung der Konfiguration per Singleton.
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
	public static synchronized Config getCurrentConfig() {
		if (currentConfig == null) {
			currentConfig = getNewDefaultConfig();
		}

		return currentConfig;
	}

	/**
	 * @return neue Instanz der Standardkonfiguration
	 */
	public static Config getNewDefaultConfig() {
		return new Config();
	}

	/**
	 * Privater Konstruktor.
	 */
	private ConfigManager() {
	}

}
