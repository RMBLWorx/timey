package rmblworx.tools.timey;

import rmblworx.tools.timey.vo.TimeDescriptor;

/**
 * @author mmatthies
 */
interface IStopwatch {

	Boolean resetStopwatch();

	TimeDescriptor startStopwatch();

	Boolean stopStopwatch();

}
