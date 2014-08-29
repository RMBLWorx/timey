package rmblworx.tools.timey;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rmblworx.tools.timey.vo.TimeDescriptor;

/**
 * Tests für den CountdownClient.
 *
 * @author mmatthies
 */
public class CountdownClientTest {
	/**
	 * Text für die Meldung eines unerwarteten Ergebnisses.
	 */
	private static final String TEXT_UNERWARTETER_RUECKGABEWERT = "Es wurde ein falscher Rückgabewert geliefert!";

	/**
	 * Zu testende Implementierung.
	 */
	private CountdownClient countdownClient;

	/**
	 * Mock der Empfängerimplementierung.
	 */
	@Mock
	private ICountdown receiver;

	/**
	 * Mock des TimeDescriptor.
	 */
	@Mock
	private TimeDescriptor timeDescriptor;

	/**
	 * @throws java.lang.Exception
	 *             wenn eine Ausnahme auftritt.
	 */
	@Before
	public final void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.setupMockedReceiver();
		this.countdownClient = new CountdownClient(this.receiver);
	}

	/**
	 * Setup-Methode für das zu mockende Objekt.
	 */
	private void setupMockedReceiver() {
		when(this.receiver.setCountdownTime(this.timeDescriptor)).thenReturn(Boolean.TRUE);
		when(this.receiver.startCountdown()).thenReturn(this.timeDescriptor);
		when(this.receiver.stopCountdown()).thenReturn(Boolean.TRUE);
	}

	/**
	 * @throws java.lang.Exception
	 *             wenn eine Ausnahme auftritt.
	 */
	@After
	public final void tearDown() throws Exception {
		this.receiver = null;
		this.countdownClient = null;
		this.timeDescriptor = null;
	}

	/**
	 * Test method for {@link CountdownClient#initCountdownStartCommand()}.
	 */
	@Test
	public final void testInitCountdownStartCommand() {
		final TimeDescriptor actual = this.countdownClient.initCountdownStartCommand();

		assertNotNull(TEXT_UNERWARTETER_RUECKGABEWERT, actual);
	}

	/**
	 * Test method for {@link CountdownClient#initCountdownStopCommand()}.
	 */
	@Test
	public final void testInitCountdownStopCommand() {
		final Boolean actual = this.countdownClient.initCountdownStopCommand();

		assertTrue(TEXT_UNERWARTETER_RUECKGABEWERT, actual);
	}

	/**
	 * Test method for {@link CountdownClient#initSetCountdownTimeCommand(TimeDescriptor)}.
	 */
	@Test
	public final void testInitSetCountdownTimeCommand() {
		final Boolean actual = this.countdownClient.initSetCountdownTimeCommand(this.timeDescriptor);

		assertTrue(TEXT_UNERWARTETER_RUECKGABEWERT, actual);
	}

}
