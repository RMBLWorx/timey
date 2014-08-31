package rmblworx.tools.timey;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Tests für die TimeyUtils.
 *
 * @author mmatthies
 */
public class TimeyUtilsTest {
	/**
	 * Text für die Meldung eines unerwarteten Ergebnisses.
	 */
	private static final String TEXT_UNERWARTETER_RUECKGABEWERT = "Es wurde ein falscher Rückgabewert geliefert!";

	/**
	 * Für die Tests benötigte, leere Zeichenkette.
	 */
	private String emptyString;
	/**
	 * Für die Tests benötigte, leere Zeichenkette.
	 */
	private final String normalString = "this is a normal string";
	/**
	 * Den für die Tests benötigten, null referenzierenden Typ einer Zeichenkette.
	 */
	private String nullString = null;

	/**
	 * @throws java.lang.Exception
	 *             wenn eine Ausnahme auftritt. wenn eine Ausnahme auftritt.
	 */
	@Before
	public final void setUp() throws Exception {
		this.emptyString = "";
		this.nullString = null;
	}

	/**
	 * @throws java.lang.Exception
	 *             wenn eine Ausnahme auftritt.
	 */
	@After
	public final void tearDown() throws Exception {
		this.emptyString = null;
		this.nullString = null;
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.TimeyUtils#getOsName()}.
	 */
	@Test
	public final void testGetOsName() {
		assertNotNull(TEXT_UNERWARTETER_RUECKGABEWERT, TimeyUtils.getOsName());
		assertTrue(TEXT_UNERWARTETER_RUECKGABEWERT, TimeyUtils.getOsName().length() > 0);
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.TimeyUtils#isEmpty(java.lang.String)}.
	 */
	@Test
	public final void testIsEmpty() {
		assertTrue(TEXT_UNERWARTETER_RUECKGABEWERT, TimeyUtils.isEmpty(this.emptyString));
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.TimeyUtils#isLinuxSystem()}.
	 */
	@Test
	public final void testIsLinuxSystem() {
		assertNotNull(TEXT_UNERWARTETER_RUECKGABEWERT, Boolean.valueOf(TimeyUtils.isLinuxSystem()));
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.TimeyUtils#isNull(java.lang.String)}.
	 */
	@Test
	public final void testIsNull() {
		assertTrue(TEXT_UNERWARTETER_RUECKGABEWERT, TimeyUtils.isNull(this.nullString));
		assertFalse(TEXT_UNERWARTETER_RUECKGABEWERT, TimeyUtils.isNull(this.normalString));
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.TimeyUtils#isNullOrEmpty(java.lang.String[])}.
	 */
	@Test
	public final void testIsNullOrEmpty() {
		assertTrue(TEXT_UNERWARTETER_RUECKGABEWERT, TimeyUtils.isNullOrEmpty(this.emptyString, this.nullString));
		assertTrue(TEXT_UNERWARTETER_RUECKGABEWERT,
				TimeyUtils.isNullOrEmpty(this.emptyString, this.nullString, this.normalString));
		assertTrue(TEXT_UNERWARTETER_RUECKGABEWERT, TimeyUtils.isNullOrEmpty(this.emptyString));
		assertFalse(TEXT_UNERWARTETER_RUECKGABEWERT, TimeyUtils.isNullOrEmpty(this.normalString));
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.TimeyUtils#isOSXSystem()}.
	 */
	@Test
	public final void testIsOSXSystem() {
		assertNotNull(TEXT_UNERWARTETER_RUECKGABEWERT, Boolean.valueOf(TimeyUtils.isOSXSystem()));
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.TimeyUtils#isWindowsSystem()}.
	 */
	@Test
	public final void testIsWindowsSystem() {
		assertNotNull(TEXT_UNERWARTETER_RUECKGABEWERT, Boolean.valueOf(TimeyUtils.isWindowsSystem()));
	}
}
