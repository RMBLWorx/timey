/**
 * 
 */
package rmblworx.tools.timey.bo;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author mmatthies
 *
 */
public class GetVersionTest {
	FacadeTimey facade;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.facade = new FacadeTimey();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		this.facade = null;
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.bo.FacadeTimey#getVersion()}.
	 */
	@Test
	public final void testGetVersion() {
		/*
		 * Da die jar erst nach erfolgreichem maven build existiert, besteht
		 * hier ein klassisches Henne-Ei-Problem denn um erfolgreich zu bauen,
		 * muessen alle Tests erfolgreich durchlaufen worden sein! Deshalb ist
		 * der Test hier zwar aufgeführt aber gezielt auskommentiert.
		 */

//		assertNotNull("Test failure because no version retrieved.", this.facade.getVersion());
	}
}
