package rmblworx.tools.timey.gui;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

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

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Controller für die Countdown-GUI.
 * @author Christian Raue {@literal <christian.raue@gmail.com>}
 */
public class CountdownController extends Controller implements TimeyEventListener {

	/**
	 * Formatiert Zeitstempel als Zeit-Werte.
	 */
	private DateTimeFormatter timeFormatter;

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
		setupTimeFormatter();

		// Start-Schaltfläche nur aktivieren, wenn Zeit > 0
		countdownStartButton.setDisable(true);
		countdownTimePicker.getTimeProperty().addListener(new ChangeListener<LocalTime>() {
			public void changed(final ObservableValue<? extends LocalTime> property, final LocalTime oldValue, final LocalTime newValue) {
				countdownStartButton.setDisable(newValue.toNanoOfDay() == 0L);
			}
		});

		final TimeyEventListener eventListener = this;
		Platform.runLater(new Runnable() {
			public void run() {
				getGuiHelper().getFacade().addEventListener(eventListener);
			}
		});

		// Betätigen einer fokussierten Schaltfläche per Enter-Taste mit JavaFX 8 nicht mehr möglich
//		countdownTimePicker.setOnKeyPressed(new EventHandler<KeyEvent>() {
//			public void handle(final KeyEvent keyEvent) {
//				if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED && keyEvent.getCode() == KeyCode.ENTER) {
//					keyEvent.consume();
//					startCountdown();
//				}
//			}
//		});
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

		final long nanos = countdownTimePicker.getValue().toNanoOfDay();

		if (nanos == 0L) {
			return;
		}

		countdownRunning = true;

		final Task<Void> task = new Task<Void>() {
			private static final long SLEEP_TIME = 100L;

			public Void call() throws InterruptedException {
				final TimeDescriptor timeDescriptor = new TimeDescriptor(nanos / DateTimeUtil.MILLI_TO_NANO);

				final ITimey facade = getGuiHelper().getFacade();
				facade.setCountdownTime(timeDescriptor);
				facade.startCountdown();

				Platform.runLater(new Runnable() {
					public void run() {
						countdownStartButton.setVisible(false);
						countdownStopButton.setVisible(true);
						countdownStopButton.requestFocus();
						transferTimeFromInputToLabel();
						enableTimeInput(false);
					}
				});

				while (countdownRunning) {
					countdownValue = timeDescriptor.getMilliSeconds();
					updateMessage(timeFormatter.format(getCountdownTime()));

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

		getGuiHelper().runInThread(task, resources);
	}

	/**
	 * Stoppt den Countdown.
	 * @param countdownExpired Ob der Countdown abgelaufen ist.
	 */
	private void stopCountdown(final boolean countdownExpired) {
		if (!countdownRunning) {
			return;
		}

		countdownRunning = false;

		getGuiHelper().runInThread(new Task<Void>() {
			public Void call() throws InterruptedException {
				if (countdownExpired) {
					countdownValue = 0;
				} else {
					getGuiHelper().getFacade().stopCountdown();
				}

				Platform.runLater(new Runnable() {
					public void run() {
						countdownTimePicker.setValue(getCountdownTime());
						countdownStartButton.setVisible(true);
						countdownStartButton.requestFocus();
						countdownStopButton.setVisible(false);
						enableTimeInput(true);
					}
				});

				return null;
			}
		}, resources);
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
	private void setupTimeFormatter() {
		if (timeFormatter == null) {
			timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
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
	 * @return Countdown-Zeit (aufgerundet auf ganze Sekunden)
	 */
	private LocalTime getCountdownTime() {
		return DateTimeUtil.getTimePart(DateTimeUtil.getLocalDateTimeFromMillis(getMillisRoundedToWholeSeconds(countdownValue)));
	}

	/**
	 * Überträgt die Zeit von den Textfeldern auf das Label.
	 */
	private void transferTimeFromInputToLabel() {
		countdownTimeLabel.setText(timeFormatter.format(countdownTimePicker.getValue()));
	}

	/**
	 * {@inheritDoc}
	 */
	public final void handleEvent(final TimeyEvent event) {
		if (event instanceof CountdownExpiredEvent) {
			stopCountdown(true);
			getGuiHelper().showTrayMessageWithFallbackToDialog(resources.getString("countdown.event.expired.title"),
					resources.getString("countdown.event.expired.text"), resources);
		}
	}

}
