package rmblworx.tools.timey.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import rmblworx.tools.timey.vo.AlarmDescriptor;
import rmblworx.tools.timey.vo.TimeDescriptor;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Test für {@link AlarmDescriptorConverter}.
 * @author Christian Raue {@literal <christian.raue@gmail.com>}
 */
public class AlarmDescriptorConverterTest {

	/**
	 * Testet {@link AlarmDescriptorConverter#getAsAlarm(AlarmDescriptor)}.
	 */
	@Test
	public final void testGetAsAlarm() {
		// TODO snooze
		final long now = System.currentTimeMillis();
		final AlarmDescriptor ad = new AlarmDescriptor(new TimeDescriptor(now), false, "alarm", "/path/to/sound", null);
		final Alarm alarm = AlarmDescriptorConverter.getAsAlarm(ad);

		assertEquals(now, alarm.getDateTimeInMillis());
		assertEquals("alarm", alarm.getDescription());
		assertEquals("/path/to/sound", alarm.getSound());
		assertFalse(alarm.isEnabled());
	}

	/**
	 * Testet {@link AlarmDescriptorConverter#getAsAlarm(AlarmDescriptor)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public final void testGetAsAlarmWithNullArgument() {
		AlarmDescriptorConverter.getAsAlarm(null);
	}

	/**
	 * Testet {@link AlarmDescriptorConverter#getAsAlarmDescriptor(Alarm)}.
	 */
	@Test
	public final void testGetAsAlarmDescriptor() {
		final long now = System.currentTimeMillis();
		final Alarm alarm = new Alarm(DateTimeUtil.getLocalDateTimeFromMillis(now), "alarm", "/path/to/sound", false);
		final AlarmDescriptor ad = AlarmDescriptorConverter.getAsAlarmDescriptor(alarm);

		assertEquals(now, ad.getAlarmtime().getMilliSeconds());
		assertEquals("alarm", ad.getDescription());
		assertEquals("/path/to/sound", ad.getSound().toString());
		assertFalse(ad.getIsActive());
	}

	/**
	 * Testet {@link AlarmDescriptorConverter#getAsAlarmDescriptor(Alarm)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public final void testGetAsAlarmDescriptorWithNullArgument() {
		AlarmDescriptorConverter.getAsAlarmDescriptor(null);
	}

}
