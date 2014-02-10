package rmblworx.tools.timey.gui;

import java.util.Locale;

public class Config {

	private static Config instance;

	private Locale locale = Locale.GERMAN;
	private boolean minimizeToTray = false;

	public static Config getInstance() {
		if (instance == null) {
			instance = new Config();
		}

		return instance;
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

}
