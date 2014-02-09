package rmblworx.tools.timey.gui;

import java.awt.SystemTray;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

public class OptionsController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private CheckBox minimizeToTrayCheckbox;

	@FXML
	void initialize() {
		assert minimizeToTrayCheckbox != null : "fx:id='minimizeToTrayCheckbox' was not injected: check your FXML file 'TimeyGui.fxml'.";

		if (minimizeToTrayCheckbox != null) {
			if (!SystemTray.isSupported()) {
				minimizeToTrayCheckbox.setDisable(true);
				minimizeToTrayCheckbox.setSelected(false);
			} else {
				minimizeToTrayCheckbox.setSelected(Config.getInstance().isMinimizeToTray());
			}

			minimizeToTrayCheckbox.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(final ActionEvent event) {
					Config.getInstance().setMinimizeToTray(minimizeToTrayCheckbox.isSelected());
				}
			});
		}
	}

}
