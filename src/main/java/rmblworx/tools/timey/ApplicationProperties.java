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
 *
 * @author Christian Raue {@literal <christian.raue@gmail.com>}
 */
class ApplicationProperties {

	/**
	 * Name des Attributs für die Versionskennung.
	 */
	private static final String PROP_APP_VERSION = "application.version";

	/**
	 * Name der Datei.
	 */
	private static final String PROPERTY_FILENAME = "app.properties";
	private InputStream inputStream;

	/**
	 * Properties.
	 */
	private Properties properties;

	/**
	 * Standardkonstruktor.
	 */
	public ApplicationProperties() {
	}

	/**
	 * Erweiterter Konstruktor. Lediglich für Testzwecke.
	 *
	 * @param properties
	 *            Referenz auf die Properties-Instanz.
	 */
	ApplicationProperties(final Properties properties, final InputStream inputStream) {
		this.properties = properties;
		this.inputStream = inputStream;
	}

	/**
	 * @return Versionskennung oder null wenn beim auslesen der Version eine Ausnahme auftrat.
	 */
	public String getVersion() {
		try {
			if (this.inputStream == null) {
				this.inputStream = new ClassPathResource(PROPERTY_FILENAME).getInputStream();
			}

			if (this.properties == null) {
				this.properties = new Properties();
			}
			this.properties.load(this.inputStream);
			return this.properties.getProperty(PROP_APP_VERSION);
		} catch (final IOException e) {
			LoggerFactory.getLogger(this.getClass()).error(
					"Error while trying to read the property file: " + e.getLocalizedMessage());
			return null;
		} finally {
			if (this.inputStream != null) {
				try {
					this.inputStream.close();
				} catch (final IOException e) {
					// ignorieren
				}
			}
		}
	}
}
