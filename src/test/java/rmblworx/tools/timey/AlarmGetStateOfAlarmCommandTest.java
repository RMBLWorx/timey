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
public class AlarmGetStateOfAlarmCommandTest {

	private ICommand command;
	@Mock
	private AlarmDescriptor descriptor;
	private Invoker<Boolean> invoker;
	@Mock
	private Alarm mockedReceiver;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.command = new AlarmGetStateOfAlarmCommand(this.mockedReceiver, this.descriptor);
		this.invoker = new Invoker<>();
		this.invoker.storeCommand(this.command);
	}

	@After
	public void tearDown() throws Exception {
		this.invoker = null;
		this.mockedReceiver = null;
		this.command = null;
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.AlarmGetStateOfAlarmCommand#execute()}.
	 */
	@Test
	public final void testExecuteTimestampIsActivated() {
		when(this.mockedReceiver.isAlarmActivated(this.descriptor)).thenReturn(Boolean.TRUE);
		assertTrue("Falscher Rueckgabewert!", this.invoker.execute());
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.AlarmGetStateOfAlarmCommand#execute()}.
	 */
	@Test
	public final void testExecuteTimestampIsNotActivated() {
		when(this.mockedReceiver.isAlarmActivated(this.descriptor)).thenReturn(Boolean.FALSE);
		assertFalse("Falscher Rueckgabewert!", this.invoker.execute());
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.AlarmGetStateOfAlarmCommand#execute()}.
	 */
	@Test
	public final void testExecuteTimestampIsNotPresent() {
		when(this.mockedReceiver.isAlarmActivated(this.descriptor)).thenReturn(null);
		assertNull("Falscher Rueckgabewert!", this.invoker.execute());
	}

	/**
	 * Test method for
	 * {@link rmblworx.tools.timey.AlarmGetStateOfAlarmCommand#AlarmGetStateOfAlarmCommand(IAlarm, rmblworx.tools.timey.vo.AlarmDescriptor)}
	 * .
	 */
	@Test(expected = NullArgumentException.class)
	public final void testShouldFailBecauseDescriptorIsNull() {
		this.command = new AlarmGetStateOfAlarmCommand(this.mockedReceiver, null);
	}

	/**
	 * Test method for
	 * {@link rmblworx.tools.timey.AlarmGetStateOfAlarmCommand#AlarmGetStateOfAlarmCommand(IAlarm, rmblworx.tools.timey.vo.AlarmDescriptor)}
	 * .
	 */
	@Test(expected = NullArgumentException.class)
	public final void testShouldFailBecauseReceiverIsNull() {
		this.command = new AlarmGetStateOfAlarmCommand(null, this.descriptor);
	}

}
