/**
 * 
 */
package rmblworx.tools.timey;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import rmblworx.tools.timey.vo.TimeDescriptor;

/**
 * Fassade fuer das System timey.
 * 
 * @author mmatthies
 */
public class TimeyFacade implements ITimey, IAlarm, ICountdown, IStopwatch {
	private final AlarmClient alarmClient = new AlarmClient(new Alarm());
	private final Logger log = LogManager.getLogger(TimeyFacade.class);
	private final StopwatchClient stopwatchClient = new StopwatchClient(new Stopwatch());

	@Override
	public final String getVersion() {
		this.log.entry();

		File file;
		JarFile jar;
		String versionNumber = "";

		try {
			file = TimeyUtils.getPathToJar("timey*.jar").get(0).toFile();
			jar = new java.util.jar.JarFile(file);
			final Manifest manifest = jar.getManifest();
			final Attributes attributes = manifest.getMainAttributes();
			if (attributes != null) {
				final Iterator<Object> it = attributes.keySet().iterator();
				while (it.hasNext()) {
					final Attributes.Name key = (Attributes.Name) it.next();
					final String keyword = key.toString();
					if (keyword.equals("Implementation-Version") || keyword.equals("Bundle-Version")) {
						versionNumber = (String) attributes.get(key);
						break;
					}
				}
			}
			jar.close();
		} catch (final IOException e) {
			this.log.error("Die timey-Jar Datei konnte nicht gefunden und somit die Version nicht ermittelt werden!");
		}

		this.log.debug("Version: " + versionNumber);
		this.log.exit();

		return versionNumber;
	}

	@Override
	public final Boolean resetStopwatch() {
		return this.stopwatchClient.initStopwatchResetCommand();
	}

	@Override
	public final TimeDescriptor setAlarmTime(final TimeDescriptor td) {
		return this.alarmClient.initSetTimeCommand(td);
	}

	@Override
	public final TimeDescriptor setCountdownTime(final TimeDescriptor td) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public final Boolean startCountdown() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public final TimeDescriptor startStopwatch() {
		return this.stopwatchClient.initStopwatchStartCommand();
	}

	@Override
	public final Boolean stopCountdown() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public final Boolean stopStopwatch() {
		return this.stopwatchClient.initStopwatchStopCommand();
	}

	@Override
	public final Boolean turnOff() {
		return this.alarmClient.initTurnOffCommand();
	}

	@Override
	public final Boolean turnOn() {
		return this.alarmClient.initTurnOnCommand();
	}

}
