package rmblworx.tools.timey.vo;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import rmblworx.tools.timey.exception.ValueMinimumArgumentException;

/**
 * Testklasse die das Wertobjekt zum kapseln der Zeitwerte testet.
 * 
 * @author mmatthies
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-timey-context.xml" })
public class TimeDescriptorTest {

	/**
	 * Konstante fuer den erwarteten Testwert.
	 */
	private static final int EXPECTED_MILLISECONDS = 100;

	@Autowired
	private TimeDescriptor descriptor;

	/**
	 * @throws java.lang.Exception
	 *             Exception.
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 *             Exception
	 */
	@After
	public void tearDown() throws Exception {
		this.descriptor = null;
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.vo.TimeDescriptor}.
	 */
	@Test
	public final void testCorrectBehaviorOfTheVo() {
		this.descriptor.setMilliSeconds(EXPECTED_MILLISECONDS);
		long actualMilliseconds = this.descriptor.getMilliSeconds();

		assertEquals("Test fehlgeschlagen: Millisekunden falsch!", EXPECTED_MILLISECONDS, actualMilliseconds);
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.vo.TimeDescriptor#setMilliSeconds(long)}.
	 */
	@Test(expected = ValueMinimumArgumentException.class)
	public final void testShouldFailBecauseValueLessThanZero() {
		this.descriptor.setMilliSeconds(-1);
	}

}
