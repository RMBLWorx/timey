package rmblworx.tools.timey.gui;

import java.awt.SystemTray;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

public class OptionsController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private CheckBox minimizeToTrayCheckbox;

	@FXML
	private ComboBox<Locale> languageChoice;

	@FXML
	void initialize() {
		assert minimizeToTrayCheckbox != null : "fx:id='minimizeToTrayCheckbox' was not injected: check your FXML file 'TimeyGui.fxml'.";
		assert languageChoice != null : "fx:id='languageChoice' was not injected: check your FXML file 'TimeyGui.fxml'.";

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

		if (languageChoice != null) {
			languageChoice.setConverter(new StringConverter<Locale>() {
				public String toString(final Locale locale) {
					return locale.getDisplayName();
				}

				public Locale fromString(final String string) {
					throw new UnsupportedOperationException();
				}
			});

			languageChoice.getItems().setAll(Arrays.asList(Main.AVAILABLE_LOCALES));
			languageChoice.setValue(Config.getInstance().getLocale());

			languageChoice.valueProperty().addListener(new ChangeListener<Locale>() {
				public void changed(final ObservableValue<? extends Locale> property, final Locale oldValue, final Locale newValue) {
					Config.getInstance().setLocale(newValue);
					// TODO Meldung anzeigen, dass Neustart der Anwendung nötig ist
				}
			});
		}
	}

}
