package rmblworx.tools.timey;

import rmblworx.tools.timey.vo.TimeDescriptor;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * @author mmatthies
 */
interface IStopwatch {

	/**
	 * Zuruecksetzen der Stoppuhr auf Ausgangszustand.
	 *
	 * @return true wenn erfolgreich sonst false
	 */
	Boolean resetStopwatch();

	/**
	 * Startet die Stoppuhr.
	 *
	 * @return Zeitwertobjekt das die gemessene Zeit kapselt.
	 */
	TimeDescriptor startStopwatch();

	/**
	 * Stoppt die Uhr und unterbricht die Zeitmessung.
	 *
	 * @return true wenn erfolgreich gestoppt sonst false
	 */
	Boolean stopStopwatch();

	/**
	 * Schaltet zwischen aktiviertem Time-Modus hin und her.
	 * @return true wenn Time-Modus aktiviert sonst false
	 */
	Boolean toggleTimeModeInStopwatch();

}
