/**
 * 
 */
package rmblworx.tools.timey;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rmblworx.tools.timey.vo.TimeDescriptor;

/**
 * @author mmatthies
 */
public class CountdownStopCommandTest {
	private Invoker invoker;
	@Mock
	private ICountdown mockedReceiver;
	private ICommand command;
	@Mock
	private TimeDescriptor descriptor;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.command = new CountdownStopCommand(this.mockedReceiver);
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
	 * {@link rmblworx.tools.timey.CountdownStopCommand#CountdownStopCommand(rmblworx.tools.timey.ICountdown)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public final void testShouldFailBecauseReceiverIsNull() {
		this.command = new CountdownStopCommand(null);
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.CountdownStopCommand#execute()}.
	 */
	@Test
	public final void testExecute() {
		when(this.mockedReceiver.stopCountdown()).thenReturn(Boolean.TRUE);
		assertTrue("Falscher Rueckgabewert!", (Boolean) this.invoker.execute());
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.CountdownStopCommand#execute()}.
	 */
	@Test
	public final void testExecuteIfCountdownCouldNotBeStoppedSuccessfully() {
		when(this.mockedReceiver.stopCountdown()).thenReturn(Boolean.FALSE);
		assertFalse("Falscher Rueckgabewert!", (Boolean) this.invoker.execute());
	}

}
