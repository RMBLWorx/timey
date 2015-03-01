package rmblworx.tools.timey;

import java.util.concurrent.TimeUnit;

import rmblworx.tools.timey.vo.TimeDescriptor;

/*
 * Copyright 2014-2015 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Einfach ausgelegte Schnittstelle für die Implementierung eines Stoppuhr-Objekts.
 *
 * @author mmatthies
 */
interface ITimer {

	/**
	 * Setzt die Stoppuhr auf Null.
	 *
	 * @return true wenn die Uhr erfolgreich auf Anfang gesetzt werden konnte sonst false.
	 */
	Boolean resetStopwatch();

	/**
	 * Startet den Stopvorgang.
	 *
	 * @param delayPerThread
	 *            Setzt die Verzögerung pro Thread. Die Maßeinheit
	 *            wird mittels des TimeUnit-Enum gesetzt.
	 * @param timeUnit
	 *            Setzt die zu verwendende Zeiteinheit für die
	 *            wiederholte Aktualisierung des Werteobjekts.
	 * @return Referenz auf das Wertobjekt das die darzustellende Zeit
	 *         kapselt. Es handelt sich hierbei um das im Konstruktor
	 *         uebergebene Objekt.
	 */
	TimeDescriptor startStopwatch(int delayPerThread, TimeUnit timeUnit);

	/**
	 * Stoppt die Zeitmessung und beendet den/die gestarteten Threads.
	 *
	 * @return true wenn erfolgreich die Uhr angehalten werden konnte sonst false.
	 */
	Boolean stopStopwatch();

	/**
	 * Versetzt beim ersten Aufruf die Stoppuhr in den TIME-Modus (Zwischenzeitmodus). Die im TimeDescriptor gelieferte
	 * Zeit wird eingefroren und die Zeitnahme im Hintergrund fortgefuehrt. Beim erneuten Aufruf liefert die Methode
	 * wieder die aktuelle Zeitmessung.
	 *
	 * @return true wenn Time-Modus aktiviert sonst false.
	 */
	Boolean toggleTimeModeInStopwatch();

}
