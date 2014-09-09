package rmblworx.tools.timey.gui.config;

import java.util.Arrays;
import java.util.Locale;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * GUI-Konfiguration.
 * @author Christian Raue {@literal <christian.raue@gmail.com>}
 */
public class Config {

	/**
	 * Unterstützte Sprachen zur Auswahl in den Optionen.
	 */
	public static final Locale[] AVAILABLE_LOCALES = {Locale.GERMAN, Locale.ENGLISH};

	/**
	 * Fallback, falls eine nicht-unterstützte Sprache angefordert wurde.
	 */
	public static final Locale FALLBACK_LOCALE = Locale.ENGLISH;

	/**
	 * Ob die Konfiguration geändert wurde.
	 */
	private boolean changed = false;

	/**
	 * Sprache.
	 */
	private Locale locale = getDefaultLocale();

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

	/**
	 * @return Systemsprache, falls unterstützt, sonst Fallback
	 */
	public final Locale getDefaultLocale() {
		final Locale defaultLocale = Locale.forLanguageTag(Locale.getDefault().getLanguage());

		if (Arrays.asList(AVAILABLE_LOCALES).contains(defaultLocale)) {
			return defaultLocale;
		}

		return FALLBACK_LOCALE;
	}

	/**
	 * @param changed Ob die Konfiguration geändert wurde.
	 */
	public final void setChanged(final boolean changed) {
		this.changed = changed;
	}

	/**
	 * @return Ob die Konfiguration geändert wurde.
	 */
	public final boolean isChanged() {
		return changed;
	}

	/**
	 * @param locale Sprache.
	 */
	public final void setLocale(final Locale locale) {
		if (this.locale != locale) {
			changed = true;
		}

		this.locale = locale;
	}

	/**
	 * @return Sprache.
	 */
	public final Locale getLocale() {
		return locale;
	}

	/**
	 * @param minimizeToTray Ob die Anwendung ins System-Tray minimiert werden soll.
	 */
	public final void setMinimizeToTray(final boolean minimizeToTray) {
		if (this.minimizeToTray != minimizeToTray) {
			changed = true;
		}

		this.minimizeToTray = minimizeToTray;
	}

	/**
	 * @return Ob die Anwendung ins System-Tray minimiert werden soll.
	 */
	public final boolean isMinimizeToTray() {
		return minimizeToTray;
	}

	/**
	 * @param stopwatchShowMilliseconds Ob für die Stoppuhr auch der Millisekunden-Anteil sichtbar sein soll.
	 */
	public final void setStopwatchShowMilliseconds(final boolean stopwatchShowMilliseconds) {
		if (this.stopwatchShowMilliseconds != stopwatchShowMilliseconds) {
			changed = true;
		}

		this.stopwatchShowMilliseconds = stopwatchShowMilliseconds;
	}

	/**
	 * @return Ob für die Stoppuhr auch der Millisekunden-Anteil sichtbar sein soll.
	 */
	public final boolean isStopwatchShowMilliseconds() {
		return stopwatchShowMilliseconds;
	}

	/**
	 * @param activeTab Aktiver Tab.
	 */
	public final void setActiveTab(final int activeTab) {
		if (this.activeTab != activeTab) {
			changed = true;
		}

		this.activeTab = activeTab;
	}

	/**
	 * @return Aktiver Tab.
	 */
	public final int getActiveTab() {
		return activeTab;
	}

}
