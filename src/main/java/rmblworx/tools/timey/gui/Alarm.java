package rmblworx.tools.timey.gui;

import java.util.Calendar;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Alarm-VO für die GUI.
 * 
 * @author Christian Raue <christian.raue@gmail.com>
 * @copyright 2014 Christian Raue
 * @license http://opensource.org/licenses/mit-license.php MIT License
 */
public class Alarm {

	private final BooleanProperty enabled;
	private final ObjectProperty<Calendar> dateTime;
	private final StringProperty description;

	/**
	 * Initialisiert den Alarm mit der aktuellen Systemzeit.
	 */
	public Alarm() {
		this(Calendar.getInstance(), null);
	}

	public Alarm(final Calendar dateTime, final String description) {
		this(dateTime, description, true);
	}

	public Alarm(final Calendar dateTime, final String description, final boolean enabled) {
		this.enabled = new SimpleBooleanProperty(enabled);
		this.dateTime = new SimpleObjectProperty<Calendar>(dateTime);
		this.description = new SimpleStringProperty(description);
	}

	public void setEnabled(final boolean enabled) {
		this.enabled.set(enabled);
	}

	public boolean isEnabled() {
		return enabled.get();
	}

	public void setDateTime(final Calendar dateTime) {
		this.dateTime.set(dateTime);
	}

	public Calendar getDateTime() {
		return dateTime.get();
	}

	public void setDescription(final String description) {
		this.description.set(description);
	}

	public String getDescription() {
		return description.get();
	}

}
