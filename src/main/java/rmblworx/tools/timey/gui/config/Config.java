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

	public static final Locale[] AVAILABLE_LOCALES = {Locale.GERMAN, Locale.ENGLISH};

	private Locale locale = Locale.GERMAN;
	private boolean minimizeToTray = false;
	private boolean stopwatchShowMilliseconds = true;

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

}
