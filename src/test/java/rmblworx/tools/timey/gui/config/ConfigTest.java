package rmblworx.tools.timey.gui.config;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Locale;

import org.junit.Test;

/**
 * Test für die GUI-Konfiguration.
 * 
 * @author Christian Raue <christian.raue@gmail.com>
 * @copyright 2014 Christian Raue
 * @license http://opensource.org/licenses/mit-license.php MIT License
 */
public class ConfigTest {

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
