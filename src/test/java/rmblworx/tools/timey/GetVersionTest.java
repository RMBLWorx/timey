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

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * @author mmatthies
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-timey-context.xml" })
public class GetVersionTest {

	private static final Logger LOG = LoggerFactory.getLogger(GetVersionTest.class);

	private TimeyFacade facade;

	@Before
	public final void setUp() {
		this.facade = new TimeyFacade();
	}

	@After
	public final void tearDown() {
		this.facade = null;
	}

	/**
	 * Test method for {@link TimeyFacade#getVersion()}.
	 */
	@Test
	public final void testGetVersion() {
		if (TimeyUtils.isWindowsSystem()) {
			LOG.debug("Windows erkannt...");
		} else if (TimeyUtils.isLinuxSystem()) {
			LOG.debug("Linux erkannt...");
		} else if (TimeyUtils.isOSXSystem()) {
			LOG.debug("OS X erkannt...");
		}

		assertNotNull("Test fehlgeschlagen da keine Version zurückgeliefert wurde.", this.facade.getVersion());
	}

}
