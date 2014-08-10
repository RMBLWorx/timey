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

import rmblworx.tools.timey.exception.NullArgumentException;
import rmblworx.tools.timey.vo.AlarmDescriptor;

public class AlarmGetAllAlarmsCommandTest {

	private Invoker invoker;
	@Mock
	private Alarm mockedReceiver;
	private ICommand command;
	@Mock
	private AlarmDescriptor descriptor;
	private List<AlarmDescriptor> list;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.command = new AlarmGetAllAlarmsCommand(this.mockedReceiver);
		this.invoker = new Invoker();
		this.invoker.storeCommand(this.command);
		this.list = new ArrayList<>();
		this.list.add(this.descriptor);
	}

	@After
	public void tearDown() throws Exception {
		this.invoker = null;
		this.mockedReceiver = null;
		this.command = null;
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.AlarmGetAllAlarmsCommand#execute()}.
	 */
	@Test
	public final void testExecute() {
		when(this.mockedReceiver.getAllAlarms()).thenReturn(this.list);
		assertNotNull("null zurueckgegeben!", this.invoker.execute());
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.AlarmGetAllAlarmsCommand#AlarmGetAllAlarmsCommand(IAlarm)}.
	 */
	@Test(expected = NullArgumentException.class)
	public final void testShouldFailBecauseReceiverIsNull() {
		this.command = new AlarmGetAllAlarmsCommand(null);
	}
}
