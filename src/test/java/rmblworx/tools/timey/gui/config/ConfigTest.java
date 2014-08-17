package rmblworx.tools.timey.gui.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Test für die GUI-Konfiguration.
 * @author Christian Raue {@literal <christian.raue@gmail.com>}
 */
public class ConfigTest {

	/**
	 * Systemsprache vor Ausführung von Tests, um diese danach wiederherstellen zu können.
	 */
	private Locale previousLocale;

	@Before
	public final void setUp() {
		previousLocale = Locale.getDefault();
		Locale.setDefault(Locale.GERMAN);
	}

	@After
	public final void tearDown() {
		Locale.setDefault(previousLocale);
	}

	/**
	 * Testet {@link Config#getDefaultLocale()}.
	 */
	@Test
	public final void testGetDefaultLocale() {
		final Map<Locale, Locale> testCases = new HashMap<>(4);
		testCases.put(new Locale("blah"), Locale.ENGLISH);
		testCases.put(Locale.GERMANY, Locale.GERMAN);
		testCases.put(Locale.GERMAN, Locale.GERMAN);
		testCases.put(Locale.ENGLISH, Locale.ENGLISH);

		for (final Entry<Locale, Locale> testCase : testCases.entrySet()) {
			Locale.setDefault(testCase.getKey());
			assertEquals(testCase.getValue(), ConfigManager.getNewDefaultConfig().getDefaultLocale());
		}
	}

	/**
	 * Testet den Änderungszustand der Konfiguration.
	 * Stellt für jedes Attribut sicher,
	 * <ul>
	 * 	<li>dass die Konfiguration durch Setzen des gleichen Wertes als nicht geändert gilt und</li>
	 * 	<li>dass die Konfiguration durch Setzen eines anderen Wertes als geändert gilt.</li>
	 * </ul>
	 */
	@Test
	public final void testChanged() {
		Config config;

		config = ConfigManager.getNewDefaultConfig();
		config.setLocale(config.getLocale());
		assertFalse(config.isChanged());
		config.setLocale(Locale.ENGLISH);
		assertTrue(config.isChanged());

		config = ConfigManager.getNewDefaultConfig();
		config.setMinimizeToTray(config.isMinimizeToTray());
		assertFalse(config.isChanged());
		config.setMinimizeToTray(!config.isMinimizeToTray());
		assertTrue(config.isChanged());

		config = ConfigManager.getNewDefaultConfig();
		config.setStopwatchShowMilliseconds(config.isStopwatchShowMilliseconds());
		assertFalse(config.isChanged());
		config.setStopwatchShowMilliseconds(!config.isStopwatchShowMilliseconds());
		assertTrue(config.isChanged());

		config = ConfigManager.getNewDefaultConfig();
		config.setActiveTab(config.getActiveTab());
		assertFalse(config.isChanged());
		config.setActiveTab(config.getActiveTab() + 1);
		assertTrue(config.isChanged());
	}

}
