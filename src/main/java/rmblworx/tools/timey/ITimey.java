package rmblworx.tools.timey;

/**
 * @author mmatthies
 */
interface ITimey extends IAlarm, ICountdown, IStopwatch {

	/**
	 * Liefert die Version von timey.
	 * 
	 * @return die aktuelle Version.
	 */
	String getVersion();
}
