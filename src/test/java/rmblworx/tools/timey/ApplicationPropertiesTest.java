package rmblworx.tools.timey;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Test fürs Auslesen der Anwendungsattribute.
 * @author Christian Raue {@literal <christian.raue@gmail.com>}
 */
public class ApplicationPropertiesTest {

	/**
	 * Testet das Auslesen der Versionskennung.
	 */
	@Test
	public final void testGetVersion() {
		final String version = new ApplicationProperties().getVersion();

		assertNotNull(version);
		assertNotEquals("", version);
		assertNotEquals("${project.version}", version);
	}

}
