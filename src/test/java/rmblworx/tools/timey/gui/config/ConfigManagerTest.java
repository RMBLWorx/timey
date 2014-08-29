package rmblworx.tools.timey.gui.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

import org.junit.Test;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Tests fürs Verwalten der GUI-Konfiguration.
 * @author Christian Raue {@literal <christian.raue@gmail.com>}
 */
public class ConfigManagerTest {

	/**
	 * Testet das Festlegen/Abrufen der aktiven Konfiguration.
	 */
	@Test
	public final void testSetGetCurrentConfig() {
		final Config expectedConfig = new Config();

		ConfigManager.setCurrentConfig(expectedConfig);

		assertSame(expectedConfig, ConfigManager.getCurrentConfig());
	}

	/**
	 * Testet das Abrufen der aktiven Konfiguration, ohne zuvor eine festgelegt zu haben.
	 */
	@Test
	public final void testGetCurrentConfig() {
		final Config currentConfig = ConfigManager.getCurrentConfig();

		// muss inhaltlich mit Standardkonfiguration übereinstimmen
		assertEquals(getConfigAsString(new Config()), getConfigAsString(currentConfig));

		// mehrmaliges Abrufen muss dieselbe Instanz liefern
		assertSame(currentConfig, ConfigManager.getCurrentConfig());
	}

	/**
	 * Testet das Abrufen einer neuen Standardkonfiguration.
	 */
	@Test
	public final void testGetNewDefaultConfig() {
		final Config defaultConfig = ConfigManager.getNewDefaultConfig();

		// muss inhaltlich mit Standardkonfiguration übereinstimmen
		assertEquals(getConfigAsString(new Config()), getConfigAsString(defaultConfig));

		// mehrmaliges Abrufen muss jeweils neue Instanz liefern
		assertNotSame(defaultConfig, ConfigManager.getNewDefaultConfig());
	}

	/**
	 * @param config Konfiguration
	 * @return String-Repräsentation der Konfiguration
	 */
	private String getConfigAsString(final Config config) {
		return new ConfigStorage().getConfigAsProperties(config).toString();
	}

}
