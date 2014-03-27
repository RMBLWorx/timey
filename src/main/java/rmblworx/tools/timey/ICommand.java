package rmblworx.tools.timey;

/**
 * Sehr allgemein gehaltene Schnittstellenbeschreibung fuer Kommandoimplementierungen.
 * 
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
