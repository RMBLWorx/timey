package rmblworx.tools.timey.gui;

import java.awt.SystemTray;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;
import rmblworx.tools.timey.gui.config.Config;
import rmblworx.tools.timey.gui.config.ConfigManager;

/**
 * Controller für die Optionen-GUI.
 * 
 * @author Christian Raue <christian.raue@gmail.com>
 * @copyright 2014 Christian Raue
 * @license http://opensource.org/licenses/mit-license.php MIT License
 */
public class OptionsController extends Controller {

	@FXML
	private ResourceBundle resources;

	@FXML
	private CheckBox minimizeToTrayCheckbox;

	@FXML
	private ComboBox<Locale> languageChoice;

	@FXML
	private void initialize() {
		assert minimizeToTrayCheckbox != null : "fx:id='minimizeToTrayCheckbox' was not injected";
		assert languageChoice != null : "fx:id='languageChoice' was not injected";

		if (minimizeToTrayCheckbox != null) {
			if (!SystemTray.isSupported()) {
				minimizeToTrayCheckbox.setDisable(true);
				minimizeToTrayCheckbox.setSelected(false);
			} else {
				minimizeToTrayCheckbox.setSelected(ConfigManager.getCurrentConfig().isMinimizeToTray());
			}
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

			languageChoice.getItems().setAll(Arrays.asList(Config.AVAILABLE_LOCALES));
			languageChoice.setValue(ConfigManager.getCurrentConfig().getLocale());

			languageChoice.valueProperty().addListener(new ChangeListener<Locale>() {
				public void changed(final ObservableValue<? extends Locale> property, final Locale oldValue, final Locale newValue) {
					ConfigManager.getCurrentConfig().setLocale(newValue);
					Platform.runLater(new Runnable() {
						public void run() {
							getGuiHelper().showDialogMessage(resources.getString("messageDialog.languageChoice.title"),
									resources.getString("messageDialog.languageChoice.text"), resources);
						}
					});
				}
			});
		}
	}

	/**
	 * Aktion bei Klick auf Tray-Checkbox.
	 */
	@FXML
	private void handleMinimizeToTrayCheckboxClick() {
		ConfigManager.getCurrentConfig().setMinimizeToTray(minimizeToTrayCheckbox.isSelected());
	}

}
