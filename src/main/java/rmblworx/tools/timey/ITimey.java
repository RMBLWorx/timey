package rmblworx.tools.timey;

import rmblworx.tools.timey.event.TimeyEventListener;

/*
 * Copyright 2014-2015 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Schnittstellenbeschreibung des timey-Systems.
 * 
 * @author mmatthies
 */
public interface ITimey extends IAlarm, ICountdown, IStopwatch {

	/**
	 * Registriert den uebergebenen Event-Listener.
	 *
	 * @param timeyEventListener
	 *            zu benachrichtigender Event-Listener
	 * @return true wenn erfolgreich sonst false
	 */
	Boolean addEventListener(TimeyEventListener timeyEventListener);

	/**
	 * Liefert die Version von timey.
	 *
	 * @return die aktuelle Version.
	 */
	String getVersion();
}
