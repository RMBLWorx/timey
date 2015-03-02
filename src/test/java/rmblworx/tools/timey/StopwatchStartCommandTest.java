package rmblworx.tools.timey;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rmblworx.tools.timey.exception.NullArgumentException;
import rmblworx.tools.timey.vo.TimeDescriptor;

/*
 * Copyright 2014-2015 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * @author mmatthies
 */
public class StopwatchStartCommandTest {

	private ICommand command;
	@Mock
	private TimeDescriptor descriptor;
	private Invoker<TimeDescriptor> invoker;
	@Mock
	private IStopwatch mockedReceiver;

	@Before
	public final void setUp() {
		MockitoAnnotations.initMocks(this);
		this.command = new StopwatchStartCommand(this.mockedReceiver);
		this.invoker = new Invoker<>();
		this.invoker.storeCommand(this.command);
	}

	@After
	public final void tearDown() {
		this.invoker = null;
		this.mockedReceiver = null;
		this.command = null;
	}

	/**
	 * Test method for {@link StopwatchStartCommand#execute()}.
	 */
	@Test
	public final void testExecute() {
		when(this.mockedReceiver.startStopwatch()).thenReturn(this.descriptor);
		assertNotNull(this.invoker.execute());
	}

	/**
	 * Test method for {@link StopwatchStartCommand#StopwatchStartCommand(IStopwatch)}.
	 */
	@Test(expected = NullArgumentException.class)
	public final void testShouldFailBecauseReceiverIsNull() {
		this.command = new StopwatchStartCommand(null);
	}

	/**
	 * Test method for {@link StopwatchStartCommand#StopwatchStartCommand(IStopwatch)}.
	 */
	@Test
	public final void testStopwatchStartCommand() {
		this.command = new StopwatchStartCommand(this.mockedReceiver);
		assertNotNull(this.command);
	}

}
