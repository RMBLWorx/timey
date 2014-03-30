package rmblworx.tools.timey.gui;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.FileChooser;
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
public class AlarmEditDialogController extends Controller {

	/**
	 * Formatiert Zeitstempel als Datum-Werte.
	 */
	private SimpleDateFormat dateFormatter;

	/**
	 * Spielt Sounds ab.
	 */
	private AudioPlayer audioPlayer = new AudioPlayer();

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
	private SimpleStringProperty ringtone = new SimpleStringProperty("");

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

		ringtone.addListener(new ChangeListener<String>() {
			public void changed(final ObservableValue<? extends String> property, final String oldValue, final String newValue) {
				alarmSelectSoundButton.setText(newValue != null ? newValue : resources.getString("sound.noSoundSelected.text"));
				alarmSelectSoundButton.setTooltip(newValue != null ? new Tooltip(newValue) : null);
				alarmNoSoundButton.setDisable(newValue == null);
				alarmPlaySoundButton.setDisable(newValue == null);
			}
		});

		ringtone.set(null);
	}

	public void setAudioPlayer(final AudioPlayer audioPlayer) {
		this.audioPlayer = audioPlayer;
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

		alarmEnabledCheckbox.setSelected(alarm.isEnabled());
		alarmDatePicker.setValue(DateTimeUtil.getDatePart(alarm.getDateTime()));
		alarmTimePicker.setTime(DateTimeUtil.getTimePart(alarm.getDateTime()));
		alarmDescriptionTextField.setText(alarm.getDescription());
		ringtone.set(alarm.getSound());
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
		Platform.runLater(new Runnable() {
			public void run() {
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
		});
	}

	/**
	 * Aktion bei Klick auf Sound-Löschen-Schaltfläche.
	 */
	@FXML
	private void handleNoSoundButtonClick() {
		Platform.runLater(new Runnable() {
			public void run() {
				ringtone.set(null);
			}
		});
	}

	/**
	 * Aktion bei Klick auf Sound-Abspielen-Schaltfläche.
	 */
	@FXML
	private void handlePlaySoundButtonClick() {
		Platform.runLater(new Runnable() {
			public void run() {
				audioPlayer.playInThread(ringtone.get(), new Thread.UncaughtExceptionHandler() {
					public void uncaughtException(final Thread thread, final Throwable exception) {
						Platform.runLater(new Runnable() {
							public void run() {
								getGuiHelper().showDialogMessage(resources.getString("messageDialog.error.title"),
										String.format(resources.getString("sound.play.error"), exception.getLocalizedMessage()),
										resources);
							}
						});
					}
				});
			}
		});
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
					alarm.setDateTime(getDateTimeFromPickers());
					alarm.setDescription(alarmDescriptionTextField.getText());
					alarm.setSound(ringtone.get());

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
	 * @return kombinierter Zeitstempel aus DatePicker und TimePicker
	 */
	private Calendar getDateTimeFromPickers() {
		final Calendar dateTime = (Calendar) alarmDatePicker.getValue().clone();
		dateTime.setTimeInMillis(DateTimeUtil.getDatePart(alarmDatePicker.getValue()).getTimeInMillis()
				+ dateTime.getTimeZone().getDSTSavings()
				+ DateTimeUtil.getTimePart(alarmTimePicker.getTime()).getTimeInMillis());

		return dateTime;
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

		if (existingAlarms != null) {
			for (final Alarm existingAlarm : existingAlarms) {
				if (alarm != existingAlarm && getDateTimeFromPickers().getTime().equals(existingAlarm.getDateTime().getTime())) {
					errors.append(resources.getString("alarmEdit.otherAlarmWithSameTimestampAlreadyExists"));
					errors.append("\n");
					break;
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

	/**
	 * Initialisiert den Datums-Formatierer.
	 */
	private void setupDateFormatter() {
		if (dateFormatter == null) {
			dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
		}
	}

}
