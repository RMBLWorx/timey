package rmblworx.tools.timey.gui;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Hilfsmethoden zum Umgang mit Datum/Zeit-Werten.
 * @author Christian Raue {@literal <christian.raue@gmail.com>}
 */
public final class DateTimeUtil {

	private static final String PATTERN_DATE_TIME = "dd.MM.yyyy HH:mm:ss";
	private static final String PATTERN_DATE = "dd.MM.yyyy";
	private static final String PATTERN_TIME = "HH:mm:ss";

	/**
	 * @param dateTime Datum-/Zeit-Wert
	 * @return Datumsanteil in ms
	 */
	public static LocalDate getDatePart(final LocalDateTime dateTime) {
		return dateTime.toLocalDate();
	}

	/**
	 * @param dateTime Datum-/Zeit-Wert
	 * @return Zeitanteil in ms
	 */
	public static LocalTime getTimePart(final LocalDateTime dateTime) {
		return dateTime.toLocalTime();
	}

	/**
	 * @param string Datum/Zeit-Wert
	 * @return {@link LocalDateTime}-Objekt mit entsprechendem Datum/Zeit-Wert
	 */
	public static LocalDateTime getLocalDateTimeForString(final String string) {
		final String[] patterns = {
			PATTERN_DATE_TIME,
			PATTERN_DATE,
			PATTERN_TIME,
		};

		return parseString(string, patterns);
	}

	/**
	 * @param string Datum-Wert
	 * @return {@link LocalDate}-Objekt mit entsprechendem Datum-Wert
	 */
	public static LocalDate getLocalDateForString(final String string) {
		final String[] patterns = {
			PATTERN_DATE,
		};

		return getDatePart(parseString(string, patterns));
	}

	/**
	 * @param string Zeit-Wert
	 * @return {@link LocalTime}-Objekt mit entsprechendem Zeit-Wert
	 */
	public static LocalTime getLocalTimeForString(final String string) {
		final String[] patterns = {
			PATTERN_TIME,
		};

		return getTimePart(parseString(string, patterns));
	}

	/**
	 * @param string Datum/Zeit-Wert
	 * @param patterns gültige Muster zum Parsen des Wertes
	 * @return {@link LocalDateTime}-Objekt mit entsprechendem Datum/Zeit-Wert
	 */
	private static LocalDateTime parseString(final String string, final String[] patterns) {
		final SimpleDateFormat dateFormat = new SimpleDateFormat();

		for (final String pattern : patterns) {
			dateFormat.applyPattern(pattern);

			try {
				return new LocalDateTime(dateFormat.parse(string));
			} catch (final ParseException e) {
				continue;
			}
		}

		throw new RuntimeException(String.format("Unparseable date/time: %s", string));
	}

	/**
	 * Privater Konstruktor.
	 */
	private DateTimeUtil() {
	}

}
