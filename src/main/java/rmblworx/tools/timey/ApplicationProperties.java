package rmblworx.tools.timey;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

/*
 * Copyright 2014-2015 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Zugriff auf Attribute der Anwendung.
 *
 * @author Christian Raue {@literal <christian.raue@gmail.com>}
 */
class ApplicationProperties {

	/**
	 * Name des Attributs für die Versionskennung.
	 */
	static final String PROP_APP_VERSION = "application.version";

	/**
	 * Name der Datei.
	 */
	private static final String PROPERTY_FILENAME = "app.properties";

	/**
	 * Logger.
	 */
	private static Logger log = LoggerFactory.getLogger(ApplicationProperties.class);

	/**
	 * Stream zum Auslesen der Attribute.
	 */
	private InputStream inputStream;

	/**
	 * Attribute.
	 */
	private Properties properties;

	/**
	 * Standardkonstruktor.
	 */
	public ApplicationProperties() {
	}

	/**
	 * Konstruktor für Testzwecke.
	 *
	 * @param properties Attribute.
	 * @param inputStream Stream zum Auslesen der Attribute.
	 * @param logger Logger.
	 */
	ApplicationProperties(final Properties properties, final InputStream inputStream, final Logger logger) {
		this.properties = properties;
		this.inputStream = inputStream;
		log = logger;
	}

	/**
	 * @return Versionskennung oder {@code null}, wenn sie nicht ermittelt werden kann.
	 */
	public String getVersion() {
		try {
			if (inputStream == null) {
				inputStream = new ClassPathResource(PROPERTY_FILENAME).getInputStream();
			}

			if (properties == null) {
				properties = new Properties();
			}

			properties.load(inputStream);

			return properties.getProperty(PROP_APP_VERSION);
		} catch (final IOException e) {
			log.error("Error while trying to read the property file: " + e.getLocalizedMessage());
			return null;
		} finally {
			TimeyUtils.closeQuietly(inputStream);
		}
	}

}
