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

import rmblworx.tools.timey.vo.TimeDescriptor;

/**
 * Testet das Kommando zum loeschen von Alarmzeitpunkten aus der Datenbank wobei ausschliesslich die Logik des Kommandos
 * getestet wird und nicht die Funktionalitaet der anderen Akteure.
 * 
 * @author mmatthies
 */
public class AlarmDeleteAlarmCommandTest {

	private Invoker invoker;
	@Mock
	private Alarm mockedReceiver;
	private ICommand command;
	@Mock
	private TimeDescriptor descriptor;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.command = new AlarmDeleteAlarmCommand(this.mockedReceiver, this.descriptor);
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
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.AlarmDeleteAlarmCommand#execute()}.
	 */
	@Test
	public final void testExecuteOnExistingTimestamp() {
		when(this.mockedReceiver.removeAlarmtimestamp(this.descriptor)).thenReturn(Boolean.TRUE);
		assertTrue("Falscher Rueckgabewert!", (Boolean) this.invoker.execute());
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.AlarmDeleteAlarmCommand#execute()}.
	 */
	@Test
	public final void testExecuteOnNonExistingTimestamp() {
		when(this.mockedReceiver.removeAlarmtimestamp(null)).thenReturn(Boolean.FALSE);
		assertFalse("Falscher Rueckgabewert!", (Boolean) this.invoker.execute());
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.AlarmDeleteAlarmCommand#execute()}.
	 */
	@Test
	public final void testExecuteOnTimestampCantBeDeleted() {
		when(this.mockedReceiver.removeAlarmtimestamp(this.descriptor)).thenReturn(null);
		assertNull("Falscher Rueckgabewert!", this.invoker.execute());
	}

	/**
	 * Test method for
	 * {@link rmblworx.tools.timey.AlarmDeleteAlarmCommand#AlarmDeleteAlarmCommand(IAlarm, TimeDescriptor)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public final void testShouldFailBecauseReceiverIsNull() {
		this.command = new AlarmDeleteAlarmCommand(null, this.descriptor);
	}

	/**
	 * Test method for
	 * {@link rmblworx.tools.timey.AlarmDeleteAlarmCommand#AlarmDeleteAlarmCommand(IAlarm, TimeDescriptor)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public final void testShouldFailBecauseTimeDescriptorIsNull() {
		this.command = new AlarmDeleteAlarmCommand(this.mockedReceiver, null);
	}
}
