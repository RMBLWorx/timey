package rmblworx.tools.timey;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Zugriff auf Attribute der Anwendung.
 * @author Christian Raue {@literal <christian.raue@gmail.com>}
 */
class ApplicationProperties {

	/**
	 * Name der Datei.
	 */
	private static final String PROPERTY_FILENAME = "app.properties";

	/**
	 * Name des Attributs für die Versionskennung.
	 */
	private static final String PROP_APP_VERSION = "application.version";

	/**
	 * @return Versionskennung
	 */
	public final String getVersion() {
		InputStream inputStream = null;
		try {
			inputStream = new ClassPathResource(PROPERTY_FILENAME).getInputStream();

			final Properties props = new Properties();
			props.load(inputStream);
			return props.getProperty(PROP_APP_VERSION);
		} catch (final IOException e) {
			LoggerFactory.getLogger(getClass()).error("Error while trying to read the property file: " + e.getLocalizedMessage());
			return null;
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (final IOException e) {
					// ignorieren
				}
			}
		}
	}

}
