package rmblworx.tools.timey;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rmblworx.tools.timey.exception.NullArgumentException;
import rmblworx.tools.timey.vo.TimeDescriptor;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * @author mmatthies
 */
public class CountdownSetTimeCommandTest {

	private ICommand command;
	@Mock
	private TimeDescriptor descriptor;
	private Invoker<Boolean> invoker;
	@Mock
	private ICountdown mockedReceiver;

	/**
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.command = new CountdownSetTimeCommand(this.mockedReceiver, this.descriptor);
		this.invoker = new Invoker<>();
		this.invoker.storeCommand(this.command);
	}

	/**
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		this.invoker = null;
		this.mockedReceiver = null;
		this.command = null;
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.CountdownSetTimeCommand#execute()}.
	 */
	@Test
	public final void testExecuteIfTimeCouldBeSetSuccessfully() {
		when(this.mockedReceiver.setCountdownTime(this.descriptor)).thenReturn(Boolean.TRUE);
		assertTrue("Falscher Rueckgabewert!", this.invoker.execute());
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.CountdownSetTimeCommand#execute()}.
	 */
	@Test
	public final void testExecuteIfTimeCouldNotBeSetSuccessfully() {
		when(this.mockedReceiver.setCountdownTime(this.descriptor)).thenReturn(Boolean.FALSE);
		assertFalse("Falscher Rueckgabewert!", this.invoker.execute());
	}

	/**
	 * Test method for
	 * {@link rmblworx.tools.timey.CountdownSetTimeCommand#CountdownSetTimeCommand(ICountdown, TimeDescriptor)}.
	 */
	@Test(expected = NullArgumentException.class)
	public final void testShouldFailBecauseReceiverIsNull() {
		this.command = new CountdownSetTimeCommand(null, this.descriptor);
	}

	/**
	 * Test method for
	 * {@link rmblworx.tools.timey.CountdownSetTimeCommand#CountdownSetTimeCommand(ICountdown, TimeDescriptor)}.
	 */
	@Test(expected = NullArgumentException.class)
	public final void testShouldFailBecauseTimeDescriptorIsNull() {
		this.command = new CountdownSetTimeCommand(this.mockedReceiver, null);
	}
}
