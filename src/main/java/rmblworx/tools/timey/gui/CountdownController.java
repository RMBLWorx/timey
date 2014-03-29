package rmblworx.tools.timey.gui;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.TimeZone;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import rmblworx.tools.timey.ITimey;
import rmblworx.tools.timey.event.CountdownExpiredEvent;
import rmblworx.tools.timey.event.TimeyEvent;
import rmblworx.tools.timey.event.TimeyEventListener;
import rmblworx.tools.timey.gui.component.TimePicker;
import rmblworx.tools.timey.vo.TimeDescriptor;

/**
 * Controller für die Countdown-GUI.
 * 
 * @author Christian Raue <christian.raue@gmail.com>
 * @copyright 2014 Christian Raue
 * @license http://opensource.org/licenses/mit-license.php MIT License
 */
public class CountdownController extends Controller implements TimeyEventListener {

	/**
	 * Zeitzone.
	 */
	private static final TimeZone TIMEZONE = TimeZone.getTimeZone("UTC");

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
	 * Countdown-Zeit in ms.
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

		final TimeyEventListener eventListener = this;
		Platform.runLater(new Runnable() {
			public void run() {
				getGuiHelper().getFacade().addEventListener(eventListener);
			}
		});
	}

	/**
	 * Aktion bei Klick auf Start-Schaltfläche.
	 */
	@FXML
	private void handleStartButtonClick() {
		startCountdown();
	}

	/**
	 * Aktion bei Klick auf Stop-Schaltfläche.
	 */
	@FXML
	private void handleStopButtonClick() {
		stopCountdown(false);
	}

	/**
	 * Startet den Countdown.
	 */
	private void startCountdown() {
		if (countdownRunning) {
			return;
		}

		final TimeDescriptor timeDescriptor = new TimeDescriptor(countdownTimePicker.getTime().getTimeInMillis());

		countdownRunning = true;
		countdownStartButton.setVisible(false);
		countdownStopButton.setVisible(true);
		countdownStopButton.requestFocus();

		transferTimeFromInputToLabel();
		enableTimeInput(false);

		final ITimey facade = getGuiHelper().getFacade();
		facade.setCountdownTime(timeDescriptor);
		facade.startCountdown();

		final Task<Void> task = new Task<Void>() {
			private static final long SLEEP_TIME = 100L;

			public Void call() throws InterruptedException {
				while (countdownRunning) {
					countdownValue = timeDescriptor.getMilliSeconds();
					updateMessage(timeFormatter.format(getMillisRoundedToWholeSeconds(countdownValue)));

					if (countdownValue == 0) {
						break;
					}

					Thread.sleep(SLEEP_TIME);
				}

				return null;
			}
		};

		task.messageProperty().addListener(new ChangeListener<String>() {
			public void changed(final ObservableValue<? extends String> property, final String oldValue, final String newValue) {
				countdownTimeLabel.setText(newValue);
			}
		});

		final Thread thread = new Thread(task);
		thread.setDaemon(true);
		thread.setPriority(Thread.MIN_PRIORITY);
		thread.start();
	}

	/**
	 * Stoppt den Countdown.
	 * @param countdownExpired Ob der Countdown abgelaufen ist.
	 */
	private void stopCountdown(final boolean countdownExpired) {
		if (!countdownRunning) {
			return;
		}

		if (countdownExpired) {
			countdownValue = 0;
		} else {
			getGuiHelper().getFacade().stopCountdown();
		}

		countdownRunning = false;

		final Calendar cal = Calendar.getInstance(TIMEZONE);
		cal.setTimeInMillis(getMillisRoundedToWholeSeconds(countdownValue - TimeZone.getDefault().getDSTSavings())); // TODO WTF?
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
	 * @param time Zeit in ms
	 * @return Zeit in ms (aufgerundet auf ganze Sekunden)
	 */
	private long getMillisRoundedToWholeSeconds(final long time) {
		final double milli = 1000D;
		return new Double(Math.ceil(time / milli) * milli).longValue();
	}

	/**
	 * Überträgt die Zeit von den Textfeldern auf das Label.
	 */
	private void transferTimeFromInputToLabel() {
		Platform.runLater(new Runnable() {
			public void run() {
				countdownTimeLabel.setText(timeFormatter.format(countdownTimePicker.getTime().getTime()));
			}
		});
	}

	/**
	 * {@inheritDoc}
	 */
	public final void handleEvent(final TimeyEvent event) {
		if (event instanceof CountdownExpiredEvent) {
			stopCountdown(true);
			getGuiHelper().showTrayMessageWithFallbackToDialog("Countdown abgelaufen", "Der Countdown ist abgelaufen.", resources);
		}
	}

}
