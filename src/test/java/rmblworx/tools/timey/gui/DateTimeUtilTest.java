package rmblworx.tools.timey.gui;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.Test;

/*
 * Copyright 2014-2015 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Test für Hilfsmethoden zum Umgang mit Datum/Zeit-Werten.
 * @author Christian Raue {@literal <christian.raue@gmail.com>}
 */
public class DateTimeUtilTest {

	/**
	 * Testet {@link DateTimeUtil#getDatePart(LocalDateTime)}.
	 */
	@Test
	public final void testGetDatePart() {
		for (final DateTimePartsValue testCase : getDateTimeParts()) {
			assertEquals(DateTimeUtil.getLocalDateForString(testCase.date),
					DateTimeUtil.getDatePart(DateTimeUtil.getLocalDateTimeForString(testCase.dateTime)));
		}
	}

	/**
	 * Testet {@link DateTimeUtil#getTimePart(LocalDateTime)}.
	 */
	@Test
	public final void testGetTimePart() {
		for (final DateTimePartsValue testCase : getDateTimeParts()) {
			assertEquals(DateTimeUtil.getLocalTimeForString(testCase.time),
					DateTimeUtil.getTimePart(DateTimeUtil.getLocalDateTimeForString(testCase.dateTime)));
		}
	}

	/**
	 * Testet {@link DateTimeUtil#getLocalDateTimeForString(String)}.
	 */
	@Test
	public final void testGetLocalDateTimeForString() {
		final String[] strings = new String[] {
			"01.01.1970 00:00:00",
		};
		for (final String string : strings) {
			assertEquals(String.format("Failed parsing and evaluating date/time string '%s'.", string),
					LocalDateTime.of(1970, 1, 1, 0, 0, 0), DateTimeUtil.getLocalDateTimeForString(string));
		}
	}

	/**
	 * Testet {@link DateTimeUtil#getLocalDateForString(String)}.
	 */
	@Test
	public final void testGetLocalDateForString() {
		final String[] strings = new String[] {
			"01.01.1970",
		};
		for (final String string : strings) {
			assertEquals(String.format("Failed parsing and evaluating date string '%s'.", string),
					LocalDate.ofEpochDay(0), DateTimeUtil.getLocalDateForString(string));
		}
	}

	/**
	 * Testet {@link DateTimeUtil#getLocalTimeForString(String)}.
	 */
	@Test
	public final void testGetLocalTimeForString() {
		final String[] strings = new String[] {
			"00:00:00",
		};
		for (final String string : strings) {
			assertEquals(String.format("Failed parsing and evaluating time string '%s'.", string),
					LocalTime.ofNanoOfDay(0), DateTimeUtil.getLocalTimeForString(string));
		}
	}

	/**
	 * Testet {@link DateTimeUtil#getLocalDateTimeInMillis(LocalDateTime)}.
	 */
	@Test
	public final void testGetLocalDateTimeInMillis() {
		assertEquals(0L, DateTimeUtil.getLocalDateTimeInMillis(DateTimeUtil.getLocalDateTimeForString("01.01.1970 00:00:00")));
		assertEquals(1419424496000L, DateTimeUtil.getLocalDateTimeInMillis(DateTimeUtil.getLocalDateTimeForString("24.12.2014 12:34:56")));
	}

	/**
	 * Testet {@link DateTimeUtil#getLocalDateTimeFromMillis(long)}.
	 */
	@Test
	public final void testGetLocalDateTimeFromMillis() {
		assertEquals(DateTimeUtil.getLocalDateTimeForString("01.01.1970 00:00:00"), DateTimeUtil.getLocalDateTimeFromMillis(0L));
		assertEquals(DateTimeUtil.getLocalDateTimeForString("24.12.2014 12:34:56"), DateTimeUtil.getLocalDateTimeFromMillis(1419424496000L));
	}

	/**
	 * @return Testfälle
	 */
	private DateTimePartsValue[] getDateTimeParts() {
		return new DateTimePartsValue[] {
			new DateTimePartsValue("24.12.2014", "12:00:00"), // Zeitpunkt in Zeitzone "CET"
			new DateTimePartsValue("04.04.2014", "12:00:00"), // Zeitpunkt in Zeitzone "CEST"
		};
	}

	private final class DateTimePartsValue {

		public final String date;
		public final String time;
		public final String dateTime;

		public DateTimePartsValue(final String date, final String time) {
			this.date = date;
			this.time = time;
			this.dateTime = String.format("%s %s", date, time);
		}

	}

}
