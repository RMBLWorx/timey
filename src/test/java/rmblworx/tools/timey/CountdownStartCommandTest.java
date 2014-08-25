package rmblworx.tools.timey;

import static org.junit.Assert.assertEquals;
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
public class CountdownStartCommandTest {
	private ICommand command;
	@Mock
	private TimeDescriptor descriptor;
	private Invoker<TimeDescriptor> invoker;
	@Mock
	private ICountdown mockedReceiver;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.command = new CountdownStartCommand(this.mockedReceiver);
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
	 * Test method for {@link rmblworx.tools.timey.CountdownStartCommand#execute()}.
	 */
	@Test
	public final void testExecute() {
		when(this.mockedReceiver.startCountdown()).thenReturn(this.descriptor);
		assertEquals("Falsche Referenz!", this.descriptor, this.invoker.execute());
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.CountdownStartCommand#CountdownStartCommand(ICountdown)}.
	 */
	@Test(expected = NullArgumentException.class)
	public final void testExecuteShouldFailBecauseReceiverIsNull() {
		this.command = new CountdownStartCommand(null);
	}

}
