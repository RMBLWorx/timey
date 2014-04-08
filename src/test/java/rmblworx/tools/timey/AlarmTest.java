/**
 *
 */
package rmblworx.tools.timey;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import rmblworx.tools.timey.exception.NullArgumentException;
import rmblworx.tools.timey.exception.ValueMinimumArgumentException;
import rmblworx.tools.timey.persistence.service.IAlarmService;
import rmblworx.tools.timey.vo.AlarmDescriptor;
import rmblworx.tools.timey.vo.TimeDescriptor;

/**
 * @author mmatthies
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-timey-context.xml" })
public class AlarmTest {

	private static final long EXPECTED_MILLISECONDS = 100;

	@Autowired
	private AlarmDescriptor descriptor;
	private IAlarm effectiveDelegate;
	private List<AlarmDescriptor> list;
	@Mock
	private IAlarmService service;

	private Boolean assertThatTimestampIsPresent(List<AlarmDescriptor> list, AlarmDescriptor expectedDescriptor) {
		Boolean result = Boolean.FALSE;
		for (AlarmDescriptor timeDescr : list) {
			if (timeDescr.getAlarmtime().getMilliSeconds() == expectedDescriptor.getAlarmtime()
					.getMilliSeconds()) {
				result = Boolean.TRUE;
				break;
			}
		}
		return result;
	}

	private List<AlarmDescriptor> createListWithDescriptors() {
		this.list = new ArrayList<AlarmDescriptor>();
		final AlarmDescriptor ad = new AlarmDescriptor(new TimeDescriptor(EXPECTED_MILLISECONDS), false, "Text",
				"/bla/blub", null);
		this.list.add(ad);
		return this.list;
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.effectiveDelegate = new Alarm(this.service, 1, TimeUnit.MINUTES);
		this.descriptor.getAlarmtime().setMilliSeconds(EXPECTED_MILLISECONDS);
		this.list = this.createListWithDescriptors();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		this.descriptor = null;
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.Alarm#getAllAlarms()}.
	 */
	@Test
	public final void testGetAllAlarms() {
		when(this.service.getAll()).thenReturn(this.list);
		this.effectiveDelegate.setAlarm(this.descriptor);
		assertNotNull("Es wurde keine leere Liste geliefert!", this.effectiveDelegate.getAllAlarms());
		assertFalse("Leere Ergebnismenge!", this.effectiveDelegate.getAllAlarms().isEmpty());
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.Alarm#isAlarmActivated(TimeDescriptor)}.
	 */
	@Test
	public final void testIsActivatedShouldReturnNullBecauseNoAlarmtimeWasSetBefore() {
		this.effectiveDelegate = new Alarm(this.service, 1, TimeUnit.MINUTES);
		AlarmDescriptor expected = null;

		when(this.service.isActivated(expected)).thenReturn(null);
		this.effectiveDelegate.setAlarm(expected);

		assertNull(this.effectiveDelegate.isAlarmActivated(expected));
	}

	/**
	 * Test method for
	 * {@link rmblworx.tools.timey.Alarm#isAlarmActivated(rmblworx.tools.timey.vo.TimeDescriptor)}.
	 */
	@Test
	public final void testIsAlarmActivated() {
		when(this.service.isActivated(this.descriptor)).thenReturn(Boolean.TRUE);
		when(this.service.create(this.descriptor)).thenReturn(Boolean.TRUE);
		when(this.service.setState(this.descriptor, Boolean.TRUE)).thenReturn(Boolean.TRUE);
		this.effectiveDelegate.setAlarm(this.descriptor);
		this.effectiveDelegate.setStateOfAlarm(this.descriptor, true);
		assertTrue("Alarmzeit nicht aktiv!", this.effectiveDelegate.isAlarmActivated(this.descriptor));
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.Alarm#removeAlarm(rmblworx.tools.timey.vo.TimeDescriptor)}.
	 */
	@Test
	public final void testRemoveAlarm() {
		this.effectiveDelegate.setAlarm(this.descriptor);
		when(this.service.delete(this.descriptor)).thenReturn(true);

		assertTrue("Alarmzeit wurde nicht aus der DB entfernt!",
				this.effectiveDelegate.removeAlarm(this.descriptor));

	}

	/**
	 * Test method for {@link rmblworx.tools.timey.Alarm#setAlarm(TimeDescriptor)}.
	 */
	@Test
	public final void testSetAlarm() {
		Boolean result = Boolean.FALSE;
		this.descriptor.getAlarmtime().setMilliSeconds(EXPECTED_MILLISECONDS);
		when(this.service.create(this.descriptor)).thenReturn(Boolean.TRUE);
		this.effectiveDelegate.setAlarm(this.descriptor);

		when(this.service.getAll()).thenReturn(this.list);
		result = this.assertThatTimestampIsPresent(this.effectiveDelegate.getAllAlarms(), this.descriptor);

		assertTrue("Alarmzeit wurde nicht erzeugt!", result);
	}

	/**
	 * Test method for
	 * {@link rmblworx.tools.timey.Alarm#setStateOfAlarm(rmblworx.tools.timey.vo.TimeDescriptor, java.lang.Boolean)}
	 * .
	 */
	@Test
	public final void testSetStateOfAlarm() {
		when(this.service.create(this.descriptor)).thenReturn(Boolean.TRUE);
		when(this.service.setState(this.descriptor, Boolean.TRUE)).thenReturn(Boolean.TRUE);
		when(this.service.isActivated(this.descriptor)).thenReturn(Boolean.TRUE);

		this.effectiveDelegate.setAlarm(this.descriptor);
		assertTrue(this.effectiveDelegate.setStateOfAlarm(this.descriptor, true));
		assertTrue("Aktivierung fehlgeschlagen!", this.effectiveDelegate.isAlarmActivated(this.descriptor));

		when(this.service.setState(this.descriptor, Boolean.FALSE)).thenReturn(Boolean.TRUE);
		when(this.service.isActivated(this.descriptor)).thenReturn(Boolean.FALSE);
		assertTrue(this.effectiveDelegate.setStateOfAlarm(this.descriptor, false));
		assertFalse("Deaktivierung fehlgeschlagen!", this.effectiveDelegate.isAlarmActivated(this.descriptor));
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.Alarm#Alarm(IAlarmService)} .
	 */
	@Test(expected = NullArgumentException.class)
	public final void testShouldFailBecauseServiceIsNull() {
		this.effectiveDelegate = new Alarm(null, 1, TimeUnit.MINUTES);
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.Alarm#Alarm(IAlarmService)} .
	 */
	@Test(expected = NullArgumentException.class)
	public final void testShouldFailBecauseTimeUnitIsNull() {
		this.effectiveDelegate = new Alarm(null, 1, null);
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.Alarm#Alarm(IAlarmService)} .
	 */
	@Test(expected = ValueMinimumArgumentException.class)
	public final void testShouldFailBecauseDelayIsLessThanOne() {
		this.effectiveDelegate = new Alarm(this.service, 0, TimeUnit.MINUTES);
	}
}
