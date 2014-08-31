package rmblworx.tools.timey;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Helferklasse mit häufig benötigten Funktionen.
 *
 * @author mmatthies
 */
public final class TimeyUtils {

	/**
	 * Linux-Kennung.
	 */
	private static final String LINUX = "linux";
	/**
	 * OS X-Kennung.
	 */
	private static final String MAC_OS_X = "mac os x";
	/**
	 * Konstante für das Property zum erfragen des Betriebsystems.
	 */
	private static final String SYSTEM_PROPERTY_OS_NAME = "os.name";
	/**
	 * Windows-Kennung.
	 */
	private static final String WINDOWS = "windows";

	/**
	 * Liefert den Namen des Betriebsystems.
	 *
	 * @return Namenkuerzel des OS.
	 */
	public static String getOsName() {
		return System.getProperty(SYSTEM_PROPERTY_OS_NAME).toLowerCase();
	}

	/**
	 * Prueft die Zeichenkette ob sie {@code Länge == 0} aufweist.
	 *
	 * @param string
	 *            zu pruefende Zeichenkette.
	 * @return true wenn {@code Länge == 0}.
	 */
	public static boolean isEmpty(final String string) {
		return string.length() < 1;
	}

	/**
	 * Liefert die Antwort darauf, ob timey gerade auf einem Linux Betriebssystem ausgefuehrt wird.
	 *
	 * @return true wenn Linux sonst false
	 */
	public static boolean isLinuxSystem() {
		final String osName = getOsName();
		return osName.indexOf(LINUX) != -1;
	}

	/**
	 * Prueft die Zeichenkette ob sie {@code null} referenziert.
	 *
	 * @param string
	 *            zu pruefende Zeichenkette.
	 * @return true wenn {@code null} referenziert wird.
	 */
	public static boolean isNull(final String string) {
		return string == null;
	}

	/**
	 * Prueft die Zeichenkette(n) ob sie {@code null} referenzieren oder die {@code Länge == 0} aufweisen.
	 *
	 * @param string
	 *            zu pruefende Zeichenkette.
	 * @return true wenn auch nur eine Zeichenkette {@code null} oder die {@code Länge == 0} aufweist.
	 */
	public static boolean isNullOrEmpty(final String... string) {
		boolean result = false;

		for (final String str : string) {
			if (isNull(str) || isEmpty(str)) {
				result = true;
				break;
			}
		}

		return result;
	}

	/**
	 * Liefert die Antwort darauf, ob timey gerade auf einem OS X-Betriebssystem ausgefuehrt wird.
	 *
	 * @return true wenn OS X sonst false
	 */
	public static boolean isOSXSystem() {
		final String osName = getOsName();
		return osName.indexOf(MAC_OS_X) != -1;
	}

	/**
	 * Liefert die Antwort darauf, ob timey gerade auf einem Windows-Betriebssystem ausgefuehrt wird.
	 *
	 * @return true wenn Windows sonst false
	 */
	public static boolean isWindowsSystem() {
		final String osName = getOsName();
		return osName.indexOf(WINDOWS) != -1;
	}

	/**
	 * Konstruktor.
	 */
	private TimeyUtils() {
	}
}
