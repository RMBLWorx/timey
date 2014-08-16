package rmblworx.tools.timey.gui.config;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Verwaltung der Konfiguration per Singleton.
 * @author Christian Raue {@literal <christian.raue@gmail.com>}
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
