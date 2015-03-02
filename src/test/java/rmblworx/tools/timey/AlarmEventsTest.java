package rmblworx.tools.timey;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import rmblworx.tools.timey.event.AlarmExpiredEvent;
import rmblworx.tools.timey.event.AlarmsModifiedEvent;
import rmblworx.tools.timey.event.TimeyEvent;
import rmblworx.tools.timey.event.TimeyEventListener;
import rmblworx.tools.timey.vo.AlarmDescriptor;
import rmblworx.tools.timey.vo.TimeDescriptor;

/*
 * Copyright 2014-2015 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Tests für die Ereignisse der Alarm-Funktionalität.
 * @author Christian Raue {@literal <christian.raue@gmail.com>}
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-timey-context.xml", "/alarm-spring-timey-context.xml" })
public class AlarmEventsTest {

	/**
	 * Max. Zeit (in ms), die auf das Eintreten eines Ereignisses gewartet werden soll.
	 */
	protected static final int WAIT_FOR_EVENT = 5000;

	private final ArgumentCaptor<TimeyEvent> eventCaptor = ArgumentCaptor.forClass(TimeyEvent.class);

	private final long now = System.currentTimeMillis();
	private final long oneHourAgo = now - 60 * 60 * 1000;
	private final long inOneMinute = now + 60 * 1000;

	/**
	 * Inaktiver Alarm, der vor einer Stunde ausgelöst wurde.
	 */
	private final AlarmDescriptor alarmOneHourAgo = new AlarmDescriptor(new TimeDescriptor(oneHourAgo), false, "alter Alarm", "", null);

	/**
	 * Alarm, der in einer Minute ausgelöst wird.
	 */
	private final AlarmDescriptor alarmInOneMinute = new AlarmDescriptor(new TimeDescriptor(inOneMinute), true, "Zukunftsalarm", "", null);

	/**
	 * Jetzt auzulösender Alarm.
	 */
	private final AlarmDescriptor alarmNow = new AlarmDescriptor(new TimeDescriptor(now), true, "relevanter Alarm", "", null);

	private ITimey facade;

	private TimeyEventListener eventListener;

	@Before
	public void setUp() {
		facade = new TimeyFacade();

		// alle Alarme löschen
		for (final AlarmDescriptor alarm : facade.getAllAlarms()) {
			facade.removeAlarm(alarm);
		}

		eventListener = mock(TimeyEventListener.class);
		facade.addEventListener(eventListener);
	}

	/**
	 * Testet Ereignisse beim Anlegen eines inaktiven Alarms.
	 */
	@Test
	public final void testCreateDisabledAlarmNoExpiredEvent() {
		facade.setAlarm(alarmOneHourAgo);

		final Class<?>[] expectedEvents = new Class<?>[] { AlarmsModifiedEvent.class };

		// sicherstellen, dass erwartete Ereignisse ausgelöst wurden
		verify(eventListener, timeout(WAIT_FOR_EVENT).times(expectedEvents.length)).handleEvent(eventCaptor.capture());
		final List<TimeyEvent> events = eventCaptor.getAllValues();
		assertEquals(expectedEvents.length, events.size());
		assertContainsAllAndOnlyTypes(events, expectedEvents);
	}

	/**
	 * Testet Ereignisse beim Anlegen eines Alarms, der erst irgendwann später ausgelöst wird.
	 */
	@Test
	public final void testCreateFutureAlarmNoExpiredEvent() {
		facade.setAlarm(alarmInOneMinute);

		final Class<?>[] expectedEvents = new Class<?>[] { AlarmsModifiedEvent.class };

		// sicherstellen, dass erwartete Ereignisse ausgelöst wurden
		verify(eventListener, timeout(WAIT_FOR_EVENT).times(expectedEvents.length)).handleEvent(eventCaptor.capture());
		final List<TimeyEvent> events = eventCaptor.getAllValues();
		assertEquals(expectedEvents.length, events.size());
		assertContainsAllAndOnlyTypes(events, expectedEvents);
	}

	/**
	 * Testet Ereignisse beim Anlegen eines jetzt auzulösenden Alarms.
	 */
	@Test
	public final void testCreateAlarmEvents() {
		facade.setAlarm(alarmNow);

		final Class<?>[] expectedEvents = new Class<?>[] {
			AlarmsModifiedEvent.class, // Anlegen des Alarms
			AlarmsModifiedEvent.class, // Deaktivierung des Alarms beim Auslösen
			AlarmExpiredEvent.class, // Auslösen des Alarms
		};

		// sicherstellen, dass erwartete Ereignisse ausgelöst wurden
		verify(eventListener, timeout(WAIT_FOR_EVENT).times(expectedEvents.length)).handleEvent(eventCaptor.capture());
		final List<TimeyEvent> events = eventCaptor.getAllValues();
		assertEquals(expectedEvents.length, events.size());
		assertContainsAllAndOnlyTypes(events, expectedEvents);
		for (final TimeyEvent event : events) {
			if (event instanceof AlarmExpiredEvent) {
				final AlarmDescriptor alarm = ((AlarmExpiredEvent) event).getAlarmDescriptor();
				assertNotNull(alarm);
				assertEquals(alarmNow.getDescription(), alarm.getDescription());
				assertFalse(alarm.getIsActive());
			}
		}
	}

	/**
	 * Testet Ereignisse beim Anlegen mehrerer Alarme.
	 */
	@Test
	public final void testCreateSeveralAlarmsEvents() {
		facade.setAlarm(alarmOneHourAgo);
		facade.setAlarm(alarmInOneMinute);
		facade.setAlarm(alarmNow);

		final Class<?>[] expectedEvents = new Class<?>[] {
			AlarmsModifiedEvent.class, // Anlegen ...
			AlarmsModifiedEvent.class, // ... der drei ...
			AlarmsModifiedEvent.class, // ... Alarme
			AlarmsModifiedEvent.class, // Deaktivierung des Alarms beim Auslösen
			AlarmExpiredEvent.class, // Auslösen des Alarms
		};

		// sicherstellen, dass erwartete Ereignisse ausgelöst wurden
		verify(eventListener, timeout(WAIT_FOR_EVENT).times(expectedEvents.length)).handleEvent(eventCaptor.capture());
		final List<TimeyEvent> events = eventCaptor.getAllValues();
		assertEquals(expectedEvents.length, events.size());
		assertContainsAllAndOnlyTypes(events, expectedEvents);
	}

	/**
	 * Stellt sicher,
	 * <ul>
	 * <li>dass jedes Element in <code>items</code> von einem der in <code>types</code> aufgeführten Typen ist und</li>
	 * <li>dass jeder in <code>types</code> aufgeführte Typ in <code>items</code> vorkommt.</li>
	 * </ul>
	 * @param items Elemente
	 * @param types Typen
	 */
	protected final void assertContainsAllAndOnlyTypes(final Collection<?> items, final Class<?>... types) {
		assertContainsAllTypes(items, types);
		assertContainsOnlyTypes(items, types);
	}

	/**
	 * Stellt sicher, dass jeder in <code>types</code> aufgeführte Typ in <code>items</code> vorkommt.
	 * @param items Elemente
	 * @param types Typen
	 */
	protected final void assertContainsOnlyTypes(final Collection<?> items, final Class<?>... types) {
		for (final Object item : items) {
			assertIsOfOneType(item, types);
		}
	}

	/**
	 * Stellt sicher, dass das Element <code>item</code> von einem der in <code>types</code> aufgeführten Typen ist.
	 * @param item Element
	 * @param types Typen
	 */
	protected final void assertIsOfOneType(final Object item, final Class<?>... types) {
		for (final Class<?> klass : types) {
			if (klass.isInstance(item)) {
				return;
			}
		}

		fail(String.format("%s ist vom unerwarteten Typ %s.", item, item.getClass()));
	}

	/**
	 * Stellt sicher, dass jedes Element in <code>items</code> von einem der in <code>types</code> aufgeführten Typen ist.
	 * @param items Elemente
	 * @param types Typen
	 */
	protected final void assertContainsAllTypes(final Collection<?> items, final Class<?>... types) {
		for (final Class<?> klass : types) {
			assertContainsElementOfType(items, klass);
		}
	}

	/**
	 * Stellt sicher, dass ein Element des Typs <code>type</code> in <code>items</code> vorkommt.
	 * @param items Elemente
	 * @param type Typ
	 */
	protected final void assertContainsElementOfType(final Collection<?> items, final Class<?> type) {
		for (final Object item : items) {
			if (type.isInstance(item)) {
				return;
			}
		}

		fail(String.format("%s enthält kein Element vom Typ %s.", items, type));
	}

}
