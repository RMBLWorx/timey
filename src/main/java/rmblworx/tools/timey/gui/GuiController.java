package rmblworx.tools.timey.gui;

import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class GuiController {

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
	void initialize() {
		assert stopwatchStartButton != null : "fx:id='stopwatchStartButton' was not injected: check your FXML file 'TimeyGui.fxml'.";
		assert stopwatchStopButton != null : "fx:id='stopwatchStopButton' was not injected: check your FXML file 'TimeyGui.fxml'.";
		assert stopwatchResetButton != null : "fx:id='stopwatchResetButton' was not injected: check your FXML file 'TimeyGui.fxml'.";
		assert stopwatchTimeLabel != null : "fx:id='stopwatchTimeLabel' was not injected: check your FXML file 'TimeyGui.fxml'.";

		if (stopwatchStartButton != null) {
			stopwatchStartButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					System.out.println(resources.getString("stopwatchStartButton.pressed"));
					stopwatchStartButton.setVisible(false);
					stopwatchStopButton.setVisible(true);
				}
			});
		}

		if (stopwatchStopButton != null) {
			stopwatchStopButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					System.out.println(resources.getString("stopwatchStopButton.pressed"));
					stopwatchStartButton.setVisible(true);
					stopwatchStopButton.setVisible(false);
				}
			});
		}

		if (stopwatchResetButton != null) {
			stopwatchResetButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					System.out.println(resources.getString("stopwatchResetButton.pressed"));
					stopwatchTimeLabel.setText("00:00:00");
				}
			});
		}
	}

}
