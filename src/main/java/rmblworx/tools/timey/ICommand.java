package rmblworx.tools.timey;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Sehr allgemein gehaltene Schnittstellenbeschreibung fuer Kommandoimplementierungen.
 * @author mmatthies
 */
interface ICommand {

	/**
	 * Ist von der konkreten Implementierung umzusetzen.
	 *
	 * @param <T>
	 *            wird von der jeweiligen konkreten Implementierung vorgegeben.
	 * @return T wird von der jeweiligen konkreten Implementierung vorgegeben.
	 */
	<T> T execute();
}
