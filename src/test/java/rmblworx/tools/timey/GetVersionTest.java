package rmblworx.tools.timey;

import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

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
	private static final String MVN_PACKAGE_DSKIP_TESTS_TRUE = "mvn package -DskipTests=true";
	private TimeyFacade facade;

	/**
	 * 
	 */
	private void executeProcess() {
		String line;
		try {
			LOG.debug("Baue jar-Archiv. Umgebungsvariable muss dazu fuer Maven 3 gesetzt sein!!!\nAuszufuehrendes Kommando: "
					+ MVN_PACKAGE_DSKIP_TESTS_TRUE);
			Process process = Runtime.getRuntime().exec(MVN_PACKAGE_DSKIP_TESTS_TRUE);
			Reader reader = new InputStreamReader(process.getInputStream());
			BufferedReader in = new BufferedReader(reader);
			while ((line = in.readLine()) != null) {
				System.out.println(line);
			}
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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
			this.executeProcess();
		}

		/*
		 * Da die jar erst nach erfolgreichem maven build existiert, besteht
		 * hier ein klassisches Henne-Ei-Problem denn um erfolgreich zu bauen,
		 * muessen alle Tests erfolgreich durchlaufen worden sein! Deshalb wird hier explizit gebaut ohne die Tests
		 * auszufuehren um im Anschluss daran den Versionstest durchfuehren zu können. .
		 */

		assertNotNull("Test failure because no version retrieved.", this.facade.getVersion("timey*.jar"));
	}

}
