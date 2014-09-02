package rmblworx.tools.timey;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rmblworx.tools.timey.event.TimeyEventDispatcher;
import rmblworx.tools.timey.event.TimeyEventListener;
import rmblworx.tools.timey.vo.AlarmDescriptor;
import rmblworx.tools.timey.vo.TimeDescriptor;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Testimplementierung für die timey-Fassade.
 *
 * @author mmatthies
 */
public class TimeyFacadeTest {

	/**
	 * Wert der vom Mock-Objekt erwarteten Version.
	 */
	private static final String EXPECTED_VERSION = "x.xx.x";
	/**
	 * Text für die Meldung eines unerwarteten Ergebnisses.
	 */
	private static final String TEXT_UNERWARTETER_RUECKGABEWERT = "Es wurde ein falscher Rückgabewert geliefert!";

	/**
	 * Mock des AlarmClient.
	 */
	@Mock
	private AlarmClient alarmClient;
	/**
	 * Mock des AlarmDeskriptor.
	 */
	@Mock
	private AlarmDescriptor alarmDescriptor;
	/**
	 * Liste aller in timey hinterlegten Alarme.
	 */
	private List<AlarmDescriptor> alarms;
	/**
	 * Mock des timey Attribut-Objekts.
	 */
	@Mock
	private ApplicationProperties applicationProperties;
	/**
	 * Mock des CountdownClient.
	 */
	@Mock
	private CountdownClient countdownClient;
	/**
	 * Die zu testende Implementierung.
	 */
	private TimeyFacade facade;
	/**
	 * Mock des StopwatchClient.
	 */
	@Mock
	private StopwatchClient stopwatchClient;
	/**
	 * Mock des TimeDeskriptor.
	 */
	@Mock
	private TimeDescriptor timeDescriptor;
	/**
	 * Mock des TimeyEventDispatcher.
	 */
	@Mock
	private TimeyEventDispatcher timeyEventDispatcher;
	/**
	 * Mock des TimeyEventListener.
	 */
	@Mock
	private TimeyEventListener timeyEventListener;

	/**
	 * Setup-Methode für den AlarmClient.
	 */
	private void mockAlarmClient() {
		when(this.alarmClient.initAlarmDeleteAlarm(this.alarmDescriptor)).thenReturn(true);
		when(this.alarmClient.initAlarmGetStateOfAlarmCommand(this.alarmDescriptor)).thenReturn(true);
		when(this.alarmClient.initAlarmSetStateOfAlarmCommand(this.alarmDescriptor, true)).thenReturn(true);
		when(this.alarmClient.initGetAllAlarms()).thenReturn(this.alarms);
		when(this.alarmClient.initSetAlarmCommand(this.alarmDescriptor)).thenReturn(true);
	}

	/**
	 * Setup-Methode für die timey Attribut-Implementierung.
	 */
	private void mockApplicationProperties() {
		when(this.applicationProperties.getVersion()).thenReturn(EXPECTED_VERSION);
	}

	/**
	 * Setup-Methode für den CountdownClient.
	 */
	private void mockCountdownClient() {
		when(this.countdownClient.initCountdownStartCommand()).thenReturn(this.timeDescriptor);
		when(this.countdownClient.initCountdownStopCommand()).thenReturn(Boolean.TRUE);
		when(this.countdownClient.initSetCountdownTimeCommand(this.timeDescriptor)).thenReturn(Boolean.TRUE);
	}

	/**
	 * Setup-Methode für den StopwatchClient.
	 */
	private void mockStopwatchClient() {
		when(this.stopwatchClient.initStopwatchResetCommand()).thenReturn(Boolean.TRUE);
		when(this.stopwatchClient.initStopwatchStartCommand()).thenReturn(this.timeDescriptor);
		when(this.stopwatchClient.initStopwatchStopCommand()).thenReturn(Boolean.TRUE);
		when(this.stopwatchClient.initStopwatchToggleTimeModeCommand()).thenReturn(Boolean.TRUE);
	}

	/**
	 * Setup-Methode für den TimeyEventDispatcher.
	 */
	private void mockTimeyEventDispatcher() {
		when(this.timeyEventDispatcher.addEventListener(this.timeyEventListener)).thenReturn(Boolean.TRUE);
	}

	/**
	 * @throws java.lang.Exception
	 *             wenn eine Ausnahme auftritt.
	 */
	@Before
	public final void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.alarms = Arrays.asList(this.alarmDescriptor, this.alarmDescriptor, this.alarmDescriptor);
		this.mockAlarmClient();
		this.mockStopwatchClient();
		this.mockTimeyEventDispatcher();
		this.mockCountdownClient();
		this.mockApplicationProperties();
		this.facade = new TimeyFacade(this.alarmClient, this.stopwatchClient, this.countdownClient,
				this.timeyEventDispatcher, this.applicationProperties);
	}

	/**
	 * @throws java.lang.Exception
	 *             wenn eine Ausnahme auftritt.
	 */
	@After
	public final void tearDown() throws Exception {
		this.alarmClient = null;
		this.stopwatchClient = null;
		this.countdownClient = null;
		this.alarmDescriptor = null;
		this.alarms = null;
		this.applicationProperties = null;
		this.facade = null;
	}

	/**
	 * Test method for {@link TimeyFacade#addEventListener(rmblworx.tools.timey.event.TimeyEventListener)}.
	 */
	@Test
	public final void testAddEventListener() {
		final Boolean actual = this.facade.addEventListener(this.timeyEventListener);

		assertTrue(TEXT_UNERWARTETER_RUECKGABEWERT, actual);
	}

	/**
	 * Test method for {@link TimeyFacade#getAllAlarms()}.
	 */
	@Test
	public final void testGetAllAlarms() {
		final List<AlarmDescriptor> actual = this.facade.getAllAlarms();

		assertNotNull(TEXT_UNERWARTETER_RUECKGABEWERT, actual);
	}

	/**
	 * Test method for {@link TimeyFacade#getVersion()}.
	 */
	@Test
	public final void testGetVersion() {
		final String actual = this.facade.getVersion();

		assertNotNull(TEXT_UNERWARTETER_RUECKGABEWERT, actual);
	}

	/**
	 * Test method for {@link TimeyFacade#isAlarmActivated(rmblworx.tools.timey.vo.AlarmDescriptor)}.
	 */
	@Test
	public final void testIsAlarmActivated() {
		final Boolean actual = this.facade.isAlarmActivated(this.alarmDescriptor);

		assertTrue(TEXT_UNERWARTETER_RUECKGABEWERT, actual);
	}

	/**
	 * Test method for {@link TimeyFacade#removeAlarm(rmblworx.tools.timey.vo.AlarmDescriptor)}.
	 */
	@Test
	public final void testRemoveAlarm() {
		final Boolean actual = this.facade.removeAlarm(this.alarmDescriptor);

		assertTrue(TEXT_UNERWARTETER_RUECKGABEWERT, actual);
	}

	/**
	 * Test method for {@link TimeyFacade#resetStopwatch()}.
	 */
	@Test
	public final void testResetStopwatch() {
		final Boolean actual = this.facade.resetStopwatch();

		assertTrue(TEXT_UNERWARTETER_RUECKGABEWERT, actual);

	}

	/**
	 * Test method for {@link TimeyFacade#setAlarm(rmblworx.tools.timey.vo.AlarmDescriptor)}.
	 */
	@Test
	public final void testSetAlarm() {
		final Boolean actual = this.facade.setAlarm(this.alarmDescriptor);

		assertTrue(TEXT_UNERWARTETER_RUECKGABEWERT, actual);
	}

	/**
	 * Test method for {@link TimeyFacade#setCountdownTime(rmblworx.tools.timey.vo.TimeDescriptor)} .
	 */
	@Test
	public final void testSetCountdownTime() {
		final Boolean actual = this.facade.setCountdownTime(this.timeDescriptor);

		assertTrue(TEXT_UNERWARTETER_RUECKGABEWERT, actual);
	}

	/**
	 * Test method for {@link TimeyFacade#setStateOfAlarm(rmblworx.tools.timey.vo.AlarmDescriptor, java.lang.Boolean)} .
	 */
	@Test
	public final void testSetStateOfAlarm() {
		final Boolean actual = this.facade.setStateOfAlarm(this.alarmDescriptor, Boolean.TRUE);

		assertTrue(TEXT_UNERWARTETER_RUECKGABEWERT, actual);
	}

	/**
	 * Test method for {@link TimeyFacade#startCountdown()}.
	 */
	@Test
	public final void testStartCountdown() {
		final TimeDescriptor actual = this.facade.startCountdown();

		assertNotNull(TEXT_UNERWARTETER_RUECKGABEWERT, actual);
	}

	/**
	 * Test method for {@link TimeyFacade#startStopwatch()}.
	 */
	@Test
	public final void testStartStopwatch() {
		final TimeDescriptor actual = this.facade.startStopwatch();

		assertNotNull(TEXT_UNERWARTETER_RUECKGABEWERT, actual);
	}

	/**
	 * Test method for {@link TimeyFacade#stopCountdown()}.
	 */
	@Test
	public final void testStopCountdown() {
		final Boolean actual = this.facade.stopCountdown();

		assertTrue(TEXT_UNERWARTETER_RUECKGABEWERT, actual);
	}

	/**
	 * Test method for {@link TimeyFacade#stopStopwatch()}.
	 */
	@Test
	public final void testStopStopwatch() {
		final Boolean actual = this.facade.stopStopwatch();

		assertTrue(TEXT_UNERWARTETER_RUECKGABEWERT, actual);
	}

	/**
	 * Test method for {@link TimeyFacade#toggleTimeModeInStopwatch()}.
	 */
	@Test
	public final void testToggleTimeModeInStopwatch() {
		final Boolean actual = this.facade.toggleTimeModeInStopwatch();

		assertTrue(TEXT_UNERWARTETER_RUECKGABEWERT, actual);
	}
}
