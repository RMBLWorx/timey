package rmblworx.tools.timey.exception;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Tests für die ValueMinimumArgumentException-Klasse.
 *
 * @author mmatthies
 */
public class ValueMinimumArgumentExceptionTest {

	/**
	 * Test method for {@link ValueMinimumArgumentException#ValueMinimumArgumentException()}.
	 */
	@Test
	public final void testValueMinimumArgumentException() {
		final ValueMinimumArgumentException valueMinimumArgumentException = new ValueMinimumArgumentException();

		assertNotNull(valueMinimumArgumentException.getLocalizedMessage());
		assertTrue(valueMinimumArgumentException.getLocalizedMessage().length() > 0);
	}

	/**
	 * Test method for {@link ValueMinimumArgumentException#ValueMinimumArgumentException(String)}.
	 */
	@Test
	public final void testValueMinimumArgumentExceptionString() {
		final String msg = "Testmeldung";
		final ValueMinimumArgumentException valueMinimumArgumentException = new ValueMinimumArgumentException(msg);

		assertNotNull(valueMinimumArgumentException.getLocalizedMessage());
		assertTrue(valueMinimumArgumentException.getLocalizedMessage().length() > 0);
		assertEquals(msg, valueMinimumArgumentException.getLocalizedMessage());
	}
}
