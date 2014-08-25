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
public class AlarmSetTimeCommandTest {

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
		this.command = new AlarmSetTimeCommand(this.mockedReceiver, this.descriptor);
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
	 * Test method for {@link rmblworx.tools.timey.AlarmSetTimeCommand#execute()}.
	 */
	@Test
	public final void testExecuteSettingTimestampExists() {
		when(this.mockedReceiver.setAlarm(this.descriptor)).thenReturn(null);
		assertNull("Falscher Rueckgabewert!", this.invoker.execute());
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.AlarmSetTimeCommand#execute()}.
	 */
	@Test
	public final void testExecuteSettingTimestampWasNotSuccessfully() {
		when(this.mockedReceiver.setAlarm(this.descriptor)).thenReturn(Boolean.FALSE);
		assertFalse("Falscher Rueckgabewert!", this.invoker.execute());
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.AlarmSetTimeCommand#execute()}.
	 */
	@Test
	public final void testExecuteSettingTimestampWasSuccessfully() {
		when(this.mockedReceiver.setAlarm(this.descriptor)).thenReturn(Boolean.TRUE);
		assertTrue("Falscher Rueckgabewert!", this.invoker.execute());
	}

	/**
	 * Test method for
	 * {@link rmblworx.tools.timey.AlarmSetTimeCommand#AlarmSetTimeCommand(IAlarm, rmblworx.tools.timey.vo.AlarmDescriptor)}
	 * .
	 */
	@Test(expected = NullArgumentException.class)
	public final void testShouldFailBecauseReceiverReferencesNull() {
		this.command = new AlarmSetTimeCommand(null, this.descriptor);
	}

	/**
	 * Test method for
	 * {@link rmblworx.tools.timey.AlarmSetTimeCommand#AlarmSetTimeCommand(IAlarm, rmblworx.tools.timey.vo.AlarmDescriptor)}
	 * .
	 */
	@Test(expected = NullArgumentException.class)
	public final void testShouldFailBecauseTimeDescriptorReferencesNull() {
		this.command = new AlarmSetTimeCommand(this.mockedReceiver, null);
	}

}
