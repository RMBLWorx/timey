/**
 * 
 */
package rmblworx.tools.timey;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
public class StopwatchResetCommandTest {

	private Invoker invoker;
	@Mock
	private IStopwatch mockedReceiver;
	private ICommand command;
	@Mock
	private TimeDescriptor descriptor;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.command = new StopwatchResetCommand(this.mockedReceiver);
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
	 * Test method for
	 * {@link rmblworx.tools.timey.StopwatchResetCommand#StopwatchResetCommand(rmblworx.tools.timey.IStopwatch)}.
	 */
	@Test
	public final void testStopwatchResetCommand() {
		this.command = new StopwatchResetCommand(this.mockedReceiver);
		assertNotNull("Falscher Rueckgabewert!", this.command);
	}

	/**
	 * Test method for
	 * {@link rmblworx.tools.timey.StopwatchResetCommand#StopwatchResetCommand(rmblworx.tools.timey.IStopwatch)}.
	 */
	@Test(expected = NullArgumentException.class)
	public final void testShouldFailBecauseReceiverIsNull() {
		this.command = new StopwatchResetCommand(null);
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.StopwatchResetCommand#execute()}.
	 */
	@Test
	public final void testExecute() {
		assertTrue("Falscher Rueckgabewert!", (Boolean) this.invoker.execute());
	}
}
