/**
 * 
 */
package rmblworx.tools.timey.bo.cmd;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import rmblworx.tools.timey.bo.IAlarm;
import rmblworx.tools.timey.vo.TimeDescriptor;

/**
 * PatternBox: "Receiver" implementation.
 * <ul>
 * <li>knows how to perform the operations associated with carrying out a
 * request. Any class may serve as a Receiver.</li>
 * </ul>
 * 
 * @author Dirk Ehms, <a href="http://www.patternbox.com">www.patternbox.com</a>
 * @author mmatthies
 */
public final class Alarm implements IAlarm {
	private final Logger log = LogManager.getLogger(Alarm.class);

	/**
	 * This construtor creates a Receiver instance.
	 */
	public Alarm() {
		super();
	}

	/**
	 * This method performs an action.
	 */
	public TimeDescriptor setAlarmTime(TimeDescriptor td) {
		this.log.entry();

		// TODO Write your action code here ...
		System.out.println("setTime");

		this.log.exit();

		return td;
	}

	/**
	 * This method performs an action.
	 */
	public Boolean turnOff() {
		this.log.entry();

		// TODO Write your action code here ...
		System.out.println("TurnOff");

		this.log.exit();
		return Boolean.TRUE;
	}

	/**
	 * This method performs an action.
	 */
	public Boolean turnOn() {
		this.log.entry();

		// TODO Write your action code here ...
		System.out.println("TurnOn");

		this.log.exit();
		return Boolean.TRUE;
	}

}
