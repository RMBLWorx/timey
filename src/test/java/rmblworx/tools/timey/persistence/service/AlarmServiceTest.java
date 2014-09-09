package rmblworx.tools.timey.persistence.service;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import rmblworx.tools.timey.vo.AlarmDescriptor;
import rmblworx.tools.timey.vo.TimeDescriptor;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * @author mmatthies
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-timey-context.xml", "/alarm-spring-timey-context.xml" })
public class AlarmServiceTest {

	private static final int EXPECTED_MILLISECONDS = 1000;
	private static final String KEIN_ALARMZEITPUNKT_ERZEUGT = "Kein Alarmzeitpunkt erzeugt!";
	private static final String NOT_NULL_MSG = "Es wurde kein null zurückgegeben!";
	private AlarmDescriptor expectedAlarmDescriptor;

	@Qualifier("alarmService")
	@Autowired
	private IAlarmService service;

	@Before
	public final void setUp() {
		this.expectedAlarmDescriptor = new AlarmDescriptor(new TimeDescriptor(EXPECTED_MILLISECONDS), true, "Text",
				"/bla", new TimeDescriptor(EXPECTED_MILLISECONDS + 1));
	}

	@After
	public final void tearDown() {
		this.expectedAlarmDescriptor = null;
	}

	/**
	 * Test method for {@link IAlarmService#create(AlarmDescriptor)}.
	 */
	@Test
	public final void testCreate() {
		boolean found = false;

		assertTrue(KEIN_ALARMZEITPUNKT_ERZEUGT, this.service.create(this.expectedAlarmDescriptor));
		final List<AlarmDescriptor> actualList = this.service.getAll();
		for (final AlarmDescriptor alarmDescriptor : actualList) {
			if (alarmDescriptor.getAlarmtime().getMilliSeconds() == EXPECTED_MILLISECONDS) {
				found = true;
			}
		}
		assertTrue(KEIN_ALARMZEITPUNKT_ERZEUGT, found);

		this.service.delete(this.expectedAlarmDescriptor);
	}

	/**
	 * Testet das Speichern eines Alarms ohne Klingelton.
	 */
	@Test
	public final void testCreateAlarmWithoutSound() {
		final AlarmDescriptor alarm = new AlarmDescriptor(new TimeDescriptor(0), true, "Text", null, null);

		try {
			assertTrue(KEIN_ALARMZEITPUNKT_ERZEUGT, this.service.create(alarm));
			boolean found = false;
			for (final AlarmDescriptor ad : this.service.getAll()) {
				if (ad.getSound() == null) {
					found = true;
					break;
				}
			}
			assertTrue(KEIN_ALARMZEITPUNKT_ERZEUGT, found);
		} finally {
			this.service.delete(alarm);
		}
	}

	/**
	 * Test method for {@link IAlarmService#delete(AlarmDescriptor)}.
	 */
	@Test
	public final void testDeleteAlarm() {
		assertTrue(KEIN_ALARMZEITPUNKT_ERZEUGT, this.service.create(this.expectedAlarmDescriptor));
		assertTrue(this.service.delete(this.expectedAlarmDescriptor));
		final List<AlarmDescriptor> actualList = this.service.getAll();
		for (final AlarmDescriptor timeDescriptor : actualList) {
			if (timeDescriptor.getAlarmtime().getMilliSeconds() == this.expectedAlarmDescriptor.getAlarmtime()
					.getMilliSeconds()) {
				fail("Alarmzeitpunkt wurde nicht gelöscht!");
			}
		}
	}

	/**
	 * Test method for {@link IAlarmService#delete(AlarmDescriptor)}.
	 */
	@Test
	public final void testDeleteAlarmShouldFailBecauseDescriptorIsNull() {
		assertNull(NOT_NULL_MSG, this.service.delete(null));
	}

	/**
	 * Test method for {@link IAlarmService#isActivated(AlarmDescriptor)}.
	 */
	@Test
	public void testIsActivatedShouldReturnNullBecauseTimestampIsNotPresent() {
		assertNull(this.service.isActivated(this.expectedAlarmDescriptor));
		assertNull(this.service.isActivated(null));
	}

	/**
	 * Test method for {@link IAlarmService#setState(AlarmDescriptor, Boolean)}.
	 */
	@Test
	public final void testSetIsActivated() {
		final Boolean expectedState = Boolean.TRUE;
		this.service.create(this.expectedAlarmDescriptor);
		this.service.setState(this.expectedAlarmDescriptor, expectedState);
		assertTrue(this.service.isActivated(this.expectedAlarmDescriptor));

		this.service.delete(this.expectedAlarmDescriptor);
	}

	/**
	 * Test method for {@link IAlarmService#setState(AlarmDescriptor, Boolean)}.
	 */
	@Test
	public final void testSetIsActivatedShouldFailBecauseDescriptorIsNull() {
		assertNull(NOT_NULL_MSG, this.service.setState(null, Boolean.FALSE));
	}

}
