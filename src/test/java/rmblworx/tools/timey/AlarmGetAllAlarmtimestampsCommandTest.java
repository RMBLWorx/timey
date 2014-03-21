package rmblworx.tools.timey;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rmblworx.tools.timey.vo.TimeDescriptor;

public class AlarmGetAllAlarmtimestampsCommandTest {

	private Invoker invoker;
	@Mock
	private Alarm mockedReceiver;
	private ICommand command;
	@Mock
	private TimeDescriptor descriptor;
	private List<TimeDescriptor> list;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.command = new AlarmGetAllAlarmtimestampsCommand(this.mockedReceiver);
		this.invoker = new Invoker();
		this.invoker.storeCommand(this.command);
		this.list = new ArrayList<TimeDescriptor>();
		this.list.add(this.descriptor);
	}

	@After
	public void tearDown() throws Exception {
		this.invoker = null;
		this.mockedReceiver = null;
		this.command = null;
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.AlarmGetAllAlarmtimestampsCommand#execute()}.
	 */
	@Test
	public final void testExecute() {
		when(this.mockedReceiver.getAllAlarmtimestamps()).thenReturn(this.list);
		assertNotNull("null zurueckgegeben!", this.invoker.execute());
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.AlarmGetAllAlarmtimestampsCommand#AlarmGetAllAlarmtimestampsCommand(IAlarm)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public final void testShouldFailBecauseReceiverIsNull() {
		this.command = new AlarmGetAllAlarmtimestampsCommand(null);
	}
}
