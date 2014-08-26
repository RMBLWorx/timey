package rmblworx.tools.timey.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.loadui.testfx.categories.TestFX;
import org.loadui.testfx.utils.FXTestUtils;

import rmblworx.tools.timey.gui.component.TimePicker;

/*
 * Copyright 2014 Christian Raue
 * MIT License http://opensource.org/licenses/mit-license.php
 */
/**
 * GUI-Tests für den Dialog zum Bearbeiten eines Alarms.
 * @author Christian Raue {@literal <christian.raue@gmail.com>}
 */
@Category(TestFX.class)
public class AlarmEditDialogControllerTest extends FxmlGuiControllerTest {

	/**
	 * Container für Elemente.
	 */
	private Scene scene;

	// GUI-Elemente
	private AlarmEditDialogController controller;
	private CheckBox alarmEnabledCheckbox;
	private DatePicker alarmDatePicker;
	private TimePicker alarmTimePicker;
	private TextField alarmDescriptionTextField;
	private Button alarmSelectSoundButton;
	private Button alarmNoSoundButton;
	private Button alarmPlaySoundButton;
	private Button alarmSaveButton;

	/**
	 * {@inheritDoc}
	 */
	protected final String getFxmlFilename() {
		return "AlarmEditDialog.fxml";
	}

	@Before
	public final void setUp() {
		scene = stage.getScene();

		controller = (AlarmEditDialogController) getController();
		alarmEnabledCheckbox = (CheckBox) scene.lookup("#alarmEnabledCheckbox");
		alarmDatePicker = (DatePicker) scene.lookup("#alarmDatePicker");
		alarmTimePicker = (TimePicker) scene.lookup("#alarmTimePicker");
		alarmDescriptionTextField = (TextField) scene.lookup("#alarmDescriptionTextField");
		alarmSelectSoundButton = (Button) scene.lookup("#alarmSelectSoundButton");
		alarmNoSoundButton = (Button) scene.lookup("#alarmNoSoundButton");
		alarmPlaySoundButton = (Button) scene.lookup("#alarmPlaySoundButton");
		alarmSaveButton = (Button) scene.lookup("#alarmSaveButton");
	}

	/**
	 * Testet den Zustand der Steuerelemente nach Öffnen des Dialogs.
	 */
	@Test
	public final void testInitialState() {
		final TextField hoursTextField = (TextField) scene.lookup("#hoursTextField");

		// Alarm vorgeben
		controller.setAlarm(new Alarm(DateTimeUtil.getLocalDateTimeForString("24.12.2014 12:00:00"), "Test"));
		FXTestUtils.awaitEvents();

		// sicherstellen, dass Formularfelder korrekt gefüllt sind
		assertTrue(alarmEnabledCheckbox.isSelected());
		assertEquals(DateTimeUtil.getLocalDateForString("24.12.2014"), alarmDatePicker.getValue());
		assertEquals(DateTimeUtil.getLocalTimeForString("12:00:00"), alarmTimePicker.getValue());
		assertEquals("12", hoursTextField.getText());
		assertEquals("Test", alarmDescriptionTextField.getText());

		assertEquals("kein Klingelton gewählt", alarmSelectSoundButton.getText());
		assertTrue(alarmNoSoundButton.isDisabled());
		assertTrue(alarmPlaySoundButton.isDisabled());
	}

	/**
	 * Testet das Speichern der Änderungen.
	 */
	@Test
	public final void testApplyChanges() {
		/*
		 * Für Tests nicht setzen, da das Schließen des Dialogs sonst sorgt dafür, dass das Fenster (Stage) für andere Tests nicht mehr zur
		 * Verfügung steht.
		 */
		// controller.setDialogStage(stage);

		// Alarm vorgeben
		final Alarm alarm = new Alarm(DateTimeUtil.getLocalDateTimeForString("01.01.1970 00:00:00"), "Bla");
		controller.setAlarm(alarm);
		FXTestUtils.awaitEvents();

		// deaktivieren
		click(alarmEnabledCheckbox);

		Platform.runLater(new Runnable() {
			public void run() {
				// Datum setzen
				alarmDatePicker.setValue(DateTimeUtil.getLocalDateForString("24.12.2014"));

				// Zeit setzen
				alarmTimePicker.setValue(DateTimeUtil.getLocalTimeForString("12:00:00"));

				// Beschreibung setzen
				alarmDescriptionTextField.setText("Test");
			}
		});
		FXTestUtils.awaitEvents();

		// Sound setzen
		controller.setRingtone("Sound");
		FXTestUtils.awaitEvents();

		// Speichern-Schaltfläche betätigen
		click(alarmSaveButton);

		// TODO auf Schließen des Dialogs warten

		// TODO sicherstellen, dass Dialog geschlossen wurde
		// assertFalse(stage.isShowing());

		// sicherstellen, dass Alarm neue Werte hat
		assertFalse(alarm.isEnabled());
		assertEquals(DateTimeUtil.getLocalDateTimeForString("24.12.2014 12:00:00"), alarm.getDateTime());
		assertEquals("Test", alarm.getDescription());
		assertEquals("Sound", alarm.getSound());
	}

	/**
	 * Testet Anzeige der Fehlermeldung bei leerem Datum.
	 */
	@Test
	public final void testErrorDateEmpty() {
		/*
		 * Für Tests nicht setzen, da das Schließen des Dialogs sonst sorgt dafür, dass das Fenster (Stage) für andere Tests nicht mehr zur
		 * Verfügung steht.
		 */
		// controller.setDialogStage(stage);

		controller.setExistingAlarms(Arrays.asList(new Alarm()));

		// Alarm vorgeben
		controller.setAlarm(new Alarm());
		FXTestUtils.awaitEvents();

		// Datum leeren
		Platform.runLater(new Runnable() {
			public void run() {
				alarmDatePicker.setValue(null);
			}
		});
		FXTestUtils.awaitEvents();

		final GuiHelper guiHelper = mock(GuiHelper.class);
		controller.setGuiHelper(guiHelper);

		// Speichern-Schaltfläche betätigen
		click(alarmSaveButton);

		// sicherstellen, dass Fehlermeldung erscheint
		verify(guiHelper).showDialogMessage(anyString(), eq("Ein Datum muss angegeben werden.\n"), isA(ResourceBundle.class));
	}

	/**
	 * Testet Anzeige bzw. Ausbleiben von Fehlermeldungen beim Speichern eines Alarms.
	 */
	@Test
	public final void testErrorWhenSavingAlarm() {
		/*
		 * Für Tests nicht setzen, da das Schließen des Dialogs sonst sorgt dafür, dass das Fenster (Stage) für andere Tests nicht mehr zur
		 * Verfügung steht.
		 */
		// controller.setDialogStage(stage);

		final List<DataErrors> testCases = new Vector<>();

		// Anlegen/Bearbeiten eines Alarms mit Zeitstempel in Vergangenheit
		testCases.add(new DataErrors(1, "Der Alarm-Zeitpunkt muss in der Zukunft liegen.\n",
				null,
				new Alarm(DateTimeUtil.getLocalDateTimeForString("01.01.1970 00:00:00"), "Alarm")));
		// Anlegen/Bearbeiten eines Alarms mit Zeitstempel in Zukunft
		testCases.add(new DataErrors(0, null,
				null,
				new Alarm(DateTimeUtil.getLocalDateTimeForString("01.01.2050 00:00:00"), "Alarm")));
		// Anlegen/Bearbeiten eines inaktiven Alarms mit Zeitstempel in Vergangenheit
		testCases.add(new DataErrors(0, null,
				null,
				new Alarm(DateTimeUtil.getLocalDateTimeForString("01.01.1970 00:00:00"), "Alarm", null, false)));
		// Anlegen/Bearbeiten eines inaktiven Alarms mit Zeitstempel in Zukunft
		testCases.add(new DataErrors(0, null,
				null,
				new Alarm(DateTimeUtil.getLocalDateTimeForString("01.01.2050 00:00:00"), "Alarm", null, false)));

		// Anlegen eines Alarms mit identischem Zeitstempel
		testCases.add(new DataErrors(1, "Ein anderer Alarm mit demselben Zeitpunkt existiert bereits.\n",
				Arrays.asList(new Alarm(DateTimeUtil.getLocalDateTimeForString("01.01.2050 00:00:00"), "Alarm1")),
				new Alarm(DateTimeUtil.getLocalDateTimeForString("01.01.2050 00:00:00"), "Alarm2")));
		// Anlegen eines Alarms mit unterschiedlichem Zeitstempel
		testCases.add(new DataErrors(0, null,
				Arrays.asList(new Alarm(DateTimeUtil.getLocalDateTimeForString("01.01.2050 00:00:00"), "Alarm1")),
				new Alarm(DateTimeUtil.getLocalDateTimeForString("11.11.2160 00:00:00"), "Alarm2")));
		// Bearbeiten eines Alarms ohne Änderung des Zeitstempels
		final Alarm alarm = new Alarm(DateTimeUtil.getLocalDateTimeForString("01.01.2050 00:00:00"), "Alarm1"); // genau ein Objekt
		testCases.add(new DataErrors(0, null,
				Arrays.asList(alarm),
				alarm));

		for (final DataErrors testCase : testCases) {
			controller.setExistingAlarms(testCase.existingAlarms);
			controller.setAlarm(testCase.alarm);
			FXTestUtils.awaitEvents();

			final GuiHelper guiHelper = mock(GuiHelper.class);
			controller.setGuiHelper(guiHelper);

			// Speichern-Schaltfläche betätigen
			click(alarmSaveButton);

			// sicherstellen, dass Fehlermeldung erscheint bzw. nicht
			verify(guiHelper, times(testCase.numberOfCalls)).showDialogMessage(anyString(),
					testCase.errorMessage == null ? anyString() : eq(testCase.errorMessage), isA(ResourceBundle.class));
		}
	}

	/**
	 * Testet das Verwerfen der Änderungen.
	 */
	@Test
	public final void testCancel() {
		final Button alarmCancelButton = (Button) scene.lookup("#alarmCancelButton");

		/*
		 * Für Tests nicht setzen, da das Schließen des Dialogs sonst sorgt dafür, dass das Fenster (Stage) für andere Tests nicht mehr zur
		 * Verfügung steht.
		 */
		// controller.setDialogStage(stage);

		// Alarm vorgeben
		final LocalDateTime now = LocalDateTime.now();
		final Alarm alarm = new Alarm(now, "bla");
		controller.setAlarm(alarm);
		FXTestUtils.awaitEvents();

		// deaktivieren
		click(alarmEnabledCheckbox);

		Platform.runLater(new Runnable() {
			public void run() {
				// Datum setzen
				alarmDatePicker.setValue(DateTimeUtil.getLocalDateForString("24.12.2014"));

				// Zeit setzen
				alarmTimePicker.setValue(DateTimeUtil.getLocalTimeForString("12:00:00"));

				// Beschreibung setzen
				alarmDescriptionTextField.setText("Test");
			}
		});
		FXTestUtils.awaitEvents();

		// Sound setzen
		controller.setRingtone("Sound");
		FXTestUtils.awaitEvents();

		// Abbrechen-Schaltfläche betätigen
		click(alarmCancelButton);

		// TODO auf Schließen des Dialogs warten

		// TODO sicherstellen, dass Dialog geschlossen wurde
		// assertFalse(stage.isShowing());

		// sicherstellen, dass Alarm ursprüngliche Werte hat
		assertTrue(alarm.isEnabled());
		assertEquals(now, alarm.getDateTime());
		assertEquals("bla", alarm.getDescription());
		assertEquals(null, alarm.getSound());
	}

	/**
	 * Testet das Auswählen, Anhören und Löschen eines Klingeltons.
	 */
	@Test
	public final void testSoundSelection() {
		/*
		 * Für Tests nicht setzen, da das Schließen des Dialogs sonst sorgt dafür, dass das Fenster (Stage) für andere Tests nicht mehr zur
		 * Verfügung steht.
		 */
		// controller.setDialogStage(stage);

		// Alarm vorgeben
		final Alarm alarm = new Alarm(DateTimeUtil.getLocalDateTimeForString("01.01.1970 00:00:00"), "Bla");
		controller.setAlarm(alarm);
		FXTestUtils.awaitEvents();

		// Sound setzen
		controller.setRingtone("Sound");
		FXTestUtils.awaitEvents();

		assertFalse(alarmNoSoundButton.isDisabled());
		assertFalse(alarmPlaySoundButton.isDisabled());

		// Sound-Abspielen-Schaltfläche betätigen
		final AudioPlayer player = mock(AudioPlayer.class);
		controller.getGuiHelper().setAudioPlayer(player);
		click(alarmPlaySoundButton);
		verify(player).playInThread(isA(ThreadHelper.class), eq("Sound"), isA(Thread.UncaughtExceptionHandler.class));

		// Sound-Löschen-Schaltfläche betätigen
		click(alarmNoSoundButton);

		assertEquals("kein Klingelton gewählt", alarmSelectSoundButton.getText());
	}

	private final class DataErrors {

		public final int numberOfCalls;
		public final String errorMessage;
		public final List<Alarm> existingAlarms;
		public final Alarm alarm;

		public DataErrors(final int numberOfCalls, final String messageMatcher, final List<Alarm> existingAlarms, final Alarm alarm) {
			this.numberOfCalls = numberOfCalls;
			this.errorMessage = messageMatcher;
			this.existingAlarms = existingAlarms;
			this.alarm = alarm;
		}

	}

}
