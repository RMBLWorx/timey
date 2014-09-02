package rmblworx.tools.timey.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import rmblworx.tools.timey.vo.AlarmDescriptor;
import rmblworx.tools.timey.vo.TimeDescriptor;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Tests für die Klasse AlarmExpiredEvent.
 *
 * @author mmatthies
 */
public class AlarmExpiredEventTest {

	/**
	 * Zeitwert. Hier nicht wichtig.
	 */
	private static final long EXPECTED_MILLISECONDS = 1000L;
	/**
	 * Fehlermeldung für nicht erwartete Rückgaben.
	 */
	private static final String UNERWARTETER_RUECKGABEWERT = "Unerwarteter Rückgabewert!";
	/**
	 * AlarmDescriptor.
	 */
	private AlarmDescriptor alarmDescriptor;
	/**
	 * AlarmExpiredEvent.
	 */
	private AlarmExpiredEvent alarmExpiredEvent;
	/**
	 * TimeDescriptor.
	 */
	private TimeDescriptor timeDescriptor;

	/**
	 * @throws java.lang.Exception
	 *             wenn eine Ausnahme auftritt.
	 */
	@Before
	public final void setUp() throws Exception {
		this.timeDescriptor = new TimeDescriptor(EXPECTED_MILLISECONDS);
		this.alarmDescriptor = new AlarmDescriptor(this.timeDescriptor, true, "desc", "/bla", this.timeDescriptor);
		this.alarmExpiredEvent = new AlarmExpiredEvent(this.alarmDescriptor);
	}

	/**
	 * @throws java.lang.Exception
	 *             wenn eine Ausnahme auftritt.
	 */
	@After
	public final void tearDown() throws Exception {
		this.timeDescriptor = null;
		this.alarmDescriptor = null;
		this.alarmExpiredEvent = null;
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.event.AlarmExpiredEvent#getAlarmDescriptor()}.
	 */
	@Test
	public final void testGetAlarmDescriptor() {
		final AlarmDescriptor actual = this.alarmExpiredEvent.getAlarmDescriptor();

		assertNotNull(UNERWARTETER_RUECKGABEWERT, actual);
		assertEquals(UNERWARTETER_RUECKGABEWERT, actual, this.alarmDescriptor);
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.event.AlarmExpiredEvent#toString()}.
	 */
	@Test
	public final void testToString() {
		final String actual = this.alarmExpiredEvent.toString();

		assertNotNull(UNERWARTETER_RUECKGABEWERT, actual);
		assertTrue(UNERWARTETER_RUECKGABEWERT, actual.length() > 0);
	}
}
