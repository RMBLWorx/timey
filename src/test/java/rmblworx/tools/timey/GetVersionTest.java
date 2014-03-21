package rmblworx.tools.timey;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author mmatthies
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-timey-context.xml" })
public class GetVersionTest {
	private static final Logger LOG = LoggerFactory.getLogger(GetVersionTest.class);

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
	 * 
	 * @throws Exception
	 * @throws IllegalArgumentException
	 */
	@Test
	public final void testGetVersion() throws IllegalArgumentException, Exception {
		if (TimeyUtils.isWindowsSystem()) {
			LOG.debug("Windows erkannt...");
		} else if (TimeyUtils.isLinuxSystem()) {
			LOG.debug("Linux erkannt...");
		} else if (TimeyUtils.isOSXSystem()) {
			LOG.debug("OS X erkannt...");
		}
		TestHelper.executeMavenPackageWithoutRunningTestsProcess();

		/*
		 * Da die jar erst nach erfolgreichem maven build existiert, besteht
		 * hier ein klassisches Henne-Ei-Problem denn um erfolgreich zu bauen,
		 * muessen alle Tests erfolgreich durchlaufen worden sein! Deshalb wird hier explizit gebaut ohne die Tests
		 * auszufuehren um im Anschluss daran den Versionstest durchfuehren zu können. .
		 */

		assertNotNull("Test fehlgeschlagen da keine Version zurueckgeliefert wurde.",
				this.facade.getVersion("timey.jar"));
	}

}
