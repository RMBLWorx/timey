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
 * Tests für die NullArgumentException-Klasse.
 *
 * @author mmatthies
 */
public class NullArgumentExceptionTest {

	/**
	 * Test method for {@link NullArgumentException#NullArgumentException()}.
	 */
	@Test
	public final void testNullArgumentException() {
		final NullArgumentException nullArgumentException = new NullArgumentException();
		assertNotNull(nullArgumentException.getLocalizedMessage());
		assertTrue(nullArgumentException.getLocalizedMessage().length() > 0);
	}

	/**
	 * Test method for {@link NullArgumentException#NullArgumentException(String)}.
	 */
	@Test
	public final void testNullArgumentExceptionString() {
		final String msg = "Testnachricht";
		final NullArgumentException nullArgumentException = new NullArgumentException(msg);

		assertNotNull(nullArgumentException.getLocalizedMessage());
		assertEquals(msg, nullArgumentException.getLocalizedMessage());
		assertTrue(nullArgumentException.getLocalizedMessage().length() > 0);
	}
}
