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

import rmblworx.tools.timey.vo.AlarmDescriptor;

/*
 * Copyright 2014-2015 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Tests für den AlarmClient.
 *
 * @author mmatthies
 */
public class AlarmClientTest {
	/**
	 * Text für die Meldung eines unerwarteten Ergebnisses.
	 */
	private static final String TEXT_UNERWARTETER_RUECKGABEWERT = "Es wurde ein falscher Rückgabewert geliefert!";
	/**
	 * Zu testende Implementierung.
	 */
	private AlarmClient alarmClient;

	/**
	 * Mock des AlarmDescriptor.
	 */
	@Mock
	private AlarmDescriptor alarmDescriptor;

	/**
	 * Liste mit gemockten Alarmen.
	 */
	private List<AlarmDescriptor> alarms;
	/**
	 * Mock der Empfängerimplementierung - Besonderheit - die Klasse Alarm hat eine nicht im Interface deklarierte
	 * Methode die jedoch in einem Subkommando zum Setzen des Zustandes im AlarmDescriptor genutzt wird. Dieser Mock ist
	 * vorrübergehend dazu da um eine ClassCastException zu verhindern.
	 */
	@Mock
	// TODO - siehe Javadoc
	private Alarm mockedReceiverImpl;
	/**
	 * Mock der Empfängerimplementierung.
	 */
	@Mock
	private IAlarm receiver;

	/**
	 * @throws java.lang.Exception
	 *             wenn eine Ausnahme auftritt.
	 */
	@Before
	public final void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.alarms = Arrays.asList(this.alarmDescriptor, this.alarmDescriptor);
		this.setupMockedReceiver();
		this.alarmClient = new AlarmClient(this.receiver);
	}

	/**
	 * Setup-Methode für das zu mockende Empfängerobjekt.
	 */
	private void setupMockedReceiver() {
		when(this.receiver.getAllAlarms()).thenReturn(this.alarms);
		when(this.receiver.isAlarmActivated(this.alarmDescriptor)).thenReturn(Boolean.TRUE);
		when(this.receiver.removeAlarm(this.alarmDescriptor)).thenReturn(Boolean.TRUE);
		when(this.receiver.setAlarm(this.alarmDescriptor)).thenReturn(Boolean.TRUE);
		when(this.receiver.setStateOfAlarm(this.alarmDescriptor, Boolean.TRUE)).thenReturn(Boolean.TRUE);
		when(this.mockedReceiverImpl.setStateOfAlarm(this.alarmDescriptor, Boolean.TRUE)).thenReturn(Boolean.TRUE);
	}

	/**
	 * @throws java.lang.Exception
	 *             wenn eine Ausnahme auftritt.
	 */
	@After
	public final void tearDown() throws Exception {
		this.receiver = null;
		this.alarmDescriptor = null;
		this.alarms = null;
	}

	/**
	 * Test method for {@link AlarmClient#initAlarmDeleteAlarm(AlarmDescriptor)}.
	 */
	@Test
	public final void testInitAlarmDeleteAlarm() {
		final Boolean actual = this.alarmClient.initAlarmDeleteAlarm(this.alarmDescriptor);

		assertTrue(TEXT_UNERWARTETER_RUECKGABEWERT, actual);
	}

	/**
	 * Test method for {@link AlarmClient#initAlarmGetStateOfAlarmCommand(AlarmDescriptor)}.
	 */
	@Test
	public final void testInitAlarmGetStateOfAlarmCommand() {
		final Boolean actual = this.alarmClient.initAlarmGetStateOfAlarmCommand(this.alarmDescriptor);

		assertTrue(TEXT_UNERWARTETER_RUECKGABEWERT, actual);
	}

	/**
	 * Test method for {@link AlarmClient#initAlarmSetStateOfAlarmCommand(AlarmDescriptor, Boolean)}.
	 */
	@Test
	public final void testInitAlarmSetStateOfAlarmCommand() {
		this.alarmClient = new AlarmClient(this.mockedReceiverImpl);
		final Boolean actual = this.alarmClient.initAlarmSetStateOfAlarmCommand(this.alarmDescriptor, Boolean.TRUE);

		assertTrue(TEXT_UNERWARTETER_RUECKGABEWERT, actual);
	}

	/**
	 * Test method for {@link AlarmClient#initGetAllAlarms()}.
	 */
	@Test
	public final void testInitGetAllAlarms() {
		final List<AlarmDescriptor> actual = this.alarmClient.initGetAllAlarms();

		assertNotNull(TEXT_UNERWARTETER_RUECKGABEWERT, actual);
	}

	/**
	 * Test method for {@link AlarmClient#initSetAlarmCommand(AlarmDescriptor)}.
	 */
	@Test
	public final void testInitSetAlarmCommand() {
		final Boolean actual = this.alarmClient.initSetAlarmCommand(this.alarmDescriptor);

		assertTrue(TEXT_UNERWARTETER_RUECKGABEWERT, actual);
	}
}
