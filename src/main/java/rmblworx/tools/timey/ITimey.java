package rmblworx.tools.timey;

import rmblworx.tools.timey.event.TimeyEventListener;

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
	 * @return die aktuelle Version.
	 */
	String getVersion();

	/**
	 * Registriert den uebergebenen Event-Listener.
	 *
	 * @param timeyEventListener
	 *            zu benachrichtigender Event-Listener
	 */
	void addEventListener(TimeyEventListener timeyEventListener);
}
