package rmblworx.tools.timey;

import java.util.concurrent.TimeUnit;

import rmblworx.tools.timey.vo.TimeDescriptor;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Einfach ausgelegte Schnittstelle für die Implementierung eines Countdown-Objekts das die konkrete Zeitmessung
 * implementiert.
 * 
 * @author mmatthies
 */
interface ICountdownTimer {

	/**
	 * Setzt den Startwert für den Countdown.
	 *
	 * @param descriptor
	 *            Referenz auf das Werteobjekt das die darzustellende Zeit kapselt.
	 * @return true wenn die Ausgangszeit erfolgreich gesetzt werden konnte.
	 */
	Boolean setCountdownTime(TimeDescriptor descriptor);

	/**
	 * Startet den Countdown.
	 *
	 * @param delayPerThread
	 *            Setzt die Verzögerung pro Thread. Die Maszeinheit
	 *            wird mittels des TimeUnit-Enum gesetzt.
	 * @param timeUnit
	 *            Setzt die zu verwendende Zeiteinheit für die
	 *            wiederholte Aktualisierung des Werteobjekts.
	 * @return Referenz auf das Wertobjekt das die darzustellende Zeit
	 *         kapselt. Es handelt sich hierbei um das ueber
	 *         {@link #setCountdownTime(rmblworx.tools.timey.vo.TimeDescriptor)} uebergebene Objekt.
	 */
	TimeDescriptor startCountdown(int delayPerThread, TimeUnit timeUnit);

	/**
	 * Stoppt den Countdown und beendet den/die gestarteten Threads.
	 *
	 * @return true wenn erfolgreich die Uhr angehalten werden konnte sonst false.
	 */
	Boolean stopCountdown();
}
