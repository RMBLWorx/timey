package rmblworx.tools.timey.exception;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Tests für die EmptyArgumentException-Klasse.
 *
 * @author mmatthies
 */
public class EmptyArgumentExceptionTest {

	/**
	 * Test method for {@link rmblworx.tools.timey.exception.EmptyArgumentException#EmptyArgumentException()}.
	 */
	@Test
	public final void testEmptyArgumentException() {
		final EmptyArgumentException emptyArgumentException = new EmptyArgumentException();

		assertNotNull(emptyArgumentException.getLocalizedMessage());
		assertTrue(emptyArgumentException.getLocalizedMessage().length() > 0);

	}

	/**
	 * Test method for
	 * {@link rmblworx.tools.timey.exception.EmptyArgumentException#EmptyArgumentException(java.lang.String)}.
	 */
	@Test
	public final void testEmptyArgumentExceptionString() {
		final String msg = "Testmeldung";
		final EmptyArgumentException emptyArgumentException = new EmptyArgumentException(msg);

		assertNotNull(emptyArgumentException.getLocalizedMessage());
		assertTrue(emptyArgumentException.getLocalizedMessage().length() > 0);
		assertEquals(msg, emptyArgumentException.getLocalizedMessage());
	}
}
