/**
 * 
 */
package rmblworx.tools.timey;

import rmblworx.tools.timey.vo.TimeDescriptor;


/**
 * @author mmatthies
 *
 */
interface IStopwatch {
	TimeDescriptor startStopwatch();
	Boolean stopStopwatch();
	Boolean resetStopwatch();
}
