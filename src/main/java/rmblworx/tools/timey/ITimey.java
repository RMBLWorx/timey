/**
 * 
 */
package rmblworx.tools.timey;

/**
 * @author mmatthies
 * 
 */
interface ITimey extends IAlarm, ICountdown, IStopWatch {

	String getVersion();
}
