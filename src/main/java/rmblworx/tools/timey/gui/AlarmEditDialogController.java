package rmblworx.tools.timey.gui;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import rmblworx.tools.timey.gui.component.TimePicker;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * Controller für den Dialog zum Bearbeiten eines Alarms.
 * @see <a href="http://code.makery.ch/java/javafx-2-tutorial-part3/">JavaFX 2 Tutorial - Part 3: Interacting with the User</a>
 * @author Christian Raue {@literal <christian.raue@gmail.com>}
 */
public class AlarmEditDialogController extends Controller {

	/**
	 * Fenster des Dialogs.
	 */
	private Stage dialogStage;

	/**
	 * Bereits existierende Alarme.
	 */
	private List<Alarm> existingAlarms;

	/**
	 * Der zu bearbeitende Alarm.
	 */
	private Alarm alarm;

	/**
	 * Der gewählte Klingelton.
	 */
	private final SimpleStringProperty ringtone = new SimpleStringProperty("");

	/**
	 * Ob der Alarm geändert wurde.
	 */
	private boolean changed = false;

	@FXML
	private ResourceBundle resources;

	@FXML
	private CheckBox alarmEnabledCheckbox;

	@FXML
	private DatePicker alarmDatePicker;

	@FXML
	private TimePicker alarmTimePicker;

	@FXML
	private TextField alarmDescriptionTextField;

	@FXML
	private Button alarmSelectSoundButton;

	@FXML
	private Button alarmNoSoundButton;

	@FXML
	private Button alarmPlaySoundButton;

	@FXML
	private void initialize() {
		assert alarmEnabledCheckbox != null : "fx:id='alarmEnabledCheckbox' was not injected";
		assert alarmDatePicker != null : "fx:id='alarmDatePicker' was not injected";
		assert alarmTimePicker != null : "fx:id='alarmTimePicker' was not injected";
		assert alarmDescriptionTextField != null : "fx:id='alarmDescriptionTextField' was not injected";
		assert alarmSelectSoundButton != null : "fx:id='alarmSelectSoundButton' was not injected";
		assert alarmNoSoundButton != null : "fx:id='alarmNoSoundButton' was not injected";
		assert alarmPlaySoundButton != null : "fx:id='alarmPlaySoundButton' was not injected";

		alarmDatePicker.setPromptText(resources.getString("alarmEdit.datePicker.placeholder"));

		alarmDatePicker.setConverter(new StringConverter<LocalDate>() {
			private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

			public LocalDate fromString(final String string) {
				if (string != null && !string.isEmpty()) {
					return LocalDate.parse(string, dateFormatter);
				}

				return null;
			}

			public String toString(final LocalDate date) {
				if (date != null) {
					return dateFormatter.format(date);
				}

				return "";
			}
		});

		ringtone.addListener(new ChangeListener<String>() {
			public void changed(final ObservableValue<? extends String> property, final String oldValue, final String newValue) {
				alarmSelectSoundButton.setText(newValue == null ? resources.getString("sound.noSoundSelected.text") : newValue);
				alarmSelectSoundButton.setTooltip(newValue == null ? null : new Tooltip(newValue));
				alarmNoSoundButton.setDisable(newValue == null);
				alarmPlaySoundButton.setDisable(newValue == null);
			}
		});

		ringtone.set(null);
	}

	public void setDialogStage(final Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	/**
	 * @param existingAlarms bereits existierende Alarme
	 */
	public void setExistingAlarms(final List<Alarm> existingAlarms) {
		this.existingAlarms = existingAlarms;
	}

	/**
	 * Setzt den Alarm und überträgt dessen Werte auf die Steuerelemente.
	 * @param alarm Alarm
	 */
	public void setAlarm(final Alarm alarm) {
		this.alarm = alarm;

		Platform.runLater(new Runnable() {
			public void run() {
				alarmEnabledCheckbox.setSelected(alarm.isEnabled());
				alarmDatePicker.setValue(DateTimeUtil.getDatePart(alarm.getDateTime()));
				alarmTimePicker.setValue(DateTimeUtil.getTimePart(alarm.getDateTime()));
				alarmDescriptionTextField.setText(alarm.getDescription());
				ringtone.set(alarm.getSound());
			}
		});
	}

	public void setRingtone(final String aRingtone) {
		Platform.runLater(new Runnable() {
			public void run() {
				ringtone.set(aRingtone);
			}
		});
	}

	/**
	 * @return ob der Alarm geändert wurde
	 */
	public final boolean isChanged() {
		return changed;
	}

	/**
	 * Aktion bei Klick auf Sound-Auswahl-Schaltfläche.
	 */
	@FXML
	private void handleSelectSoundButtonClick() {
		final FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(resources.getString("sound.selectSound.title"));
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters().addAll(
			new FileChooser.ExtensionFilter("Sound", "*.aac", "*.aif", "*.aiff", "*.m3u8", "*.m4a", "*.mp3", "*.wav")
		);

		final File file = fileChooser.showOpenDialog(dialogStage);
		if (file != null) {
			ringtone.set(file.getPath());
		}
	}

	/**
	 * Aktion bei Klick auf Sound-Löschen-Schaltfläche.
	 */
	@FXML
	private void handleNoSoundButtonClick() {
		ringtone.set(null);
	}

	/**
	 * Aktion bei Klick auf Sound-Abspielen-Schaltfläche.
	 */
	@FXML
	private void handlePlaySoundButtonClick() {
		getGuiHelper().playSoundInThread(ringtone.get(), resources);
	}

	/**
	 * Aktion bei Klick auf Speichern-Schaltfläche.
	 */
	@FXML
	private void handleSaveButtonClick() {
		if (isInputValid()) {
			alarm.setEnabled(alarmEnabledCheckbox.isSelected());
			alarm.setDateTime(getDateTimeFromPickers());
			alarm.setDescription(alarmDescriptionTextField.getText());
			alarm.setSound(ringtone.get());

			changed = true;

			if (dialogStage != null) {
				dialogStage.close();
			}
		}
	}

	/**
	 * Aktion bei Klick auf Abbrechen-Schaltfläche.
	 */
	@FXML
	private void handleCancelButtonClick() {
		if (dialogStage != null) {
			dialogStage.close();
		}
	}

	/**
	 * @return kombinierter Zeitstempel aus DatePicker und TimePicker
	 */
	private LocalDateTime getDateTimeFromPickers() {
		final LocalDate date = alarmDatePicker.getValue();
		final LocalTime time = alarmTimePicker.getValue();

		return LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), time.getHour(), time.getMinute(), time.getSecond());
	}

	/**
	 * @return ob die Daten gültig sind
	 */
	private boolean isInputValid() {
		final StringBuilder errors = new StringBuilder();

		if (alarmDatePicker.getValue() == null) {
			errors.append(resources.getString("alarmEdit.date.empty"));
			errors.append('\n');
		}

		if (alarmDatePicker.getValue() != null) {
			final LocalDateTime selectedDateTime = getDateTimeFromPickers();

			if (alarmEnabledCheckbox.isSelected() && selectedDateTime.isBefore(LocalDateTime.now())) {
				errors.append(resources.getString("alarmEdit.alarmTimestampMustBeInFuture"));
				errors.append('\n');
			}

			if (existingAlarms != null) {
				for (final Alarm existingAlarm : existingAlarms) {
					if (alarm != existingAlarm && selectedDateTime.equals(existingAlarm.getDateTime())) {
						errors.append(resources.getString("alarmEdit.otherAlarmWithSameTimestampAlreadyExists"));
						errors.append('\n');
						break;
					}
				}
			}
		}

		if (errors.length() > 0) {
			// Fehlermeldungen anzeigen
			getGuiHelper().showDialogMessage(resources.getString("messageDialog.error.title"), errors.toString(), resources);
			return false;
		}

		return true;
	}

}
