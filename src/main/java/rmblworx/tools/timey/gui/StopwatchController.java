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
import rmblworx.tools.timey.TimeyFacade;
import rmblworx.tools.timey.vo.TimeDescriptor;

public class StopwatchController {

	private TimeyFacade facade = new TimeyFacade();
	private SimpleDateFormat dateFormatter;

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

	private boolean stopwatchRunning = false;
	private long stopwatchValue;

	@FXML
	void initialize() {
		assert stopwatchStartButton != null : "fx:id='stopwatchStartButton' was not injected: check your FXML file 'TimeyGui.fxml'.";
		assert stopwatchStopButton != null : "fx:id='stopwatchStopButton' was not injected: check your FXML file 'TimeyGui.fxml'.";
		assert stopwatchResetButton != null : "fx:id='stopwatchResetButton' was not injected: check your FXML file 'TimeyGui.fxml'.";
		assert stopwatchTimeLabel != null : "fx:id='stopwatchTimeLabel' was not injected: check your FXML file 'TimeyGui.fxml'.";
		assert stopwatchShowMillisecondsCheckbox != null : "fx:id='stopwatchShowMillisecondsCheckbox' was not injected: check your FXML file 'TimeyGui.fxml'.";

		if (stopwatchStartButton != null) {
			stopwatchStartButton.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(final ActionEvent event) {
					if (stopwatchRunning) {
						return;
					}

					stopwatchRunning = true;
					stopwatchStartButton.setVisible(false);
					stopwatchStopButton.setVisible(true);

					final Config config = Config.getInstance();
					final TimeDescriptor td = facade.startStopwatch();

					final Task<Void> task = new Task<Void>() {
						private static final long SLEEP_TIME_FINE_GRAINED = 5L;
						private static final long SLEEP_TIME_COARSE_GRAINED = 1000L;

						public Void call() throws InterruptedException {
							while (stopwatchRunning) {
								stopwatchValue = td.getMilliSeconds();
								updateMessage(dateFormatter.format(stopwatchValue));
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

					facade.stopStopwatch();
					stopwatchRunning = false;
					stopwatchStartButton.setVisible(true);
					stopwatchStopButton.setVisible(false);
				}
			});
		}

		if (stopwatchResetButton != null) {
			stopwatchResetButton.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(final ActionEvent event) {
					facade.resetStopwatch();
					stopwatchValue = 0L;
					resetStopwatchTimeLabel();
				}
			});
		}

		if (stopwatchShowMillisecondsCheckbox != null) {
			stopwatchShowMillisecondsCheckbox.setSelected(Config.getInstance().isStopwatchShowMilliseconds());
			setupDateFormatter();
			resetStopwatchTimeLabel();

			stopwatchShowMillisecondsCheckbox.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(final ActionEvent event) {
					Config.getInstance().setStopwatchShowMilliseconds(stopwatchShowMillisecondsCheckbox.isSelected());
					setupDateFormatter();
					resetStopwatchTimeLabel();
				}
			});
		}
	}

	private void setupDateFormatter() {
		if (dateFormatter == null) {
			dateFormatter = new SimpleDateFormat();
			dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		}

		dateFormatter.applyPattern(Config.getInstance().isStopwatchShowMilliseconds() ? "HH:mm:ss.SSS" : "HH:mm:ss");
	}

	private void resetStopwatchTimeLabel() {
		if (!stopwatchRunning) {
			Platform.runLater(new Runnable() {
				public void run() {
					stopwatchTimeLabel.setText(dateFormatter.format(stopwatchValue));
				}
			});
		}
	}

}
