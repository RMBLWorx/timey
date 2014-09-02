package rmblworx.tools.timey;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Test fürs Auslesen der Anwendungsattribute.
 *
 * @author Christian Raue {@literal <christian.raue@gmail.com>}
 */
public class ApplicationPropertiesTest {
	@Mock
	private InputStream inputStream;
	@Mock
	private Properties props;

	@Before
	public void setup() throws IOException {
		MockitoAnnotations.initMocks(this);
		// this.inputStream = new ClassPathResource("app.properties").getInputStream();
		this.setupMockedInputStream();
		this.setupMockedProperties();
	}

	/**
	 * Setup-Methode für das zu mockende Properties-Objekt.
	 *
	 * @throws IOException
	 *             wenn IOException auftritt.
	 */
	private void setupMockedInputStream() throws IOException {
		// when(this.inputStream.close()).thenThrow(IOException.class);
		doThrow(new IOException()).when(this.inputStream).close();
	}

	/**
	 * Setup-Methode für das zu mockende Properties-Objekt.
	 */
	@SuppressWarnings("unchecked")
	private void setupMockedProperties() {
		when(this.props.getProperty("application.version")).thenThrow(IOException.class);
	}

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

	/**
	 * Löst gezielt eine IOException aus um das korrekte Verhalten der Methode zu testen. Erwartet wird lediglich eine
	 * Fehlermeldung im Log und die Rückgabe von <code>null</code>.
	 */
	@Test
	public final void testGetVersionShouldReturnNullIfIOExceptionOccurs() {
		assertNull("Es wurde eine Referenz geliefert!",
				new ApplicationProperties(this.props, this.inputStream).getVersion());
	}

}
