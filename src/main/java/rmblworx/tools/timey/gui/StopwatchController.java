package rmblworx.tools.timey.gui;

import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.TimeZone;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import rmblworx.tools.timey.ITimey;
import rmblworx.tools.timey.gui.config.Config;
import rmblworx.tools.timey.gui.config.ConfigManager;
import rmblworx.tools.timey.vo.TimeDescriptor;

/**
 * Controller für die Stoppuhr-GUI.
 * 
 * @author Christian Raue <christian.raue@gmail.com>
 * @copyright 2014 Christian Raue
 * @license http://opensource.org/licenses/mit-license.php MIT License
 */
public class StopwatchController {

	/**
	 * Fassade zur Steuerung der Stoppuhr.
	 */
	private final ITimey facade = FacadeManager.getFacade();

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
	final void initialize() {
		assert stopwatchTimeLabel != null : "fx:id='stopwatchTimeLabel' was not injected";
		assert stopwatchStartButton != null : "fx:id='stopwatchStartButton' was not injected";
		assert stopwatchStopButton != null : "fx:id='stopwatchStopButton' was not injected";
		assert stopwatchResetButton != null : "fx:id='stopwatchResetButton' was not injected";
		assert stopwatchShowMillisecondsCheckbox != null : "fx:id='stopwatchShowMillisecondsCheckbox' was not injected";

		if (stopwatchStartButton != null) {
			stopwatchStartButton.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(final ActionEvent event) {
					if (stopwatchRunning) {
						return;
					}

					final Config config = ConfigManager.getCurrentConfig();
					final TimeDescriptor td = startStopwatch();

					final Task<Void> task = new Task<Void>() {
						private static final long SLEEP_TIME_FINE_GRAINED = 5L;
						private static final long SLEEP_TIME_COARSE_GRAINED = 1000L;

						public Void call() throws InterruptedException {
							while (stopwatchRunning) {
								stopwatchValue = td.getMilliSeconds();
								updateMessage(timeFormatter.format(stopwatchValue));
								Thread.sleep(config.isStopwatchShowMilliseconds() ? SLEEP_TIME_FINE_GRAINED : SLEEP_TIME_COARSE_GRAINED);
							}

							return null;
						}
					};

					task.messageProperty().addListener(new ChangeListener<String>() {
						public void changed(final ObservableValue<? extends String> property, final String oldValue,
								final String newValue) {
							stopwatchTimeLabel.setText(newValue);
						}
					});

					final Thread thread = new Thread(task);
					thread.setDaemon(true);
					thread.setPriority(Thread.MIN_PRIORITY);
					thread.start();
				}
			});
		}

		if (stopwatchStopButton != null) {
			stopwatchStopButton.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(final ActionEvent event) {
					if (!stopwatchRunning) {
						return;
					}

					stopStopwatch();
				}
			});
		}

		if (stopwatchResetButton != null) {
			stopwatchResetButton.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(final ActionEvent event) {
					facade.resetStopwatch();
					stopwatchValue = 0L;
					updateStopwatchTimeLabel();
					stopwatchStartButton.requestFocus();
				}
			});
		}

		if (stopwatchShowMillisecondsCheckbox != null) {
			stopwatchShowMillisecondsCheckbox.setSelected(ConfigManager.getCurrentConfig().isStopwatchShowMilliseconds());
			setupTimeFormatter();
			updateStopwatchTimeLabel();

			stopwatchShowMillisecondsCheckbox.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(final ActionEvent event) {
					ConfigManager.getCurrentConfig().setStopwatchShowMilliseconds(stopwatchShowMillisecondsCheckbox.isSelected());
					setupTimeFormatter();
					updateStopwatchTimeLabel();
				}
			});
		}
	}

	/**
	 * Startet die Stoppuhr.
	 * @return Zeitobjekt
	 */
	protected TimeDescriptor startStopwatch() {
		stopwatchRunning = true;
		stopwatchStartButton.setVisible(false);
		stopwatchStopButton.setVisible(true);
		stopwatchStopButton.requestFocus();

		return facade.startStopwatch();
	}

	/**
	 * Stoppt die Stoppuhr.
	 */
	protected void stopStopwatch() {
		facade.stopStopwatch();
		stopwatchRunning = false;
		stopwatchStartButton.setVisible(true);
		stopwatchStartButton.requestFocus();
		stopwatchStopButton.setVisible(false);
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
