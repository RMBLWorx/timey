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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;

/**
 * JavaFX-Komponente zur Angabe einer Zeit bestehend aus Stunden, Minuten und Sekunden.
 * 
 * @author Christian Raue <christian.raue@gmail.com>
 * @copyright 2014 Christian Raue
 * @license http://opensource.org/licenses/mit-license.php MIT License
 */
public class TimePicker extends AnchorPane {

	/**
	 * Minimalwert für Stunden, Minuten und Sekunden.
	 */
	private static final long MIN_VALUE = 0L;

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
        createGui();
    }

	private void createGui() {
		try {
			final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TimePicker.fxml"));
			fxmlLoader.setRoot(this);
			fxmlLoader.setController(this);
			fxmlLoader.load();
			getStylesheets().add(getClass().getResource("TimePicker.css").toExternalForm());
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	final void initialize() {
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

		// Eingaben auf Zahlen beschränken
		textField.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
			public void handle(final KeyEvent keyEvent) {
				if (!"0123456789".contains(keyEvent.getCharacter())) {
					keyEvent.consume();
				}
			}
		});

		textProperty.addListener(new ChangeListener<String>() {
			public void changed(final ObservableValue<? extends String> property, final String oldValue, final String newValue) {
				// Wert auf gültigen Bereich beschränken
				try {
					final long value = Long.parseLong(newValue);
					if (value < MIN_VALUE) {
						textProperty.setValue(getTwoDigitValue(MIN_VALUE));
					} else if (value > maxValue) {
						textProperty.setValue(getTwoDigitValue(maxValue));
					} else {
						textProperty.setValue(newValue);
					}

					// Änderung an Zeit-Property durchstellen
					timeProperty.set(getTime());
				} catch (final NumberFormatException e) {
					// alten Wert wieder setzen (sinnvoll bei Änderung per Copy & Paste)
					textProperty.setValue(oldValue);
				}
			}
		});

		// bei Verlassen des Feldes sicherstellen, dass Wert zweistellig
		textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(final ObservableValue<? extends Boolean> property, final Boolean oldValue, final Boolean newValue) {
				if (Boolean.FALSE.equals(newValue)) {
					textProperty.setValue(getTwoDigitValue(Long.parseLong(textProperty.get())));
				}
			}
		});

		// Textfeld mit Slider koppeln
		if (slider != null) {
			slider.setMax(maxValue);
			textProperty.bindBidirectional(slider.valueProperty(), new StringConverter<Number>() {
				public String toString(final Number number) {
					return getTwoDigitValue(number.longValue());
				}
				public Number fromString(final String string) {
					return Long.parseLong(string);
				}
			});
		}
	}

	/**
	 * @param value Wert
	 * @return zweistellig formatierter Wert
	 */
	private String getTwoDigitValue(final long value) {
		return String.format("%02d", value);
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
