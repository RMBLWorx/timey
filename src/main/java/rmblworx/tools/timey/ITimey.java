package rmblworx.tools.timey;

import rmblworx.tools.timey.event.TimeyEventListener;
import rmblworx.tools.timey.exception.EmptyArgumentException;
import rmblworx.tools.timey.exception.NullArgumentException;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Schnittstellenbeschreibung des timey-Systems.
 * @author mmatthies
 */
public interface ITimey extends IAlarm, ICountdown, IStopwatch {

	/**
	 * Liefert die Version von timey.
	 * 
	 * @param globPattern
	 *            Pattern zur Beschreibung des zu suchenden Jar-Archivs.
	 * @return die aktuelle Version.
	 * @throws IllegalStateException
	 *             wenn es mehr als ein jar-Archiv geben sollte ist eine eindeutige Versionsbenennung nicht moeglich.
	 * @throws NullArgumentException
	 *             wenn {@code null} adressiert wird
	 * @throws EmptyArgumentException
	 *             wenn die Laenge der Zeichenkette kleiner Eins
	 */
	String getVersion(String globPattern) throws IllegalStateException, NullArgumentException, EmptyArgumentException;

	/**
	 * Registriert den uebergebenen Event-Listener.
	 * 
	 * @param timeyEventListener
	 *            zu benachrichtigender Event-Listener
	 */
	void addEventListener(TimeyEventListener timeyEventListener);
}
