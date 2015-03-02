package rmblworx.tools.timey;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rmblworx.tools.timey.exception.NullArgumentException;
import rmblworx.tools.timey.vo.AlarmDescriptor;

/*
 * Copyright 2014-2015 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * @author mmatthies
 */
public class AlarmGetAllAlarmsCommandTest {

	private ICommand command;
	@Mock
	private AlarmDescriptor descriptor;
	private Invoker<List<AlarmDescriptor>> invoker;
	private List<AlarmDescriptor> list;
	@Mock
	private Alarm mockedReceiver;

	@Before
	public final void setUp() {
		MockitoAnnotations.initMocks(this);
		this.command = new AlarmGetAllAlarmsCommand(this.mockedReceiver);
		this.invoker = new Invoker<>();
		this.invoker.storeCommand(this.command);
		this.list = new ArrayList<>();
		this.list.add(this.descriptor);
	}

	@After
	public final void tearDown() {
		this.invoker = null;
		this.mockedReceiver = null;
		this.command = null;
	}

	/**
	 * Test method for {@link AlarmGetAllAlarmsCommand#execute()}.
	 */
	@Test
	public final void testExecute() {
		when(this.mockedReceiver.getAllAlarms()).thenReturn(this.list);
		assertNotNull("null zurückgegeben!", this.invoker.execute());
	}

	/**
	 * Test method for {@link AlarmGetAllAlarmsCommand#AlarmGetAllAlarmsCommand(IAlarm)}.
	 */
	@Test(expected = NullArgumentException.class)
	public final void testShouldFailBecauseReceiverIsNull() {
		this.command = new AlarmGetAllAlarmsCommand(null);
	}
}
