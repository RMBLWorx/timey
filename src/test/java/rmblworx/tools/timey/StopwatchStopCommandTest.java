package rmblworx.tools.timey;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rmblworx.tools.timey.exception.NullArgumentException;
import rmblworx.tools.timey.vo.TimeDescriptor;

public class StopwatchStopCommandTest {
	private Invoker invoker;
	@Mock
	private IStopwatch mockedReceiver;
	private ICommand command;
	@Mock
	private TimeDescriptor descriptor;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.command = new StopwatchStopCommand(this.mockedReceiver);
		this.invoker = new Invoker();
		this.invoker.storeCommand(this.command);
	}

	@After
	public void tearDown() throws Exception {
		this.invoker = null;
		this.mockedReceiver = null;
		this.command = null;
	}

	/**
	 * Test method for
	 * {@link rmblworx.tools.timey.StopwatchStopCommand#StopwatchStopCommand(rmblworx.tools.timey.IStopwatch)}.
	 */
	@Test
	public final void testStopwatchStopCommand() {
		this.command = new StopwatchStopCommand(this.mockedReceiver);
		assertNotNull(this.command);
	}

	/**
	 * Test method for
	 * {@link rmblworx.tools.timey.StopwatchStopCommand#StopwatchStopCommand(rmblworx.tools.timey.IStopwatch)}.
	 */
	@Test(expected = NullArgumentException.class)
	public final void testShouldFailBecauseReceiverIsNull() {
		this.command = new StopwatchStopCommand(null);
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.StopwatchStopCommand#execute()}.
	 */
	@Test
	public final void testExecute() {
		when(this.mockedReceiver.stopStopwatch()).thenReturn(Boolean.TRUE);
		assertTrue("Falscher Rueckgabewert!", (Boolean) this.invoker.execute());
	}
}
