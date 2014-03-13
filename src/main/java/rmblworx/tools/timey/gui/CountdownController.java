package rmblworx.tools.timey.gui;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.TimeZone;

import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import rmblworx.tools.timey.SimpleCountdown;
import rmblworx.tools.timey.vo.TimeDescriptor;

/**
 * Controller für die Countdown-GUI.
 * 
 * @author Christian Raue <christian.raue@gmail.com>
 * @copyright 2014 Christian Raue
 * @license http://opensource.org/licenses/mit-license.php MIT License
 */
public class CountdownController {

	/**
	 * Fassade zur Steuerung des Countdowns.
	 */
	// private TimeyFacade facade = new TimeyFacade();
	private SimpleCountdown countdown = new SimpleCountdown(); // TODO durch Fassade ersetzen

	/**
	 * Formatiert Zeitstempel als Zeit-Werte.
	 */
	private SimpleDateFormat timeFormatter;

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

	/**
	 * Maximalwert für das Stunden-Textfeld.
	 */
	private static final long MAX_HOURS = 23L;

	/**
	 * Maximalwert für das Minuten-Textfeld.
	 */
	private static final long MAX_MINUTES = 59L;

	/**
	 * Maximalwert für das Sekunden-Textfeld.
	 */
	private static final long MAX_SECONDS = 59L;

	@FXML
	private ResourceBundle resources;

	@FXML
	private Button countdownStartButton;

	@FXML
	private Button countdownStopButton;

	@FXML
	private TextField countdownHoursField;

	@FXML
	private TextField countdownMinutesField;

	@FXML
	private TextField countdownSecondsField;

	@FXML
	private Slider countdownHoursSlider;

	@FXML
	private Slider countdownMinutesSlider;

	@FXML
	private Slider countdownSecondsSlider;

	@FXML
	private Label countdownTimeLabel;

	@FXML
	private AnchorPane countdownTimeInputPane;

	/**
	 * Ob der Countdown läuft.
	 */
	private boolean countdownRunning = false;

	/**
	 * Countdown-Zeit.
	 */
	private long countdownValue;

	@FXML
	void initialize() {
		assert countdownHoursField != null : "fx:id='countdownHoursField' was not injected";
		assert countdownMinutesField != null : "fx:id='countdownMinuteField' was not injected";
		assert countdownSecondsField != null : "fx:id='countdownSecondsField' was not injected";
		assert countdownHoursSlider != null : "fx:id='countdownHoursSlider' was not injected";
		assert countdownMinutesSlider != null : "fx:id='countdownMinutesSlider' was not injected";
		assert countdownSecondsSlider != null : "fx:id='countdownSecondsSlider' was not injected";
		assert countdownStartButton != null : "fx:id='countdownStartButton' was not injected";
		assert countdownStopButton != null : "fx:id='countdownStopButton' was not injected";
		assert countdownTimeLabel != null : "fx:id='countdownTimeLabel' was not injected";
		assert countdownTimeInputPane != null : "fx:id='countdownTimeInputPane' was not injected";

		if (countdownStartButton != null) {
			countdownStartButton.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(final ActionEvent event) {
					if (countdownRunning) {
						return;
					}

					final TimeDescriptor td = new TimeDescriptor(getTimeFromInputFields());
					startCountdown(td);

					final Task<Void> task = new Task<Void>() {
						private static final long SLEEP_TIME_COARSE_GRAINED = 1000L;

						public Void call() throws InterruptedException {
							while (countdownRunning) {
								countdownValue = td.getMilliSeconds();
								updateMessage(timeFormatter.format(countdownValue));
								Thread.sleep(SLEEP_TIME_COARSE_GRAINED);
							}

							return null;
						}
					};

					task.messageProperty().addListener(new ChangeListener<String>() {
						public void changed(final ObservableValue<? extends String> property, final String oldValue,
								final String newValue) {
							countdownTimeLabel.setText(newValue);
						}
					});

					final Thread thread = new Thread(task);
					thread.setDaemon(true);
					thread.setPriority(Thread.MIN_PRIORITY);
					thread.start();
				}
			});
		}

		if (countdownStopButton != null) {
			countdownStopButton.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(final ActionEvent event) {
					if (!countdownRunning) {
						return;
					}

					stopCountdown();
				}
			});
		}

		if (countdownHoursField != null) {
			bindTextInputListenersAndSlider(countdownHoursField, countdownHoursSlider, MAX_HOURS);
		}

		if (countdownMinutesField != null) {
			bindTextInputListenersAndSlider(countdownMinutesField, countdownMinutesSlider, MAX_MINUTES);
		}

		if (countdownSecondsField != null) {
			bindTextInputListenersAndSlider(countdownSecondsField, countdownSecondsSlider, MAX_SECONDS);
		}

		setupTimeFormatters();
	}

	/**
	 * Verbindet ein Textfeld bidirektional mit einem Slider und sorgt für Validierung der Eingaben.
	 * @param textField Textfeld
	 * @param slider Slider
	 * @param maxValue Maximalwert für das Textfeld
	 */
	protected void bindTextInputListenersAndSlider(final TextField textField, final Slider slider, final long maxValue) {
		final StringProperty textProperty = textField.textProperty();

		// Inhalt auf gültigen Wertebereich beschränken
		textProperty.addListener(new CountdownTimePartChangeListener(textProperty, maxValue));

		// Start-Schaltfläche nur aktivieren, wenn Zeit > 0
		countdownStartButton.setDisable(true);
		textProperty.addListener(new ChangeListener<String>() {
			public void changed(final ObservableValue<? extends String> property, final String oldValue, final String newValue) {
				countdownStartButton.setDisable(getTimeFromInputFields() == 0);
			}
		});

		// bei Verlassen des Feldes sicherstellen, dass Wert zweistellig
		textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(final ObservableValue<? extends Boolean> property, final Boolean oldValue, final Boolean newValue) {
				if (Boolean.FALSE.equals(newValue)) {
					textProperty.setValue(String.format("%02d", Long.parseLong(textProperty.get())));
				}
			}
		});

		// Textfeld mit Slider koppeln
		if (slider != null) {
			slider.setMax(maxValue);
			textProperty.bindBidirectional(slider.valueProperty(), new CountdownTimePartConverter());
		}
	}

	/**
	 * Startet den Countdown.
	 * @param timeDescriptor Zeitobjekt
	 */
	protected void startCountdown(final TimeDescriptor timeDescriptor) {
		countdownRunning = true;
		countdownStartButton.setVisible(false);
		countdownStopButton.setVisible(true);
		countdownStopButton.requestFocus();
		enableTimeInput(false);

		transferTimeFromInputToLabel();

		countdown.setCountdownTime(timeDescriptor);
		countdown.startCountdown();
	}

	/**
	 * Stoppt den Countdown.
	 */
	protected void stopCountdown() {
		countdown.stopCountdown();
		countdownRunning = false;

		transferTimeToInput();

		countdownStartButton.setVisible(true);
		countdownStartButton.requestFocus();
		countdownStopButton.setVisible(false);
		enableTimeInput(true);
	}

	/**
	 * Aktiviert bzw. deaktiviert alle nötigen Bedienelemente zur Eingabe einer Zeit.
	 * @param enabled ob Felder aktiv sein sollen
	 */
	protected void enableTimeInput(final boolean enabled) {
		countdownTimeLabel.setVisible(!enabled);
		countdownTimeInputPane.setVisible(enabled);
		countdownTimeInputPane.setDisable(!enabled);
	}

	/**
	 * Initialisiert die Zeitformatierer.
	 */
	private void setupTimeFormatters() {
		final TimeZone timeZone = TimeZone.getTimeZone("UTC");

		if (timeFormatter == null) {
			timeFormatter = new SimpleDateFormat();
			timeFormatter.setTimeZone(timeZone);
			timeFormatter.applyPattern("HH:mm:ss");
		}

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

	/**
	 * Liefert die durch die Textfelder beschriebene Zeit.
	 * @return Zeit in ms
	 */
	protected long getTimeFromInputFields() {
		final Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		cal.clear();
		cal.add(Calendar.HOUR_OF_DAY, Integer.parseInt(countdownHoursField.getText()));
		cal.add(Calendar.MINUTE, Integer.parseInt(countdownMinutesField.getText()));
		cal.add(Calendar.SECOND, Integer.parseInt(countdownSecondsField.getText()));

		return cal.getTime().getTime();
	}

	/**
	 * Überträgt die Zeit von den Textfeldern auf das Label.
	 */
	protected void transferTimeFromInputToLabel() {
		countdownTimeLabel.setText(timeFormatter.format(getTimeFromInputFields()));
	}

	/**
	 * Überträgt die Countdown-Zeit auf die Textfelder.
	 */
	protected void transferTimeToInput() {
		countdownHoursField.setText(hoursFormatter.format(countdownValue));
		countdownMinutesField.setText(minutesFormatter.format(countdownValue));
		countdownSecondsField.setText(secondsFormatter.format(countdownValue));
	}

}
