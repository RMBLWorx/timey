package rmblworx.tools.timey;

import rmblworx.tools.timey.exception.EmptyArgumentException;
import rmblworx.tools.timey.exception.NullArgumentException;

/**
 * @author mmatthies
 */
public interface ITimey extends IAlarm, ICountdown, IStopwatch {

	/**
	 * Liefert die Version von timey.
	 * 
	 * @return die aktuelle Version.
	 * @throws Exception wenn es mehr als ein jar-Archiv geben sollte ist eine eindeutige Versionsbenennung nicht moeglich.
	 * @throws NullArgumentException wenn {@code null} adressiert wird
	 * @throws EmptyArgumentException wenn die Laenge der Zeichenkette kleiner 1 oder
	 */
	String getVersion(String globPattern) throws Exception, NullArgumentException, EmptyArgumentException;
}
