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

import rmblworx.tools.timey.exception.NullArgumentException;
import rmblworx.tools.timey.vo.AlarmDescriptor;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Testet das Kommando zum löschen von Alarmzeitpunkten aus der Datenbank wobei ausschliesslich die Logik des Kommandos
 * getestet wird und nicht die Funktionalität der anderen Akteure.
 *
 * @author mmatthies
 */
public class AlarmDeleteAlarmCommandTest {

	private ICommand command;
	@Mock
	private AlarmDescriptor descriptor;
	private Invoker<Boolean> invoker;
	@Mock
	private Alarm mockedReceiver;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.command = new AlarmDeleteAlarmCommand(this.mockedReceiver, this.descriptor);
		this.invoker = new Invoker<>();
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
		when(this.mockedReceiver.removeAlarm(this.descriptor)).thenReturn(Boolean.TRUE);
		assertTrue("Falscher Rueckgabewert!", this.invoker.execute());
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.AlarmDeleteAlarmCommand#execute()}.
	 */
	@Test
	public final void testExecuteOnNonExistingTimestamp() {
		when(this.mockedReceiver.removeAlarm(null)).thenReturn(Boolean.FALSE);
		assertFalse("Falscher Rueckgabewert!", this.invoker.execute());
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.AlarmDeleteAlarmCommand#execute()}.
	 */
	@Test
	public final void testExecuteOnTimestampCantBeDeleted() {
		when(this.mockedReceiver.removeAlarm(this.descriptor)).thenReturn(null);
		assertNull("Falscher Rueckgabewert!", this.invoker.execute());
	}

	/**
	 * Test method for
	 * {@link rmblworx.tools.timey.AlarmDeleteAlarmCommand#AlarmDeleteAlarmCommand(IAlarm, rmblworx.tools.timey.vo.AlarmDescriptor)}
	 * .
	 */
	@Test(expected = NullArgumentException.class)
	public final void testShouldFailBecauseReceiverIsNull() {
		this.command = new AlarmDeleteAlarmCommand(null, this.descriptor);
	}

	/**
	 * Test method for
	 * {@link rmblworx.tools.timey.AlarmDeleteAlarmCommand#AlarmDeleteAlarmCommand(IAlarm, rmblworx.tools.timey.vo.AlarmDescriptor)}
	 * .
	 */
	@Test(expected = NullArgumentException.class)
	public final void testShouldFailBecauseTimeDescriptorIsNull() {
		this.command = new AlarmDeleteAlarmCommand(this.mockedReceiver, null);
	}
}
