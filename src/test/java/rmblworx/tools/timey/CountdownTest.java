package rmblworx.tools.timey;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rmblworx.tools.timey.exception.NullArgumentException;
import rmblworx.tools.timey.vo.TimeDescriptor;

/**
 * Tests für die Countdown Implementierung.
 *
 * @author mmatthies
 */
public class CountdownTest {

	/**
	 * Irgend ein Zeitwert. Der Wert spielt hier keine Rolle.
	 */
	private static final Long EXPECTED_MILLISECONDS = 1000L;
	/**
	 * Fehlermeldungstext für ausbleibende Exception.
	 */
	private static final String KEINE_AUSNAHME_GEWORFEN = "Es wurde keine Ausnahme geworfen!";
	/**
	 * Fehlermeldungstext.
	 */
	private static final String UNERWARTETER_RUECKGABEWERT = "Unerwartete Rückgabe!";

	/**
	 * Zu testende Klasse.
	 */
	private ICountdown countdown;

	/**
	 * Gemocktes CountdownTimer-Objekt.
	 */
	@Mock
	private ICountdownTimer countdownTimer;

	/**
	 * Gemocktes TimeDescriptor-Objekt.
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
		this.setupMockedTimeDescriptor();
		this.setupMockedCountdownTimer();
		this.countdown = new Countdown(this.countdownTimer, 1, TimeUnit.MILLISECONDS);
	}

	/**
	 * Setup-Methode für das CountdownTimer-Objekt.
	 */
	private void setupMockedCountdownTimer() {
		when(this.countdownTimer.setCountdownTime(this.timeDescriptor)).thenReturn(Boolean.TRUE);
		when(this.countdownTimer.startCountdown(1, TimeUnit.MILLISECONDS)).thenReturn(this.timeDescriptor);
		when(this.countdownTimer.stopCountdown()).thenReturn(Boolean.TRUE);
	}

	/**
	 * Setup-Methode für das TimeDescriptor-Objekt.
	 */
	private void setupMockedTimeDescriptor() {
		when(this.timeDescriptor.getMilliSeconds()).thenReturn(EXPECTED_MILLISECONDS);
	}

	/**
	 * @throws java.lang.Exception
	 *             wenn eine Ausnahme auftritt.
	 */
	@After
	public final void tearDown() throws Exception {
		this.countdownTimer = null;
		this.timeDescriptor = null;
		this.countdown = null;
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.Countdown#setCountdownTime(rmblworx.tools.timey.vo.TimeDescriptor)}.
	 */
	@Test
	public final void testSetCountdownTime() {
		final Boolean actual = this.countdown.setCountdownTime(this.timeDescriptor);

		assertTrue(UNERWARTETER_RUECKGABEWERT, actual);
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.Countdown#setCountdownTime(rmblworx.tools.timey.vo.TimeDescriptor)}.
	 */
	@Test(expected = NullArgumentException.class)
	public final void testSetCountdownTimeShouldThrowNullArgumentException() {
		this.countdown.setCountdownTime(null);
		fail(KEINE_AUSNAHME_GEWORFEN);
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.Countdown#startCountdown()}.
	 */
	@Test
	public final void testStartCountdown() {
		final TimeDescriptor actual = this.countdown.startCountdown();

		assertNotNull(UNERWARTETER_RUECKGABEWERT, actual);
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.Countdown#stopCountdown()}.
	 */
	@Test
	public final void testStopCountdown() {
		final Boolean actual = this.countdown.stopCountdown();

		assertTrue(UNERWARTETER_RUECKGABEWERT, actual);
	}
}
