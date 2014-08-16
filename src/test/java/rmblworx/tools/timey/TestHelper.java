package rmblworx.tools.timey;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Helferklasse fuer, in so manchem Test, haeufig benoetigte Funktionalitaeten.
 * @author mmatthies
 */
final class TestHelper {
	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(TestHelper.class);

	/**
	 * Beschreibt den Maven Befehl um zu paketieren ohne die Tests auszufuehren.
	 */
	private static final String MVN_INSTALL_DSKIP_TESTS_TRUE = "mvn -q install -B -DskipTests=true";

	/**
	 * Stoesst einen Prozess auf der Kommandozeile an, welcher die timey-Jar erzeugt ohne die Tests auszufuehren.
	 * @throws IOException
	 */
	public static void executeMavenPackageWithoutRunningTestsProcess() throws IOException {
		String line;
		LOG.debug("Baue jar-Archiv. Umgebungsvariable muss dazu fuer Maven 3 gesetzt sein!!!\nAuszufuehrendes Kommando: "
				+ MVN_INSTALL_DSKIP_TESTS_TRUE);
		final Process process = Runtime.getRuntime().exec(MVN_INSTALL_DSKIP_TESTS_TRUE);
		final Reader reader = new InputStreamReader(process.getInputStream());
		final BufferedReader in = new BufferedReader(reader);
		while ((line = in.readLine()) != null) {
			System.out.println(line);
		}
		in.close();
		reader.close();
		process.destroy();
	}

	/**
	 * Privater Standardkonstruktor.
	 */
	private TestHelper() {
	}
}
