/**
 * 
 */
package rmblworx.tools.timey.bo;

/**
 * @author mmatthies
 * 
 */
interface ITimey extends IAlarm, ICountdown, IStopWatch {

	String getVersion();
}
