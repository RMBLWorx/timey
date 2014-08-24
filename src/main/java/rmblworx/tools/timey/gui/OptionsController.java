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
import javafx.scene.control.Label;
import javafx.util.StringConverter;
import rmblworx.tools.timey.gui.config.Config;
import rmblworx.tools.timey.gui.config.ConfigManager;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Controller für die Optionen-GUI.
 * @author Christian Raue {@literal <christian.raue@gmail.com>}
 */
public class OptionsController extends Controller {

	@FXML
	private ResourceBundle resources;

	@FXML
	private CheckBox minimizeToTrayCheckbox;

	@FXML
	private ComboBox<Locale> languageChoice;

	@FXML
	private Label appVersionLabel;

	@FXML
	private void initialize() {
		if (!SystemTray.isSupported()) {
			minimizeToTrayCheckbox.setDisable(true);
			minimizeToTrayCheckbox.setSelected(false);
		} else {
			minimizeToTrayCheckbox.setSelected(ConfigManager.getCurrentConfig().isMinimizeToTray());
		}

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
				final ResourceBundle i18nNewLocale = getGuiHelper().getResourceBundle(newValue);
				getGuiHelper().showDialogMessage(i18nNewLocale.getString("messageDialog.languageChoice.title"),
						i18nNewLocale.getString("messageDialog.languageChoice.text"), i18nNewLocale);
			}
		});

		Platform.runLater(new Runnable() {
			public void run() {
				appVersionLabel.setText(getGuiHelper().getFacade().getVersion());
			}
		});
	}

	/**
	 * Aktion bei Klick auf Tray-Checkbox.
	 */
	@FXML
	private void handleMinimizeToTrayCheckboxClick() {
		ConfigManager.getCurrentConfig().setMinimizeToTray(minimizeToTrayCheckbox.isSelected());
	}

}
