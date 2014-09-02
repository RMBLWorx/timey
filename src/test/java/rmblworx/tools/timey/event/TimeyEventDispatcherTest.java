package rmblworx.tools.timey.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import rmblworx.tools.timey.gui.AlarmController;
import rmblworx.tools.timey.gui.CountdownController;
import rmblworx.tools.timey.gui.TimeyController;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Tests für die TimeyEventDispatcher-Implementierung.
 *
 * @author mmatthies
 */
public class TimeyEventDispatcherTest {

	/**
	 * Für jeweiligen Test erwarteter Listener.
	 */
	private static final TimeyEventListener EXPECTED_LISTENER = new TimeyController();
	/**
	 * Fehlermeldung bei unerwarteter Rückgabe.
	 */
	private static final String UNERWARTETER_RUECKGABEWERT = "Unerwarteter Rückgabewert!";
	/**
	 * Liste für den Test. Beinhaltet TimeyEventListener.
	 */
	private List<TimeyEventListener> allEventListener;
	/**
	 * Zu testende Klasse.
	 */
	private TimeyEventDispatcher timeyEventDispatcher;

	/**
	 * Stellt fest ob der Listener in der Liste enthalten ist.
	 *
	 * @param actual
	 *            zu durchsuchende Liste mit Listener.
	 * @param expectedListener
	 *            der erwartete Listener.
	 * @return true wenn vorhanden sonst false
	 */
	private boolean containsListener(final List<TimeyEventListener> actual, final TimeyEventListener expectedListener) {
		return actual.contains(expectedListener);
	}

	/**
	 * @throws java.lang.Exception
	 *             wenn eine Ausnahme auftritt.
	 */
	@Before
	public void setUp() throws Exception {
		this.timeyEventDispatcher = new TimeyEventDispatcher();
		this.allEventListener = new ArrayList<TimeyEventListener>();
		this.allEventListener.add(new AlarmController());
		this.allEventListener.add(new CountdownController());
	}

	/**
	 * @throws java.lang.Exception
	 *             wenn eine Ausnahme auftritt.
	 */
	@After
	public void tearDown() throws Exception {
		this.timeyEventDispatcher = null;
		this.allEventListener = null;
	}

	/**
	 * Test method for
	 * {@link rmblworx.tools.timey.event.TimeyEventDispatcher#addEventListener(rmblworx.tools.timey.event.TimeyEventListener)}
	 * .
	 */
	@Test
	public final void testAddEventListener() {
		this.timeyEventDispatcher.addEventListener(EXPECTED_LISTENER);
		final List<TimeyEventListener> actual = this.timeyEventDispatcher.getRegisteredListeners();

		assertNotNull(UNERWARTETER_RUECKGABEWERT, actual);
		assertTrue(UNERWARTETER_RUECKGABEWERT, this.containsListener(actual, EXPECTED_LISTENER));
		assertFalse(UNERWARTETER_RUECKGABEWERT, actual.isEmpty());
		assertTrue(UNERWARTETER_RUECKGABEWERT, actual.size() == 1);
	}

	/**
	 * Test method for
	 * {@link rmblworx.tools.timey.event.TimeyEventDispatcher#dispatchEvent(rmblworx.tools.timey.event.TimeyEvent)}.
	 */
	@Test
	public final void testDispatchEvent() {
		// TODO Dispatch Event testen
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.event.TimeyEventDispatcher#getRegisteredListeners()}.
	 */
	@Test
	public final void testGetRegistredListener() {
		List<TimeyEventListener> actual = this.timeyEventDispatcher.getRegisteredListeners();
		assertNotNull(UNERWARTETER_RUECKGABEWERT, actual);

		this.timeyEventDispatcher.addEventListener(EXPECTED_LISTENER);
		actual = this.timeyEventDispatcher.getRegisteredListeners();

		assertNotNull(UNERWARTETER_RUECKGABEWERT, actual);
		assertTrue(UNERWARTETER_RUECKGABEWERT, this.containsListener(actual, EXPECTED_LISTENER));
		assertFalse(UNERWARTETER_RUECKGABEWERT, actual.isEmpty());
		assertTrue(UNERWARTETER_RUECKGABEWERT, actual.size() == 1);
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.event.TimeyEventDispatcher#getRegisteredListeners()}.
	 */
	@Test(expected = UnsupportedOperationException.class)
	public final void testGetRegistredListenerShouldThrowUnsupportedOperationException() {
		final List<TimeyEventListener> actual = this.timeyEventDispatcher.getRegisteredListeners();
		actual.clear();
	}

	/**
	 * Test method for
	 * {@link rmblworx.tools.timey.event.TimeyEventDispatcher#removeEventListener(rmblworx.tools.timey.event.TimeyEventListener)}
	 * .
	 */
	@Test
	public final void testRemoveEventListener() {
		this.timeyEventDispatcher.addEventListener(EXPECTED_LISTENER);
		this.timeyEventDispatcher.removeEventListener(EXPECTED_LISTENER);
		final List<TimeyEventListener> actual = this.timeyEventDispatcher.getRegisteredListeners();

		assertFalse(UNERWARTETER_RUECKGABEWERT, this.containsListener(actual, EXPECTED_LISTENER));
	}
}
