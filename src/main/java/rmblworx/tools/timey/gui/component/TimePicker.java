package rmblworx.tools.timey.gui.component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import rmblworx.tools.timey.gui.CountdownTimePartChangeListener;
import rmblworx.tools.timey.gui.CountdownTimePartConverter;

/**
 * JavaFX-Komponente zur Angabe einer Zeit bestehend aus Stunden, Minuten und Sekunden.
 * 
 * @author Christian Raue <christian.raue@gmail.com>
 * @copyright 2014 Christian Raue
 * @license http://opensource.org/licenses/mit-license.php MIT License
 */
public class TimePicker extends AnchorPane {

	/**
	 * Maximalwert für Stunden.
	 */
	private static final long MAX_HOURS = 23L;

	/**
	 * Maximalwert für Minuten.
	 */
	private static final long MAX_MINUTES = 59L;

	/**
	 * Maximalwert für Sekunden.
	 */
	private static final long MAX_SECONDS = 59L;

	/**
	 * Zeit als Property.
	 */
	private final LongProperty timeProperty = new SimpleLongProperty(0L);

	/**
	 * Formatiert Zeitstempel als Stunden-Werte.
	 */
	private SimpleDateFormat hoursFormatter;

	/**
	 * Formatiert Zeitstempel als Minuten-Werte.
	 */
	private SimpleDateFormat minutesFormatter;

	/**
	 * Formatiert Zeitstempel als Sekunden-Werte.
	 */
	private SimpleDateFormat secondsFormatter;

	@FXML
	private TextField hoursTextField;

	@FXML
	private TextField minutesTextField;

	@FXML
	private TextField secondsTextField;

	@FXML
	private Slider hoursSlider;

	@FXML
	private Slider minutesSlider;

	@FXML
	private Slider secondsSlider;

	public TimePicker() {
        init();
    }

	private void init() {
		try {
			final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TimePicker.fxml"));
			fxmlLoader.setRoot(this);
			fxmlLoader.setController(this);
			fxmlLoader.load();
			getStylesheets().add(getClass().getResource("TimePicker.css").toExternalForm());
		} catch (final IOException e) {
			e.printStackTrace();
		}

		assert hoursTextField != null : "fx:id='hoursTextField' was not injected";
		assert minutesTextField != null : "fx:id='minutesTextField' was not injected";
		assert secondsTextField != null : "fx:id='secondsTextField' was not injected";
		assert hoursSlider != null : "fx:id='hoursSlider' was not injected";
		assert minutesSlider != null : "fx:id='minutesSlider' was not injected";
		assert secondsSlider != null : "fx:id='secondsSlider' was not injected";

		if (hoursTextField != null) {
			bindTextInputListenersAndSlider(hoursTextField, hoursSlider, MAX_HOURS);
		}

		if (minutesTextField != null) {
			bindTextInputListenersAndSlider(minutesTextField, minutesSlider, MAX_MINUTES);
		}

		if (secondsTextField != null) {
			bindTextInputListenersAndSlider(secondsTextField, secondsSlider, MAX_SECONDS);
		}

		setupTimeFormatters();
	}

	/**
	 * @param time Zeit in ms
	 */
	public void setTime(final long time) {
		hoursTextField.setText(hoursFormatter.format(time));
		minutesTextField.setText(minutesFormatter.format(time));
		secondsTextField.setText(secondsFormatter.format(time));
	}

	/**
	 * @return Zeit in ms
	 */
	public long getTime() {
		final Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		cal.clear();
		cal.add(Calendar.HOUR_OF_DAY, Integer.parseInt(hoursTextField.getText()));
		cal.add(Calendar.MINUTE, Integer.parseInt(minutesTextField.getText()));
		cal.add(Calendar.SECOND, Integer.parseInt(secondsTextField.getText()));

		return cal.getTime().getTime();
	}

	/**
	 * @return Zeit als Property
	 */
	public LongProperty getTimeProperty() {
		return timeProperty;
	}

	/**
	 * Verbindet ein Textfeld bidirektional mit einem Slider und sorgt für Validierung der Eingaben.
	 * @param textField Textfeld
	 * @param slider Slider
	 * @param maxValue Maximalwert für das Textfeld
	 */
	private void bindTextInputListenersAndSlider(final TextField textField, final Slider slider, final long maxValue) {
		final StringProperty textProperty = textField.textProperty();

		// Inhalt auf gültigen Wertebereich beschränken
		textProperty.addListener(new CountdownTimePartChangeListener(textProperty, maxValue));

		// bei Verlassen des Feldes sicherstellen, dass Wert zweistellig
		textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(final ObservableValue<? extends Boolean> property, final Boolean oldValue, final Boolean newValue) {
				if (Boolean.FALSE.equals(newValue)) {
					textProperty.setValue(String.format("%02d", Long.parseLong(textProperty.get())));
				}
			}
		});

		// Änderungen an Zeit-Property durchstellen
		textProperty.addListener(new ChangeListener<String>() {
			public void changed(final ObservableValue<? extends String> property, final String oldValue, final String newValue) {
				timeProperty.set(getTime());
			}
		});

		// Textfeld mit Slider koppeln
		if (slider != null) {
			slider.setMax(maxValue);
			textProperty.bindBidirectional(slider.valueProperty(), new CountdownTimePartConverter());
		}
	}

	/**
	 * Initialisiert die Zeitformatierer.
	 */
	private void setupTimeFormatters() {
		final TimeZone timeZone = TimeZone.getTimeZone("UTC");

		if (hoursFormatter == null) {
			hoursFormatter = new SimpleDateFormat();
			hoursFormatter.setTimeZone(timeZone);
			hoursFormatter.applyPattern("HH");
		}

		if (minutesFormatter == null) {
			minutesFormatter = new SimpleDateFormat();
			minutesFormatter.setTimeZone(timeZone);
			minutesFormatter.applyPattern("mm");
		}

		if (secondsFormatter == null) {
			secondsFormatter = new SimpleDateFormat();
			secondsFormatter.setTimeZone(timeZone);
			secondsFormatter.applyPattern("ss");
		}
	}

}
