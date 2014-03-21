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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import rmblworx.tools.timey.persistence.service.IAlarmTimestampService;
import rmblworx.tools.timey.vo.TimeDescriptor;

/**
 * @author mmatthies
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-timey-context.xml" })
public class AlarmTest {

	private static final long EXPECTED_MILLISECONDS = 100;

	@Autowired
	private TimeDescriptor descriptor;
	private IAlarm effectiveDelegate;
	private List<TimeDescriptor> list;
	@Mock
	private IAlarmTimestampService service;

	private Boolean assertThatTimestampIsPresent(List<TimeDescriptor> list, TimeDescriptor expectedDescriptor) {
		Boolean result = Boolean.FALSE;
		for (TimeDescriptor timeDescr : list) {
			if (timeDescr.getMilliSeconds() == expectedDescriptor.getMilliSeconds()) {
				result = Boolean.TRUE;
				break;
			}
		}
		return result;
	}

	private List<TimeDescriptor> createListWithDescriptors() {
		List<TimeDescriptor> list = new ArrayList<TimeDescriptor>();
		list.add(new TimeDescriptor(EXPECTED_MILLISECONDS));
		return list;
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.effectiveDelegate = new Alarm(this.service);
		this.descriptor.setMilliSeconds(EXPECTED_MILLISECONDS);
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
	 * Test method for {@link rmblworx.tools.timey.Alarm#getAllAlarmtimestamps()}.
	 */
	@Test
	public final void testGetAllAlarmtimestamps() {
		when(this.service.getAll()).thenReturn(this.list);
		this.effectiveDelegate.setAlarmtimestamp(this.descriptor);
		assertNotNull("Es wurde keine leere Liste geliefert!", this.effectiveDelegate.getAllAlarmtimestamps());
		assertFalse("Leere Ergebnismenge!", this.effectiveDelegate.getAllAlarmtimestamps().isEmpty());
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.Alarm#isAlarmtimestampActivated(TimeDescriptor)}.
	 */
	@Test
	public final void testIsActivatedShouldReturnNullBecauseNoAlarmtimeWasSetBefore() {
		this.effectiveDelegate = new Alarm(this.service);
		TimeDescriptor expected = null;

		when(this.service.isActivated(expected)).thenReturn(null);
		this.effectiveDelegate.setAlarmtimestamp(expected);

		assertNull(this.effectiveDelegate.isAlarmtimestampActivated(expected));
	}

	/**
	 * Test method for
	 * {@link rmblworx.tools.timey.Alarm#isAlarmtimestampActivated(rmblworx.tools.timey.vo.TimeDescriptor)}.
	 */
	@Test
	public final void testIsAlarmtimestampActivated() {
		when(this.service.isActivated(this.descriptor)).thenReturn(Boolean.TRUE);
		when(this.service.create(this.descriptor)).thenReturn(Boolean.TRUE);
		when(this.service.setState(this.descriptor, Boolean.TRUE)).thenReturn(Boolean.TRUE);
		this.effectiveDelegate.setAlarmtimestamp(this.descriptor);
		this.effectiveDelegate.setStateOfAlarmtimestamp(this.descriptor, true);
		assertTrue("Alarmzeit nicht aktiv!", this.effectiveDelegate.isAlarmtimestampActivated(this.descriptor));
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.Alarm#removeAlarmtimestamp(rmblworx.tools.timey.vo.TimeDescriptor)}.
	 */
	@Test
	public final void testRemoveAlarmtimestamp() {
		this.effectiveDelegate.setAlarmtimestamp(this.descriptor);
		when(this.service.delete(this.descriptor)).thenReturn(true);

		assertTrue("Alarmzeit wurde nicht aus der DB entfernt!",
				this.effectiveDelegate.removeAlarmtimestamp(this.descriptor));

	}

	/**
	 * Test method for {@link rmblworx.tools.timey.Alarm#setAlarmtimestamp(TimeDescriptor)}.
	 */
	@Test
	public final void testSetAlarmtimestamp() {
		Boolean result = Boolean.FALSE;
		this.descriptor.setMilliSeconds(EXPECTED_MILLISECONDS);
		when(this.service.create(this.descriptor)).thenReturn(Boolean.TRUE);
		this.effectiveDelegate.setAlarmtimestamp(this.descriptor);

		when(this.service.getAll()).thenReturn(this.list);
		result = this.assertThatTimestampIsPresent(this.effectiveDelegate.getAllAlarmtimestamps(), this.descriptor);

		assertTrue("Alarmzeit wurde nicht erzeugt!", result);
	}

	/**
	 * Test method for
	 * {@link rmblworx.tools.timey.Alarm#setStateOfAlarmtimestamp(rmblworx.tools.timey.vo.TimeDescriptor, java.lang.Boolean)}
	 * .
	 */
	@Test
	public final void testSetStateOfAlarmtimestamp() {
		when(this.service.create(this.descriptor)).thenReturn(Boolean.TRUE);
		when(this.service.setState(this.descriptor, Boolean.TRUE)).thenReturn(Boolean.TRUE);
		when(this.service.isActivated(this.descriptor)).thenReturn(Boolean.TRUE);

		this.effectiveDelegate.setAlarmtimestamp(this.descriptor);
		assertTrue(this.effectiveDelegate.setStateOfAlarmtimestamp(this.descriptor, true));
		assertTrue("Aktivierung fehlgeschlagen!", this.effectiveDelegate.isAlarmtimestampActivated(this.descriptor));

		when(this.service.setState(this.descriptor, Boolean.FALSE)).thenReturn(Boolean.TRUE);
		when(this.service.isActivated(this.descriptor)).thenReturn(Boolean.FALSE);
		assertTrue(this.effectiveDelegate.setStateOfAlarmtimestamp(this.descriptor, false));
		assertFalse("Deaktivierung fehlgeschlagen!", this.effectiveDelegate.isAlarmtimestampActivated(this.descriptor));
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.Alarm#Alarm(IAlarmTimestampService)} .
	 */
	@Test(expected = IllegalArgumentException.class)
	public final void testShouldFailBecauseServiceIsNull() {
		this.effectiveDelegate = new Alarm(null);
	}
}
