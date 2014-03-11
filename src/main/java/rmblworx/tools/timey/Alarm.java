package rmblworx.tools.timey;

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
public class Alarm implements IAlarm {

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
		// TODO Write your action code here ...
		System.out.println("setTime");

		return descriptor;
	}

	/**
	 * This method performs an action.
	 */
	@Override
	public Boolean turnOff() {
		// TODO Write your action code here ...
		System.out.println("TurnOff");

		return Boolean.TRUE;
	}

	/**
	 * This method performs an action.
	 */
	@Override
	public Boolean turnOn() {
		// TODO Write your action code here ...
		System.out.println("TurnOn");

		return Boolean.TRUE;
	}

}
