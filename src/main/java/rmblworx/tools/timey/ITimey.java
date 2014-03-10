package rmblworx.tools.timey;

/**
 * @author mmatthies
 */
public interface ITimey extends IAlarm, ICountdown, IStopwatch {

	/**
	 * Liefert die Version von timey.
	 * 
	 * @return die aktuelle Version.
	 * @throws Exception wenn es mehr als ein jar-Archiv geben sollte ist eine eindeutige Versionsbenennung nicht moeglich.
	 * @throws IllegalArgumentException wenn die Laenge der Zeichenkette kleiner 1 oder wenn {@code null} adressiert wird.
	 */
	String getVersion(String globPattern) throws Exception, IllegalArgumentException;
}
