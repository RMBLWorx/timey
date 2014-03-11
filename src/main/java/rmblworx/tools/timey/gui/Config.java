package rmblworx.tools.timey.gui;

import java.util.Locale;

public final class Config {

	private static Config instance;

	private Locale locale = Locale.GERMAN;
	private boolean minimizeToTray = false;
	private boolean stopwatchShowMilliseconds = true;

	public static Config getInstance() {
		if (instance == null) {
			instance = new Config();
		}

		return instance;
	}

	private Config() {
	}

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
