package rmblworx.tools.timey;

/**
 * @author mmatthies
 */
interface ITimey extends IAlarm, ICountdown, IStopwatch {

	/**
	 * @return die aktuelle Version.
	 */
	String getVersion();

}
