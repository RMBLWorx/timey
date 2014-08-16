package rmblworx.tools.timey;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rmblworx.tools.timey.exception.EmptyArgumentException;
import rmblworx.tools.timey.exception.NullArgumentException;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * @author mmatthies
 */
public class JarVersionDetectorTest {

	private static final Logger LOG = LoggerFactory.getLogger(JarVersionDetectorTest.class);

	/**
	 * Ob die JAR-Datei gebaut werden konnte.
	 */
	private static boolean jarBuilt = false;

	private JarVersionDetector jarVersionDetector;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try {
			TestHelper.executeMavenPackageWithoutRunningTestsProcess();
			jarBuilt = true;
		} catch (final IOException e) {
			LOG.error(e.getLocalizedMessage());
			return;
		}
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
		if (!jarBuilt) {
			return; // Test überspringen
		}

		assertNotNull(this.jarVersionDetector.detectJarVersion("timey.jar"));
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.JarVersionDetector#detectJarVersion(java.lang.String)}.
	 */
	@Test
	public final void testShouldFailBecausePatternIsNull() {
		if (!jarBuilt) {
			return; // Test überspringen
		}

		try {
			this.jarVersionDetector.detectJarVersion(null);
			fail();
		} catch (final NullArgumentException e) {
			// Test OK
		}
	}

	/**
	 * Test method for {@link rmblworx.tools.timey.JarVersionDetector#detectJarVersion(java.lang.String)}.
	 */
	@Test
	public final void testShouldFailBecausePatternIsEmpty() {
		if (!jarBuilt) {
			return; // Test überspringen
		}

		try {
			this.jarVersionDetector.detectJarVersion("");
			fail();
		} catch (final EmptyArgumentException e) {
			// Test OK
		}
	}
}
