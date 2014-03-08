/**
 * 
 */
package rmblworx.tools.timey.persistence.service;

import static org.junit.Assert.assertEquals;

import java.sql.Timestamp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import rmblworx.tools.timey.persistence.model.AlarmTimestamp;

/**
 * @author mmatthies
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-timey-context.xml" })
public class AlarmTimestampServiceTest {

	private long currentTimeMillis;
	private AlarmTimestamp expectedEntity;
	private Timestamp expectedTimestamp;

	@Autowired
	private IAlarmTimestampService service;

	@Before
	public void setUp() throws Exception {
		this.currentTimeMillis = System.currentTimeMillis();
		this.expectedTimestamp = new Timestamp(this.currentTimeMillis);
		this.expectedEntity = new AlarmTimestamp();
	}

	@After
	public void tearDown() throws Exception {
		this.expectedEntity = null;
		this.expectedTimestamp = null;
		this.currentTimeMillis = 0;
	}

	/**
	 * Test method for
	 * {@link rmblworx.tools.timey.persistence.service. IAlarmTimestampService#create(rmblworx.tools.timey.persistence.model.AlarmTimestamp)}
	 * .
	 */
	@Test
	public void testCreate() {

		this.expectedEntity.setAlarmTimestamp(this.expectedTimestamp);
		this.expectedEntity.setIsActivated(Boolean.TRUE);
		this.service.create(this.expectedEntity);
		final AlarmTimestamp actualEntity = this.service.findById(this.expectedEntity.getId());

		assertEquals(this.expectedEntity.getId(), actualEntity.getId());
		assertEquals(this.expectedEntity.getAlarmTimestamp(), actualEntity.getAlarmTimestamp());
		assertEquals(this.expectedEntity.getIsActivated(), actualEntity.getIsActivated());
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.persistence.service.IAlarmTimestampService#delete(Long)} .
	 */
	@Test
	public void testDeleteAlarmTimestamp() {

		this.expectedEntity.setAlarmTimestamp(this.expectedTimestamp);
		this.expectedEntity.setIsActivated(Boolean.TRUE);
		this.service.create(this.expectedEntity);

		Long id = this.expectedEntity.getId();
		this.service.delete(this.expectedEntity.getId());

		assertEquals(null, this.service.findById(id));
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.persistence.service.IAlarmTimestampService#delete(Long)} .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testDeleteAlarmTimestampShouldFailBecauseIdNull() {
		this.service.delete(null);
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.persistence.service.IAlarmTimestampService#activate(Long, Boolean)} .
	 */
	@Test
	public void testSetIsActivated() {

		this.expectedEntity.setAlarmTimestamp(this.expectedTimestamp);
		this.expectedEntity.setIsActivated(Boolean.TRUE);
		this.service.create(this.expectedEntity);
		AlarmTimestamp actualEntity = this.service.findById(this.expectedEntity.getId());

		assertEquals(this.expectedEntity.getIsActivated(), actualEntity.getIsActivated());

		this.service.activate(actualEntity.getId(), Boolean.FALSE);
		actualEntity = this.service.findById(actualEntity.getId());

		assertEquals(Boolean.FALSE, actualEntity.getIsActivated());
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.persistence.service.IAlarmTimestampService#activate(Long, Boolean)} .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetIsActivatedShouldFailBecauseIdNull() {
		this.service.activate(null, Boolean.FALSE);
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.persistence.service.IAlarmTimestampService#activate(Long, Boolean)} .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetIsActivatedShouldFailBecauseIsActivatedIsNull() {
		this.service.activate(this.expectedEntity.getId(), null);
	}
}
