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
 * @author mmatthies
 */
public class AlarmSetStateOfAlarmCommandTest {

	private ICommand command;
	@Mock
	private AlarmDescriptor descriptor;
	private Invoker<Boolean> invoker;
	private Boolean isActivated;
	@Mock
	private Alarm mockedReceiver;

	@Before
	public final void setUp() {
		MockitoAnnotations.initMocks(this);
		this.isActivated = Boolean.TRUE;
		this.command = new AlarmSetStateOfAlarmCommand(this.mockedReceiver, this.descriptor, this.isActivated);
		this.invoker = new Invoker<>();
		this.invoker.storeCommand(this.command);
	}

	@After
	public final void tearDown() {
		this.invoker = null;
		this.mockedReceiver = null;
		this.command = null;
		this.isActivated = null;
	}

	/**
	 * Test method for {@link AlarmSetStateOfAlarmCommand#execute()}.
	 */
	@Test
	public final void testExecute() {
		when(this.mockedReceiver.setStateOfAlarm(this.descriptor, this.isActivated)).thenReturn(Boolean.TRUE);
		assertTrue("Falsches Ergebnis!", this.invoker.execute());
	}

	/**
	 * Test method for {@link AlarmSetStateOfAlarmCommand#execute()}.
	 */
	@Test
	public final void testExecuteShouldReturnFalseBecauseCouldNotBeSet() {
		when(this.mockedReceiver.setStateOfAlarm(this.descriptor, this.isActivated)).thenReturn(Boolean.FALSE);
		assertFalse("Falsches Ergebnis!", this.invoker.execute());
	}

	/**
	 * Test method for {@link AlarmSetStateOfAlarmCommand#execute()}.
	 */
	@Test
	public final void testExecuteShouldReturnNullBecauseAlarmDoesntExist() {
		when(this.mockedReceiver.setStateOfAlarm(this.descriptor, this.isActivated)).thenReturn(null);
		assertNull("Falsches Ergebnis!", null);
	}

	/**
	 * Test method for {@link AlarmSetStateOfAlarmCommand#AlarmSetStateOfAlarmCommand(IAlarm, AlarmDescriptor, Boolean)}.
	 */
	@Test(expected = NullArgumentException.class)
	public final void testShouldFailBecauseIsActivatedReferencesNull() {
		this.command = new AlarmSetStateOfAlarmCommand(this.mockedReceiver, this.descriptor, null);
	}

	/**
	 * Test method for {@link AlarmSetStateOfAlarmCommand#AlarmSetStateOfAlarmCommand(IAlarm, AlarmDescriptor, Boolean)}.
	 */
	@Test(expected = NullArgumentException.class)
	public final void testShouldFailBecauseReceiverReferencesNull() {
		this.command = new AlarmSetStateOfAlarmCommand(null, this.descriptor, Boolean.TRUE);
	}

	/**
	 * Test method for {@link AlarmSetStateOfAlarmCommand#AlarmSetStateOfAlarmCommand(IAlarm, AlarmDescriptor, Boolean)}.
	 */
	@Test(expected = NullArgumentException.class)
	public final void testShouldFailBecauseTimeDescriptorReferencesNull() {
		this.command = new AlarmSetStateOfAlarmCommand(this.mockedReceiver, null, Boolean.TRUE);
	}
}
