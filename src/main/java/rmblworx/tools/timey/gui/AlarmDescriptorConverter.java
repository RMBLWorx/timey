package rmblworx.tools.timey.gui;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;

import rmblworx.tools.timey.vo.AlarmDescriptor;
import rmblworx.tools.timey.vo.TimeDescriptor;

/**
 * Konvertiert zwischen {@link Alarm} und {@link AlarmDescriptor}.
 * 
 * @author Christian Raue <christian.raue@gmail.com>
 * @copyright 2014 Christian Raue
 * @license http://opensource.org/licenses/mit-license.php MIT License
 */
public final class AlarmDescriptorConverter {

	public static Alarm getAsAlarm(final AlarmDescriptor ad) {
		final LocalDateTime dateTime = new LocalDateTime(ad.getAlarmtime().getMilliSeconds(), DateTimeZone.UTC);
		final String sound = ad.getSound();

		return new Alarm(dateTime, ad.getDescription(), sound, ad.getIsActive());
	}

	public static AlarmDescriptor getAsAlarmDescriptor(final Alarm alarm) {
		final TimeDescriptor time = new TimeDescriptor(alarm.getDateTimeInMillis());
		final String sound = alarm.getSound();

		return new AlarmDescriptor(time, alarm.isEnabled(), alarm.getDescription(), sound, null);
	}

	public static List<Alarm> getAsAlarms(final List<AlarmDescriptor> in) {
		final List<Alarm> out = new ArrayList<>(in.size());

		for (final AlarmDescriptor ad : in) {
			out.add(AlarmDescriptorConverter.getAsAlarm(ad));
		}

		return out;
	}

	// wird derzeit nicht benötigt
	public static List<AlarmDescriptor> getAsAlarmDescriptors(final List<Alarm> in) {
		final List<AlarmDescriptor> out = new ArrayList<>(in.size());

		for (final Alarm alarm: in) {
			out.add(AlarmDescriptorConverter.getAsAlarmDescriptor(alarm));
		}

		return out;
	}

	/**
	 * Instanziierung verhindern.
	 */
	private AlarmDescriptorConverter() {
	}

}
