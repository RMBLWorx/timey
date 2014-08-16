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
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * @author mmatthies
 */
public class StopwatchStartCommandTest {
	private Invoker invoker;
	@Mock
	private IStopwatch mockedReceiver;
	private ICommand command;
	@Mock
	private TimeDescriptor descriptor;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.command = new StopwatchStartCommand(this.mockedReceiver);
		this.invoker = new Invoker();
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
	 * Test method for
	 * {@link rmblworx.tools.timey.StopwatchStartCommand#StopwatchStartCommand(rmblworx.tools.timey.IStopwatch)}.
	 */
	@Test
	public final void testStopwatchStartCommand() {
		this.command = new StopwatchStartCommand(this.mockedReceiver);
		assertNotNull(this.command);
	}

	/**
	 * Test method for
	 * {@link rmblworx.tools.timey.StopwatchStartCommand#StopwatchStartCommand(rmblworx.tools.timey.IStopwatch)}.
	 */
	@Test(expected = NullArgumentException.class)
	public final void testShouldFailBecauseReceiverIsNull() {
		this.command = new StopwatchStartCommand(null);
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.StopwatchStartCommand#execute()}.
	 */
	@Test
	public final void testExecute() {
		when(this.mockedReceiver.startStopwatch()).thenReturn(this.descriptor);
		assertNotNull(this.invoker.execute());
	}

}
