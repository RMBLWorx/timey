package rmblworx.tools.timey.gui.config;

import java.util.Locale;

/**
 * Konfiguration.
 * 
 * @author Christian Raue <christian.raue@gmail.com>
 * @copyright 2014 Christian Raue
 * @license http://opensource.org/licenses/mit-license.php MIT License
 */
public class Config {

	/**
	 * Verfügbare Sprachen zur Auswahl in den Optionen.
	 */
	public static final Locale[] AVAILABLE_LOCALES = {Locale.GERMAN, Locale.ENGLISH};

	/**
	 * Sprache.
	 */
	private Locale locale = Locale.GERMAN;

	/**
	 * Ob die Anwendung ins System-Tray minimiert werden soll.
	 */
	private boolean minimizeToTray = false;

	/**
	 * Ob für die Stoppuhr auch der Millisekunden-Anteil sichtbar sein soll.
	 */
	private boolean stopwatchShowMilliseconds = true;

	/**
	 * Aktiver Tab.
	 */
	private int activeTab = 0;

	public void setLocale(final Locale locale) {
		this.locale = locale;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setMinimizeToTray(final boolean minimizeToTray) {
		this.minimizeToTray = minimizeToTray;
	}

	public boolean isMinimizeToTray() {
		return minimizeToTray;
	}

	public void setStopwatchShowMilliseconds(final boolean stopwatchShowMilliseconds) {
		this.stopwatchShowMilliseconds = stopwatchShowMilliseconds;
	}

	public boolean isStopwatchShowMilliseconds() {
		return stopwatchShowMilliseconds;
	}

	public int getActiveTab() {
		return activeTab;
	}

	public void setActiveTab(final int activeTab) {
		this.activeTab = activeTab;
	}

}
