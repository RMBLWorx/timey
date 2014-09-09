package rmblworx.tools.timey.gui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Hilfsmethoden zum Umgang mit Datum/Zeit-Werten.
 * @author Christian Raue {@literal <christian.raue@gmail.com>}
 */
public final class DateTimeUtil {

	public static final long MILLI_TO_NANO = 1000L * 1000L;

	private static final String PATTERN_DATE_TIME = "dd.MM.yyyy HH:mm:ss";
	private static final String PATTERN_DATE = "dd.MM.yyyy";
	private static final String PATTERN_TIME = "HH:mm:ss";

	/**
	 * @param dateTime Datum-/Zeit-Wert
	 * @return Datumsanteil
	 */
	public static LocalDate getDatePart(final LocalDateTime dateTime) {
		return dateTime.toLocalDate();
	}

	/**
	 * @param dateTime Datum-/Zeit-Wert
	 * @return Zeitanteil
	 */
	public static LocalTime getTimePart(final LocalDateTime dateTime) {
		return dateTime.toLocalTime();
	}

	/**
	 * @param string Datum/Zeit-Wert
	 * @return {@link LocalDateTime}-Objekt mit entsprechendem Datum/Zeit-Wert
	 */
	public static LocalDateTime getLocalDateTimeForString(final String string) {
		return LocalDateTime.parse(string, DateTimeFormatter.ofPattern(PATTERN_DATE_TIME));
	}

	/**
	 * @param string Datum-Wert
	 * @return {@link LocalDate}-Objekt mit entsprechendem Datum-Wert
	 */
	public static LocalDate getLocalDateForString(final String string) {
		return LocalDate.parse(string, DateTimeFormatter.ofPattern(PATTERN_DATE));
	}

	/**
	 * @param string Zeit-Wert
	 * @return {@link LocalTime}-Objekt mit entsprechendem Zeit-Wert
	 */
	public static LocalTime getLocalTimeForString(final String string) {
		return LocalTime.parse(string, DateTimeFormatter.ofPattern(PATTERN_TIME));
	}

	/**
	 * @param dateTime Datum/Zeit-Wert
	 * @return UTC-basierter Datum/Zeit-Wert in ms
	 */
	public static long getLocalDateTimeInMillis(final LocalDateTime dateTime) {
		return LocalDateTime.of(1970, 1, 1, 0, 0, 0).until(dateTime, ChronoUnit.MILLIS);
	}

	/**
	 * @param millis UTC-basierter Datum/Zeit-Wert in ms
	 * @return Datum/Zeit-Wert
	 */
	public static LocalDateTime getLocalDateTimeFromMillis(final long millis) {
		return LocalDateTime.of(1970, 1, 1, 0, 0, 0).plus(millis, ChronoUnit.MILLIS);
	}

	/**
	 * Instanziierung verhindern.
	 */
	private DateTimeUtil() {
	}

}
