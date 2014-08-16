package rmblworx.tools.timey.gui;

import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.TimeZone;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import rmblworx.tools.timey.gui.config.Config;
import rmblworx.tools.timey.gui.config.ConfigManager;
import rmblworx.tools.timey.vo.TimeDescriptor;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Controller für die Stoppuhr-GUI.
 * @author Christian Raue {@literal <christian.raue@gmail.com>}
 */
public class StopwatchController extends Controller {

	/**
	 * Formatiert Zeitstempel als Zeit-Werte.
	 */
	private SimpleDateFormat timeFormatter;

	@FXML
	private ResourceBundle resources;

	@FXML
	private Button stopwatchStartButton;

	@FXML
	private Button stopwatchStopButton;

	@FXML
	private ToggleButton stopwatchSplitTimeButton;

	@FXML
	private Button stopwatchResetButton;

	@FXML
	private Label stopwatchTimeLabel;

	@FXML
	private CheckBox stopwatchShowMillisecondsCheckbox;

	/**
	 * Ob die Stoppuhr läuft.
	 */
	private boolean stopwatchRunning = false;

	/**
	 * Stoppuhr-Zeit.
	 */
	private long stopwatchValue;

	@FXML
	private void initialize() {
		assert stopwatchTimeLabel != null : "fx:id='stopwatchTimeLabel' was not injected";
		assert stopwatchStartButton != null : "fx:id='stopwatchStartButton' was not injected";
		assert stopwatchSplitTimeButton != null : "fx:id='stopwatchSplitTimeButton' was not injected";
		assert stopwatchStopButton != null : "fx:id='stopwatchStopButton' was not injected";
		assert stopwatchResetButton != null : "fx:id='stopwatchResetButton' was not injected";
		assert stopwatchShowMillisecondsCheckbox != null : "fx:id='stopwatchShowMillisecondsCheckbox' was not injected";

		if (stopwatchShowMillisecondsCheckbox != null) {
			stopwatchShowMillisecondsCheckbox.setSelected(ConfigManager.getCurrentConfig().isStopwatchShowMilliseconds());
			setupTimeFormatter();
			updateStopwatchTimeLabel();
		}
	}

	/**
	 * Aktion bei Klick auf Start-Schaltfläche.
	 */
	@FXML
	private void handleStartButtonClick() {
		startStopwatch();
	}

	/**
	 * Aktion bei Klick auf Zwischenzeit-Schaltfläche.
	 */
	@FXML
	private void handleSplitTimeButtonClick() {
		getGuiHelper().runInThread(new Task<Void>() {
			public Void call() {
				getGuiHelper().getFacade().toggleTimeModeInStopwatch();

				stopwatchStopButton.setDisable(stopwatchSplitTimeButton.isSelected());

				return null;
			}
		}, resources);
	}

	/**
	 * Aktion bei Klick auf Stop-Schaltfläche.
	 */
	@FXML
	private void handleStopButtonClick() {
		stopStopwatch();
	}

	/**
	 * Aktion bei Klick auf Zurücksetzen-Schaltfläche.
	 */
	@FXML
	private void handleResetButtonClick() {
		getGuiHelper().runInThread(new Task<Void>() {
			public Void call() {
				getGuiHelper().getFacade().resetStopwatch();
				stopwatchValue = 0L;
				updateStopwatchTimeLabel();
				stopwatchSplitTimeButton.setSelected(false);
				stopwatchStopButton.setDisable(false);
				stopwatchStartButton.requestFocus();

				return null;
			}
		}, resources);
	}

	/**
	 * Aktion bei Klick auf Millisekunden-Checkbox.
	 */
	@FXML
	private void handleShowMillisecondsCheckboxClick() {
		ConfigManager.getCurrentConfig().setStopwatchShowMilliseconds(stopwatchShowMillisecondsCheckbox.isSelected());
		setupTimeFormatter();
		updateStopwatchTimeLabel();
	}

	/**
	 * Startet die Stoppuhr.
	 */
	private void startStopwatch() {
		if (stopwatchRunning) {
			return;
		}

		stopwatchRunning = true;

		final Task<Void> task = new Task<Void>() {
			private static final long SLEEP_TIME_FINE_GRAINED = 5L;
			private static final long SLEEP_TIME_COARSE_GRAINED = 1000L;

			public Void call() throws InterruptedException {
				final Config config = ConfigManager.getCurrentConfig();
				final TimeDescriptor td = getGuiHelper().getFacade().startStopwatch();

				stopwatchStartButton.setVisible(false);
				stopwatchSplitTimeButton.setDisable(false);
				stopwatchStopButton.setVisible(true);
				stopwatchStopButton.requestFocus();

				while (stopwatchRunning) {
					stopwatchValue = td.getMilliSeconds();
					updateMessage(timeFormatter.format(stopwatchValue));
					Thread.sleep(config.isStopwatchShowMilliseconds() ? SLEEP_TIME_FINE_GRAINED : SLEEP_TIME_COARSE_GRAINED);
				}

				return null;
			}
		};

		task.messageProperty().addListener(new ChangeListener<String>() {
			public void changed(final ObservableValue<? extends String> property, final String oldValue, final String newValue) {
				stopwatchTimeLabel.setText(newValue);
			}
		});

		getGuiHelper().runInThread(task, resources);
	}

	/**
	 * Stoppt die Stoppuhr.
	 */
	private void stopStopwatch() {
		if (!stopwatchRunning) {
			return;
		}

		stopwatchRunning = false;

		getGuiHelper().runInThread(new Task<Void>() {
			public Void call() throws InterruptedException {
				getGuiHelper().getFacade().stopStopwatch();

				stopwatchStartButton.setVisible(true);
				stopwatchStartButton.requestFocus();
				stopwatchSplitTimeButton.setDisable(true);
				stopwatchSplitTimeButton.setSelected(false);
				stopwatchStopButton.setVisible(false);

				return null;
			}
		}, resources);
	}

	/**
	 * Initialisiert den Zeitformatierer bzw. aktualisiert dessen Format.
	 */
	private void setupTimeFormatter() {
		if (timeFormatter == null) {
			timeFormatter = new SimpleDateFormat();
			timeFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		}

		timeFormatter.applyPattern(ConfigManager.getCurrentConfig().isStopwatchShowMilliseconds() ? "HH:mm:ss.SSS" : "HH:mm:ss");
	}

	/**
	 * Aktualisiert die Anzeige der gemessenen Zeit, falls die Stoppuhr nicht läuft.
	 */
	private void updateStopwatchTimeLabel() {
		if (!stopwatchRunning) {
			Platform.runLater(new Runnable() {
				public void run() {
					stopwatchTimeLabel.setText(timeFormatter.format(stopwatchValue));
				}
			});
		}
	}

}
