package rmblworx.tools.timey;

import rmblworx.tools.timey.vo.TimeDescriptor;

/**
 * PatternBox: "Client" implementation.
 * <ul>
 * <li>creates a ConcreteCommand object and sets its receiver.</li>
 * </ul>
 * 
 * @author Dirk Ehms, <a href="http://www.patternbox.com">www.patternbox.com</a>
 * @author mmatthies
 */
public class AlarmClient {
	/** stores the Receiver instance of the Client. */
	private final IAlarm fReceiver;

	/**
	 * This construtor creates a Client instance and stores the given Receiver.
	 */
	public AlarmClient(final IAlarm receiver) {
		super();
		this.fReceiver = receiver;
	}

	public Boolean initAlarmDeleteAlarm(TimeDescriptor descriptor) {
		final AlarmDeleteAlarmCommand cmd = new AlarmDeleteAlarmCommand(this.fReceiver, descriptor);
		final Invoker invoker = new Invoker();
		invoker.storeCommand(cmd);

		return invoker.execute();

	}

	public Boolean initAlarmGetStateOfAlarmCommand(TimeDescriptor descriptor) {
		final AlarmGetStateOfAlarmCommand cmd = new AlarmGetStateOfAlarmCommand(this.fReceiver, descriptor);
		final Invoker invoker = new Invoker();
		invoker.storeCommand(cmd);

		return invoker.execute();
	}

	public Boolean initAlarmSetStateOfAlarmCommand(TimeDescriptor descriptor, Boolean isActivated) {
		final AlarmSetStateOfAlarmCommand cmd = new AlarmSetStateOfAlarmCommand(this.fReceiver, descriptor, isActivated);
		final Invoker invoker = new Invoker();
		invoker.storeCommand(cmd);

		return invoker.execute();
	}

	public <T> T initGetAllAlarmtimestamps() {
		final AlarmGetAllAlarmtimestampsCommand cmd = new AlarmGetAllAlarmtimestampsCommand(this.fReceiver);
		final Invoker invoker = new Invoker();
		invoker.storeCommand(cmd);

		return invoker.execute();
	}

	/**
	 * This method creates a ConcreteCommand instance and specifies a Receiver
	 * object.
	 */
	public <T> T initSetAlarmtimestampCommand(final TimeDescriptor td) {
		final AlarmSetTimeCommand cmd = new AlarmSetTimeCommand(this.fReceiver, td);
		final Invoker invoker = new Invoker();
		invoker.storeCommand(cmd);

		return invoker.execute();
	}
}
