/**
 * 
 */
package rmblworx.tools.timey;

/**
 * @author mmatthies
 * 
 */
interface ITimey extends IAlarm, ICountdown, IStopwatch {

	String getVersion();
}
