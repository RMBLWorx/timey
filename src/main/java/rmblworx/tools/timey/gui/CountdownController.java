package rmblworx.tools.timey.gui;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.TimeZone;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import rmblworx.tools.timey.TimeyFacade;
import rmblworx.tools.timey.gui.component.TimePicker;
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
	 * Zeitzone.
	 */
	private static final TimeZone TIMEZONE = TimeZone.getTimeZone("UTC");

	/**
	 * Fassade zur Steuerung des Countdowns.
	 */
	private TimeyFacade facade = new TimeyFacade();

	/**
	 * Formatiert Zeitstempel als Zeit-Werte.
	 */
	private SimpleDateFormat timeFormatter;

	@FXML
	private ResourceBundle resources;

	@FXML
	private Button countdownStartButton;

	@FXML
	private Button countdownStopButton;

	@FXML
	private Label countdownTimeLabel;

	@FXML
	private TimePicker countdownTimePicker;

	/**
	 * Ob der Countdown läuft.
	 */
	private boolean countdownRunning = false;

	/**
	 * Countdown-Zeit.
	 */
	private long countdownValue;

	@FXML
	final void initialize() {
		assert countdownStartButton != null : "fx:id='countdownStartButton' was not injected";
		assert countdownStopButton != null : "fx:id='countdownStopButton' was not injected";
		assert countdownTimeLabel != null : "fx:id='countdownTimeLabel' was not injected";
		assert countdownTimePicker != null : "fx:id='countdownTimePicker' was not injected";

		if (countdownStartButton != null) {
			countdownStartButton.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(final ActionEvent event) {
					if (countdownRunning) {
						return;
					}

					final TimeDescriptor td = new TimeDescriptor(countdownTimePicker.getTime().getTimeInMillis());
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

		countdownTimePicker.setTimeZone(TIMEZONE);
		setupTimeFormatters();

		// Start-Schaltfläche nur aktivieren, wenn Zeit > 0
		countdownStartButton.setDisable(true);
		countdownTimePicker.getTimeProperty().addListener(new ChangeListener<Calendar>() {
			public void changed(final ObservableValue<? extends Calendar> property, final Calendar oldValue, final Calendar newValue) {
				countdownStartButton.setDisable(newValue.getTimeInMillis() == 0L);
			}
		});
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

		facade.setCountdownTime(timeDescriptor);
		facade.startCountdown();
	}

	/**
	 * Stoppt den Countdown.
	 */
	protected void stopCountdown() {
		facade.stopCountdown();
		countdownRunning = false;

		final Calendar cal = Calendar.getInstance(TIMEZONE);
		cal.setTimeInMillis(countdownValue);
		countdownTimePicker.setTime(cal);

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
		countdownTimePicker.setVisible(enabled);
		countdownTimePicker.setDisable(!enabled);
	}

	/**
	 * Initialisiert den Zeitformatierer.
	 */
	private void setupTimeFormatters() {
		if (timeFormatter == null) {
			timeFormatter = new SimpleDateFormat();
			timeFormatter.setTimeZone(TIMEZONE);
			timeFormatter.applyPattern("HH:mm:ss");
		}
	}

	/**
	 * Überträgt die Zeit von den Textfeldern auf das Label.
	 */
	protected void transferTimeFromInputToLabel() {
		countdownTimeLabel.setText(timeFormatter.format(countdownTimePicker.getTime().getTime()));
	}

}
