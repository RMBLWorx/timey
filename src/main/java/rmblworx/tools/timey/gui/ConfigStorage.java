package rmblworx.tools.timey.gui;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Locale;
import java.util.Properties;

public class ConfigStorage {

	public void saveConfig(final String filename) {
		final Config config = Config.getInstance();

		final Properties props = new Properties();
		props.setProperty("locale", config.getLocale().toString());
		props.setProperty("minimizeToTray", Boolean.toString(config.isMinimizeToTray()));
		props.setProperty("stopwatchShowMilliseconds", Boolean.toString(config.isStopwatchShowMilliseconds()));

		OutputStream os = null;
		try {
			os = new FileOutputStream(filename);
			props.storeToXML(os, null);
		} catch (final IOException e) {
			System.err.println(e.getLocalizedMessage());
		} finally {
			closeStream(os);
		}
	}

	public void loadConfig(final String filename) {
		if (!new File(filename).isFile()) {
			return;
		}

		final Properties props = new Properties();

		InputStream in = null;
		try {
			in = new FileInputStream(filename);
			props.loadFromXML(in);
		} catch (final IOException e) {
			System.err.println(e.getLocalizedMessage());
			return;
		} finally {
			closeStream(in);
		}

		final Config config = Config.getInstance();

		final String propLocale = props.getProperty("locale");
		if (propLocale != null) {
			final Locale locale = new Locale(propLocale);
			if (Arrays.asList(Main.AVAILABLE_LOCALES).contains(locale)) {
				config.setLocale(locale);
			}
		}

		final String propMinimizeToTray = props.getProperty("minimizeToTray");
		if (propMinimizeToTray != null) {
			config.setMinimizeToTray(Boolean.parseBoolean(propMinimizeToTray));
		}

		final String propStopwatchShowMilliseconds = props.getProperty("stopwatchShowMilliseconds");
		if (propStopwatchShowMilliseconds != null) {
			config.setStopwatchShowMilliseconds(Boolean.parseBoolean(propStopwatchShowMilliseconds));
		}
	}

	private void closeStream(final Closeable stream) {
		if (stream != null) {
			try {
				stream.close();
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
