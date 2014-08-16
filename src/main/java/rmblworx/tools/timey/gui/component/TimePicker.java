package rmblworx.tools.timey.gui.component;

import java.io.IOException;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;

import org.joda.time.LocalTime;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * JavaFX-Komponente zur Angabe einer Zeit bestehend aus Stunden, Minuten und Sekunden.
 * @author Christian Raue {@literal <christian.raue@gmail.com>}
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
	private final ObjectProperty<LocalTime> timeProperty = new SimpleObjectProperty<>();

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
		// Zeit auf 0 setzen
		timeProperty.set(new LocalTime(0));

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
	private void initialize() {
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
	}

	/**
	 * @param time Zeit in ms
	 */
	public void setTime(final LocalTime time) {
		hoursTextField.setText(getTwoDigitValue(time.getHourOfDay()));
		minutesTextField.setText(getTwoDigitValue(time.getMinuteOfHour()));
		secondsTextField.setText(getTwoDigitValue(time.getSecondOfMinute()));
	}

	/**
	 * @return Zeit in ms
	 */
	public LocalTime getTime() {
		return new LocalTime(Integer.parseInt(hoursTextField.getText()), Integer.parseInt(minutesTextField.getText()),
				Integer.parseInt(secondsTextField.getText()));
	}

	/**
	 * @return Zeit als Property
	 */
	public ObjectProperty<LocalTime> getTimeProperty() {
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
					} else if (newValue.length() > 2) {
						textProperty.setValue(getTwoDigitValue(value));
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
					try {
						textProperty.setValue(getTwoDigitValue(Long.parseLong(textProperty.get())));
					} catch (final NumberFormatException e) {
						textProperty.setValue(getTwoDigitValue(MIN_VALUE));
					}
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

		// auf Scroll-Ereignis im Textfeld reagieren
		textField.setOnScroll(new EventHandler<ScrollEvent>() {
			public void handle(final ScrollEvent event) {
				// Scrollen über Feld ohne Fokus hat keine Auswirkung
				if (!textField.isFocused()) {
					return;
				}

				final double change = event.getDeltaY() > 0 ? 1 : -1;
				slider.setValue(slider.getValue() + change);

				event.consume();
			}
		});
	}

	/**
	 * @param value Wert
	 * @return zweistellig formatierter Wert
	 */
	private String getTwoDigitValue(final long value) {
		return String.format("%02d", value);
	}

}
