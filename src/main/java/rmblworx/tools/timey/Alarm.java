/**
 * 
 */
package rmblworx.tools.timey;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import rmblworx.tools.timey.vo.TimeDescriptor;

/**
 * PatternBox: "Receiver" implementation.
 * <ul>
 * <li>knows how to perform the operations associated with carrying out a request. Any class may serve as a Receiver.</li>
 * </ul>
 * 
 * @author Dirk Ehms, <a href="http://www.patternbox.com">www.patternbox.com</a>
 * @author mmatthies
 */
final class Alarm implements IAlarm {
	private static final Logger LOG = LogManager.getLogger(Alarm.class);

	/**
	 * This construtor creates a Receiver instance.
	 */
	public Alarm() {
		super();
	}

	/**
	 * This method performs an action.
	 */
	@Override
	public TimeDescriptor setAlarmTime(final TimeDescriptor descriptor) {
		LOG.entry();

		// TODO Write your action code here ...
		System.out.println("setTime");

		LOG.exit();

		return descriptor;
	}

	/**
	 * This method performs an action.
	 */
	@Override
	public Boolean turnOff() {
		LOG.entry();

		// TODO Write your action code here ...
		System.out.println("TurnOff");

		LOG.exit();
		return Boolean.TRUE;
	}

	/**
	 * This method performs an action.
	 */
	@Override
	public Boolean turnOn() {
		LOG.entry();

		// TODO Write your action code here ...
		System.out.println("TurnOn");

		LOG.exit();
		return Boolean.TRUE;
	}

}
