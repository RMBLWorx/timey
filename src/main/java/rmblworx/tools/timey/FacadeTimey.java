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
 * 
 */
public class FacadeTimey implements ITimey, IAlarm, ICountdown {
	private final Logger log = LogManager.getLogger(FacadeTimey.class);
	private final AlarmClient alarmSteuerung = new AlarmClient(new Alarm());

	@Override
	public String getVersion() {
		this.log.entry();

		File file;
		JarFile jar;
		String versionNumber = "";

		try {
			file = TimeyUtils.getPathToJar("timey*.jar").get(0).toFile();
			jar = new java.util.jar.JarFile(file);
			Manifest manifest = jar.getManifest();
			Attributes attributes = manifest.getMainAttributes();
			if (attributes != null) {
				Iterator<Object> it = attributes.keySet().iterator();
				while (it.hasNext()) {
					Attributes.Name key = (Attributes.Name) it.next();
					String keyword = key.toString();
					if (keyword.equals("Implementation-Version")
							|| keyword.equals("Bundle-Version")) {
						versionNumber = (String) attributes.get(key);
						break;
					}
				}
			}
			jar.close();
		} catch (IOException e) {
			this.log.error("Die timey-Jar Datei konnte nicht gefunden und somit die Version nicht ermittelt werden!");
		}

		this.log.debug("Version: " + versionNumber);
		this.log.exit();

		return versionNumber;
	}

	@Override
	public TimeDescriptor setCountdownTime(TimeDescriptor td) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TimeDescriptor setAlarmTime(TimeDescriptor td) {
		return this.alarmSteuerung.initSetTimeCommand(td);
	}

	@Override
	public Boolean startCountdown() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Boolean startStopWatch() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Boolean stopCountdown() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Boolean stopStopWatch() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Boolean turnOff() {
		return this.alarmSteuerung.initTurnOffCommand();
	}

	@Override
	public Boolean turnOn() {
		return this.alarmSteuerung.initTurnOnCommand();
	}
}
