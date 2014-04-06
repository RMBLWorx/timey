package rmblworx.tools.timey.gui;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import org.joda.time.LocalDateTime;

/**
 * Alarm-VO für die GUI.
 * 
 * @author Christian Raue <christian.raue@gmail.com>
 * @copyright 2014 Christian Raue
 * @license http://opensource.org/licenses/mit-license.php MIT License
 */
public class Alarm {

	private final BooleanProperty enabled;
	private final ObjectProperty<LocalDateTime> dateTime;
	private final StringProperty description;
	private final StringProperty sound;

	/**
	 * Initialisiert den Alarm mit der aktuellen Systemzeit.
	 */
	public Alarm() {
		this(LocalDateTime.now(), null);
	}

	public Alarm(final LocalDateTime dateTime, final String description) {
		this(dateTime, description, null, true);
	}

	public Alarm(final LocalDateTime dateTime, final String description, final boolean enabled) {
		this(dateTime, description, null, enabled);
	}

	public Alarm(final LocalDateTime dateTime, final String description, final String sound, final boolean enabled) {
		this.enabled = new SimpleBooleanProperty(enabled);
		this.dateTime = new SimpleObjectProperty<LocalDateTime>(dateTime);
		this.description = new SimpleStringProperty(description);
		this.sound = new SimpleStringProperty(sound);
	}

	public void setEnabled(final boolean enabled) {
		this.enabled.set(enabled);
	}

	public boolean isEnabled() {
		return enabled.get();
	}

	public void setDateTime(final LocalDateTime dateTime) {
		this.dateTime.set(dateTime);
	}

	public LocalDateTime getDateTime() {
		return dateTime.get();
	}

	public void setDescription(final String description) {
		this.description.set(description);
	}

	public String getDescription() {
		return description.get();
	}

	public void setSound(final String sound) {
		this.sound.set(sound);
	}

	public String getSound() {
		return sound.get();
	}

}
