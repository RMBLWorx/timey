/**
 * 
 */
package rmblworx.tools.timey.persistence.service;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import rmblworx.tools.timey.vo.TimeDescriptor;

/**
 * @author mmatthies
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-timey-context.xml" })
public class AlarmTimestampServiceTest {

	private static final int EXPECTED_MILLISECONDS = 1000;
	private static final String KEIN_ALARMZEITPUNKT_ERZEUGT = "Kein Alarmzeitpunkt erzeugt!";
	private static final String NOT_NULL_MSG = "Es wurde kein null zurueckgegeben!";
	private TimeDescriptor expectedTimeDescriptor;

	@Autowired
	private IAlarmTimestampService service;

	@Before
	public void setUp() throws Exception {
		this.expectedTimeDescriptor = new TimeDescriptor(EXPECTED_MILLISECONDS);
	}

	@After
	public void tearDown() throws Exception {
		this.expectedTimeDescriptor = null;
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.persistence.service. IAlarmTimestampService#create(TimeDescriptor)} .
	 */
	@Test
	public void testCreate() {
		boolean found = false;

		assertTrue(KEIN_ALARMZEITPUNKT_ERZEUGT, this.service.create(this.expectedTimeDescriptor));
		final List<TimeDescriptor> actualList = this.service.getAll();
		for (TimeDescriptor timeDescriptor : actualList) {
			if (timeDescriptor.getMilliSeconds() == EXPECTED_MILLISECONDS) {
				found = true;
			}
		}
		assertTrue(KEIN_ALARMZEITPUNKT_ERZEUGT, found);

		this.service.delete(this.expectedTimeDescriptor);
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.persistence.service.IAlarmTimestampService#delete(TimeDescriptor)} .
	 */
	@Test
	public void testDeleteAlarmTimestamp() {

		assertTrue(KEIN_ALARMZEITPUNKT_ERZEUGT, this.service.create(this.expectedTimeDescriptor));
		assertTrue(this.service.delete(this.expectedTimeDescriptor));
		final List<TimeDescriptor> actualList = this.service.getAll();
		for (TimeDescriptor timeDescriptor : actualList) {
			if (timeDescriptor.getMilliSeconds() == this.expectedTimeDescriptor.getMilliSeconds()) {
				fail("Alarmzeitpunkt wurde nicht geloescht!");
			}
		}
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.persistence.service.IAlarmTimestampService#delete(TimeDescriptor)} .
	 */
	@Test
	public void testDeleteAlarmTimestampShouldFailBecauseDescriptorIsNull() {
		assertNull(NOT_NULL_MSG, this.service.delete(null));
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.persistence.service.IAlarmTimestampService#setState(Long, Boolean)} .
	 */
	@Test
	public void testSetIsActivated() {
		final Boolean expectedState = Boolean.TRUE;
		this.service.create(this.expectedTimeDescriptor);
		this.service.setState(this.expectedTimeDescriptor, expectedState);
		assertTrue(this.service.isActivated(this.expectedTimeDescriptor));

		this.service.delete(this.expectedTimeDescriptor);
	}
	/**
	 * Test method for {@link rmblworx.tools.timey.persistence.service.IAlarmTimestampService#isActivated(TimeDescriptor)} .
	 */
	@Test
	public void testIsActivatedShouldReturnNullBecauseTimestampIsNotPresent() {
		assertNull(this.service.isActivated(this.expectedTimeDescriptor));
		assertNull(this.service.isActivated(null));
	}

	/**
	 * Test method for
	 * {@link rmblworx.tools.timey.persistence.service.IAlarmTimestampService#setState(TimeDescriptor, Boolean)} .
	 */
	@Test
	public void testSetIsActivatedShouldFailBecauseDescriptorIsNull() {
		assertNull(NOT_NULL_MSG, this.service.setState(null, Boolean.FALSE));
	}
}
