package rmblworx.tools.timey.gui;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;
import jfxtras.labs.scene.control.CalendarTextField;
import rmblworx.tools.timey.gui.component.TimePicker;
import rmblworx.tools.timey.gui.config.ConfigManager;

/**
 * Controller für den Dialog zum Bearbeiten eines Alarms.
 * @see http://edu.makery.ch/blog/2012/11/20/javafx-tutorial-addressapp-3/
 * 
 * @author Christian Raue <christian.raue@gmail.com>
 * @copyright 2014 Christian Raue
 * @license http://opensource.org/licenses/mit-license.php MIT License
 */
public class AlarmEditDialogController {

	/**
	 * Formatiert Zeitstempel als Datum-Werte.
	 */
	private SimpleDateFormat dateFormatter;

	/**
	 * Fenster des Dialogs.
	 */
	private Stage dialogStage;

	/**
	 * Der zu bearbeitende Alarm.
	 */
	private Alarm alarm;

	/**
	 * Ob der Alarm geändert wurde.
	 */
	private boolean changed = false;

	@FXML
	private ResourceBundle resources;

	@FXML
	private CheckBox alarmEnabledCheckbox;

	@FXML
	private CalendarTextField alarmDatePicker;

	@FXML
	private TimePicker alarmTimePicker;

	@FXML
	private TextField alarmDescriptionTextField;

	@FXML
	private void initialize() {
		assert alarmEnabledCheckbox != null : "fx:id='alarmEnabledCheckbox' was not injected";
		assert alarmDatePicker != null : "fx:id='alarmDatePicker' was not injected";
		assert alarmTimePicker != null : "fx:id='alarmTimePicker' was not injected";
		assert alarmDescriptionTextField != null : "fx:id='alarmDescriptionTextField' was not injected";

		setupDateFormatter();

		alarmDatePicker.setDateFormat(dateFormatter);
		alarmDatePicker.setLocale(ConfigManager.getCurrentConfig().getLocale());
		alarmDatePicker.setPromptText(resources.getString("alarmEdit.datePicker.placeholder"));
		alarmDatePicker.setParseErrorCallback(new Callback<Throwable, Void>() {
			public Void call(final Throwable error) {
				System.err.println(error.getLocalizedMessage());
				return null;
			}
		});
	}

	public void setDialogStage(final Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	/**
	 * Setzt den Alarm und überträgt dessen Werte auf die Steuerelemente.
	 * @param alarm Alarm
	 */
	public void setAlarm(final Alarm alarm) {
		this.alarm = alarm;

		alarmEnabledCheckbox.setSelected(alarm.isEnabled());
		alarmDatePicker.setValue(DateTimeUtil.getDatePart(alarm.getDateTime()));
		alarmTimePicker.setTime(DateTimeUtil.getTimePart(alarm.getDateTime()));
		alarmDescriptionTextField.setText(alarm.getDescription());
	}

	/**
	 * @return ob der Alarm geändert wurde
	 */
	public final boolean isChanged() {
		return changed;
	}

	/**
	 * Aktion bei Klick auf Speichern-Schaltfläche.
	 */
	@FXML
	private void handleSaveButtonClick() {
		Platform.runLater(new Runnable() {
			public void run() {
				if (isInputValid()) {
					alarm.setEnabled(alarmEnabledCheckbox.isSelected());

					final Calendar dateTime = (Calendar) alarmDatePicker.getValue().clone();
					dateTime.setTimeInMillis(DateTimeUtil.getDatePart(alarmDatePicker.getValue()).getTimeInMillis()
							+ dateTime.getTimeZone().getDSTSavings()
							+ DateTimeUtil.getTimePart(alarmTimePicker.getTime()).getTimeInMillis());
					alarm.setDateTime(dateTime);

					alarm.setDescription(alarmDescriptionTextField.getText());

					changed = true;

					if (dialogStage != null) {
						dialogStage.close();
					}
				}
			}
		});
	}

	/**
	 * Aktion bei Klick auf Abbrechen-Schaltfläche.
	 */
	@FXML
	private void handleCancelButtonClick() {
		Platform.runLater(new Runnable() {
			public void run() {
				if (dialogStage != null) {
					dialogStage.close();
				}
			}
		});
	}

	/**
	 * @return ob die Daten gültig sind
	 */
	private boolean isInputValid() {
		final StringBuilder errors = new StringBuilder();

		if (alarmDatePicker.getValue() == null) {
			errors.append(resources.getString("alarmEdit.date.empty"));
			errors.append("\n");
		}

		if (errors.length() > 0) {
			// Fehlermeldungen anzeigen
			new GuiHelper().showMessageDialog(resources.getString("messageDialog.error.title"), errors.toString(), resources);
			return false;
		}

		return true;
	}

	/**
	 * Initialisiert den Datums-Formatierer.
	 */
	private void setupDateFormatter() {
		if (dateFormatter == null) {
			dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
		}
	}

}
