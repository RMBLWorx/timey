package rmblworx.tools.timey.gui.config;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.LoggerFactory;

/*
 * Copyright 2014-2015 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Speichern/Laden der GUI-Konfiguration auf Basis einer Datei.
 * @author Christian Raue {@literal <christian.raue@gmail.com>}
 */
public class FileConfigStorage extends ConfigStorage {

	/**
	 * @param config zu speichernde Konfiguration
	 * @param filename Dateiname der Konfigurationsdatei (kann auch Pfad enthalten)
	 */
	public final void saveToFile(final Config config, final String filename) {
		OutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(filename);
			saveConfig(config, outputStream);
		} catch (final IOException e) {
			LoggerFactory.getLogger(getClass()).error("Error while trying to save the config file: " + e.getLocalizedMessage());
		} finally {
			closeStream(outputStream);
		}
	}

	/**
	 * @param filename Dateiname der Konfigurationsdatei (kann auch Pfad enthalten)
	 * @return geladene Konfiguration
	 */
	public final Config loadFromFile(final String filename) {
		if (!new File(filename).isFile()) {
			return ConfigManager.getNewDefaultConfig();
		}

		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(filename);
			return loadConfig(inputStream, true);
		} catch (final IOException e) {
			LoggerFactory.getLogger(getClass()).error("Error while trying to load the config file: " + e.getLocalizedMessage());
			return ConfigManager.getNewDefaultConfig();
		} finally {
			closeStream(inputStream);
		}
	}

	/**
	 * @param stream zu schließender Stream
	 */
	protected final void closeStream(final Closeable stream) {
		if (stream != null) {
			try {
				stream.close();
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
	}

}
