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
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import rmblworx.tools.timey.vo.TimeDescriptor;

/**
 * Fassade fuer das System timey.
 * 
 * @author mmatthies
 */
public class TimeyFacade implements ITimey, IAlarm, ICountdown, IStopwatch {
	//	private final AlarmClient alarmClient;
	private static final Logger LOG = LogManager.getLogger(TimeyFacade.class);
	private final ApplicationContext springContext;
	private final StopwatchClient stopwatchClient;

	public TimeyFacade(){
		this.springContext = new ClassPathXmlApplicationContext("spring-timey-context.xml");

		//		this.alarmClient = (AlarmClient) this.springContext.getBean("alarmClient");
		this.stopwatchClient = (StopwatchClient) this.springContext.getBean("stopwatchClient");
	}

	@Override
	public  String getVersion() {


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
			LOG.error("Die timey-Jar Datei konnte nicht gefunden und somit die Version nicht ermittelt werden!");
		}

		LOG.debug("Version: " + versionNumber);


		return versionNumber;
	}

	@Override
	public  Boolean resetStopwatch() {
		return this.stopwatchClient.initStopwatchResetCommand();
	}

	@Override
	public  TimeDescriptor setAlarmTime(final TimeDescriptor descriptor) {
		//		return this.alarmClient.initSetTimeCommand(descriptor);
		return null;
	}

	@Override
	public  TimeDescriptor setCountdownTime(final TimeDescriptor descriptor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public  Boolean startCountdown() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public  TimeDescriptor startStopwatch() {
		return this.stopwatchClient.initStopwatchStartCommand();
	}

	@Override
	public  Boolean stopCountdown() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public  Boolean stopStopwatch() {
		return this.stopwatchClient.initStopwatchStopCommand();
	}

	@Override
	public  Boolean turnOff() {
		//		return this.alarmClient.initTurnOffCommand();
		return null;
	}

	@Override
	public  Boolean turnOn() {
		//		return this.alarmClient.initTurnOnCommand();
		return null;
	}

}
