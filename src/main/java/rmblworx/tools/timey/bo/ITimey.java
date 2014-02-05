/**
 * 
 */
package rmblworx.tools.timey.bo;

import rmblworx.tools.timey.vo.TimeDescriptor;


/**
 * @author mmatthies
 *
 */
public interface ITimey {
	
	boolean start();
	boolean stop();
	TimeDescriptor setAlarm(TimeDescriptor td);
	TimeDescriptor setCountdown(TimeDescriptor td);

}
