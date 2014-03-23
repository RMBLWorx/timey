/**
 * 
 */
package rmblworx.tools.timey;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rmblworx.tools.timey.exception.NullArgumentException;
import rmblworx.tools.timey.vo.TimeDescriptor;

/**
 * @author mmatthies
 */
public class AlarmSetStateOfAlarmCommandTest {

	private Invoker invoker;
	@Mock
	private Alarm mockedReceiver;
	private ICommand command;
	@Mock
	private TimeDescriptor descriptor;
	private Boolean isActivated;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.isActivated = Boolean.TRUE;
		this.command = new AlarmSetStateOfAlarmCommand(this.mockedReceiver, this.descriptor, this.isActivated);
		this.invoker = new Invoker();
		this.invoker.storeCommand(this.command);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		this.invoker = null;
		this.mockedReceiver = null;
		this.command = null;
		this.isActivated = null;
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.AlarmSetStateOfAlarmCommand#execute()}.
	 */
	@Test
	public final void testExecute() {
		when(this.mockedReceiver.setStateOfAlarmtimestamp(this.descriptor, this.isActivated)).thenReturn(Boolean.TRUE);
		assertTrue("Falsches Ergebnis!", (Boolean) this.invoker.execute());
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.AlarmSetStateOfAlarmCommand#execute()}.
	 */
	@Test
	public final void testExecuteShouldReturnNullBecauseAlarmDoesntExist() {
		when(this.mockedReceiver.setStateOfAlarmtimestamp(this.descriptor, this.isActivated)).thenReturn(null);
		assertNull("Falsches Ergebnis!", null);
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.AlarmSetStateOfAlarmCommand#execute()}.
	 */
	@Test
	public final void testExecuteShouldReturnFalseBecauseCouldNotBeSet() {
		when(this.mockedReceiver.setStateOfAlarmtimestamp(this.descriptor, this.isActivated)).thenReturn(Boolean.FALSE);
		assertFalse("Falsches Ergebnis!", (Boolean) this.invoker.execute());
	}

	/**
	 * Test method for
	 * {@link rmblworx.tools.timey.AlarmSetStateOfAlarmCommand#AlarmSetStateOfAlarmCommand(IAlarm, TimeDescriptor, Boolean)}
	 * .
	 */
	@Test(expected = NullArgumentException.class)
	public final void testShouldFailBecauseTimeDescriptorReferencesNull() {
		this.command = new AlarmSetStateOfAlarmCommand(this.mockedReceiver, null, Boolean.TRUE);
	}

	/**
	 * Test method for
	 * {@link rmblworx.tools.timey.AlarmSetStateOfAlarmCommand#AlarmSetStateOfAlarmCommand(IAlarm, TimeDescriptor, Boolean)}
	 * .
	 */
	@Test(expected = NullArgumentException.class)
	public final void testShouldFailBecauseReceiverReferencesNull() {
		this.command = new AlarmSetStateOfAlarmCommand(null, this.descriptor, Boolean.TRUE);
	}

	/**
	 * Test method for
	 * {@link rmblworx.tools.timey.AlarmSetStateOfAlarmCommand#AlarmSetStateOfAlarmCommand(IAlarm, TimeDescriptor, Boolean)}
	 * .
	 */
	@Test(expected = NullArgumentException.class)
	public final void testShouldFailBecauseIsActivatedReferencesNull() {
		this.command = new AlarmSetStateOfAlarmCommand(this.mockedReceiver, this.descriptor, null);
	}
}
