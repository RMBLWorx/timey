package rmblworx.tools.timey.exception;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Tests für die ValueMinimumArgumentException-Klasse.
 *
 * @author mmatthies
 */
public class ValueMinimumArgumentExceptionTest {

	/**
	 * Test method for
	 * {@link rmblworx.tools.timey.exception.ValueMinimumArgumentException#ValueMinimumArgumentException()}.
	 */
	@Test
	public final void testValueMinimumArgumentException() {
		final ValueMinimumArgumentException valueMinimumArgumentException = new ValueMinimumArgumentException();

		assertNotNull(valueMinimumArgumentException.getLocalizedMessage());
		assertTrue(valueMinimumArgumentException.getLocalizedMessage().length() > 0);
	}

	/**
	 * Test method for
	 * {@link rmblworx.tools.timey.exception.ValueMinimumArgumentException#ValueMinimumArgumentException(java.lang.String)}
	 * .
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
