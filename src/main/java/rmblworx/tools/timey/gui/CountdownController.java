package rmblworx.tools.timey.gui;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.TimeZone;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import rmblworx.tools.timey.ITimey;
import rmblworx.tools.timey.gui.component.TimePicker;
import rmblworx.tools.timey.vo.TimeDescriptor;

/**
 * Controller für die Countdown-GUI.
 * 
 * @author Christian Raue <christian.raue@gmail.com>
 * @copyright 2014 Christian Raue
 * @license http://opensource.org/licenses/mit-license.php MIT License
 */
public class CountdownController extends Controller {

	/**
	 * Zeitzone.
	 */
	private static final TimeZone TIMEZONE = TimeZone.getTimeZone("UTC");

	/**
	 * Fassade zur Steuerung des Countdowns.
	 */
	private final ITimey facade = FacadeManager.getFacade();

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
	private void initialize() {
		assert countdownStartButton != null : "fx:id='countdownStartButton' was not injected";
		assert countdownStopButton != null : "fx:id='countdownStopButton' was not injected";
		assert countdownTimeLabel != null : "fx:id='countdownTimeLabel' was not injected";
		assert countdownTimePicker != null : "fx:id='countdownTimePicker' was not injected";

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
	 * Aktion bei Klick auf Start-Schaltfläche.
	 */
	@FXML
	private void handleStartButtonClick() {
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

					if (countdownValue == 0) {
						break;
					}

					Thread.sleep(SLEEP_TIME_COARSE_GRAINED);
				}

				return null;
			}
		};

		task.messageProperty().addListener(new ChangeListener<String>() {
			public void changed(final ObservableValue<? extends String> property, final String oldValue, final String newValue) {
				countdownTimeLabel.setText(newValue);

				// TODO auf Ereignis umstellen
				if ("00:00:00".equals(newValue)) {
					getGuiHelper().showTrayMessageWithFallbackToDialog("Countdown abgelaufen", "Der Countdown ist abgelaufen.", resources);
				}
			}
		});

		final Thread thread = new Thread(task);
		thread.setDaemon(true);
		thread.setPriority(Thread.MIN_PRIORITY);
		thread.start();
	}

	/**
	 * Aktion bei Klick auf Stop-Schaltfläche.
	 */
	@FXML
	private void handleStopButtonClick() {
		if (!countdownRunning) {
			return;
		}

		stopCountdown();
	}

	/**
	 * Startet den Countdown.
	 * @param timeDescriptor Zeitobjekt
	 */
	private void startCountdown(final TimeDescriptor timeDescriptor) {
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
	private void stopCountdown() {
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
	private void enableTimeInput(final boolean enabled) {
		countdownTimeLabel.setVisible(!enabled);
		countdownTimePicker.setVisible(enabled);
		countdownTimePicker.setDisable(!enabled);
	}

	/**
	 * Initialisiert den Zeitformatierer.
	 */
	private void setupTimeFormatters() {
		if (timeFormatter == null) {
			timeFormatter = new SimpleDateFormat("HH:mm:ss");
			timeFormatter.setTimeZone(TIMEZONE);
		}
	}

	/**
	 * Überträgt die Zeit von den Textfeldern auf das Label.
	 */
	private void transferTimeFromInputToLabel() {
		countdownTimeLabel.setText(timeFormatter.format(countdownTimePicker.getTime().getTime()));
	}

}
