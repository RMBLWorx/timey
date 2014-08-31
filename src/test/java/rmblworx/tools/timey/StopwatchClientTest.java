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

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Tests für den StopwatchClient.
 *
 * @author mmatthies
 */
public class StopwatchClientTest {
	/**
	 * Text für die Meldung eines unerwarteten Ergebnisses.
	 */
	private static final String TEXT_UNERWARTETER_RUECKGABEWERT = "Es wurde ein falscher Rückgabewert geliefert!";
	/**
	 * Mock der Empfängerimplementierung.
	 */
	@Mock
	private IStopwatch receiver;

	/**
	 * Zu testende Implementierung.
	 */
	private StopwatchClient stopwatchClient;

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
		this.stopwatchClient = new StopwatchClient(this.receiver);
	}

	/**
	 * Setup-Methode für das zu mockende Empfängerobjekt.
	 */
	private void setupMockedReceiver() {
		when(this.receiver.resetStopwatch()).thenReturn(Boolean.TRUE);
		when(this.receiver.startStopwatch()).thenReturn(this.timeDescriptor);
		when(this.receiver.stopStopwatch()).thenReturn(Boolean.TRUE);
		when(this.receiver.toggleTimeModeInStopwatch()).thenReturn(Boolean.TRUE);
	}

	/**
	 * @throws java.lang.Exception
	 *             wenn eine Ausnahme auftritt.
	 */
	@After
	public final void tearDown() throws Exception {
		this.timeDescriptor = null;
		this.stopwatchClient = null;
		this.receiver = null;
	}

	/**
	 * Test method for {@link StopwatchClient#initStopwatchResetCommand()}.
	 */
	@Test
	public final void testInitStopwatchResetCommand() {
		final Boolean actual = this.stopwatchClient.initStopwatchResetCommand();

		assertTrue(TEXT_UNERWARTETER_RUECKGABEWERT, actual);
	}

	/**
	 * Test method for {@link StopwatchClient#initStopwatchStartCommand()}.
	 */
	@Test
	public final void testInitStopwatchStartCommand() {
		final TimeDescriptor actual = this.stopwatchClient.initStopwatchStartCommand();

		assertNotNull(TEXT_UNERWARTETER_RUECKGABEWERT, actual);
	}

	/**
	 * Test method for {@link StopwatchClient#initStopwatchStopCommand()}.
	 */
	@Test
	public final void testInitStopwatchStopCommand() {
		final Boolean actual = this.stopwatchClient.initStopwatchStopCommand();

		assertTrue(TEXT_UNERWARTETER_RUECKGABEWERT, actual);
	}

	/**
	 * Test method for {@link StopwatchClient#initStopwatchToggleTimeModeCommand()}.
	 */
	@Test
	public final void testInitStopwatchToggleTimeModeCommand() {
		final Boolean actual = this.stopwatchClient.initStopwatchToggleTimeModeCommand();

		assertTrue(TEXT_UNERWARTETER_RUECKGABEWERT, actual);
	}
}
