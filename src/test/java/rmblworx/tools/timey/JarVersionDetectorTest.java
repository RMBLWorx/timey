/**
 * 
 */
package rmblworx.tools.timey;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author mmatthies
 */
public class JarVersionDetectorTest {
	private JarVersionDetector jarVersionDetector;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		TestHelper.executeMavenPackageWithoutRunningTestsProcess();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.jarVersionDetector = new JarVersionDetector();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		this.jarVersionDetector = null;
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.JarVersionDetector#detectJarVersion(java.lang.String)}.
	 */
	@Test
	public final void testDetectJarVersion() {
		assertNotNull(this.jarVersionDetector.detectJarVersion("timey*.jar"));
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.JarVersionDetector#detectJarVersion(java.lang.String)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public final void testShouldFailBecausePatternIsNull() {
		this.jarVersionDetector.detectJarVersion(null);
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.JarVersionDetector#detectJarVersion(java.lang.String)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public final void testShouldFailBecausePatternIsEmpty() {
		this.jarVersionDetector.detectJarVersion("");
	}
}
