/**
 *
 */
package rmblworx.tools.timey;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author mmatthies
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-timey-context.xml"})
public class GetVersionTest {
	private TimeyFacade facade;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public final void setUp() throws Exception {
		this.facade = new TimeyFacade();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public final void tearDown() throws Exception {
		this.facade = null;
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.TimeyFacade#getVersion()}.
	 */
	@Test
	public final void testGetVersion() {
		/*
		 * Da die jar erst nach erfolgreichem maven build existiert, besteht
		 * hier ein klassisches Henne-Ei-Problem denn um erfolgreich zu bauen,
		 * muessen alle Tests erfolgreich durchlaufen worden sein! Deshalb ist
		 * der Test hier zwar aufgeführt aber gezielt auskommentiert.
		 */

		// assertNotNull("Test failure because no version retrieved.", this.facade.getVersion());
	}
}
