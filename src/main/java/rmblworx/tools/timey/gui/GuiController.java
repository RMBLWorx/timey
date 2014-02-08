package rmblworx.tools.timey.gui;

import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import rmblworx.tools.timey.TimeyFacade;
import rmblworx.tools.timey.vo.TimeDescriptor;

public class GuiController {

	private TimeyFacade facade = new TimeyFacade();

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
	private CheckBox includeMillisecondsCheckbox;

	private boolean stopwatchRunning = false;

	@FXML
	void initialize() {
		assert stopwatchStartButton != null : "fx:id='stopwatchStartButton' was not injected: check your FXML file 'TimeyGui.fxml'.";
		assert stopwatchStopButton != null : "fx:id='stopwatchStopButton' was not injected: check your FXML file 'TimeyGui.fxml'.";
		assert stopwatchResetButton != null : "fx:id='stopwatchResetButton' was not injected: check your FXML file 'TimeyGui.fxml'.";
		assert stopwatchTimeLabel != null : "fx:id='stopwatchTimeLabel' was not injected: check your FXML file 'TimeyGui.fxml'.";
		assert includeMillisecondsCheckbox != null : "fx:id='includeMillisecondsCheckbox' was not injected: check your FXML file 'TimeyGui.fxml'.";

		if (stopwatchStartButton != null) {
			stopwatchStartButton.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					System.out.println(resources.getString("stopwatchStartButton.pressed")); // TODO entfernen

					stopwatchStartButton.setVisible(false);
					stopwatchStopButton.setVisible(true);

					stopwatchRunning = true;
					final boolean includeMilliseconds = includeMillisecondsCheckbox.isSelected();

					final TimeDescriptor td = facade.startStopwatch();

					Task <Void> task = new Task<Void>() {
						public Void call() throws InterruptedException {
							SimpleDateFormat formatter = new SimpleDateFormat();

							if (includeMilliseconds) {
								formatter.applyPattern("HH:mm:ss.SSS");
							} else {
								formatter.applyPattern("HH:mm:ss");
							}

							while (stopwatchRunning) {
								updateMessage(formatter.format(td.getMilliSeconds()));
								if (includeMilliseconds) {
									Thread.sleep(5);
								} else {
									Thread.sleep(1000);
								}
							}

							return null;
						}
					};

					stopwatchTimeLabel.textProperty().bind(task.messageProperty());

					EventHandler<WorkerStateEvent> unbindLabel = new EventHandler<WorkerStateEvent>() {
						public void handle(WorkerStateEvent event) {
							stopwatchTimeLabel.textProperty().unbind();
						}
					};

					task.setOnSucceeded(unbindLabel);
					task.setOnFailed(unbindLabel);
					task.setOnCancelled(unbindLabel);

					Thread thread = new Thread(task);
					thread.setDaemon(true);
					thread.setPriority(Thread.MIN_PRIORITY);
					thread.start();
				}
			});
		}

		if (stopwatchStopButton != null) {
			stopwatchStopButton.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					System.out.println(resources.getString("stopwatchStopButton.pressed")); // TODO entfernen

					stopwatchRunning = false;
					stopwatchStartButton.setVisible(true);
					stopwatchStopButton.setVisible(false);
				}
			});
		}

		if (stopwatchResetButton != null) {
			stopwatchResetButton.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					System.out.println(resources.getString("stopwatchResetButton.pressed")); // TODO entfernen

					resetStopwatchTimeLabel();
				}
			});
		}

		if (includeMillisecondsCheckbox != null) {
			includeMillisecondsCheckbox.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					resetStopwatchTimeLabel();
				}
			});
		}
	}

	protected void resetStopwatchTimeLabel() {
		if (stopwatchRunning) {
			return;
		}

		stopwatchTimeLabel.setText(includeMillisecondsCheckbox.isSelected() ? "00:00:00.000" : "00:00:00");
	}

}
